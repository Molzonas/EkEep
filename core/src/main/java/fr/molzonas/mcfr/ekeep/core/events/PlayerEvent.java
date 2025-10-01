package fr.molzonas.mcfr.ekeep.core.events;

import fr.molzonas.mcfr.ekeep.api.entities.QuizAnswer;
import fr.molzonas.mcfr.ekeep.api.entities.QuizQuestion;
import fr.molzonas.mcfr.ekeep.api.entities.QuizTotal;
import fr.molzonas.mcfr.ekeep.core.Ekeep;
import fr.molzonas.mcfr.ekeep.core.database.DatabaseManager;
import fr.molzonas.mcfr.ekeep.core.database.generated.tables.records.EkPlayerRecord;
import fr.molzonas.mcfr.ekeep.core.database.generated.tables.records.EkTeamRecord;
import fr.molzonas.mcfr.ekeep.core.utils.EKUtils;
import fr.molzonas.mcfr.ekeep.core.utils.Reloadable;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import org.apache.commons.collections4.bidimap.DualLinkedHashBidiMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class PlayerEvent implements Listener, Reloadable {
    private static final DualLinkedHashBidiMap<Team, UUID> PLAYERTEAM_CACHE = new DualLinkedHashBidiMap<>();

    @EventHandler
    public void onPlayerLogin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PLAYERTEAM_CACHE.containsValue(player.getUniqueId())) {
            assignTeam(event, PLAYERTEAM_CACHE.getKey(player.getUniqueId()));
            return;
        }
        Optional<EkPlayerRecord> optEkp = DatabaseManager.getInstance().getPlayerDatabase().getByUuid(player.getUniqueId());
        if (optEkp.isEmpty()) {
            onNoPlayerFound(event);
            return;
        }
        EkPlayerRecord ekp = optEkp.get();
        Optional<EkTeamRecord> optTeam = DatabaseManager.getInstance().getTeamDatabase().getByUuid(UUID.fromString(ekp.getTeamUuid()));
        if (optTeam.isEmpty()) {
            onNoTeam(event, ekp);
        } else {
            onTeam(event, ekp, optTeam.get());
        }
    }

    @EventHandler
    public void onPlayerLogout(PlayerQuitEvent event) {
        Team team = PLAYERTEAM_CACHE.getKey(event.getPlayer().getUniqueId());
        if (team == null) return;
        team.removePlayer(event.getPlayer());
    }

    private void onNoTeam(PlayerJoinEvent event, EkPlayerRecord player) {
        if (player == null) {
            onError("No user found on team assignation.");
            return;
        }
        if (!launchQuestions(event, player)) {
            onError("Quiz failed for " + event.getPlayer().getName() + " team assignation.");
        } else {
            onTeam(event, player, DatabaseManager.getInstance().getTeamDatabase()
                    .getByUuid(UUID.fromString(player.getTeamUuid())).orElse(null));
        }
    }

    private void onTeam(PlayerJoinEvent event, EkPlayerRecord player, EkTeamRecord team) {
        if (player == null) {
            onError("No user found on player join initialization for " + event.getPlayer().getName() + ".");
            return;
        }
        if (team == null) {
            onError("No team found on player join intialization for " + event.getPlayer().getName() + ". Starting team assignation...");
            onNoTeam(event, player);
            return;
        }
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        String teamName = ("ek_" + team.getUuid().replace("-", "")).substring(0, 16);
        Team scTeam = scoreboard.getTeam(teamName);
        if (scTeam == null) {
            scTeam = scoreboard.registerNewTeam(teamName);
            scTeam.setOption(Team.Option.NAME_TAG_VISIBILITY, Team.OptionStatus.ALWAYS);
            scTeam.color(NamedTextColor.nearestTo(Objects.requireNonNull(TextColor.fromHexString(team.getMainColor()))));
        }
        PLAYERTEAM_CACHE.put(scTeam, event.getPlayer().getUniqueId());
    }

    private void assignTeam(PlayerJoinEvent event, Team team) {
        team.addPlayer(event.getPlayer());
    }

    private void onNoPlayerFound(PlayerJoinEvent event) {
        EkPlayerRecord ekp = new EkPlayerRecord();
        ekp.setUuid(event.getPlayer().getUniqueId().toString());
        ekp.setCreatedAt(LocalDateTime.now());
        boolean rs = DatabaseManager.getInstance().getPlayerDatabase().add(ekp);
        if (rs) onNoTeam(event, DatabaseManager.getInstance().getPlayerDatabase()
                .getByUuid(UUID.fromString(ekp.getUuid())).orElse(null));
        else onError("Cannot register user " + event.getPlayer().getName() + " in database.");
    }

    private void onError(String msg) {
        EKUtils.error("Error on player team management: " + msg);
    }

    private boolean launchQuestions(PlayerJoinEvent event, EkPlayerRecord player) {
        Optional<QuizTotal> qt = Ekeep.getInstance().getQuizConfig().getQuizTotal();
        if (qt.isEmpty()) return false;
        for (QuizQuestion q : qt.get().getQuestions()) {
            event.getPlayer().sendMessage(EKUtils.toComponent(q.getQuestion()));
            for (QuizAnswer a : q.getAnswers()) {
                event.getPlayer().sendMessage("- " + a.getAnswer());
            }
            event.getPlayer().sendMessage("---");
        }
        // TODO quiz to select your team
        return true;
    }

    @Override
    public void reload() {
        PLAYERTEAM_CACHE.clear();
    }
}
