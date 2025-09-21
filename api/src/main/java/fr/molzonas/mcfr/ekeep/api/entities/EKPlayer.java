package fr.molzonas.mcfr.ekeep.api.entities;

import java.time.Instant;
import java.util.UUID;

public record EKPlayer(
        UUID playerUuid,
        UUID teamId,
        UUID teamRoleId,
        String lastPlayerName,
        Instant lastLogin,
        long totalPlaytime,
        long lastMonthPlaytime,
        Instant updatedAt
) {}
