package fr.molzonas.mcfr.ekeep.core.database.providers;

import org.jooq.SQLDialect;

public class MariaDBBaseProvider extends MySQLDBBaseProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.MARIADB;
    }
}
