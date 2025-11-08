package fr.molzonas.ekeep.api.exceptions;

import java.util.UUID;

public class EkTeamNotFoundException extends RuntimeException {
    private final UUID uuid;
    public EkTeamNotFoundException(UUID uuid, String message) {
        super(message);
        this.uuid = uuid;
    }
    public EkTeamNotFoundException(UUID uuid, String message, Exception cause) {
        super(message, cause);
        this.uuid = uuid;
    }
}
