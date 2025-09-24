package fr.molzonas.mcfr.ekeep.core.configs.base;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public final class KeyRegistry {
    private static final Set<String> PATHS = ConcurrentHashMap.newKeySet();
    private static final Set<Key<?>> KEYS =  ConcurrentHashMap.newKeySet();

    private KeyRegistry() {}

    public static <T> Key<T> register(Key<T> k) {
        if (!PATHS.add(k.getPath())) {
            throw new IllegalStateException("Duplicate key `" + k.getPath() + "`");
        }
        KEYS.add(k);
        return k;
    }

    public static Set<Key<?>> getAll() {
        return KEYS;
    }
}
