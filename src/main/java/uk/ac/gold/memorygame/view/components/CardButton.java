package uk.ac.gold.memorygame.view.components;

import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import uk.ac.gold.memorygame.model.Card;

public abstract class CardButton extends Button {

    private static final Logger LOGGER = LogManager.getLogger();

    // Each CardButton has a reference to its own data model object so it can
    // update itself when notified.
    protected final Card cardModel;

    public CardButton(Card cardModel) {
        this.cardModel = cardModel;

        setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
        setAlignment(Pos.CENTER);
    }

    public void setCardClickHandler(Consumer<Card> handler) {
        setOnAction(_ -> handler.accept(cardModel));
    }

    public void update() {
        if (isFaceUp() == cardModel.isFaceUp()) {
            // No update necessary.
            return;
        }

        if (cardModel.isFaceUp()) {
            show();
        } else {
            hide();
        }
    }

    public void match() {
        LOGGER.debug("Matched {}", cardModel);
    }

    protected abstract boolean isFaceUp();

    protected abstract void show();

    protected abstract void hide();
}
