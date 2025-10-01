package fr.molzonas.mcfr.ekeep.core.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.molzonas.mcfr.ekeep.core.configs.base.ConfigKey;
import fr.molzonas.mcfr.ekeep.core.configs.base.TypeRef;
import fr.molzonas.mcfr.ekeep.core.utils.EKUtils;
import fr.molzonas.mcfr.ekeep.core.utils.Reloadable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Config implements Reloadable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final FileConfiguration config;
    private final ConcurrentHashMap<ConfigKey, Object> cache = new ConcurrentHashMap<>();
    private boolean cacheEnabled = true;

    public Config(JavaPlugin plugin) {
        this.config = plugin.getConfig();
    }

    public Config(FileConfiguration config) {
        this.config = config;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(ConfigKey key) {
        return (T) this.get(key, key.getType());
    }

    public boolean is(ConfigKey key) {
        return this.get(key, new TypeRef<>() {});
    }

    public <T> T get(ConfigKey key, TypeRef<T> typeRef) {
        if (cacheEnabled && cache.containsKey(key)) {
            return coerce(cache.get(key), key.getType());
        }
        Object raw = config.get(key.getPath(), null);
        if (raw == null) return null;
        if (cacheEnabled) this.cache.put(key, raw);
        return coerce(raw, typeRef);
    }

    public <T> T coerce(Object raw, TypeRef<?> type) {
        if (raw == null) return null;
        return MAPPER.convertValue(raw, MAPPER.getTypeFactory().constructType(type.getType()));
    }

    public <T> List<T> getList(ConfigKey key, TypeRef<T> expectedType) {
        if (cacheEnabled && cache.containsKey(key)) {
            return coerce(cache.get(key), new TypeRef<List<T>>() {});
        }
        Object raw = config.get(key.getPath());
        List<?> list;
        if (raw instanceof List) {
            list = (List<?>) raw;
        } else if (raw == null) {
            Object defaultValue = key.getDefaultValue();
            list = (defaultValue instanceof List<?> dl) ? dl : List.of();
        } else {
            list = List.of(raw);
        }

        List<T> result = new ArrayList<>(list.size());
        for (Object o : list) {
            if (o == null) continue;
            try {
                result.add(coerce(o, expectedType));
            } catch (ClassCastException e) {
                EKUtils.warn("Config '" + key.getPath() + "' [" + e + "] is invalid.");
            }
        }
        return result;
    }

    public boolean exists(ConfigKey key) {
        if (cacheEnabled && cache.containsKey(key)) return true;
        return config.isSet(key.getPath());
    }

    public boolean isCacheEnabled() {
        return cacheEnabled;
    }

    public void setCacheEnabled(boolean cacheEnabled) {
        this.cacheEnabled = cacheEnabled;
    }

    @Override
    public void reload() {
        this.cache.clear();
    }
}
