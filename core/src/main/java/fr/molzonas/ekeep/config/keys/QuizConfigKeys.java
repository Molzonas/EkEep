package fr.molzonas.ekeep.config.keys;

import java.util.Optional;

public enum QuizConfigKeys implements ConfigKey {
    NB_QUESTIONS("nb-questions", Integer.class, 10),
    ASK_PROVIDER("ask-provider", String.class, "chat"),
    BALANCING_ENABLED("balancing.enabled",  Boolean.class, true),
    BALANCING_TYPE("balancing.balancing-type", String.class, "simple-count"),
    MAX_PERCENTAGE_DIFFERENCE_TEAM("balancing.max-percentage-difference-team", Float.class, 0.1f),
    BONUS_PER_PERCENT_THRESHOLD("balancing.bonus-per-percent-threshold", Float.class, 1f),
    ;

    private final String path;
    private final Class<?> type;
    private final Object defaultValue;

    QuizConfigKeys(String path, Class<?> type, Object defaultValue) {
        this.path = "quiz." + path;
        this.type = type;
        this.defaultValue = defaultValue;
    }

    QuizConfigKeys(String path, Class<?> type) {
        this.path = "quiz." + path;
        this.type = type;
        this.defaultValue = null;
    }

    @Override
    public String path() {
        return this.path;
    }

    @Override
    public Optional<Object> defaultValue() {
        return Optional.ofNullable(this.defaultValue);
    }

    @Override
    public Class<?> type() {
        return this.type;
    }
}
