package fr.molzonas.ekeep.logging;

import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;
import fr.molzonas.ekeep.util.EKUtils;
import net.kyori.adventure.text.Component;

public class EKLogger {
    public static final String LOG_PREFIX = "[EkEep] ";

    private EKLogger() {}

    private static String addPrefix(String message) {
        return LOG_PREFIX + message;
    }
    private static Component addPrefix(Component message) {
        return Component.text().append(EKUtils.toComponent(LOG_PREFIX)).append(message).build();
    }

    public static void info(String message, Object... args) {
        EkEep.getInstance().getComponentLogger().info(addPrefix(message), args);
    }
    public static void info(Component message, Object... args) {
        EkEep.getInstance().getComponentLogger().info(addPrefix(message), args);
    }
    public static void warn(String message, Object... args) {
        EkEep.getInstance().getComponentLogger().warn(addPrefix(message), args);
    }
    public static void warn(Component message, Object... args) {
        EkEep.getInstance().getComponentLogger().warn(addPrefix(message), args);
    }
    public static void error(String message, Object... args) {
        EkEep.getInstance().getComponentLogger().error(addPrefix(message), args);
    }
    public static void error(Component message, Object... args) {
        EkEep.getInstance().getComponentLogger().error(addPrefix(message), args);
    }
    public static void debug(String message, Object... args) {
        if (EkEep.getInstance().getEKConfig().is(MainConfigKeys.DEBUG))
            EkEep.getInstance().getComponentLogger().debug(addPrefix(message), args);
    }
    public static void debug(Component message, Object... args) {
        if (EkEep.getInstance().getEKConfig().is(MainConfigKeys.DEBUG))
            EkEep.getInstance().getComponentLogger().debug(addPrefix(message), args);
    }
}
