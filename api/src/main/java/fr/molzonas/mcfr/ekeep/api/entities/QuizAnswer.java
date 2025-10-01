package fr.molzonas.mcfr.ekeep.api.entities;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class QuizAnswer {
    private String answer;
    private Map<UUID, Integer> points = new HashMap<>();

    public QuizAnswer(String answer, Map<UUID, Integer> points) {
        this.answer = answer;
        this.points = points;
    }

    public String getAnswer() {
        return answer;
    }

    public Map<UUID, Integer> getPoints() {
        return points;
    }
}
