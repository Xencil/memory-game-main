package uk.ac.gold.memorygame.view.components;

import javafx.scene.text.Text;

public class ScoreView extends Text {

    public ScoreView() {
        // Initialise score display.
        this.setText("Score:");
    }

    public void update(int score) {
        // Update score display given current score.
        this.setText("Score: " + score);
    }
}
