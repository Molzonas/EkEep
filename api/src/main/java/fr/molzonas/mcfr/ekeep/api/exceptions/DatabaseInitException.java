package fr.molzonas.mcfr.ekeep.api.exceptions;

import fr.molzonas.mcfr.ekeep.api.exceptions.enums.DatabaseInitExceptionStep;

public class DatabaseInitException extends RuntimeException {
    private final DatabaseInitExceptionStep step;

    public DatabaseInitException(DatabaseInitExceptionStep step, String message, Exception cause) {
        super(message, cause);
        this.step = step;
    }

    public DatabaseInitException(DatabaseInitExceptionStep step, String message) {
        super(message);
        this.step = step;
    }

    public DatabaseInitException(DatabaseInitExceptionStep step) {
        this.step = step;
    }

    public DatabaseInitExceptionStep getStep() {
        return this.step;
    }
}
