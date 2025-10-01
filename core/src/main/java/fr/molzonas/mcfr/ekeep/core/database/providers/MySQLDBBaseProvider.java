package fr.molzonas.mcfr.ekeep.core.database.providers;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.mcfr.ekeep.core.configs.DatabaseConfiguration;
import fr.molzonas.mcfr.ekeep.core.database.base.BaseProvider;
import org.jooq.SQLDialect;

public class MySQLDBBaseProvider implements BaseProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.MYSQL;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration c) {
        StringBuilder ssl = new StringBuilder("jdbc:mysql://").append(c.getHost())
                .append(":").append(c.getPort()).append("/").append(c.getDatabase())
                .append("?useUnicode=true&characterEncoding=utf8&sessionVariables=sql_mode='STRICT_ALL_TABLES'");
        if (c.isSslEnabled()) {
            ssl.append("&useSSL=true");
            if (c.getSslMode().equals("VERIFY_CA")) ssl.append("&verifyServerCertificate=true");
            else ssl.append("&requireSSL=true");
        }
        return ssl.toString();
    }

    @Override
    public void tune(HikariConfig hc, DatabaseConfiguration mc) {
        hc.addDataSourceProperty("cachePrepStmts","true");
        hc.addDataSourceProperty("prepStmtCacheSize","250");
        hc.addDataSourceProperty("prepStmtCacheSqlLimit","2048");
    }

    @Override
    public String migrationLocation() {
        return "classpath:database/migration/mariadb";
    }

    @Override
    public String getDatasourceClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.mysql.cj.jdbc.MysqlDataSource";
    }
}
