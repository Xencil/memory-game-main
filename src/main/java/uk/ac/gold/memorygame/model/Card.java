package uk.ac.gold.memorygame.model;

public class Card {

    private final int pairId; // Unique integer ID for each card pair.

    private boolean faceUp = false;
    private boolean matched = false;

    public Card(int pairId) {
        this.pairId = pairId;
    }

    /*
     * -----------------------------
     * Identity & matching
     * -----------------------------
     */

    public int getPairId() {
        return -1;
    }

    public boolean matches(Card other) {
        return false;
    }

    @Override
    public String toString() {
        return String.format(
                "%s[pairId=%d]",
                getClass().getSimpleName(),
                getPairId());
    }

    /*
     * -----------------------------
     * State
     * -----------------------------
     */

    public boolean isFaceUp() {
        return false;
    }

    public boolean isMatched() {
        return false;
    }

    public void flipUp() {
    }

    public void flipDown() {
    }

    public void setMatched(boolean matched) {
    }
}
