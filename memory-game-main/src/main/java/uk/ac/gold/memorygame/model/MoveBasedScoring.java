package uk.ac.gold.memorygame.model;

import uk.ac.gold.memorygame.model.exceptions.ValueOutOfRangeException;

public class MoveBasedScoring implements ScoringStrategy {

    private int score;
    private final int pointsForCorrect;
    private final int penaltyForIncorrect;

    public MoveBasedScoring(int pointsForCorrect, int penaltyForIncorrect) {
        if (pointsForCorrect < 1) {
            throw new ValueOutOfRangeException(
                    "Points for correct guesses should be greater than zero, received: "
                            + pointsForCorrect);
        }
        if (penaltyForIncorrect < 0) {
            throw new ValueOutOfRangeException(
                    "Penalty for incorrect guesses should be greater than or equal to zero, received: "
                            + penaltyForIncorrect);
        }
        this.pointsForCorrect = pointsForCorrect;
        this.penaltyForIncorrect = penaltyForIncorrect;
        this.score = 0;
    }

    @Override
    public void updateScore(boolean match) {
        if (match) {
            score += pointsForCorrect;
        } else {
            score -= penaltyForIncorrect;
        }

        // Prevent negative score.
        if (score < 0)
            score = 0;
    }

    @Override
    public int getScore() {
        return score;
    }

    @Override
    public void reset() {
        score = 0;
    }
}
