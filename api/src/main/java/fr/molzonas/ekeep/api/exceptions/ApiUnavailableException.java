package fr.molzonas.ekeep.api.exceptions;

public class ApiUnavailableException extends RuntimeException {
    public ApiUnavailableException(String message) {
        super(message);
    }
    public ApiUnavailableException(String message, Exception cause) {
        super(message, cause);
    }
}
