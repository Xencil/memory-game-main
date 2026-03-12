package uk.ac.gold.memorygame.model;

public interface GameState {
    // Lifecycle hook, called once a state is activated.
    void onEnter();

    // Controller calls this when handling UI input from view.
    void selectCard(Card card);
}
