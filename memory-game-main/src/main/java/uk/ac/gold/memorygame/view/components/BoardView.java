package uk.ac.gold.memorygame.view.components;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.scene.Parent;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import uk.ac.gold.memorygame.config.CardDeck;
import uk.ac.gold.memorygame.model.Card;

public class BoardView {

    private static final Logger LOGGER = LogManager.getLogger();
    private final GridPane root = new GridPane(5, 5);
    private final Map<Card, CardButton> cardViewButtons = new HashMap<>();
    public Parent getRoot() {
        return root;
    }

    public CardButton getCardButton(Card card) {
        return cardViewButtons.get(card);
    }

    public void buildCards(List<Card> cards, CardDeck<?>  cardDeck) {
        int numOffCards = cards.size();
        int ncols = (int) Math.ceil(Math.sqrt(numOffCards));
        int nrows = (int) Math.ceil((double) numOffCards / ncols);

        // Create column constraints.
        for (int c = 0; c < ncols; c++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0 / ncols);
            root.getColumnConstraints().add(cc);
        }

        for (int r = 0; r< nrows;r++) {
            RowConstraints rc = new RowConstraints();
            rc.setPercentHeight(100.0 / nrows);
            root.getRowConstraints().add(rc);
        }

        for (int i= 0;i<numOffCards;i++){
            Card cModel=cards.get(i);
            CardButton cButton =CardButtonFactory.create(cardDeck,cModel);
            cardViewButtons.put(cModel,cButton);
            
            int x = i/ ncols;
            int y = i % ncols;
            root.add(cButton,x, y);
        }
    }

    public void setCardClickHandler(Consumer<Card> handler) {

        for (CardButton cv : cardViewButtons.values()) {
            cv.setCardClickHandler(handler);
        }
    }

    public void update() {

        for (CardButton cv : cardViewButtons.values())
            cv.update();
    }
}
