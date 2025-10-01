package fr.molzonas.mcfr.ekeep.api.entities;

import java.util.List;
import java.util.Optional;

public class QuizQuestion {
    private final String id;
    private final String question;
    private final List<QuizAnswer> answers;
    private boolean answered = false;
    private QuizAnswer selectedResponse;

    public QuizQuestion(String id, String question, QuizAnswer... answers) {
        this.id = id;
        this.question = question;
        this.answers = List.of(answers);
    }

    public QuizQuestion(String id, String question, List<QuizAnswer> answers) {
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

    public List<QuizAnswer> getAnswers() {
        return answers;
    }

    public boolean isAnswered() {
        return answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }

    public Optional<QuizAnswer> getSelectedResponse() {
        return Optional.ofNullable(selectedResponse);
    }

    public void setSelectedResponse(QuizAnswer selectedResponse) {
        this.selectedResponse = selectedResponse;
    }
}
