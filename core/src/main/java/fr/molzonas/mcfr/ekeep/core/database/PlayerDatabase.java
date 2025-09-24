package fr.molzonas.mcfr.ekeep.core.database;

import fr.molzonas.mcfr.ekeep.core.database.generated.tables.EkPlayer;
import fr.molzonas.mcfr.ekeep.core.database.generated.tables.records.EkPlayerRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PlayerDatabase implements BaseDatabase<EkPlayerRecord> {
    DSLContext dsl;

    public PlayerDatabase(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public List<EkPlayerRecord> getAll() {
        EkPlayer player = new EkPlayer("p");
        return dsl.select().from(player).fetchInto(EkPlayerRecord.class);
    }

    @Override
    public boolean add(EkPlayerRecord value) {
        return false;
    }

    @Override
    public boolean remove(EkPlayerRecord value) {
        return false;
    }

    @Override
    public boolean update(EkPlayerRecord value) {
        return false;
    }

    @Override
    public Optional<EkPlayerRecord> getById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<EkPlayerRecord> getByUuid(UUID uuid) {
        EkPlayer player = new EkPlayer("p");
        return dsl.select().from(player).where(player.UUID.eq(uuid.toString())).fetchOptionalInto(EkPlayerRecord.class);
    }
}
