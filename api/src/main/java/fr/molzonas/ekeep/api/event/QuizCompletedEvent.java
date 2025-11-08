package fr.molzonas.ekeep.api.event;

import fr.molzonas.ekeep.api.domain.quiz.QQuiz;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.UUID;

public class QuizCompletedEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID playerUuid;
    private final UUID adoptedTeamUuid;
    @Nullable private final QQuiz quiz;

    public QuizCompletedEvent(UUID playerUuid, UUID adoptedTeamUuid) {
        this(playerUuid, adoptedTeamUuid, null);
    }

    public QuizCompletedEvent(@Nonnull UUID playerUuid, @Nonnull UUID adoptedTeamUuid, @Nullable QQuiz quiz) {
        this.playerUuid = playerUuid;
        this.adoptedTeamUuid = adoptedTeamUuid;
        this.quiz = quiz;
    }

    public @Nonnull UUID getPlayerUuid() {
        return playerUuid;
    }

    public @Nonnull UUID getAdoptedTeamUuid() {
        return adoptedTeamUuid;
    }

    public @Nullable QQuiz getQuiz() {
        return quiz;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }

    public static @Nonnull HandlerList getHandlerList() {
        return HANDLERS;
    }
}
