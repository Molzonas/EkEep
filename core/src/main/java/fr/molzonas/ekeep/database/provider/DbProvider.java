package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public interface DbProvider {
    public record ProviderResult(boolean isOk, String message, Exception e) {}

    SQLDialect dialect();
    String jdbcUrl(DatabaseConfiguration databaseConfiguration);
    default void tune(HikariConfig hikariConfig, DatabaseConfiguration databaseConfiguration) {}
    String migrationLocation();
    String getDatasourceClassName();
    String getDriverClassName();
    default DbProvider.ProviderResult init(DatabaseConfiguration c) {
        return new DbProvider.ProviderResult(true, "", null);
    }
}
