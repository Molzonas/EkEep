package fr.molzonas.ekeep.database.internal;

import fr.molzonas.ekeep.database.Database;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class DatabaseImpl implements Database {
    private final DSLContext dsl;
    private final AutoCloseable closeable;

    public DatabaseImpl(DSLContext dsl, AutoCloseable closeable) {
        this.dsl = Objects.requireNonNull(dsl);
        this.closeable = Objects.requireNonNull(closeable);
    }

    @Override
    public DSLContext dsl() {
        return dsl;
    }

    @Override
    public <T> T tx(Function<DSLContext, T> function) {
        DSLContext dsl = this.dsl();
        return dsl.transactionResult(conf -> function.apply(DSL.using(conf)));
    }

    @Override
    public void tx(Consumer<DSLContext> function) {
        DSLContext dsl = this.dsl();
        dsl.transaction(conf -> function.accept(DSL.using(conf)));
    }

    @Override
    public void close() {
        try {
            this.closeable.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to close the database", e);
        }
    }
}
