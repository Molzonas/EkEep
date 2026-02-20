package fr.molzonas.ekeep.database;

public class DatabaseException extends RuntimeException {
    private final Step step;

    public DatabaseException(Step initStep, String message, Exception exception) {
        super(message, exception);
        this.step = initStep;
    }

    public Step getStep() {
        return this.step;
    }

    public enum Step {
        UNKNOWN,
        PROVIDER_SELECT,
        DATASOURCE_CREATE,
        FLYWAY_MIGRATE,
        JOOQ_INIT,
        PING
    }
}
