package uk.ac.gold.memorygame.config;

import java.util.List;

public class TextCardDeck implements CardDeck {

    private final String name;
    private final List<String> items;

    public TextCardDeck(String name, List<String> items) {
        this.name = name;
        this.items = items;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int numberOfItems() {
        return items.size();
    };

    @Override
    public List<String> getItems() {
        return items;
    }

    @Override
    public String get(int pairId) {
        return items.get(pairId);
    }
}
