package uk.ac.gold.memorygame.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class GameModelTest {

    int numberOfPairs = 2;
    Board board = new Board(numberOfPairs, false); // No shuffle.

    int pointsForCorrect = 2;
    int penaltyForIncorrect = 1;
    MoveBasedScoring scoring = new MoveBasedScoring(
            pointsForCorrect, penaltyForIncorrect);
    GameModel gameModel = new GameModel(board, scoring);

    @Test
    void testInitialisation() {
        assertEquals(board, gameModel.getBoard());
        assertEquals(0, gameModel.getScore());
    }

    @Test
    void testWaitingForFirstCard() {
        Card card = board.getCard(0);
        gameModel.selectCard(card);
        assertTrue(card.isFaceUp());
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);
    }

    @Test
    void testSecondCardCorrect() {
        Card first = board.getCard(0);
        Card second = board.getCard(1);
        assertTrue(first.matches(second));

        // Select first card.
        gameModel.selectCard(first);
        assertTrue(first.isFaceUp());
        assertFalse(first.isMatched());
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);

        // Select second card.
        gameModel.selectCard(second);

        // Check matched state.
        assertTrue(first.isMatched());
        assertTrue(second.isMatched());
        assertTrue(first.isFaceUp());
        assertTrue(second.isFaceUp());

        // Check returns to first card state.
        assertTrue(gameModel.getState() instanceof WaitingForFirstCardState);
    }

    @Test
    void testSecondCardIncorrect() {
        Card first = board.getCard(0);
        Card second = board.getCard(2);
        assertFalse(first.matches(second));

        // Select first card.
        gameModel.selectCard(first);
        assertTrue(first.isFaceUp());
        assertFalse(first.isMatched());
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);

        // Select second card.
        gameModel.selectCard(second);

        // Check non-matched state.
        assertFalse(first.isMatched());
        assertFalse(second.isMatched());
        assertFalse(first.isFaceUp());
        assertFalse(second.isFaceUp());

        // Check returns to first card state.
        assertTrue(gameModel.getState() instanceof WaitingForFirstCardState);
    }

    @Test
    void testInvalidSelection() {
        Card first = board.getCard(0);

        // Select first card.
        gameModel.selectCard(first);
        // Select first card again.
        gameModel.selectCard(first);

        // Check invalid selection was ignored and that the model is still
        // waiting for a valid second card.
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);
    }

    @Test
    void testAllCardsCorrect() {
        Card first = board.getCard(0);
        Card second = board.getCard(1);
        Card third = board.getCard(2);
        Card fourth = board.getCard(3);

        assertTrue(first.matches(second));
        assertTrue(third.matches(fourth));

        gameModel.selectCard(first);
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);
        gameModel.selectCard(second);
        assertTrue(first.isMatched());
        assertTrue(second.isMatched());
        assertTrue(first.isFaceUp());
        assertTrue(second.isFaceUp());
        assertEquals(1, gameModel.getBoard().countMatchedPairs());
        assertTrue(gameModel.getState() instanceof WaitingForFirstCardState);

        gameModel.selectCard(third);
        assertTrue(gameModel.getState() instanceof WaitingForSecondCardState);
        gameModel.selectCard(fourth);
        assertTrue(third.isMatched());
        assertTrue(fourth.isMatched());
        assertTrue(third.isFaceUp());
        assertTrue(fourth.isFaceUp());
        assertEquals(2, gameModel.getBoard().countMatchedPairs());

        // Matched: check state.
        assertTrue(gameModel.getBoard().allCardsMatched());

        // Check game over method.
        assertTrue(gameModel.isGameOver());

        // Game over state.
        assertTrue(gameModel.getState() instanceof GameOverState);
    }
}
