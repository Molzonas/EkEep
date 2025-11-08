package fr.molzonas.ekeep.inventory;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class AbstractMenu implements Listener {
    protected final JavaPlugin plugin;
    protected final Player player;
    protected Inventory inventory;

    protected AbstractMenu(final JavaPlugin plugin, final Player player) {
        this.plugin = plugin;
        this.player = player;
    }

    protected abstract Inventory build();

    public void open() {
        this.inventory = build();
        this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
        this.player.openInventory(this.inventory);
    }

    public void close() {
        HandlerList.unregisterAll(this);
        this.player.closeInventory();
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event) {
        if (event.getPlayer().equals(this.player) && event.getInventory().equals(this.inventory)) {
            HandlerList.unregisterAll(this);
        }
    }
}
