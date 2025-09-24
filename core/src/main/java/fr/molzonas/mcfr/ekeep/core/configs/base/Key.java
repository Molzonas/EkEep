package fr.molzonas.mcfr.ekeep.core.configs.base;

import java.lang.reflect.Type;

public class Key<T> {
    private final String path;
    private final Type type;
    private final T defaultValue;

    private Key(String path, Type type, T defaultValue) {
        this.path = path;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    private Key(String path, java.lang.reflect.Type type) {
        this.path = path;
        this.type = type;
        this.defaultValue = null;
    }

    public static <T> Key<T> of(String path, TypeRef<T> ref, T def) {
        return new Key<>(path, ref.getType(), def);
    }

    public static <T> Key<T> of(String path, TypeRef<T> ref) {
        return new Key<>(path, ref.getType(), null);
    }

    public String getPath() {
        return path;
    }

    public Type getType() {
        return type;
    }

    public T getDefaultValue() {
        return defaultValue;
    }
}
