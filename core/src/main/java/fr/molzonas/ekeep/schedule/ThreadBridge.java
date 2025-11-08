package fr.molzonas.ekeep.schedule;

import fr.molzonas.ekeep.bootstrap.EkEep;
import org.bukkit.Bukkit;

import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.Executor;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ThreadBridge {
    private final Executor db;

    public ThreadBridge(Executor db) {
        this.db = db;
    }

    public <T> CompletableFuture<T> dbCall(Callable<T> c) {
        return CompletableFuture.supplyAsync(() -> {
            try { return c.call(); } catch (Exception e) { throw new CompletionException(e); }
        }, db);
    }

    public <T> CompletableFuture<T> dbSupply(Supplier<T> runnable) {
        return CompletableFuture.supplyAsync(runnable, db);
    }

    public <T> CompletableFuture<T> syncSupply(Supplier<T> sup) {
        CompletableFuture<T> future = new CompletableFuture<>();
        Bukkit.getScheduler().runTask(EkEep.getInstance(), () -> {
            try {
                future.complete(sup.get());
            } catch (Throwable t) {
                future.completeExceptionally(t);
            }
        });
        return future;
    }

    public CompletableFuture<Void> syncRun(Runnable run) {
        return syncSupply(() -> { run.run(); return null; });
    }

    public <T, U> Function<T, CompletableFuture<U>> thenDb(Function<T, U> fn) {
        return t -> dbSupply(() -> fn.apply(t));
    }
    public <T, U> Function<T, CompletableFuture<U>> thenSync(Function<T, U> fn) {
        return t -> syncSupply(() -> fn.apply(t));
    }

}
