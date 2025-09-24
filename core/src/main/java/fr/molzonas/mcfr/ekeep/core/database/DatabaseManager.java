package fr.molzonas.mcfr.ekeep.core.database;

import org.jooq.DSLContext;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

public class DatabaseManager {
    private static DatabaseManager instance;
    private final PlayerDatabase playerDatabase;
    private final TeamDatabase teamDatabase;


    public DatabaseManager() {
        DSLContext create = DSL.using(SQLDialect.MYSQL);
        this.playerDatabase = new PlayerDatabase(create);
        this.teamDatabase = new TeamDatabase(create);
    }

    public static void init() {
        instance = new DatabaseManager();
    }

    public static DatabaseManager getInstance() {
        return instance;
    }

    public PlayerDatabase getPlayerDatabase() {
        return playerDatabase;
    }

    public TeamDatabase getTeamDatabase() {
        return teamDatabase;
    }
}
