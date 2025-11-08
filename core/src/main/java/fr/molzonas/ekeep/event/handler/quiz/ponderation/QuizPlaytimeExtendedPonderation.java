package fr.molzonas.ekeep.event.handler.quiz.ponderation;

import fr.molzonas.ekeep.api.domain.quiz.QAnswer;
import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.event.handler.quiz.QuizAskingTypeHandler;
import fr.molzonas.ekeep.event.handler.quiz.QuizPonderationHandler;
import org.bukkit.entity.Player;

import java.util.Map;
import java.util.UUID;

public class QuizPlaytimeExtendedPonderation implements QuizPonderationHandler {
    @Override
    public Map<UUID, Integer> ponderate(QQuestion question, QAnswer answer) {
        return Map.of();
    }
}
