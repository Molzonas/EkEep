package fr.molzonas.ekeep.event.handler.quiz;

import fr.molzonas.ekeep.api.domain.quiz.QAnswer;
import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.event.handler.quiz.ponderation.QuizPlaytimeExtendedPonderation;
import fr.molzonas.ekeep.event.handler.quiz.ponderation.QuizPlaytimePonderation;
import fr.molzonas.ekeep.event.handler.quiz.ponderation.QuizSimpleCountPonderation;

import java.util.Map;
import java.util.UUID;

public interface QuizPonderationHandler {
    Map<UUID, Integer> ponderate(QQuestion question, QAnswer answer);

    static QuizPonderationHandler getByProviderName(String providerName, EkEep.BaseContext ctx) {
        return switch (providerName.toLowerCase().trim()) {
            case "playtime" -> new QuizPlaytimePonderation();
            case "playtime-extended" -> new QuizPlaytimeExtendedPonderation();
            default -> new QuizSimpleCountPonderation();
        };
    }
}
