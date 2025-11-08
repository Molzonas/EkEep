package fr.molzonas.ekeep.api.domain.quiz;

import java.util.List;
import java.util.Optional;

public class QQuestion {
    private final String id;
    private final String question;
    private final List<QAnswer> answers;
    private boolean answered = false;
    private QAnswer selectedResponse;

    public QQuestion(String id, String question, QAnswer... answers) {
        this.id = id;
        this.question = question;
        this.answers = List.of(answers);
    }

    public QQuestion(String id, String question, List<QAnswer> answers) {
        this.id = id;
        this.question = question;
        this.answers = answers;
    }

    public String getId() {
        return id;
    }

    public String getQuestion() {
        return question;
    }

    public List<QAnswer> getAnswers() {
        return answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Optional<QAnswer> getSelectedResponse() {
        return Optional.ofNullable(selectedResponse);
    }

    public void setSelectedResponse(QAnswer selectedResponse) {
        this.selectedResponse = selectedResponse;
    }
}
