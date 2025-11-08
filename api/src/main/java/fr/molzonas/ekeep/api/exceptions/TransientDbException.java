package fr.molzonas.ekeep.api.exceptions;

public class TransientDbException extends RuntimeException {
    public TransientDbException(Throwable exception) {
        super(exception);
    }
}
