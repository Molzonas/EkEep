package fr.molzonas.mcfr.ekeep.core.database.providers;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.mcfr.ekeep.core.configs.DatabaseConfiguration;
import fr.molzonas.mcfr.ekeep.core.database.base.BaseProvider;
import org.jooq.SQLDialect;

public class SQLiteBaseProvider implements BaseProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.SQLITE;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration c) {
        return "jdbc:sqlite:" + c.getFile() + "?journal_mode=WAL&busy_timeout=5000";
    }

    @Override public void tune(HikariConfig hc, DatabaseConfiguration mc) {
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
}
