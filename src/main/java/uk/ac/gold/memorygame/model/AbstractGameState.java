package uk.ac.gold.memorygame.model;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

abstract class AbstractGameState implements GameState {

    protected final Logger LOGGER = LogManager.getLogger(getClass());
    protected final GameModel model;

    protected AbstractGameState(GameModel model) {
        this.model = model;
    }

    @Override
    public void onEnter() {
        // Default: do nothing
    }

    @Override
    public void selectCard(Card card) {
        throw new IllegalStateException("Cannot select card in state: " + this);
    }

    public String toString() {
        return getClass().getSimpleName();
    }
}
