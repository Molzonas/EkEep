package fr.molzonas.mcfr.ekeep.core.database;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class DatabaseExec {
    private final Plugin plugin;
    private final Executor executor;
    public DatabaseExec(Plugin plugin, Executor executor) {
        this.plugin = plugin;
        this.executor = executor;
    }
    public DatabaseExec(Plugin plugin) {
        this.plugin = plugin;
        this.executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors(), r -> new Thread(r, "EkEep-DB-"));
    }

    public <T> CompletableFuture<T> execute(Supplier<T> supplier) {
        return CompletableFuture.supplyAsync(supplier, executor);
    }

    public void async(Runnable runnable) {
        CompletableFuture.runAsync(runnable);
    }

    public <T> void backToMain(CompletableFuture<T> completableFuture, Consumer<T> then) {
        completableFuture.thenAccept(res -> Bukkit.getScheduler().runTask(plugin, () -> then.accept(res)));
    }
}
