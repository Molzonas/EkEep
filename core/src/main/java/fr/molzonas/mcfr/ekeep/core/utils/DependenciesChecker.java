package fr.molzonas.mcfr.ekeep.core.utils;

import fr.molzonas.mcfr.ekeep.core.Ekeep;

import java.util.HashMap;
import java.util.Map;

public class DependenciesChecker implements Reloadable {
    public static final String BSTATS = "bstats";
    public static final String PROTOCOL_LIB = "protocollib";
    public static final String MODEL_ENGINE = "modelengine";
    public static final String MYTHIC_MOBS = "mythicmobs";
    public static final String FANCY_HOLOGRAMS = "fancyholograms";

    private final Ekeep core;
    private final Map<String, Boolean> cache = new HashMap<>();

    public DependenciesChecker(Ekeep core) {
        this.core = core;
    }

    public boolean isPresent(String dependency) {
        return cache.computeIfAbsent(dependency, x ->
            this.core.getServer().getPluginManager().getPlugin(dependency) != null
        );
    }

    @Override
    public void reload() {
        this.cache.clear();
    }
}
