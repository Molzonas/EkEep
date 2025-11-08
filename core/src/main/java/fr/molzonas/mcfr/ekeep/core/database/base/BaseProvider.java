package fr.molzonas.mcfr.ekeep.core.database.base;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.mcfr.ekeep.core.configs.DatabaseConfiguration;
import org.jooq.SQLDialect;

public interface BaseProvider {
    public record ProviderResult(boolean isOk, String message, Exception e) {}

    SQLDialect dialect();
    String jdbcUrl(DatabaseConfiguration mzDatabaseConfiguration);
    default void tune(HikariConfig hikariConfig, DatabaseConfiguration mzDatabaseConfiguration) {}
    String migrationLocation();
    String getDatasourceClassName();
    default ProviderResult init(DatabaseConfiguration c) {
        return new ProviderResult(true, "", null);
    }
}
