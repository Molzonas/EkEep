package fr.molzonas.mcfr.ekeep.core.database;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BaseDatabase<T> {
    boolean add(T value);

    boolean remove(T value);

    boolean update(T value);

    Optional<T> getById(int id);

    Optional<T> getByUuid(UUID uuid);

    List<T> getAll();
}
