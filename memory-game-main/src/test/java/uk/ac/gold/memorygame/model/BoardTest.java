package uk.ac.gold.memorygame.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import uk.ac.gold.memorygame.model.exceptions.BoardStateException;

class BoardTest {

    @Test
    void testInitialisation() {
        int numberOfPairs = 2;
        Board board = new Board(numberOfPairs);

        assertEquals(numberOfPairs, board.numberOfPairs());
        assertEquals(numberOfPairs * 2, board.numberOfCards());
    }

    @Test
    void testMatchCount() {
        int numberOfPairs = 2;
        Board board = new Board(numberOfPairs, false);

        for (int i = 0; i < board.numberOfCards() + 1; i++) {
            if (i % 2 == 0) {
                // Even numbers should not throw an exception.
                try {
                    assertEquals(i, board.countMatchedCards());
                } catch (BoardStateException exception) {
                }
            } else {
                // Odd numbers should throw an exception.
                BoardStateException exception = assertThrowsExactly(
                        BoardStateException.class, () -> {
                            board.countMatchedCards();
                        });
                assertEquals(
                        "Matched card count must be even, counted: " + i,
                        exception.getMessage());
            }

            if (i < board.numberOfCards()) {
                // Update current card matched state.
                board.getCard(i).setMatched(true);
            }
        }
    }

    @Test
    void testPairCount() {
        int numberOfPairs = 2;
        Board board = new Board(numberOfPairs, false);

        for (int i = 0; i < board.numberOfCards(); i++) {
            if (i % 2 == 0) {
                // Even numbers should not throw an exception.
                try {
                    assertEquals(i / 2, board.countMatchedPairs());
                } catch (BoardStateException exception) {
                }
            } else {
                // Odd numbers should throw an exception.
                BoardStateException exception = assertThrowsExactly(
                        BoardStateException.class, () -> {
                            board.countMatchedCards();
                        });
                assertEquals(
                        "Matched card count must be even, counted: " + i,
                        exception.getMessage());
            }

            if (i < board.numberOfCards()) {
                // Update current card matched state.
                board.getCard(i).setMatched(true);
            }
        }
    }

    @Test
    void testAllCardsMatched() {
        int numberOfPairs = 4;
        Board board = new Board(numberOfPairs, false);

        for (int i = 0; i < board.numberOfCards(); i += 2) {
            // Set the next pair of cards to matched.
            board.getCard(i).setMatched(true);
            board.getCard(i + 1).setMatched(true);
            // Only return true after final pair is matched.
            if (i < board.numberOfCards() - 2) {
                assertFalse(board.allCardsMatched());
            } else {
                assertTrue(board.allCardsMatched());
            }
        }
    }
}
