package fr.molzonas.mcfr.ekeep.core.database.impl;

import fr.molzonas.mcfr.ekeep.core.database.base.BaseDatabase;
import fr.molzonas.mcfr.ekeep.core.database.generated.tables.records.EkTeamRecord;
import org.jooq.DSLContext;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeamDatabase implements BaseDatabase<EkTeamRecord> {
    DSLContext dsl;

    public TeamDatabase(DSLContext dsl) {
        this.dsl = dsl;
    }

    @Override
    public boolean add(EkTeamRecord value) {
        return false;
    }

    @Override
    public boolean remove(EkTeamRecord value) {
        return false;
    }

    @Override
    public boolean update(EkTeamRecord value) {
        return false;
    }

    @Override
    public Optional<EkTeamRecord> getById(int id) {
        return Optional.empty();
    }

    @Override
    public Optional<EkTeamRecord> getByUuid(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<EkTeamRecord> getAll() {
        return List.of();
    }
}
