package fr.molzonas.ekeep.bootstrap;

import fr.molzonas.ekeep.api.enums.ReloadableType;
import fr.molzonas.ekeep.api.lifecycle.Reloadable;
import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.YamlConfig;
import fr.molzonas.ekeep.logging.EKLogger;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;

public final class EkEep extends JavaPlugin {
    private static EkEep instance;
    private final List<Reloadable> reloadableList = new LinkedList<>();

    private Config ekConfig;

    @Override
    public void onEnable() {
        instance = this;

        this.initResources();
        this.initConfig();
        this.initLang();
        this.initDatabase();
        this.initEvents();
        this.initCommands();
        this.initApi();

        EKLogger.info("EkEep has been enabled");
    }

    @Override
    public void onDisable() {
        EKLogger.info("EkEep has been disabled");
    }

    private void initResources() {
        this.saveDefaultConfig();
        this.saveResource("quiz.yml", false);
        this.saveResource("i18n/messages_fr.properties", false);
        this.saveResource("i18n/messages_en.properties", false);
    }

    private void initConfig() {
        this.ekConfig = reloadable(new YamlConfig(this.getConfig()));
    }

    private void initLang() {
        // TODO
    }

    private void initDatabase() {
        // TODO
    }

    private void initEvents() {
        // TODO
    }

    private void initCommands() {
        // TODO
    }

    private void initApi() {

    }

    public Config getEKConfig() {
        return this.ekConfig;
    }

    public static EkEep getInstance() {
        return instance;
    }

    private <T> T reloadable(T reloadable) {
        if (reloadable instanceof Reloadable r) {
            reloadableList.add(r);
        }
        return reloadable;
    }

    public void reloadAll() {
        reloadableList.forEach(Reloadable::reload);
    }

    public void reloadOnly(ReloadableType type) {
        reloadableList.stream()
                .filter(x -> x.reloadableType().equals(type))
                .forEach(Reloadable::reload);
    }
}
