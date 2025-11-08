package fr.molzonas.ekeep.database;

import com.zaxxer.hikari.HikariDataSource;
import fr.molzonas.ekeep.database.provider.Provider;
import fr.molzonas.ekeep.exception.ErrorTranslator;
import fr.molzonas.ekeep.logging.DBQueryLogger;
import org.jooq.DSLContext;
import org.jooq.conf.RenderQuotedNames;
import org.jooq.conf.Settings;
import org.jooq.impl.DSL;

public class JooqProvider implements AutoCloseable {
    private final HikariDataSource hc;
    private final Provider provider;

    public JooqProvider(HikariDataSource hc, Provider provider) {
        this.hc = hc;
        this.provider = provider;
    }

    public DSLContext dsl() {
        Settings settings = new Settings()
                .withExecuteLogging(false)
                .withRenderQuotedNames(RenderQuotedNames.NEVER);
        return DSL.using(hc, provider.dialect(), settings)
                .configuration()
                .set(new DBQueryLogger())
                .set(new ErrorTranslator())
                .dsl();
    }

    @Override
    public void close() {
        hc.close();
    }
}
