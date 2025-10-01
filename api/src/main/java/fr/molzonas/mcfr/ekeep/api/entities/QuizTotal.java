package fr.molzonas.mcfr.ekeep.api.entities;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuizTotal {
    private List<QuizQuestion> questions;
    private UUID player;

    public QuizTotal(QuizQuestion... questions) {
        this.questions = List.of(questions);
    }

    public QuizTotal(List<QuizQuestion> questions) {
        this.questions = questions;
    }

    public List<QuizQuestion> getQuestions() {
        return questions;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public Map<UUID, Integer> getTotal() {
        Map<UUID, Integer> answ = new HashMap<>();
        for (QuizQuestion q : questions) {
            if (!q.isAnswered() || q.getSelectedResponse().isEmpty()) continue;
            Map<UUID, Integer> tmp = q.getSelectedResponse().get().getPoints();
            for (Map.Entry<UUID, Integer> u : tmp.entrySet()) {
                answ.merge(u.getKey(), u.getValue(), Integer::sum);
            }
        }
        return answ;
    }
}
