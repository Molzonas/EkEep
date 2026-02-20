package fr.molzonas.ekeep.database.provider;

import com.zaxxer.hikari.HikariConfig;
import fr.molzonas.ekeep.database.DatabaseConfiguration;
import org.jooq.SQLDialect;

public class MySQLProvider implements DbProvider {
    @Override
    public SQLDialect dialect() {
        return SQLDialect.MYSQL;
    }

    @Override
    public String jdbcUrl(DatabaseConfiguration databaseConfiguration) {
        StringBuilder ssl = new StringBuilder("jdbc:mysql://").append(databaseConfiguration.getHost())
                .append(":").append(databaseConfiguration.getPort()).append("/").append(databaseConfiguration.getDatabase())
                .append("?useUnicode=true&characterEncoding=utf8&sessionVariables=sql_mode='STRICT_ALL_TABLES'");
        if (databaseConfiguration.isSslEnabled()) {
            ssl.append("&useSSL=true");
            if (databaseConfiguration.getSslMode().equals("VERIFY_CA")) ssl.append("&verifyServerCertificate=true");
            else ssl.append("&requireSSL=true");
        }
        return ssl.toString();
    }

    @Override
    public void tune(HikariConfig hc, DatabaseConfiguration databaseConfiguration) {
        hc.addDataSourceProperty("cachePrepStmts","true");
        hc.addDataSourceProperty("prepStmtCacheSize","250");
        hc.addDataSourceProperty("prepStmtCacheSqlLimit","2048");

        hc.setConnectionInitSql("SET NAMES utf8mb4");
    }

    @Override
    public String migrationLocation() {
        return "classpath:database/migration/mysql";
    }

    @Override
    public String getDatasourceClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.mysql.cj.jdbc.MysqlDataSource";
    }

    @Override
    public String getDriverClassName() {
        return "fr.molzonas.mcfr.ekeep.libs.database.mysql.cj.jdbc.Driver";
    }
}
