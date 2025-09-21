package fr.molzonas.mcfr.ekeep.api.entities;

import java.time.Instant;
import java.util.UUID;

public record EKEventLog(
        UUID uuidPlayer,
        UUID teamId,
        EKEventType eventType,
        Instant at,
        String dataJson
) {}
