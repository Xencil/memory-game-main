package uk.ac.gold.memorygame.observer;

import java.util.List;

import uk.ac.gold.memorygame.model.Card;

public interface ObservableGameModel {
    void addObserver(GameModelObserver observer);

    void removeObserver(GameModelObserver observer);

    void notifyCardFlipUp(Card card);

    void notifyMatch(List<Card> cards);

    void notifyMismatch(List<Card> cards);

    void notifyStateChange();

    void notifyGameOver();
}
