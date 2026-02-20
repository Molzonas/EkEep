package fr.molzonas.ekeep.database;

import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.database.internal.DatabaseBootstrap;
import fr.molzonas.ekeep.repository.PlayerRepository;
import fr.molzonas.ekeep.repository.TeamRepository;

import java.util.Objects;

public class DatabaseManager implements AutoCloseable {
    private final Database database;
    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public DatabaseManager(EkEep.BaseContext context) {
        Objects.requireNonNull(context, "Context");

        DatabaseConfiguration dbConfig = DatabaseConfiguration.getFromConfiguration(context.config());
        this.database = DatabaseBootstrap.create(context, dbConfig);
        this.playerRepository = new PlayerRepository(database);
        this.teamRepository = new TeamRepository(database);
    }

    public Database getDatabase() {
        return this.database;
    }

    public PlayerRepository player() {
        return this.playerRepository;
    }

    public TeamRepository team() {
        return this.teamRepository;
    }

    @Override
    public void close() throws Exception {
        database.close();
    }
}
