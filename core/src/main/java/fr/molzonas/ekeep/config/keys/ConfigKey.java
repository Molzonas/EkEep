package fr.molzonas.ekeep.config.keys;

import java.util.Optional;

public interface ConfigKey {
    String path();
    Optional<Object> defaultValue();
    Class<?> type();
    @SuppressWarnings("unchecked")
    default <T> Optional<T> typedDefaultValue() {
        return Optional.ofNullable((T) this.defaultValue().orElse(null));
    }
}
