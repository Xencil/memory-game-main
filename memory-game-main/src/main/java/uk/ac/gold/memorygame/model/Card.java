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
        return pairId;
    }

    public boolean matches(Card other) {
        if(other==null){
            return false;
        }
        return this.pairId ==other.pairId;
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
        return faceUp;
    }

    public boolean isMatched() {
        return matched;
    }

    public void flipUp() {
    	faceUp =true;
    }

    public void flipDown() {
    	faceUp = false;
    }

    public void setMatched(boolean matched) {
    	this.matched =matched;
    }
}
