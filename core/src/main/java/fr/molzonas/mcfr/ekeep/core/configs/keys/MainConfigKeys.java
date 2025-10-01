package fr.molzonas.mcfr.ekeep.core.configs.keys;

import fr.molzonas.mcfr.ekeep.core.configs.base.ConfigKey;
import fr.molzonas.mcfr.ekeep.core.configs.base.TypeRef;

public enum MainConfigKeys implements ConfigKey {
    DEBUG("debug", new TypeRef<Boolean>() {}, false),
    ENABLE_UPDATE_CHECK("enable-update-check", new TypeRef<Boolean>() {}, false),
    CACHE_PROFILES_TTL_SECONDS("cache.profiles-ttl-seconds", new  TypeRef<Integer>() {}, 60),
    ;

    MainConfigKeys(String path, TypeRef<?> type, Object defaultValue) {
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
