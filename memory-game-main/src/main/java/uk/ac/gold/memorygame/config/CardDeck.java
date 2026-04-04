package uk.ac.gold.memorygame.config;

import java.util.List;

public interface CardDeck<E> {
    String name();
    int numberOfItems();
    List<E> getItems();
    E get(int pairId);
}
