package fr.molzonas.ekeep.config;

import fr.molzonas.ekeep.config.keys.ConfigKey;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class PropertiesConfig extends Config {
    private final Properties properties;

    public PropertiesConfig(Properties properties) {
        this.properties = properties;
    }

    public PropertiesConfig(File file) throws IOException {
        this.properties = new Properties();
        try (FileInputStream fis = new FileInputStream(file)) {
            this.properties.load(fis);
        }
    }

    @Override
    protected <T> T getValue(ConfigKey key, Class<T> clazz) {
        return clazz.cast(properties.getProperty(key.path()));
    }

    @Override
    protected boolean valueExists(ConfigKey key) {
        return properties.containsKey(key.path());
    }
}
