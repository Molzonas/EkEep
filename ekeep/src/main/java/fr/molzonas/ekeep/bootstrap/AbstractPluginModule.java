package fr.molzonas.ekeep.bootstrap;

import fr.molzonas.ekeep.api.lifecycle.Reloadable;
import org.bukkit.Server;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public abstract class AbstractPluginModule implements Reloadable {
    protected final JavaPlugin plugin;
    protected AbstractPluginModule(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public abstract void enable();
    public abstract void disable();
    @Override
    public void reload() {
        this.disable();
        this.enable();
    }

    protected final Logger log() { return this.plugin.getLogger(); }
    protected final Server server() { return this.plugin.getServer(); }
}
