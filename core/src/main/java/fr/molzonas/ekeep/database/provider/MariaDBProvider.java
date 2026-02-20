package fr.molzonas.ekeep.database.provider;

import org.jooq.SQLDialect;

public class MariaDBProvider extends MySQLProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.MARIADB;
    }

    @Override
    public String migrationLocation() {
        return "classpath:database/migration/mariadb";
    }
}
