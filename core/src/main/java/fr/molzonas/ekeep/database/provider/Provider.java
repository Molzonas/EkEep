package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public interface Provider {
    public record ProviderResult(boolean isOk, String message, Exception e) {}

    SQLDialect dialect();
    String jdbcUrl(DatabaseConfiguration mzDatabaseConfiguration);
    default void tune(HikariConfig hikariConfig, DatabaseConfiguration mzDatabaseConfiguration) {}
    String migrationLocation();
    String getDatasourceClassName();
    String getDriverClassName();
    default ProviderResult init(DatabaseConfiguration c) {
        return new ProviderResult(true, "", null);
    }

    static Provider select(String type) {
        return switch (type.toLowerCase().trim()) {
            case "mysql" -> new MySQLProvider();
            case "postgres", "postgresql" -> new PostgreSQLProvider();
            case "h2" -> new H2Provider();
            default -> new SQLiteProvider();
        };
    }
}
