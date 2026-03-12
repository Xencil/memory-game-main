package uk.ac.gold.memorygame.model;

public interface ScoringStrategy {
    void updateScore(boolean isMatch);

    int getScore();

    void reset();
}
