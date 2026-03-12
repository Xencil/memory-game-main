package uk.ac.gold.memorygame.model;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class CardTest {

    @Test
    void testInitialisation() {
        int pairId = 1;

        Card card = new Card(pairId);
        assertEquals(pairId, card.getPairId());
        assertFalse(card.isFaceUp());
        assertFalse(card.isMatched());
    }

    @Test
    void testFlipUpAndDown() {
        Card card = new Card(1);
        assertFalse(card.isFaceUp());
        card.flipUp();
        assertTrue(card.isFaceUp());
        card.flipDown();
        assertFalse(card.isFaceUp());
    }

    @Test
    void testMatching() {
        Card c1 = new Card(1);
        Card c2 = new Card(1);
        Card c3 = new Card(2);

        assertTrue(c1.matches(c2));
        assertFalse(c1.matches(c3));

        c1.setMatched(true);
        c2.setMatched(true);
        assertTrue(c1.isMatched());
        assertTrue(c2.isMatched());
    }
}
