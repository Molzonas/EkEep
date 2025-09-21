package fr.molzonas.mcfr.ekeep.api.entities;

import java.util.UUID;

public record EKTeam(
        UUID id,
        String name,
        String mainColor,
        String secondaryColor,
        int totalScore
) {}
