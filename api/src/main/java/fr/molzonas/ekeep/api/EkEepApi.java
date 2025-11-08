package fr.molzonas.ekeep.api;

import fr.molzonas.ekeep.api.exceptions.ApiUnavailableException;
import fr.molzonas.ekeep.api.manager.PlayerManager;
import fr.molzonas.ekeep.api.manager.QuizManager;
import fr.molzonas.ekeep.api.manager.TeamManager;
import org.bukkit.Bukkit;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * EkEep API entry point.
 * <p>
 * Provides access to core managers such as {@link PlayerManager}, {@link TeamManager}, and {@link QuizManager}.
 * <br>
 * Use {@link #get()} or {@link #orNull()} to retrieve the active API instance via Bukkit's Services Manager.
 *
 * @see PlayerManager
 * @see TeamManager
 * @see QuizManager
 */
public interface EkEepApi {
    PlayerManager players();
    TeamManager teams();
    QuizManager quiz();

    /**
     * Fetch non-null EkEep API from Bukkit Services Manager.
     *
     * @throws ApiUnavailableException if the plugin is disabled
     * @return {@link EkEepApi} loaded
     * @see #orNull()
     */
    static @Nonnull EkEepApi get() throws ApiUnavailableException {
        EkEepApi api = orNull();
        if (api == null) throw new ApiUnavailableException("EkEep API could not be loaded (plugin disabled)");
        return api;
    }

    /**
     * Fetch nullable EkEep API from Bukkit Services Manager.
     *
     * @return {@link EkEepApi}
     * @see #get()
     */
    static @Nullable EkEepApi orNull() {
        return Bukkit.getServicesManager().load(EkEepApi.class);
    }
}
