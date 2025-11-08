package fr.molzonas.ekeep.api.exceptions;

public class DatabaseOperationException extends RuntimeException {
    public DatabaseOperationException(Throwable exception) {
        super(exception);
    }
}
