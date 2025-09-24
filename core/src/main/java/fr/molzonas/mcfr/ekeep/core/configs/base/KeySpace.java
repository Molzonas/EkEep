package fr.molzonas.mcfr.ekeep.core.configs.base;

public final class KeySpace {
    private final String prefix;

    private KeySpace(String prefix) {
        this.prefix = prefix.endsWith(".") ? prefix : prefix + ".";
    }

    public static KeySpace of(String p) {
        return new KeySpace(p);
    }

    private String p(String path) {
        return prefix + path;
    }

    public <T> Key<T> key(String path, TypeRef<T> c, T def) {
        return Key.of(p(path), c, def);
    }

    public <T> Key<T> key(String path, TypeRef<T> t) {
        return Key.of(p(path), t);
    }
}
