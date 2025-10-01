package fr.molzonas.mcfr.ekeep.core.configs.base;

import org.bukkit.configuration.file.FileConfiguration;

public interface ConfigKey {
    String getPath();
    Object getDefaultValue();
    TypeRef<?> getType();


    default Object getRaw(FileConfiguration cfg) {
        return cfg.isSet(getPath()) ? cfg.get(getPath()) : getDefaultValue();
    }
    default <T> T get(FileConfiguration cfg, Class<T> expected) {
        Object raw = getRaw(cfg);
        return raw == null ? null : expected.cast(raw);
    }
}
