package fr.molzonas.ekeep.api;

import java.io.IOException;
import java.util.Properties;

public class EkEepVersion {
    private static final String VERSION;
    static {
        String v = "UNKNOWN";
        try (var is = EkEepVersion.class.getClassLoader().getResourceAsStream("ekeep-version.properties")) {
            if (is != null) {
                Properties p = new Properties();
                p.load(is);
                v = p.getProperty("revision", v);
            }
        } catch (IOException ignored) {
            // No throw if version unknown : just marked as unknown
        }
        VERSION = v;
    }
    public static String get() { return VERSION; }
}
