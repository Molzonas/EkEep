package fr.molzonas.ekeep.config;

import fr.molzonas.ekeep.config.keys.ConfigKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class YamlConfig extends Config {
    private final YamlConfiguration config;

    public YamlConfig(YamlConfiguration config) {
        this.config = config;
    }

    public YamlConfig(FileConfiguration config) {
        this.config = (YamlConfiguration) config;
    }

    public YamlConfig(File file) {
        this.config = YamlConfiguration.loadConfiguration(file);
    }

    @Override
    protected <T> T getValue(ConfigKey key, Class<T> clazz) {
        return this.config.getObject(key.path(), clazz);
    }

    @Override
    protected boolean valueExists(ConfigKey key) {
        return this.config.contains(key.path());
    }
}
