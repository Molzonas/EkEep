package fr.molzonas.mcfr.ekeep.core;

import fr.molzonas.mcfr.ekeep.core.configs.Config;
import fr.molzonas.mcfr.ekeep.core.configs.DatabaseConfiguration;
import fr.molzonas.mcfr.ekeep.core.configs.QuizConfiguration;
import fr.molzonas.mcfr.ekeep.core.configs.keys.DBConfigKeys;
import fr.molzonas.mcfr.ekeep.core.configs.keys.MainConfigKeys;
import fr.molzonas.mcfr.ekeep.core.database.DatabaseManager;
import fr.molzonas.mcfr.ekeep.core.events.PlayerEvent;
import fr.molzonas.mcfr.ekeep.core.utils.EKUtils;
import fr.molzonas.mcfr.ekeep.core.utils.GitUpdateChecker;
import fr.molzonas.mcfr.ekeep.core.utils.Reloadable;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public final class Ekeep extends JavaPlugin {
    private static Ekeep instance;
    private Config mainConfig;
    private QuizConfiguration quizConfiguration;
    private final List<Reloadable> reloadables = Collections.synchronizedList(new LinkedList<>());
    private final List<Listener> listeners = Collections.synchronizedList(new LinkedList<>());

    @Override
    public void onEnable() {
        instance = this;

        initResources();
        initConfig();
        initDatabase();
        initListeners();
        initCommands();

        if (this.mainConfig.get(MainConfigKeys.ENABLE_UPDATE_CHECK))
            GitUpdateChecker.logCheckAsync(this);

        EKUtils.info("Plugin loaded.");
    }

    @Override
    public void onDisable() {
        if (DatabaseManager.getInstance() != null) DatabaseManager.getInstance().shutdown();
        HandlerList.unregisterAll(this);
        EKUtils.info("Plugin unloaded.");
    }

    private void initResources() {
        this.saveDefaultConfig();
        this.saveResource("quiz.yml", false);
    }

    private void initConfig() {
        this.mainConfig = save(new Config(this.getConfig()));
        this.quizConfiguration = save(new QuizConfiguration(YamlConfiguration.loadConfiguration(Objects.requireNonNull(this.getTextResource("quiz.yml")))));
    }

    private void initDatabase() {
        // TODO REMOVE
        for (DBConfigKeys key : DBConfigKeys.values()) {
            EKUtils.info(String.format("%s -> {%s}", key.toString(), this.mainConfig.get(key)));
        }
        // TODO REMOVE

        save(DatabaseManager.init(DatabaseConfiguration.getFromConfiguration(this.mainConfig)));
    }

    private void initListeners() {
        Bukkit.getPluginManager().registerEvents(save(new PlayerEvent()), this);
    }

    private void initCommands() {

    }

    public Config getMainConfig() {
        return this.mainConfig;
    }

    public QuizConfiguration getQuizConfig() { return this.quizConfiguration; }

    public static Ekeep getInstance() {
        return instance;
    }

    public void reload() {
        reloadables.forEach(Reloadable::reload);
    }

    private <T> T save(T value) {
        if (value instanceof Reloadable r) {
            this.reloadables.add(r);
        }
        if (value instanceof Listener l) {
            this.listeners.add(l);
        }
        return value;
    }
}
