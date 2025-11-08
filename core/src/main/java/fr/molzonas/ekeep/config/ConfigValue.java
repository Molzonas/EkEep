package fr.molzonas.ekeep.config;

import fr.molzonas.ekeep.config.keys.ConfigKey;

public class ConfigValue<T> {
    private ConfigKey key;
    private T value;

    public ConfigValue(ConfigKey key, T value) {
        this.key = key;
        this.value = value;
    }

    public ConfigKey getKey() {
        return this.key;
    }

    public T getValue() {
        return this.value;
    }

    public boolean isNull() {
        return this.value == null;
    }

    public <X> X getValueAs(Class<X> clazz) {
        return clazz.cast(this.value);
    }
}
