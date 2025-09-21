package fr.molzonas.mcfr.ekeep.api.entities;

import java.time.Instant;
import java.util.UUID;

public record EKTeamPlayerContribution(
        UUID playerId,
        UUID teamId,
        Instant at,
        long contribution,
        EKContributionType contributionType,
        EKEventLog eventLog
) {}
