package fr.molzonas.ekeep.logging;

import org.bukkit.Bukkit;
import org.jooq.ExecuteContext;
import org.jooq.ExecuteListener;

public class DBQueryLogger implements ExecuteListener {
    private static final long SLOW_MS = 100;
    private long start;

    @Override
    public void start(ExecuteContext ctx) {
        start = System.nanoTime();
    }

    @Override
    public void end(ExecuteContext ctx) {
        long ms = (System.nanoTime() - start) / 1_000_000;
        if (ms >= SLOW_MS) {
            EKLogger.warn("[DB] Slow query (" + ms + " ms): " + ctx.sql());
        }
    }

    @Override
    public void exception(ExecuteContext ctx) {
        EKLogger.warn("[DB] SQL error: " + ctx.sql() + " -> " + ctx.sqlException());
    }
}
