package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public class PostgreSQLProvider implements DbProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.POSTGRES;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration databaseConfiguration) {
        String sslMode = databaseConfiguration.getSslMode() == null || databaseConfiguration.getSslMode().isBlank() ? "require" : databaseConfiguration.getSslMode();
        String mode = databaseConfiguration.isSslEnabled() ? sslMode : "disable";
        return "jdbc:postgresql://" + databaseConfiguration.getHost() + ":" + databaseConfiguration.getPort() + "/" + databaseConfiguration.getDatabase() + "?sslmode=" + mode;
    }

    @Override
    public void tune(HikariConfig hc, DatabaseConfiguration databaseConfiguration) {
        hc.addDataSourceProperty("reWriteBatchedInserts", "true");
    }

    @Override
    public String migrationLocation() {
        return "classpath:database/migration/postgres";
    }

    @Override
    public String getDatasourceClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.postgresql.ds.PGSimpleDataSource";
    }

    @Override
    public String getDriverClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.postgresql.Driver";
    }
}
