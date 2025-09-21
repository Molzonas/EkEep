package fr.molzonas.mcfr.ekeep.api.entities;

import java.util.UUID;

public record EKTeamRole(
    UUID teamId,
    String name,
    String luckpermsRole
) {}
