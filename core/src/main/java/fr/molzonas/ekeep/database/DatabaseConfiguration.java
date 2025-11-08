package fr.molzonas.ekeep.database;

import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;

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
        if (config.exists(MainConfigKeys.TYPE)) builder.setType(config.getOrDefault(MainConfigKeys.TYPE));
        if (config.exists(MainConfigKeys.HOST)) builder.setHost(config.getOrDefault(MainConfigKeys.HOST));
        if (config.exists(MainConfigKeys.PORT)) builder.setPort(config.getOrDefault(MainConfigKeys.PORT));
        if (config.exists(MainConfigKeys.SCHEMA)) builder.setDatabase(config.getOrDefault(MainConfigKeys.SCHEMA));
        if (config.exists(MainConfigKeys.USERNAME)) builder.setUsername(config.getOrDefault(MainConfigKeys.USERNAME));
        if (config.exists(MainConfigKeys.PASSWORD)) builder.setPassword(config.getOrDefault(MainConfigKeys.PASSWORD));
        if (config.exists(MainConfigKeys.FILE)) builder.setFile(config.getOrDefault(MainConfigKeys.FILE));
        if (config.exists(MainConfigKeys.MAXIMUM_POOL_SIZE)) builder.setMaximumPoolSize(config.getOrDefault(MainConfigKeys.MAXIMUM_POOL_SIZE));
        if (config.exists(MainConfigKeys.MINIMUM_IDLE)) builder.setMinimumIdle(config.getOrDefault(MainConfigKeys.MINIMUM_IDLE));
        if (config.exists(MainConfigKeys.MAXIMUM_LIFETIME)) builder.setMaximumLifetime(config.getOrDefault(MainConfigKeys.MAXIMUM_LIFETIME));
        if (config.exists(MainConfigKeys.CONNECTION_TIMEOUT)) builder.setConnectionTimeout(config.getOrDefault(MainConfigKeys.CONNECTION_TIMEOUT));
        if (config.exists(MainConfigKeys.IDLE_TIMEOUT)) builder.setIdleTimeout(config.getOrDefault(MainConfigKeys.IDLE_TIMEOUT));
        if (config.exists(MainConfigKeys.SSL_ENABLED)) builder.setSslEnabled(config.getOrDefault(MainConfigKeys.SSL_ENABLED));
        if (config.exists(MainConfigKeys.SSL_MODE)) builder.setSslMode(config.getOrDefault(MainConfigKeys.SSL_MODE));
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
