package fr.molzonas.ekeep.api.domain.quiz;

import java.util.Map;
import java.util.UUID;

public record QAnswer(String answer, Map<UUID, Integer> points) {}
