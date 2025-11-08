package fr.molzonas.ekeep.api.domain.quiz;

import java.util.*;

public class QQuiz {
    private static final Random RD = new Random();
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

    public UUID getResultTeamUuid() {
        Map<UUID, Integer> rs = this.getTotal();
        UUID team = null;
        int maxScore = -1;
        for (Map.Entry<UUID, Integer> u : rs.entrySet()) {
            if (u.getValue() > maxScore && maxScore > 0) {
                maxScore = u.getValue();
                team = u.getKey();
            }
        }
        return team;
    }

    public List<QQuestion> getRandomQuestions(int nb) {
        if (nb <= 0) return new ArrayList<>();
        if (nb >= this.questions.size()) return this.questions;
        List<QQuestion> rdQuestions = new ArrayList<>(this.questions);
        List<QQuestion> rdQuestionsResult = new ArrayList<>();
        Collections.shuffle(rdQuestions);
        for (int i = 0; i < nb; i++) {
            int randIndex = RD.nextInt(rdQuestions.size());
            rdQuestionsResult.add(rdQuestions.get(randIndex));
        }
        return rdQuestionsResult;
    }

    public static QQuiz copy(QQuiz q) {
        return new QQuiz(q.getQuestions());
    }
}
