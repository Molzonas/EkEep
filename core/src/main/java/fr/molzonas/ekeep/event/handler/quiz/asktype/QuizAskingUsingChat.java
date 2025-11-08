package fr.molzonas.ekeep.event.handler.quiz.asktype;

import fr.molzonas.ekeep.api.domain.quiz.QAnswer;
import fr.molzonas.ekeep.api.domain.quiz.QQuestion;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.event.handler.quiz.QuizAskingTypeHandler;
import fr.molzonas.ekeep.event.handler.quiz.QuizHandler;
import fr.molzonas.ekeep.i18n.Message;
import fr.molzonas.ekeep.util.EKUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;

public class QuizAskingUsingChat implements QuizAskingTypeHandler {
    private final EkEep.BaseContext context;
    private final QuizHandler handler;

    public QuizAskingUsingChat(QuizHandler handler, EkEep.BaseContext context) {
        this.context = context;
        this.handler = handler;
    }

    @Override
    public void ask(Player player, QQuestion question) {
        player.sendMessage(Message.of("quiz.question-decorator"), question.getQuestion());
        for (int i = 0; i < question.getAnswers().size(); i++) {
            player.sendMessage(Message.of("quiz.question-answers-decorator", (i+1), question.getAnswers().get(i).answer()));
        }
        Bukkit.getPluginManager().registerEvents(new Listener() {
            @EventHandler
            public void msg(AsyncChatEvent event) {
                Player p = event.getPlayer();
                if (!p.getUniqueId().equals(player.getUniqueId()) || p.getServer() != player.getServer()) return;
                String msg = EKUtils.toString(event.message());
                int v = -1;
                try {
                    v = Integer.parseInt(msg.trim());
                } catch (NumberFormatException e) {
                    return;
                }
                if (v <= 0 || v > question.getAnswers().size()) return;
                QAnswer a = question.getAnswers().get(v);
                HandlerList.unregisterAll(this);
                handler.onAnswer(question, a);
            }
        }, this.context.plugin());
    }
}
