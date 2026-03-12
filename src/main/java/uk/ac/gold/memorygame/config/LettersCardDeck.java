package uk.ac.gold.memorygame.config;

import java.util.Arrays;

public class LettersCardDeck extends TextCardDeck {

    public LettersCardDeck() {
        super("Letters", Arrays.asList("ABCDEFGHIJKLMNOPQRSTUVWXYZ".split("")));
    }
}
