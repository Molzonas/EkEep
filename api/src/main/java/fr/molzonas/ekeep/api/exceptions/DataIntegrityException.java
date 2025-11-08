package fr.molzonas.ekeep.api.exceptions;

public class DataIntegrityException extends RuntimeException {
    public DataIntegrityException(Throwable exception) {
        super(exception);
    }
}
