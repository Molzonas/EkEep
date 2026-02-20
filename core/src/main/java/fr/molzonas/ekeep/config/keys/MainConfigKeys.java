package fr.molzonas.ekeep.config.keys;

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
    MAXIMUM_POOL_SIZE("database.maximum-pool-size", Integer.class, 10),
    MINIMUM_IDLE("database.minimum-idle", Integer.class, 2),
    MAXIMUM_LIFETIME("database.maximum-lifetime", Integer.class, 1_800_000),
    CONNECTION_TIMEOUT("database.connection-timeout", Integer.class, 10_000),
    IDLE_TIMEOUT("database.idle-timeout", Integer.class, 60_000),
    SSL_ENABLED("database.ssl-enabled", Boolean.class, false),
    SSL_MODE("database.ssl-mode", String.class, ""),
    BASELINE_ON_MIGRATE("database.baseline-on-migrate", Boolean.class, false)
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
