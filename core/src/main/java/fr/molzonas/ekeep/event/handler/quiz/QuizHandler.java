package fr.molzonas.ekeep.event.handler.quiz;

import fr.molzonas.ekeep.api.domain.quiz.QAnswer;
import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.config.keys.QuizConfigKeys;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuizHandler {
    private final Player player;
    private final QQuiz quiz;
    private final EkEep.BaseContext ctx;
    private final Map<UUID, Integer> total = new HashMap<>();
    private final QuizAskingTypeHandler askHandler;
    private final QuizPonderationHandler ponderationHandler;
    private List<QQuestion> questions;
    private int nb;

    public QuizHandler(EkEep.BaseContext context, Player player, QQuiz quiz) {
        this.ctx = context;
        this.player = player;
        this.quiz = quiz;
        this.askHandler = QuizAskingTypeHandler
                .getByProviderName(context.config().getOrDefault(QuizConfigKeys.ASK_PROVIDER, String.class),
                        this, this.ctx);
        this.ponderationHandler = QuizPonderationHandler
                .getByProviderName(context.config().getOrDefault(QuizConfigKeys.BALANCING_TYPE, String.class),
                        this.ctx);
    }

    public void start() {
        if (!player.isConnected()) return;
        questions = this.quiz.getRandomQuestions(10);
        this.ask(questions.get(nb));
    }

    public void ask(QQuestion question) {
        if (!player.isConnected()) return;
        askHandler.ask(player, question);
    }

    public void onAnswer(QQuestion question, QAnswer answer) {
        if (!player.isConnected()) return;
        nb++;
        Map<UUID, Integer> answerPoints = ponderationHandler.ponderate(question, answer);
        for (Map.Entry<UUID, Integer> entry : answerPoints.entrySet()) {
            this.total.put(entry.getKey(), entry.getValue() + this.total.getOrDefault(entry.getKey(), 0));
        }
        if (nb < this.ctx.config().getOrDefault(QuizConfigKeys.NB_QUESTIONS, Integer.class)) {
            this.ask(this.questions.get(nb));
        }
    }
}
