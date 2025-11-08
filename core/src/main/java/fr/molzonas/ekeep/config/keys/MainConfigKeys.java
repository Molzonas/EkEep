package fr.molzonas.ekeep.config.keys;

import java.util.Locale;
import java.util.Optional;

public enum MainConfigKeys implements ConfigKey {
    // BASE
    DEBUG("debug", Boolean.class, false),
    ENABLE_UPDATE_CHECK("enable-update-check", Boolean.class, false),
    LOCALE("locale", String.class, "en-us"),

    // CACHE
    CACHE_PROFILES_TTL_SECONDS("cache.profiles-ttl-seconds", Integer.class, 60),

    // DATABASE
    TYPE("database.type", String.class, "mariadb"),
    HOST("database.host", String.class, "127.0.0.1"),
    PORT("database.port", Integer.class, "3306"),
    SCHEMA("database.schema", String.class, "database"),
    USERNAME("database.username", String.class, "root"),
    PASSWORD("database.password", String.class, ""),
    FILE("database.file", String.class, "plugins/ekEep/ekEep.db"),
    MAXIMUM_POOL_SIZE("database.maximumPoolSize", Integer.class, 10),
    MINIMUM_IDLE("database.minimumIdle", Integer.class, 2),
    MAXIMUM_LIFETIME("database.maximumLifetime", Integer.class, 1_800_000),
    CONNECTION_TIMEOUT("database.connectionTimeout", Integer.class, 10_000),
    IDLE_TIMEOUT("database.idleTimeout", Integer.class, 60_000),
    SSL_ENABLED("database.sslEnabled", Boolean.class, false),
    SSL_MODE("database.sslMode", String.class, ""),
    ;

    private final String path;
    private final Class<?> type;
    private final Object defaultValue;

    MainConfigKeys(String path, Class<?> type, Object defaultValue) {
        this.path = path;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    MainConfigKeys(String path, Class<?> type) {
        this.path = path;
        this.type = type;
        this.defaultValue = null;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public Optional<Object> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public Class<?> type() {
        return this.type;
    }
}
