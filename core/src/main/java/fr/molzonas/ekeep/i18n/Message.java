package fr.molzonas.ekeep.i18n;

import fr.molzonas.ekeep.util.EKUtils;
import net.kyori.adventure.text.Component;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Message {
    public static final Locale DEFAULT_LOCALE = Locale.ENGLISH;
    private static final String DIRECTORY = "i18n";
    private static final String BUNDLE_NAME = "messages";
    private static final ConcurrentHashMap<Locale, ResourceBundle> CACHE = new ConcurrentHashMap<>();
    private static Locale currentLocale = Locale.ENGLISH;
    private static File dataFolder;
    private static boolean isLoaded = false;

    private Message() {}

    public static void load(Locale locale, File pluginDataFolder) {
        if (isLoaded) unload();
        if (locale == null) locale = DEFAULT_LOCALE;
        Message.currentLocale = locale;
        clearCache();
        dataFolder = pluginDataFolder;
        isLoaded = true;
    }

    public static void unload() {
        dataFolder = null;
        clearCache();
        isLoaded = false;
    }

    public static String of(String key, Object... args) {
        return of(key, currentLocale, args);
    }

    public static String of(String key, Locale locale, Object... args) {
        checkLoaded();
        Locale loc = (locale != null) ? locale : currentLocale;
        ResourceBundle bundle = getBundle(loc);
        String pattern = bundle.containsKey(key) ? bundle.getString(key) : ResourceBundle.getBundle(BUNDLE_NAME, loc).getString(key);
        MessageFormat mf = new  MessageFormat(pattern, loc);
        return mf.format(args == null ? new Object[0] : args);
    }

    public static Component ofCpmt(String key, Object... args) {
        return EKUtils.toComponent(of(key, args));
    }

    public static Component ofCpmt(String key, Locale locale, Object... args) {
        return EKUtils.toComponent(of(key, locale, args));
    }

    public static void clearCache() {
        CACHE.clear();
        ResourceBundle.clearCache(Message.class.getClassLoader());
    }

    private static ResourceBundle getBundle(Locale loc) {
        return CACHE.computeIfAbsent(loc, l ->
                ResourceBundle.getBundle(BUNDLE_NAME, l, new Utf8Control()));
    }

    public static Locale getCurrentLocale() {
        return currentLocale;
    }

    private static void checkLoaded() {
        if (!isLoaded) throw new IllegalStateException("I18N not loaded.");
    }

    private static class Utf8Control extends ResourceBundle.Control {
        @Override
        public ResourceBundle newBundle(String baseName, Locale locale, String format,
                                        ClassLoader loader, boolean reload) throws IOException {
            String bundleName = toBundleName(baseName, locale);
            String resourceName = toResourceName(bundleName, "properties");
            File file = new File(dataFolder, DIRECTORY + File.separator + resourceName);

            try (FileInputStream fis = new FileInputStream(file)) {
                try (InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
                    Properties prop = new Properties();
                    prop.load(isr);
                    return new PropertiesResourceBundle(prop);
                }
            }
        }
    }

    private static class PropertiesResourceBundle extends ResourceBundle {
        private final Properties props;
        PropertiesResourceBundle(Properties props) { this.props = props; }
        @Override protected Object handleGetObject(@NotNull String key) { return props.getProperty(key); }
        @NotNull
        @Override public Enumeration<String> getKeys() {
            return Collections.enumeration(props.stringPropertyNames());
        }
        @Override public boolean containsKey(@NotNull String key) { return props.containsKey(key); }
    }
}
