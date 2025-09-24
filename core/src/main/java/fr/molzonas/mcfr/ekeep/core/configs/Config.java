package fr.molzonas.mcfr.ekeep.core.configs;

import com.fasterxml.jackson.databind.ObjectMapper;
import fr.molzonas.mcfr.ekeep.core.configs.base.Key;
import org.bukkit.configuration.file.FileConfiguration;

import java.lang.reflect.Type;
import java.util.concurrent.ConcurrentHashMap;

public final class Config {
    private static final ObjectMapper MAPPER =  new ObjectMapper();
    private final ConcurrentHashMap<String, Object> cache = new ConcurrentHashMap<>();
    private FileConfiguration fc;

    public Config(FileConfiguration file) {
        this.init(file);
    }

    public void init(FileConfiguration file) {
        this.fc = file;
    }

    public <T> T get(Key<T> key) {
        return convert(cache.computeIfAbsent(key.getPath(), x -> this.fc.get(key.getPath())), key.getType());
    }

    public <T> void set(Key<T> key, T value) {
        cache.put(key.getPath(), value);
        fc.set(key.getPath(), value);
    }

    private static <T> T convert(Object raw, Type type) {
        return MAPPER.convertValue(raw, MAPPER.getTypeFactory().constructType(type));
    }
}
