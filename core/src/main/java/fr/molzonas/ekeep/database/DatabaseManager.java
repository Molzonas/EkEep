package fr.molzonas.ekeep.database;

import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.repository.PlayerRepository;
import fr.molzonas.ekeep.repository.TeamRepository;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Predicate;

public class DatabaseManager {
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;
    private final EkEep.BaseContext baseContext;
    private ExecutorService executors = Executors.newFixedThreadPool(10);

    public DatabaseManager(EkEep.BaseContext baseContext) {
        this.baseContext = baseContext;
        DatabaseConfiguration dbConfig = DatabaseConfiguration.getFromConfiguration(this.baseContext.config());
        Database db = new DatabaseImpl(dbConfig);

        playerRepository = new PlayerRepository(db);
        teamRepository = new TeamRepository(db);
    }

    public PlayerRepository player() {
        return this.playerRepository;
    }

    public TeamRepository team() {
        return this.teamRepository;
    }
}
