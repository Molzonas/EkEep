package fr.molzonas.mcfr.ekeep.core.configs.base;

import java.lang.reflect.Type;

public abstract class TypeRef<T> {
    private final Type type;
    protected TypeRef() {
        this.type = ((java.lang.reflect.ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

    public Type getType() { return type; }
}
