package fr.molzonas.ekeep.database;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;
import fr.molzonas.ekeep.database.provider.Provider;

public class DataSourceFactory {
    public static HikariDataSource create(DatabaseConfiguration cfg) {
        Provider p = Provider.select(cfg.getType());
        HikariConfig hc = new HikariConfig();
        hc.setMaximumPoolSize(cfg.getMaximumPoolSize());
        hc.setMinimumIdle(cfg.getMinimumIdle());
        hc.setConnectionTimeout(cfg.getConnectionTimeout());
        hc.setIdleTimeout(cfg.getIdleTimeout());
        hc.setMaxLifetime(cfg.getMaximumLifetime());
        hc.setPoolName("EkEep-Hikari");

        hc.setDriverClassName(p.getDriverClassName());
        hc.setJdbcUrl(p.jdbcUrl(cfg));
        if (!cfg.isSQLite()) {
            hc.setUsername(cfg.getUsername());
            hc.setPassword(cfg.getPassword());
        }
        p.tune(hc, cfg);
        return new HikariDataSource(hc);
    }
}
