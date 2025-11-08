package fr.molzonas.ekeep.database;

import com.zaxxer.hikari.HikariDataSource;
import fr.molzonas.ekeep.database.provider.Provider;
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.util.function.Consumer;
import java.util.function.Function;

public final class DatabaseImpl implements Database {
    private final JooqProvider provider;
    public DatabaseImpl(DatabaseConfiguration config) {
        HikariDataSource dataSource = DataSourceFactory.create(config);
        this.provider = new JooqProvider(dataSource, Provider.select(config.getType()));
    }

    @Override
    public DSLContext dsl() {
        return provider.dsl();
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
        this.provider.close();
    }
}
