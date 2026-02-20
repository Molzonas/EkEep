package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

import java.io.File;
import java.io.IOException;

public class SQLiteProvider implements DbProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.SQLITE;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration databaseConfiguration) {
        return "jdbc:sqlite:" + databaseConfiguration.getFile() + "?journal_mode=WAL&busy_timeout=5000";
    }

    @Override public void tune(HikariConfig hc, DatabaseConfiguration databaseConfiguration) {
        hc.setMaximumPoolSize(1);
        hc.setMinimumIdle(1);
    }

    @Override
    public String migrationLocation() {
        return "classpath:database/migration/sqlite";
    }

    @Override
    public String getDatasourceClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.sqlite.SQLiteDataSource";
    }

    @Override
    public String getDriverClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.sqlite.JDBC";
    }

    @Override
    public ProviderResult init(DatabaseConfiguration c) {
        if (c.getFile() == null || c.getFile().trim().isEmpty()) return new ProviderResult(false, "No filepath found for SQLite initialisation", null);
        File f = new  File(c.getFile());
        if (!f.exists()) {
            try {
                f.createNewFile();
            } catch (IOException e) {
                return new ProviderResult(false, "Error creating SQLite file " + f.getAbsolutePath(), e);
            }
        }
        return new ProviderResult(true, "", null);
    }
}
