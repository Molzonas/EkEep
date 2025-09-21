package fr.molzonas.mcfr.ekeep.core.utils;

import fr.molzonas.mcfr.ekeep.core.Ekeep;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class EKUtils {
    private static Ekeep plugin;
    private static final MiniMessage mm = MiniMessage.miniMessage();
    public static final String LOG_PREFIX = "[EkEep] ";

    private EKUtils() {}

    public static void init(Ekeep plugin) {
        EKUtils.plugin = plugin;
    }
    public static Ekeep getPlugin() {
        return plugin;
    }

    /* Component <-> String */
    public static Component toComponent(String text) {
        return mm.deserialize(text);
    }
    public static String toString(Component component) {
        return mm.serialize(component);
    }

    /* Logging */
    public static void info(String message, Object... args) {
        plugin.getComponentLogger().info(message, args);
    }

    public static void info(Component message, Object... args) {
        plugin.getComponentLogger().info(message, args);
    }
    public static void warn(String message, Object... args) {
        plugin.getComponentLogger().warn(message, args);
    }
    public static void warn(Component message, Object... args) {
        plugin.getComponentLogger().warn(message, args);
    }
    public static void error(String message, Object... args) {
        plugin.getComponentLogger().error(message, args);
    }
    public static void error(Component message, Object... args) {
        plugin.getComponentLogger().error(message, args);
    }
    public static void debug(String message, Object... args) {
        plugin.getComponentLogger().debug(message, args);
    }
    public static void debug(Component message, Object... args) {
        plugin.getComponentLogger().debug(message, args);
    }
}
