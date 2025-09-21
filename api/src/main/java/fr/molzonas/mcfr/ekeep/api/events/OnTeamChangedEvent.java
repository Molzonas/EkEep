package fr.molzonas.mcfr.ekeep.api.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class OnTeamChangedEvent extends Event implements Cancellable {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID playerUuid;
    private final String oldTeamId;
    private final String newTeamId;
    private final String reason;
    private final String infos;
    private boolean cancelled;

    public OnTeamChangedEvent(UUID playerUuid, String oldTeamId, String newTeamId, String reason, String infos) {
        this.playerUuid = playerUuid;
        this.oldTeamId = oldTeamId;
        this.newTeamId = newTeamId;
        this.reason = reason;
        this.infos = infos;
    }

    @Override
    public boolean isCancelled() {
        return this.cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public UUID getPlayerUuid() {
        return playerUuid;
    }

    public String getOldTeamId() {
        return oldTeamId;
    }

    public String getNewTeamId() {
        return newTeamId;
    }

    public String getReason() {
        return reason;
    }

    public String getInfos() {
        return infos;
    }
}
