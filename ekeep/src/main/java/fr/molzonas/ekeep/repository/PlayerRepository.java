package fr.molzonas.ekeep.repository;

import fr.molzonas.ekeep.database.Database;
import fr.molzonas.ekeep.database.generated.tables.EkEventLog;
import fr.molzonas.ekeep.database.generated.tables.EkPlayer;
import fr.molzonas.ekeep.database.generated.tables.records.EkEventLogRecord;
import fr.molzonas.ekeep.database.generated.tables.records.EkPlayerRecord;
import fr.molzonas.mcfr.ekeep.api.entities.EKEventLog;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerRepository {
    private final Database db;
    public PlayerRepository(Database db) {
        this.db = db;
    }

    public boolean insert(EkPlayerRecord r) {
        return this.db.dsl().executeInsert(r) > 0;
    }

    public boolean update(EkPlayerRecord r) {
        return this.db.dsl().executeUpdate(r) > 0;
    }

    public boolean delete(EkPlayerRecord r) {
        return this.db.dsl().executeDelete(r) > 0;
    }

    public boolean delete(UUID uuid) {
        EkPlayer p = new  EkPlayer("p");
        return this.db.dsl().deleteFrom(p)
                .where(p.UUID.eq(uuid.toString()))
                .execute() > 0;
    }

    public Optional<EkPlayerRecord> selectByUuid(UUID uuid) {
        EkPlayer p = new  EkPlayer("p");
        return this.db.dsl()
                .select()
                .from(p)
                .where(p.UUID.eq(uuid.toString()))
                .fetchOptionalInto(EkPlayerRecord.class);
    }

    public List<EkPlayerRecord> selectAll() {
        return this.db.dsl()
                .select()
                .from(EkPlayer.EK_PLAYER)
                .fetchInto(EkPlayerRecord.class);
    }

    public List<EkPlayerRecord> selectByTeam(UUID teamUuid) {
        EkPlayer p = new  EkPlayer("p");
        return this.db.dsl()
                .select()
                .from(p)
                .where(p.TEAM_UUID.eq(teamUuid.toString()))
                .fetchInto(EkPlayerRecord.class);
    }

    public List<EkEventLogRecord> selectEventLog(UUID uuid) {
        EkEventLog e = new  EkEventLog("e");
        return this.db.dsl()
                .select()
                .from(e)
                .where(e.UUID_PLAYER.eq(uuid.toString()))
                .fetchInto(EkEventLogRecord.class);
    }
}
