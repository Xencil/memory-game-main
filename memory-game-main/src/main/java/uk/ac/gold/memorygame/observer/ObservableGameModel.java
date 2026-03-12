package uk.ac.gold.memorygame.observer;

public interface ObservableGameModel {
    void addObserver(GameModelObserver observer);

    void removeObserver(GameModelObserver observer);

    void notifyStateChange();

    void notifyGameOver();
}
