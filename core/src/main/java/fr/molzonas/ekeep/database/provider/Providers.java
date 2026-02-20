package fr.molzonas.ekeep.database.provider;

public class Providers {
    public static DbProvider select(String type) {
        return switch (type.toLowerCase().trim()) {
            case "mysql" -> new MySQLProvider();
            case "postgres", "postgresql" -> new PostgreSQLProvider();
            case "h2" -> new H2Provider();
            case "sqlite" -> new SQLiteProvider();
            default -> null;
        };
    }

    public static DbProvider selectOrThrow(String type) {
        DbProvider p = select(type);
        if (p == null) throw new IllegalArgumentException("Unsupported database type: " + type);
        return p;
    }
}
