package fr.molzonas.ekeep.event.handler;

import fr.molzonas.ekeep.bootstrap.EkEep;
import fr.molzonas.ekeep.util.EKUtils;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;

public final class EventWaiter implements Listener {
    private final EkEep plugin;

    public EventWaiter(EkEep plugin) {
        this.plugin = plugin;
    }

    public void register() {
        Bukkit.getPluginManager().registerEvents(this, this.plugin);
    }

    public <T> CompletableFuture<T> awaitChatReply(Player p,
                                                   Duration timeout,
                                                   Function<String, T> parser) {
        CompletableFuture<T> future = new CompletableFuture<>();
        UUID id = p.getUniqueId();

        var task = Bukkit.getScheduler().runTaskLater(this.plugin,
                () -> {
                    if (!future.isDone()) future.completeExceptionally(new RuntimeException("timeout"));
                },
                timeout.toMillis() / 50L);

        Listener listener = new Listener() {
            @EventHandler
            public void onQuit(PlayerQuitEvent e) {
                if (e.getPlayer().getUniqueId().equals(id)) {
                    cleanup();
                    future.completeExceptionally(new RuntimeException("quit"));
                }
            }

            @EventHandler
            public void onChat(AsyncChatEvent e) {
                if (!e.getPlayer().getUniqueId().equals(id)) return;
                String msg = EKUtils.toString(e.message());
                T value;
                try {
                    value = parser.apply(msg.trim());
                } catch (Exception ex) {
                    return;
                }
                e.setCancelled(true);
                cleanup();
                future.complete(value);
            }

            private void cleanup() {
                HandlerList.unregisterAll(this);
                task.cancel();
            }
        };

        Bukkit.getPluginManager().registerEvents(listener, this.plugin);
        return future;
    }
}