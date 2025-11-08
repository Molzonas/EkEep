package fr.molzonas.ekeep.repository;

import fr.molzonas.ekeep.database.Database;
import fr.molzonas.ekeep.database.generated.tables.EkPlayer;
import fr.molzonas.ekeep.database.generated.tables.EkTeam;
import fr.molzonas.ekeep.database.generated.tables.records.EkTeamRecord;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamRepository {
    private final Database db;
    public TeamRepository(Database db) {
        this.db = db;
    }

    public boolean insert(EkTeamRecord r) {
        return this.db.dsl().executeInsert(r) > 0;
    }

    public boolean update(EkTeamRecord r) {
        return this.db.dsl().executeUpdate(r) > 0;
    }

    public boolean delete(EkTeamRecord r) {
        return this.db.dsl().executeDelete(r) > 0;
    }

    public Optional<EkTeamRecord> selectByUuid(UUID uuid) {
        EkTeam t = new EkTeam("t");
        return this.db.dsl()
                .select()
                .from(t)
                .where(t.UUID.eq(uuid.toString()))
                .fetchOptionalInto(EkTeamRecord.class);
    }

    public List<EkTeamRecord> selectAll() {
        return this.db.dsl()
                .select()
                .from(EkTeam.EK_TEAM)
                .fetchInto(EkTeamRecord.class);
    }

    public Optional<EkTeamRecord> selectByPlayer(UUID uuid) {
        EkTeam t = new EkTeam("t");
        EkPlayer p = new  EkPlayer("p");
        return this.db.dsl()
                .select()
                .from(t)
                .where(t.UUID.eq(
                        this.db.dsl()
                                .select(p.TEAM_UUID)
                                .from(p)
                                .where(p.UUID.eq(uuid.toString()))
                    )
                )
                .fetchOptionalInto(EkTeamRecord.class);
    }
}
