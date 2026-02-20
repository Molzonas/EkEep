package fr.molzonas.ekeep.database;

import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;

import java.util.List;

public class DatabaseConfiguration {
    private String type = "mariadb";
    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "root";
    private String username = "username";
    private String password = "password";
    private String file = "plugins/MyPlugin/myplugin.db";
    private int maximumPoolSize = 10;
    private int minimumIdle = 2;
    private int maximumLifetime = 1800000;
    private int connectionTimeout = 10000;
    private int idleTimeout = 60000;
    private boolean sslEnabled = false;
    private boolean baselineOnMigrate = false;
    private String sslMode = "";

    private DatabaseConfiguration() {
    }

    public static DatabaseConfiguration.Builder builder() {
        return new DatabaseConfiguration.Builder();
    }

    public boolean isSQLite() {
        return "sqlite".equalsIgnoreCase(type);
    }

    public boolean isMariaDB() {
        return List.of("maria", "mariadb", "mariadbsql").contains(type.toLowerCase());
    }

    public boolean isPostgreSQL() {
        return List.of("postgres", "postgresql", "postgre").contains(type.toLowerCase());
    }

    public String getType() {
        return type;
    }

    public String getHost() {
        return host;
    }

    public int getPort() {
        return port;
    }

    public String getDatabase() {
        return database;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getFile() {
        return file;
    }

    public int getMaximumPoolSize() {
        return maximumPoolSize;
    }

    public int getMinimumIdle() {
        return minimumIdle;
    }

    public int getMaximumLifetime() {
        return maximumLifetime;
    }

    public int getConnectionTimeout() {
        return connectionTimeout;
    }

    public int getIdleTimeout() {
        return idleTimeout;
    }

    public boolean isSslEnabled() {
        return sslEnabled;
    }

    public String getSslMode() {
        return sslMode;
    }

    public boolean isBaselineOnMigrate() {
        return baselineOnMigrate;
    }

    public static DatabaseConfiguration getDefault() {
        return new DatabaseConfiguration();
    }

    public static DatabaseConfiguration getFromConfiguration(Config config) {
        DatabaseConfiguration.Builder builder = DatabaseConfiguration.builder();
        if (config.exists(MainConfigKeys.TYPE)) builder.setType(config.getOrDefault(MainConfigKeys.TYPE));
        if (config.exists(MainConfigKeys.HOST)) builder.setHost(config.getOrDefault(MainConfigKeys.HOST));
        if (config.exists(MainConfigKeys.PORT)) builder.setPort(config.getOrDefault(MainConfigKeys.PORT));
        if (config.exists(MainConfigKeys.SCHEMA)) builder.setDatabase(config.getOrDefault(MainConfigKeys.SCHEMA));
        if (config.exists(MainConfigKeys.USERNAME)) builder.setUsername(config.getOrDefault(MainConfigKeys.USERNAME));
        if (config.exists(MainConfigKeys.PASSWORD)) builder.setPassword(config.getOrDefault(MainConfigKeys.PASSWORD));
        if (config.exists(MainConfigKeys.FILE)) builder.setFile(config.getOrDefault(MainConfigKeys.FILE));
        if (config.exists(MainConfigKeys.MAXIMUM_POOL_SIZE))
            builder.setMaximumPoolSize(config.getOrDefault(MainConfigKeys.MAXIMUM_POOL_SIZE));
        if (config.exists(MainConfigKeys.MINIMUM_IDLE))
            builder.setMinimumIdle(config.getOrDefault(MainConfigKeys.MINIMUM_IDLE));
        if (config.exists(MainConfigKeys.MAXIMUM_LIFETIME))
            builder.setMaximumLifetime(config.getOrDefault(MainConfigKeys.MAXIMUM_LIFETIME));
        if (config.exists(MainConfigKeys.CONNECTION_TIMEOUT))
            builder.setConnectionTimeout(config.getOrDefault(MainConfigKeys.CONNECTION_TIMEOUT));
        if (config.exists(MainConfigKeys.IDLE_TIMEOUT))
            builder.setIdleTimeout(config.getOrDefault(MainConfigKeys.IDLE_TIMEOUT));
        if (config.exists(MainConfigKeys.SSL_ENABLED))
            builder.setSslEnabled(config.getOrDefault(MainConfigKeys.SSL_ENABLED));
        if (config.exists(MainConfigKeys.SSL_MODE)) builder.setSslMode(config.getOrDefault(MainConfigKeys.SSL_MODE));
        if (config.exists(MainConfigKeys.BASELINE_ON_MIGRATE)) builder.setBaselineOnMigrate(config.getOrDefault(MainConfigKeys.BASELINE_ON_MIGRATE));
        return builder.build();
    }

    public static class Builder {
        final DatabaseConfiguration mzDatabaseConfiguration;

        public Builder() {
            this.mzDatabaseConfiguration = new DatabaseConfiguration();
        }

        public void setType(String type) {
            this.mzDatabaseConfiguration.type = type;
        }

        public void setHost(String host) {
            this.mzDatabaseConfiguration.host = host;
        }

        public void setPort(int port) {
            this.mzDatabaseConfiguration.port = port;
        }

        public void setDatabase(String database) {
            this.mzDatabaseConfiguration.database = database;
        }

        public void setUsername(String username) {
            this.mzDatabaseConfiguration.username = username;
        }

        public void setPassword(String password) {
            this.mzDatabaseConfiguration.password = password;
        }

        public void setFile(String file) {
            this.mzDatabaseConfiguration.file = file;
        }

        public void setMaximumPoolSize(int maximumPoolSize) {
            this.mzDatabaseConfiguration.maximumPoolSize = maximumPoolSize;
        }

        public void setMinimumIdle(int minimumIdle) {
            this.mzDatabaseConfiguration.minimumIdle = minimumIdle;
        }

        public void setMaximumLifetime(int maximumLifetime) {
            this.mzDatabaseConfiguration.maximumLifetime = maximumLifetime;
        }

        public void setConnectionTimeout(int connectionTimeout) {
            this.mzDatabaseConfiguration.connectionTimeout = connectionTimeout;
        }

        public void setIdleTimeout(int idleTimeout) {
            this.mzDatabaseConfiguration.idleTimeout = idleTimeout;
        }

        public void setSslEnabled(boolean sslEnabled) {
            this.mzDatabaseConfiguration.sslEnabled = sslEnabled;
        }

        public void setSslMode(String sslMode) {
            this.mzDatabaseConfiguration.sslMode = sslMode;
        }

        public void setBaselineOnMigrate(boolean baselineOnMigrate) {
            this.mzDatabaseConfiguration.baselineOnMigrate = baselineOnMigrate;
        }

        public DatabaseConfiguration build() {
            return this.mzDatabaseConfiguration;
        }
    }
}