package fr.molzonas.ekeep.event.listener;

import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import fr.molzonas.ekeep.api.exceptions.EkTeamNotFoundException;
import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.config.mapper.QuizMapper;
import fr.molzonas.ekeep.database.DatabaseManager;
import fr.molzonas.ekeep.database.generated.tables.records.EkPlayerRecord;
import fr.molzonas.ekeep.database.generated.tables.records.EkTeamRecord;
import fr.molzonas.ekeep.repository.PlayerRepository;
import fr.molzonas.ekeep.repository.TeamRepository;
import fr.molzonas.ekeep.schedule.ThreadBridge;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import me.neznamy.tab.api.nametag.NameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.Executor;

public class OnPlayerLogin implements Listener {
    private final ThreadBridge thread;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final EkEep.BaseContext baseContext;
    private final QuizMapper quizMapper;

    public OnPlayerLogin(EkEep.BaseContext baseContext, DatabaseManager dbManager, QuizMapper quizzMapper, Executor dbExecutor) {
        this.thread = new ThreadBridge(dbExecutor);
        this.baseContext = baseContext;
        this.playerRepository = dbManager.player();
        this.teamRepository = dbManager.team();
        this.quizMapper = quizzMapper;
    }

    @EventHandler
    private void onPlayerLogin(PlayerLoginEvent event) {
        async(() -> this.checkPlayerInDatabase(event.getPlayer()));
    }

    private void checkPlayerInDatabase(Player player) {
        if (!player.isConnected()) return;
        Optional<EkPlayerRecord> optEkp = this.playerRepository.selectByUuid(player.getUniqueId());
        if (optEkp.isEmpty()) {
            EkPlayerRecord newPlayerRecord = new EkPlayerRecord();
            newPlayerRecord.setUuid(player.getUniqueId().toString());
            newPlayerRecord.setLastPlayername(player.getName());
            newPlayerRecord.setCreatedAt(LocalDateTime.now());
            newPlayerRecord.setLastLogin(LocalDateTime.now());
            newPlayerRecord.setUpdatedAt(LocalDateTime.now());
            playerRepository.insert(newPlayerRecord);
            optEkp = playerRepository.selectByUuid(player.getUniqueId());
            EkPlayerRecord finalEkp = optEkp.get();
            sync(() -> startQuiz(player, finalEkp));
            return;
        }
        EkPlayerRecord ekp = optEkp.get();
        if (!player.isConnected()) return;
        Optional<EkTeamRecord> optEkt = this.teamRepository.selectByPlayer(player.getUniqueId());
        if (optEkt.isEmpty()) {
            EkPlayerRecord finalEkp = optEkp.get();
            sync(() -> startQuiz(player, finalEkp));
        } else {
            EkTeamRecord ekt = optEkt.get();
            sync(() -> initShowTeam(player, ekp, ekt));
        }
    }

    private void initShowTeam(Player player, EkPlayerRecord ekp, EkTeamRecord ekt) {
        if (!player.isConnected()) return;
        TabPlayer tp = TabAPI.getInstance().getPlayer(player.getUniqueId());
        TabAPI.getInstance().getNameTagManager().setPrefix(tp, "<color:#" + ekt.getMainColor() + ">");
        TabAPI.getInstance().getNameTagManager().setSuffix(tp, "</color>");
    }

    private void startQuiz(Player player, EkPlayerRecord ekp) {
        if (!player.isConnected()) return;
        QQuiz quiz = quizMapper.getQuiz();
        quiz.setPlayer(UUID.fromString(ekp.getUuid()));

        // TODO
        // For the test : let's say Team A
        UUID answ = UUID.fromString("1ef37b1c-9995-11f0-8869-fa163eb548f0"); // Endy
        //

        Optional<EkTeamRecord> team = this.teamRepository.selectByUuid(answ);
        if (team.isEmpty()) throw new EkTeamNotFoundException(answ, "Team not found after " + ekp.getLastPlayername() + " quiz");
        sync(() -> this.baseContext.logger().info(player.getName() + " team defined to " + team.get().getName() + " (quiz)."));
    }

    private void quizStep(QQuiz quiz, int step) {

        this.quizResult(quiz, step);
    }

    private void quizResult(QQuiz quiz, int step) {

        this.quizStep(quiz, step);
    }

    private void async(Runnable r) {
        Bukkit.getScheduler().runTaskAsynchronously(this.baseContext.plugin(), r);
    }

    private void sync(Runnable r) {
        Bukkit.getScheduler().runTask(this.baseContext.plugin(), r);
    }
}
