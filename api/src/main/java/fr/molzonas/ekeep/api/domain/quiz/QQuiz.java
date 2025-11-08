package fr.molzonas.ekeep.api.domain.quiz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QQuiz {
    private final List<QQuestion> questions;
    private UUID player;

    public QQuiz(QQuestion... questions) {
        this.questions = List.of(questions);
    }

    public QQuiz(List<QQuestion> questions) {
        this.questions = questions;
    }

    public List<QQuestion> getQuestions() {
        return questions;
    }

    public UUID getPlayer() {
        return player;
    }

    public void setPlayer(UUID player) {
        this.player = player;
    }

    public Map<UUID, Integer> getTotal() {
        Map<UUID, Integer> rs = new HashMap<>();
        for (QQuestion q : questions) {
            if (!q.isAnswered() || q.getSelectedResponse().isEmpty()) continue;
            Map<UUID, Integer> tmp = q.getSelectedResponse().get().points();
            for (Map.Entry<UUID, Integer> u : tmp.entrySet()) {
                rs.merge(u.getKey(), u.getValue(), Integer::sum);
            }
        }
        return rs;
    }

    public static QQuiz copy(QQuiz q) {
        return new QQuiz(q.getQuestions());
    }
}
