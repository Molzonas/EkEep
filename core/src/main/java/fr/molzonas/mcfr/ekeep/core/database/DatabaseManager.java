package fr.molzonas.mcfr.ekeep.core.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.molzonas.mcfr.ekeep.api.exceptions.DatabaseInitException;
import fr.molzonas.mcfr.ekeep.api.exceptions.enums.DatabaseInitExceptionStep;
import fr.molzonas.mcfr.ekeep.core.Ekeep;
import fr.molzonas.mcfr.ekeep.core.configs.Config;
import fr.molzonas.mcfr.ekeep.core.configs.DatabaseConfiguration;
import fr.molzonas.mcfr.ekeep.core.configs.base.TypeRef;
import fr.molzonas.mcfr.ekeep.core.configs.keys.DBConfigKeys;
import fr.molzonas.mcfr.ekeep.core.database.base.BaseProvider;
import fr.molzonas.mcfr.ekeep.core.database.impl.PlayerDatabase;
import fr.molzonas.mcfr.ekeep.core.database.impl.TeamDatabase;
import fr.molzonas.mcfr.ekeep.core.database.providers.MariaDBBaseProvider;
import fr.molzonas.mcfr.ekeep.core.database.providers.MySQLDBBaseProvider;
import fr.molzonas.mcfr.ekeep.core.database.providers.PostgreSQLBaseProvider;
import fr.molzonas.mcfr.ekeep.core.database.providers.SQLiteBaseProvider;
import fr.molzonas.mcfr.ekeep.core.utils.EKUtils;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.MigrationInfoService;
import org.jooq.DSLContext;
import org.jooq.conf.ParamType;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public class DatabaseManager {
    private static final int LEAK_DETECTION_THRESHOLD = 15000;
    private static DatabaseManager instance;
    private final DatabaseConfiguration config;
    private HikariDataSource dataSource;
    private PlayerDatabase playerDatabase;
    private TeamDatabase teamDatabase;
    private boolean up = false;

    public DatabaseManager(DatabaseConfiguration config) {
        this.config = config;
    }

    public static DatabaseManager init(DatabaseConfiguration config) {
        instance = new DatabaseManager(config);
        return instance;
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public PlayerDatabase getPlayerDatabase() {
        return playerDatabase;
    }

    public TeamDatabase getTeamDatabase() {
        return teamDatabase;
    }

    public DatabaseManager load() throws DatabaseInitException {
        BaseProvider provider;
        DSLContext dslContext;
        Config ekConfig = Ekeep.getInstance().getMainConfig();
        String type = ekConfig.get(DBConfigKeys.TYPE, new TypeRef<String>() {});
        provider = getProvider(type);
        if (provider == null) {
            throw new DatabaseInitException(DatabaseInitExceptionStep.PROVIDER_INITIALIZATION, "Provider not found");
        }

        BaseProvider.ProviderResult pr = provider.init(config);
        if (!pr.isOk()) {
            EKUtils.error("An error as been found in database initialization :");
            EKUtils.error(pr.message());
            if (pr.e() != null) {
                EKUtils.debug(pr.e().getMessage());
                throw new DatabaseInitException(DatabaseInitExceptionStep.PROVIDER_INITIALIZATION, pr.message(), pr.e());
            } else {
                throw new DatabaseInitException(DatabaseInitExceptionStep.PROVIDER_INITIALIZATION, pr.message());
            }
        }

        HikariConfig hc = new HikariConfig();
        hc.setDataSourceClassName(provider.getDatasourceClassName());
        hc.setJdbcUrl(provider.jdbcUrl(config));
        if (!config.isSQLite()) {
            hc.setUsername(config.getUsername());
            hc.setPassword(config.getPassword());
        }
        hc.setMaximumPoolSize(config.getMaximumPoolSize());
        hc.setMinimumIdle(config.getMinimumIdle());
        hc.setConnectionTimeout(config.getConnectionTimeout());
        hc.setIdleTimeout(config.getIdleTimeout());
        hc.setMaxLifetime(config.getMaximumLifetime());
        provider.tune(hc, config);
        hc.setLeakDetectionThreshold(LEAK_DETECTION_THRESHOLD);
        hc.setPoolName("MZCore-" + config.getType());
        hc.setConnectionInitSql("SET NAMES utf8mb4");
        this.dataSource = new HikariDataSource(hc);

        EKUtils.debug("HikariConfig created, HikariDataSource initialized.");

        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations(provider.migrationLocation())
                .baselineOnMigrate(true)
                .load();
        flyway.migrate();

        MigrationInfoService info = flyway.info();

        EKUtils.info("DB schema at " + info.current().getVersion() + " - " + info.current().getDescription());

        Settings st = new Settings()
                .withParamType(ParamType.INDEXED)
                .withExecuteLogging(false);

        dslContext = DSL.using(dataSource, provider.dialect(), st);
        this.playerDatabase = new PlayerDatabase(dslContext);
        this.teamDatabase = new TeamDatabase(dslContext);

        this.up = true;

        return this;
    }

    public BaseProvider getProvider(String type) {
        return switch (type.trim().toLowerCase()) {
            case "mariadb", "maria" -> new MariaDBBaseProvider();
            case "mysql" -> new MySQLDBBaseProvider();
            case "postgresql", "postgres", "psql" -> new PostgreSQLBaseProvider();
            default -> new SQLiteBaseProvider();
        };
    }

    public void shutdown() {
        this.dataSource.close();
    }

    public boolean isUp() {
        return this.up;
    }
}
