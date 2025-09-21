package fr.molzonas.mcfr.ekeep.core;

import fr.molzonas.mcfr.ekeep.core.utils.GitUpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class Ekeep extends JavaPlugin {

    @Override
    public void onEnable() {
        // TODO everything

        GitUpdateChecker.logCheckAsync(this);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
