package fr.molzonas.ekeep.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.molzonas.ekeep.api.enums.ReloadableType;
import fr.molzonas.ekeep.api.lifecycle.Reloadable;
import fr.molzonas.ekeep.config.keys.ConfigKey;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public abstract class Config implements Reloadable {
    private static final ObjectMapper MAPPER = new ObjectMapper();
    private final ConcurrentHashMap<String, ConfigValue<?>> cache = new ConcurrentHashMap<>();
    @SuppressWarnings("unchecked")
    public <T> Optional<T> get(ConfigKey key) {
        return (Optional<T>) this.get(key, key.type());
    }

    public boolean exists(ConfigKey key) {
        if (cache.containsKey(key.path())) return this.cache.get(key.path()).isNull();
        return this.valueExists(key);
    }

    public boolean is(ConfigKey key) {
        return this.get(key, Boolean.class).orElse(false);
    }

    public <T>Optional<T> get(ConfigKey key, Class<T> clazz) {
        return Optional.ofNullable(cache.computeIfAbsent(key.path(), x ->
                new ConfigValue<>(key, coerce(getValue(key, clazz), clazz))
        ).getValueAs(clazz));
    }

    public <T> T getOrDefault(ConfigKey key, Class<T> clazz) {
        return this.get(key, clazz).orElse(clazz.cast(key.defaultValue().orElse(null)));
    }

    @SuppressWarnings("unchecked")
    public <T> T getOrDefault(ConfigKey key) {
        Optional<T> value = this.get(key);
        if (value.isPresent()) return value.get();
        else if (key.defaultValue().isPresent()) return (T) key.defaultValue().get();
        else return null;
    }

    protected <T> T coerce(Object raw, Class<T> clazz) {
        if (clazz.isInstance(raw)) {
            return clazz.cast(raw);
        }
        return MAPPER.convertValue(raw, MAPPER.getTypeFactory().constructType(clazz));
    }

    protected abstract <T> T getValue(ConfigKey key, Class<T> clazz);
    protected abstract boolean valueExists(ConfigKey key);

    @Override
    public void reload() {
        this.cache.clear();
    }

    @Override
    public ReloadableType reloadableType() {
        return ReloadableType.CONFIG;
    }
}
