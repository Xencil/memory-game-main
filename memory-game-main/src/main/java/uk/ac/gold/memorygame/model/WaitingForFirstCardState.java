package uk.ac.gold.memorygame.model;

public class WaitingForFirstCardState extends AbstractGameState {

    public WaitingForFirstCardState(GameModel model) {
        super(model);
    }

    @Override
    public void selectCard(Card card) {
        if (card.isMatched()) {
            // Ignore invalid selection.
            LOGGER.info("Already matched: {}", card);
            return;
        }

        // Flip the first card.
        card.flipUp();

        // Transition to the next state.
        model.setState(new WaitingForSecondCardState(model, card));
    }
}
