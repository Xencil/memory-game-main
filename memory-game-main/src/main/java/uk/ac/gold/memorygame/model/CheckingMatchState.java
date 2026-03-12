package uk.ac.gold.memorygame.model;

public class CheckingMatchState extends AbstractGameState {

    private final Card first;
    private final Card second;

    public CheckingMatchState(GameModel model, Card first, Card second) {
        super(model);
        this.first = first;
        this.second = second;
    }

    @Override
    public void onEnter() {
        // Update move counter.
        model.incrementMoves();

        // Check the selected cards.
        checkMatch();
    }

    public void checkMatch() {
        LOGGER.debug("Check match between {} and {}", first, second);

        if (first.matches(second)) {
            handleMatch();
        } else {
            handleMismatch();
        }

        if (model.isGameOver()) {
            model.setState(new GameOverState(model));
        } else {
            model.setState(new WaitingForFirstCardState(model));
        }
    }

    private void handleMatch() {
        LOGGER.debug("Match: {} and {}", first, second);

        first.setMatched(true);
        second.setMatched(true);
        model.updateScore(true);
    }

    private void handleMismatch() {
        LOGGER.debug("Mismatch: {} and {}", first, second);

        first.flipDown();
        second.flipDown();
        model.updateScore(false);
    }
}
