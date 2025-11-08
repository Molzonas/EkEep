package fr.molzonas.ekeep.event.handler.quiz;

import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.event.handler.quiz.asktype.QuizAskingUsingChat;
import fr.molzonas.ekeep.event.handler.quiz.asktype.QuizAskingUsingFancyHolograms;
import fr.molzonas.ekeep.event.handler.quiz.asktype.QuizAskingUsingInventory;
import fr.molzonas.ekeep.event.handler.quiz.asktype.QuizAskingUsingTypewriter;
import org.bukkit.entity.Player;

public interface QuizAskingTypeHandler {
    void ask(Player player, QQuestion question);

    static QuizAskingTypeHandler getByProviderName(String providerName, QuizHandler handler, EkEep.BaseContext ctx) {
        return switch (providerName.toLowerCase().trim()) {
            case "holograms" -> new QuizAskingUsingFancyHolograms();
            case "inventory" -> new QuizAskingUsingInventory();
            case "typewriter" -> new QuizAskingUsingTypewriter();
            default -> new QuizAskingUsingChat(handler, ctx);
        };
    }
}
