package uk.ac.gold.memorygame.observer;

public interface GameModelObserver {
    void onStateChange();

    void onGameOver();
}
