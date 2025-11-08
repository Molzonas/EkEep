package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public class PostgreSQLProvider implements Provider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.POSTGRES;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration c) {
        String sslMode = c.getSslMode() == null || c.getSslMode().isBlank() ? "require" : c.getSslMode();
        String mode = c.isSslEnabled() ? sslMode : "disable";
        return "jdbc:postgresql://" + c.getHost() + ":" + c.getPort() + "/" + c.getDatabase() + "?sslmode=" + mode;
    }

    @Override
    public void tune(HikariConfig hc, DatabaseConfiguration mc) {
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
