package fr.molzonas.ekeep.database;

import org.jooq.DSLContext;

import java.util.function.Consumer;
import java.util.function.Function;

public interface Database extends AutoCloseable {
    DSLContext dsl();
    <T> T tx(Function<DSLContext, T> function);
    void tx(Consumer<DSLContext> function);
    @Override void close();
}
