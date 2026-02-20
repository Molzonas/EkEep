package fr.molzonas.ekeep.database.internal;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.database.Database;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import fr.molzonas.ekeep.database.DatabaseException;
import fr.molzonas.ekeep.database.provider.DbProvider;
import fr.molzonas.ekeep.database.provider.Providers;
import fr.molzonas.ekeep.exception.ErrorTranslator;
import fr.molzonas.ekeep.logging.DBQueryLogger;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.jooq.DSLContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

import java.util.Objects;

public final class DatabaseBootstrap {
    private DatabaseBootstrap() {}

    public static Database create(EkEep.BaseContext context, DatabaseConfiguration config) throws DatabaseException {
        Objects.requireNonNull(context, "context");
        Objects.requireNonNull(config, "config");

        DbProvider provider;
        try {
            provider = Providers.selectOrThrow(config.getType());
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.Step.PROVIDER_SELECT, "Failed to select a database provider for type " + config.getType(), e);
        }

        HikariDataSource dataSource = null;

        try {
            dataSource = createDataSource(config, provider);
            migrate(context, config, dataSource, provider);
            DSLContext dsl = createDsl(provider, dataSource);

            try {
                dsl.selectOne().fetchOne();
            } catch (Exception e) {
                throw new DatabaseException(DatabaseException.Step.PING, "Failed to test the database connection", e);
            }

            context.logger().info("Database initialisation successful with provider " + provider.getClass().getSimpleName());
            return new DatabaseImpl(dsl, dataSource);

        } catch (DatabaseException dbException) {
            safeClose(dataSource);
            throw dbException;
        } catch (Exception e) {
            safeClose(dataSource);
            throw new DatabaseException(DatabaseException.Step.UNKNOWN, "An unexpected error occurred during database initialization", e);
        }
    }

    private static HikariDataSource createDataSource(DatabaseConfiguration config, DbProvider provider) throws DatabaseException {
        try {
            HikariConfig hcConfig = new HikariConfig();

            hcConfig.setPoolName("EkEep-" + config.getType().toLowerCase());
            hcConfig.setMaximumPoolSize(config.getMaximumPoolSize());
            hcConfig.setMinimumIdle(config.getMinimumIdle());
            hcConfig.setConnectionTimeout(config.getConnectionTimeout());
            hcConfig.setIdleTimeout(config.getIdleTimeout());
            hcConfig.setMaxLifetime(config.getMaximumLifetime());

            hcConfig.setDriverClassName(provider.getDriverClassName());
            hcConfig.setJdbcUrl(provider.jdbcUrl(config));
            if (!config.isSQLite()) {
                hcConfig.setUsername(config.getUsername());
                hcConfig.setPassword(config.getPassword());
            }
            provider.tune(hcConfig, config);
            return new HikariDataSource(hcConfig);
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.Step.DATASOURCE_CREATE, "Failed to initialize the data source", e);
        }
    }

    private static void migrate(EkEep.BaseContext context, DatabaseConfiguration config, HikariDataSource dataSource, DbProvider provider) throws DatabaseException {
        try {
            Flyway flyway = Flyway.configure()
                    .dataSource(dataSource)
                    .locations(provider.migrationLocation())
                    .baselineOnMigrate(config.isBaselineOnMigrate())
                    .load();

            flyway.migrate();

            MigrationInfoService info = flyway.info();
            if (info.current() != null) {
                context.logger().info("DB schema at version " + info.current().getVersion() + " - " + info.current().getDescription());
            } else {
                context.logger().info("DB schema is not versionned");
            }
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.Step.FLYWAY_MIGRATE, "Failed to migrate the database schema", e);
        }
    }

    private static DSLContext createDsl(DbProvider provider, HikariDataSource dataSource) throws DatabaseException {
        try {
            Settings settings = new Settings()
                    .withExecuteLogging(false)
                    .withRenderQuotedNames(RenderQuotedNames.NEVER)
                    .withParamType(ParamType.INDEXED);

            return DSL.using(dataSource, provider.dialect(), settings)
                    .configuration()
                    .set(new DBQueryLogger())
                    .set(new ErrorTranslator())
                    .dsl();
        } catch (Exception e) {
            throw new DatabaseException(DatabaseException.Step.JOOQ_INIT, "Failed to create the Jooq provider", e);
        }

    }

    private static void safeClose(HikariDataSource dataSource) {
        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception ignored) {}
        }
    }
}
