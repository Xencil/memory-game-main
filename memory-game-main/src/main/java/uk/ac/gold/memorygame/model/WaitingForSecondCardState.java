package uk.ac.gold.memorygame.model;

public class WaitingForSecondCardState extends AbstractGameState {

    private final Card firstCard;

    public WaitingForSecondCardState(GameModel model, Card firstCard) {
        super(model);
        this.firstCard = firstCard;
    }

    @Override
    public void selectCard(Card secondCard) {
        if (secondCard.isFaceUp() || secondCard.isMatched()) {
            // Ignore invalid selection.
            LOGGER.info("Already matched or selected: {}", secondCard);
            return;
        }

        // Flip the second card.
        secondCard.flipUp();

        // Notify observers.
        model.notifyCardFlipUp(secondCard);

        // Transition to checking match.
        model.setState(new CheckingMatchState(model, firstCard, secondCard));
    }
}
