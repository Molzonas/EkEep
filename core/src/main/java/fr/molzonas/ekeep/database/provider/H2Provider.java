package fr.molzonas.ekeep.database.provider;

import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public class H2Provider implements DbProvider {
    @Override
    public SQLDialect dialect() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration databaseConfiguration) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String migrationLocation() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDatasourceClassName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public String getDriverClassName() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
