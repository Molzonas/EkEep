package fr.molzonas.mcfr.ekeep.core.configs.base;

public abstract class BaseKeys {
    protected static <T> Key<T> key(KeySpace space, String path, TypeRef<T> type, T defaultValue) {
        return KeyRegistry.register(space.key(path, type, defaultValue));
    }

    protected static <T> Key<T> key(KeySpace space, String path, TypeRef<T> type) {
        return KeyRegistry.register(space.key(path, type));
    }
}
