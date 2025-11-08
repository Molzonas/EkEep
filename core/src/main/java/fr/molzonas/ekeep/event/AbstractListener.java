package fr.molzonas.ekeep.event;

import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractListener implements Listener {
    protected final JavaPlugin plugin;
    protected AbstractListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }
    public void register() {
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
    }

    public void unregister() {
        HandlerList.unregisterAll(this);
    }
}
