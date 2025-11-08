package fr.molzonas.ekeep.api;

import fr.molzonas.ekeep.api.manager.*;

public class EkEepApiImpl implements EkEepApi {
    private final PlayerManager playerManager;
    private final QuizManager quizManager;
    private final TeamManager teamManager;

    public EkEepApiImpl() {
        this.playerManager = new PlayerManagerImpl();
        this.quizManager = new QuizManagerImpl();
        this.teamManager = new TeamManagerImpl();
    }

    @Override
    public PlayerManager players() {
        return this.playerManager;
    }

    @Override
    public TeamManager teams() {
        return this.teamManager;
    }

    @Override
    public QuizManager quiz() {
        return this.quizManager;
    }
}
