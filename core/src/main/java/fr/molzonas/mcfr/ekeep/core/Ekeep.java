package fr.molzonas.mcfr.ekeep.core;

import fr.molzonas.mcfr.ekeep.core.configs.Config;
import fr.molzonas.mcfr.ekeep.core.utils.GitUpdateChecker;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ekeep extends JavaPlugin {
    private static Ekeep instance;
    private Config mainConfig;

    @Override
    public void onEnable() {
        instance = this;

        initConfig();

        // TODO everything

        GitUpdateChecker.logCheckAsync(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    private void initConfig() {
        this.mainConfig = new Config(this.getConfig());
    }

    public Config getMainConfig() {
        return this.mainConfig;
    }

    public static Ekeep getInstance() {
        return instance;
    }
}
