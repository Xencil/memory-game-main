package uk.ac.gold.memorygame.observer;

import java.util.List;

import uk.ac.gold.memorygame.model.Card;

public interface GameModelObserver {
    void onCardFlipUp(Card card);

    void onMatch(List<Card> cards);

    void onMismatch(List<Card> cards);

    void onStateChange();

    void onGameOver();
}
