package uk.ac.gold.memorygame.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import uk.ac.gold.memorygame.model.exceptions.BoardStateException;

public class Board {

    private final List<Card> cards = new ArrayList<>();
    private int numberOfPairs;


    public Board(int numberOfPairs) {
    	this(numberOfPairs,true);
    }

    public Board(int numberOfPairs, boolean shuffle) {
        this.numberOfPairs =numberOfPairs;

        for (int i =0;i <numberOfPairs;i++){
            cards.add(new Card(i));
            cards.add(new Card(i));
        }

        if (shuffle) {
            Collections.shuffle(cards);
        }
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
        return cards.size();
    }

    public int numberOfPairs() {
    	return numberOfPairs;
    }

    /*
     * -----------------------------
     * Game-related queries
     * -----------------------------
     */

    public int countMatchedCards() {
    	int counter =0;

        for(Card card : cards){
            if(card.isMatched()){
            	counter++;
            }
        }

        if (counter % 2 != 0) {
            throw new BoardStateException("Matched card count must be even, counted: " + counter);
        }

        return counter;
    }

    public int countMatchedPairs() {
    	return countMatchedCards()/2;
    }

    public boolean allCardsMatched() {
    	return countMatchedPairs() ==numberOfPairs;
    }
}
