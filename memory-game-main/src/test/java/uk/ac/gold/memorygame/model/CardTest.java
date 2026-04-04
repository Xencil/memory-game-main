package uk.ac.gold.memorygame.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class CardTest {

    @Test
    @Order(1)
    void testInitialisation() {
        int pairId = 1;

        Card card = new Card(pairId);
        assertEquals(pairId, card.getPairId());
        assertFalse(card.isFaceUp());
        assertFalse(card.isMatched());
    }

    @Test
    @Order(2)
    void testFlipUpAndDown() {
        Card card = new Card(1);
        assertFalse(card.isFaceUp());
        card.flipUp();
        assertTrue(card.isFaceUp());
        card.flipDown();
        assertFalse(card.isFaceUp());
    }

    @Test
    @Order(3)
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
