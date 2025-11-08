package fr.molzonas.ekeep.logging;

import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;
import fr.molzonas.ekeep.util.EKUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.bukkit.plugin.java.JavaPlugin;

public class EKLogger {
    public static final String LOG_PREFIX = "[EkEep] ";
    private final JavaPlugin plugin;
    private final Config config;
    private final ComponentLogger logger;
    
    public EKLogger(JavaPlugin plugin, Config config) {
        this.plugin = plugin;
        this.logger = this.plugin.getComponentLogger();
        this.config = config;
    }

    private String addPrefix(String message) {
        return LOG_PREFIX + message;
    }
    private Component addPrefix(Component message) {
        return Component.text().append(EKUtils.toComponent(LOG_PREFIX)).append(message).build();
    }

    public void info(String message, Object... args) {
        logger.info(addPrefix(message), args);
    }
    public void info(Component message, Object... args) {
        logger.info(addPrefix(message), args);
    }
    public void warn(String message, Object... args) {
        logger.warn(addPrefix(message), args);
    }
    public void warn(Component message, Object... args) {
        logger.warn(addPrefix(message), args);
    }
    public void error(String message, Object... args) {
        logger.error(addPrefix(message), args);
    }
    public void error(Component message, Object... args) {
        logger.error(addPrefix(message), args);
    }
    public void debug(String message, Object... args) {
        if (config.is(MainConfigKeys.DEBUG))
            logger.debug(addPrefix(message), args);
    }
    public void debug(Component message, Object... args) {
        if (config.is(MainConfigKeys.DEBUG))
            logger.debug(addPrefix(message), args);
    }
}
