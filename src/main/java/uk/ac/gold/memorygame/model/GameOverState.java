package uk.ac.gold.memorygame.model;

public class GameOverState extends AbstractGameState {

    public GameOverState(GameModel model) {
        super(model);
    }

    @Override
    public void onEnter() {
        LOGGER.debug("onEnter");
        model.notifyGameOver();
    }
}
