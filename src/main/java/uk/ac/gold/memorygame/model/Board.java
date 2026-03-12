package uk.ac.gold.memorygame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.gold.memorygame.model.exceptions.BoardStateException;

public class Board {

    private final List<Card> cards = new ArrayList<>();

    public Board(int numberOfPairs) {
    }

    public Board(int numberOfPairs, boolean shuffle) {
    }

    /*
     * -----------------------------
     * Access
     * -----------------------------
     */

    public List<Card> getCards() {
        // Prevent modification.
        return Collections.unmodifiableList(cards);
    }

    public Card getCard(int id) {
        return cards.get(id);
    }

    public int numberOfCards() {
        return -1;
    }

    public int numberOfPairs() {
        return -1;
    }

    /*
     * -----------------------------
     * Game-related queries
     * -----------------------------
     */

    public int countMatchedCards() {
        return -1;
    }

    public int countMatchedPairs() {
        return -1;
    }

    public boolean allCardsMatched() {
        return false;
    }
}
