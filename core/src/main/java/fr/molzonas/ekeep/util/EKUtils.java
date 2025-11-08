package fr.molzonas.ekeep.util;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;

public final class EKUtils {
    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static void call(Event event) {
        Bukkit.getPluginManager().callEvent(event);
    }
    public static Component toComponent(String text) {
        return mm.deserialize(text);
    }
    public static String toString(Component component) {
        return PlainTextComponentSerializer.plainText().serialize(component);
    }
    public static long tickToMillis(long tick) {
        return tick * 50;
    }
    public static long millisToTick(long tick) {
        return tick / 50;
    }
    public static String format(long ms) {
        long s = ms / 1000 % 60;
        long m = ms / (1000 * 60) % 60;
        long h = ms / (1000 * 60 * 60);
        return "%02dh:%02dm:%02ds".formatted(h, m, s);
    }
    public static void ensureFileExists(File file) throws IOException {
        if (!file.exists()) {
            file.mkdirs();
            file.createNewFile();
        }
    }
}
