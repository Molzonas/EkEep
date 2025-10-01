package fr.molzonas.mcfr.ekeep.core.configs;

import fr.molzonas.mcfr.ekeep.core.configs.keys.DBConfigKeys;

import java.util.List;

public class DatabaseConfiguration {
    private String type = "mariadb";
    private String host = "127.0.0.1";
    private int port = 3306;
    private String database = "root";
    private String  username = "username";
    private String password = "password";
    private String file = "plugins/MyPlugin/myplugin.db";
    private int maximumPoolSize = 10;
    private int minimumIdle = 2;
    private int maximumLifetime = 1800000;
    private int connectionTimeout = 10000;
    private int idleTimeout = 60000;
    private boolean sslEnabled = false;
    private String sslMode = "";

    private DatabaseConfiguration() {}

    public static Builder builder() {
        return new Builder();
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

    public static DatabaseConfiguration getDefault() {
        return new DatabaseConfiguration();
    }

    public static DatabaseConfiguration getFromConfiguration(Config config) {
        DatabaseConfiguration.Builder builder = DatabaseConfiguration.builder();
        if (config.exists(DBConfigKeys.TYPE)) builder.setType(config.get(DBConfigKeys.TYPE));
        if (config.exists(DBConfigKeys.HOST)) builder.setHost(config.get(DBConfigKeys.HOST));
        if (config.exists(DBConfigKeys.PORT)) builder.setPort(config.get(DBConfigKeys.PORT));
        if (config.exists(DBConfigKeys.SCHEMA)) builder.setDatabase(config.get(DBConfigKeys.SCHEMA));
        if (config.exists(DBConfigKeys.USERNAME)) builder.setUsername(config.get(DBConfigKeys.USERNAME));
        if (config.exists(DBConfigKeys.PASSWORD)) builder.setPassword(config.get(DBConfigKeys.PASSWORD));
        if (config.exists(DBConfigKeys.FILE)) builder.setFile(config.get(DBConfigKeys.FILE));
        if (config.exists(DBConfigKeys.MAXIMUM_POOL_SIZE)) builder.setMaximumPoolSize(config.get(DBConfigKeys.MAXIMUM_POOL_SIZE));
        if (config.exists(DBConfigKeys.MINIMUM_IDLE)) builder.setMinimumIdle(config.get(DBConfigKeys.MINIMUM_IDLE));
        if (config.exists(DBConfigKeys.MAXIMUM_LIFETIME)) builder.setMaximumLifetime(config.get(DBConfigKeys.MAXIMUM_LIFETIME));
        if (config.exists(DBConfigKeys.CONNECTION_TIMEOUT)) builder.setConnectionTimeout(config.get(DBConfigKeys.CONNECTION_TIMEOUT));
        if (config.exists(DBConfigKeys.IDLE_TIMEOUT)) builder.setIdleTimeout(config.get(DBConfigKeys.IDLE_TIMEOUT));
        if (config.exists(DBConfigKeys.SSL_ENABLED)) builder.setSslEnabled(config.get(DBConfigKeys.SSL_ENABLED));
        if (config.exists(DBConfigKeys.SSL_MODE)) builder.setSslMode(config.get(DBConfigKeys.SSL_MODE));
        return builder.build();
    }

    public static class Builder {
        final DatabaseConfiguration mzDatabaseConfiguration;
        public Builder() {
            this.mzDatabaseConfiguration = new DatabaseConfiguration();
        }
        public Builder setType(String type) {
            this.mzDatabaseConfiguration.type = type;
            return this;
        }

        public Builder setHost(String host) {
            this.mzDatabaseConfiguration.host = host;
            return this;
        }

        public Builder setPort(int port) {
            this.mzDatabaseConfiguration.port = port;
            return this;
        }

        public Builder setDatabase(String database) {
            this.mzDatabaseConfiguration.database = database;
            return this;
        }

        public Builder setUsername(String username) {
            this.mzDatabaseConfiguration.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.mzDatabaseConfiguration.password = password;
            return this;
        }

        public Builder setFile(String file) {
            this.mzDatabaseConfiguration.file = file;
            return this;
        }

        public Builder setMaximumPoolSize(int maximumPoolSize) {
            this.mzDatabaseConfiguration.maximumPoolSize = maximumPoolSize;
            return this;
        }

        public Builder setMinimumIdle(int minimumIdle) {
            this.mzDatabaseConfiguration.minimumIdle = minimumIdle;
            return this;
        }

        public Builder setMaximumLifetime(int maximumLifetime) {
            this.mzDatabaseConfiguration.maximumLifetime = maximumLifetime;
            return this;
        }

        public Builder setConnectionTimeout(int connectionTimeout) {
            this.mzDatabaseConfiguration.connectionTimeout = connectionTimeout;
            return this;
        }

        public Builder setIdleTimeout(int idleTimeout) {
            this.mzDatabaseConfiguration.idleTimeout = idleTimeout;
            return this;
        }

        public Builder setSslEnabled(boolean sslEnabled) {
            this.mzDatabaseConfiguration.sslEnabled = sslEnabled;
            return this;
        }

        public Builder setSslMode(String sslMode) {
            this.mzDatabaseConfiguration.sslMode = sslMode;
            return this;
        }

        public DatabaseConfiguration build() {
            return this.mzDatabaseConfiguration;
        }
    }
}
