package fr.molzonas.ekeep.event.listener;

import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.database.DatabaseManager;
import fr.molzonas.ekeep.database.generated.Ekeep;
import fr.molzonas.ekeep.database.generated.tables.records.EkPlayerRecord;
import fr.molzonas.ekeep.database.generated.tables.records.EkTeamRecord;
import fr.molzonas.ekeep.repository.PlayerRepository;
import fr.molzonas.ekeep.repository.TeamRepository;
import fr.molzonas.ekeep.schedule.ThreadBridge;
import fr.molzonas.ekeep.util.EKUtils;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;

public class OnPlayerLogin implements Listener {
    private final ThreadBridge thread;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public OnPlayerLogin(Executor dbExecutor) {
        this.thread = new ThreadBridge(dbExecutor);
        this.playerRepository = EkEep.getInstance().getDatabase().player();
        this.teamRepository = EkEep.getInstance().getDatabase().team();
    }

    @EventHandler
    private void onPlayerLogin(PlayerLoginEvent event) {
        async(() -> this.checkPlayerInDatabase(event.getPlayer()));
    }

    private void checkPlayerInDatabase(Player player) {
        Optional<EkPlayerRecord> ekp = this.playerRepository.selectByUuid(player.getUniqueId());
        if (ekp.isEmpty()) {
            EkPlayerRecord newPlayerRecord = new EkPlayerRecord();
            newPlayerRecord.setUuid(player.getUniqueId().toString());
            newPlayerRecord.setLastPlayername(player.getName());
            newPlayerRecord.setCreatedAt(LocalDateTime.now());
            newPlayerRecord.setLastLogin(LocalDateTime.now());
            newPlayerRecord.setUpdatedAt(LocalDateTime.now());
            playerRepository.insert(newPlayerRecord);
            ekp = playerRepository.selectByUuid(player.getUniqueId());

        }
    }

    private void startQuiz() {
        QQuiz quiz = EkEep.getInstance().getQuizConfig();
    }

    private void quizStep(QQuiz quiz, int step) {

        this.quizResult(quiz, step);
    }

    private void quizResult(QQuiz quiz, int step) {

        this.quizStep(quiz, step);
    }

    private void async(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(EkEep.getInstance(), r);
    }

    private void sync(Runnable r) {
        Bukkit.getScheduler().runTask(EkEep.getInstance(), r);
    }
}
