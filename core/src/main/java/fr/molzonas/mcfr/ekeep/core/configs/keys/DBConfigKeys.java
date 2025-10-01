package fr.molzonas.mcfr.ekeep.core.configs.keys;

import fr.molzonas.mcfr.ekeep.core.configs.base.ConfigKey;
import fr.molzonas.mcfr.ekeep.core.configs.base.TypeRef;

public enum DBConfigKeys implements ConfigKey {
    TYPE("database.type", new TypeRef<String>(){}, "mariadb"),
    HOST("database.host", new TypeRef<String>() {}, "127.0.0.1"),
    PORT("database.port", new TypeRef<Integer>() {}, "3306"),
    SCHEMA("database.schema", new TypeRef<String>() {}, "database"),
    USERNAME("database.username", new TypeRef<String>() {}, "root"),
    PASSWORD("database.password", new TypeRef<String>() {}, ""),
    FILE("database.file", new TypeRef<String>() {}, "plugins/ekEep/ekEep.db"),
    MAXIMUM_POOL_SIZE("database.maximumPoolSize", new TypeRef<Integer>() {}, 10),
    MINIMUM_IDLE("database.minimumIdle", new TypeRef<Integer>() {}, 2),
    MAXIMUM_LIFETIME("database.maximumLifetime", new TypeRef<Integer>() {}, 1_800_000),
    CONNECTION_TIMEOUT("database.connectionTimeout", new TypeRef<Integer>() {}, 10_000),
    IDLE_TIMEOUT("database.idleTimeout", new TypeRef<Integer>() {}, 60_000),
    SSL_ENABLED("database.sslEnabled", new TypeRef<Boolean>() {}, false),
    SSL_MODE("database.sslMode", new TypeRef<String>() {}, ""),
    ;

    DBConfigKeys(String path, TypeRef<?> type, Object defaultValue) {
        this.path = path;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    private final String path;
    private final Object defaultValue;
    private final TypeRef<?> type;

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public Object getDefaultValue() {
        return this.defaultValue;
    }

    @Override
    public TypeRef<?> getType() {
        return this.type;
    }
}
