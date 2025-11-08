package fr.molzonas.ekeep.bootstrap;

import fr.molzonas.ekeep.api.EkEepApi;
import fr.molzonas.ekeep.api.EkEepApiImpl;
import fr.molzonas.ekeep.api.enums.ReloadableType;
import fr.molzonas.ekeep.api.lifecycle.Reloadable;
import fr.molzonas.ekeep.config.Config;
import fr.molzonas.ekeep.config.YamlConfig;
import fr.molzonas.ekeep.config.keys.MainConfigKeys;
import fr.molzonas.ekeep.config.mapper.QuizMapper;
import fr.molzonas.ekeep.database.DatabaseManager;
import fr.molzonas.ekeep.event.listener.OnPlayerLogin;
import fr.molzonas.ekeep.i18n.Message;
import fr.molzonas.ekeep.logging.EKLogger;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class EkEep extends JavaPlugin {
    public record BaseContext(EkEep plugin, EKLogger logger, Config config) {}

    private final List<Reloadable> reloadableList = new LinkedList<>();
    private DatabaseManager databaseManager;
    private QuizMapper quizMapper;
    private EKLogger logger;
    private Executor databaseExecutor = Executors.newSingleThreadExecutor();

    private BaseContext baseContext;

    private Config ekConfig;

    @Override
    public void onEnable() {
        this.initResources();
        this.initConfig();
        this.initLang();
        this.initDatabase();
        this.initEvents();
        this.initCommands();
        this.initApi();

        this.logger.info("EkEep has been enabled");
    }

    @Override
    public void onDisable() {
        getServer().getServicesManager().unregister(EkEepApi.class);
        Message.unload();
        this.logger.info("EkEep has been disabled");
    }

    private void initResources() {
        this.saveDefaultConfig();
        this.saveResource("quiz.yml", false);
        this.saveResource("i18n/messages_fr.properties", false);
        this.saveResource("i18n/messages_en.properties", false);
    }

    private void initConfig() {
        this.ekConfig = reloadable(new YamlConfig(this.getConfig()));
        this.logger = new EKLogger(this, this.ekConfig);
        this.quizMapper = new QuizMapper(YamlConfiguration.loadConfiguration(Objects.requireNonNull(this.getTextResource("quiz.yml"))));
        this.baseContext = new BaseContext(this, this.logger, this.ekConfig);
    }

    private void initLang() {
        Locale l = Locale.forLanguageTag(getEKConfig().get(MainConfigKeys.LOCALE, String.class).orElse("en-us"));
        Message.load(l, this.getDataFolder());
    }

    private void initDatabase() {
        this.databaseManager = new DatabaseManager(baseContext);
    }

    private void initEvents() {
        Bukkit.getPluginManager().registerEvents(new OnPlayerLogin(this.baseContext, this.databaseManager, this.quizMapper, databaseExecutor), this);
    }

    private void initCommands() {
        // TODO
    }

    private void initApi() {
        getServer()
                .getServicesManager()
                .register(
                        EkEepApi.class,
                        new EkEepApiImpl(),
                        this,
                        ServicePriority.Normal
                );
    }

    public Config getEKConfig() {
        return this.ekConfig;
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
