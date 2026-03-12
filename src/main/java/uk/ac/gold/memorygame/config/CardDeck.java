package uk.ac.gold.memorygame.config;

import java.util.List;

public interface CardDeck {
    String name();

    int numberOfItems();

    List<String> getItems();

    String get(int pairId);
}
