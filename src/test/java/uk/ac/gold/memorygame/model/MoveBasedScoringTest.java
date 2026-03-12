package uk.ac.gold.memorygame.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;

import uk.ac.gold.memorygame.model.exceptions.ValueOutOfRangeException;

@TestMethodOrder(OrderAnnotation.class)
class MoveBasedScoringTest {

    @Test
    @Order(1)
    void testInitialisation() {
        int pointsForCorrect = 2;
        int penaltyForIncorrect = 1;

        MoveBasedScoring scoring = new MoveBasedScoring(
                pointsForCorrect, penaltyForIncorrect);

        assertEquals(0, scoring.getScore());
    }

    @Test
    @Order(2)
    void testInvalidPointsArg() {
        int pointsForCorrect = 0;
        int penaltyForIncorrect = 1;

        ValueOutOfRangeException exception = assertThrowsExactly(
                ValueOutOfRangeException.class, () -> {
                    new MoveBasedScoring(pointsForCorrect, penaltyForIncorrect);
                });
        assertEquals(
                "Points for correct guesses should be greater than zero, received: "
                        + pointsForCorrect,
                exception.getMessage());
    }

    @Test
    @Order(3)
    void testInvalidPenaltyArg() {
        int pointsForCorrect = 1;
        int penaltyForIncorrect = -1;

        ValueOutOfRangeException exception = assertThrowsExactly(
                ValueOutOfRangeException.class, () -> {
                    new MoveBasedScoring(pointsForCorrect, penaltyForIncorrect);
                });
        assertEquals(
                "Penalty for incorrect guesses should be greater than or equal to zero, received: "
                        + penaltyForIncorrect,
                exception.getMessage());
    }

    @Test
    @Order(4)
    void testIncrement() {
        int pointsForCorrect = 2;
        int penaltyForIncorrect = 1;

        MoveBasedScoring scoring = new MoveBasedScoring(
                pointsForCorrect, penaltyForIncorrect);

        scoring.updateScore(true);
        assertEquals(pointsForCorrect, scoring.getScore());
        scoring.updateScore(true);
        assertEquals(pointsForCorrect * 2, scoring.getScore());
    }

    @Test
    @Order(5)
    void testDecrement() {
        int pointsForCorrect = 2;
        int penaltyForIncorrect = 1;

        MoveBasedScoring scoring = new MoveBasedScoring(
                pointsForCorrect, penaltyForIncorrect);

        // Should not go below zero.
        scoring.updateScore(false);
        assertEquals(0, scoring.getScore());

        scoring.updateScore(true);
        scoring.updateScore(false);
        assertEquals(pointsForCorrect - penaltyForIncorrect, scoring.getScore());
    }

    @Test
    @Order(6)
    void testReset() {
        int pointsForCorrect = 2;
        int penaltyForIncorrect = 1;

        MoveBasedScoring scoring = new MoveBasedScoring(
                pointsForCorrect, penaltyForIncorrect);

        scoring.reset();
        assertEquals(0, scoring.getScore());

        scoring.updateScore(true);
        scoring.reset();
        assertEquals(0, scoring.getScore());
    }
}
