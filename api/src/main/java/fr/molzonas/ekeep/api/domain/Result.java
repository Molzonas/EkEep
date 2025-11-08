package fr.molzonas.ekeep.api.domain;

import java.util.Optional;

public final class Result<T> {
    private final T value;
    private final String message;
    private final Exception exception;
    private final boolean ok;

    private Result(T value, String message, Exception exception, boolean ok) {
        this.value = value;
        this.message = message;
        this.exception = exception;
        this.ok = ok;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, null, true);
    }

    public static <T> Result<T> success(T value, String message) {
        return new Result<>(value, message, null, true);
    }

    public static <T> Result<T> failure(Exception e) {
        return new Result<>(null, null, e, false);
    }

    public static <T> Result<T> failure(String message) {
        return new Result<>(null, message, null, false);
    }

    public static <T> Result<T> failure(Exception e, String message) {
        return new Result<>(null, message, e, false);
    }

    public Optional<T> getValue() {
        return Optional.ofNullable(value);
    }

    public Optional<String> getMessage() {
        return Optional.ofNullable(message);
    }

    public Optional<Exception> getException() {
        return Optional.ofNullable(exception);
    }

    public boolean isOk() {
        return ok;
    }
}
