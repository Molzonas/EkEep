package fr.molzonas.mcfr.ekeep.api;

import org.bukkit.Bukkit;

import java.util.Optional;

public final class EKProvider {
    private static volatile EkeepAPI cached;

    private EKProvider() {}

    public static EkeepAPI getPlugin() {
        EkeepAPI api = cached;
        if (api != null) return api;
        api = load().orElseThrow(() -> new IllegalStateException("EK core is not present or not enabled."));
        cached = api;
        return api;
    }

    public static Optional<EkeepAPI> getOptPlugin() {
        EkeepAPI api = cached;
        return api != null ? Optional.of(api) : load();
    }

    public static void invalidate() { cached = null; }

    private static Optional<EkeepAPI> load() {
        return Optional.ofNullable(Bukkit.getServicesManager().load(EkeepAPI.class));
    }
}
