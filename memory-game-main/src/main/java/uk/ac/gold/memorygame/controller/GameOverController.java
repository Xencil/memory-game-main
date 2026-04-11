package uk.ac.gold.memorygame.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.scene.Parent;
import uk.ac.gold.memorygame.MemoryGameApp;
import uk.ac.gold.memorygame.view.GameOverView;
public class GameOverController {

    private static final Logger LOGGER = LogManager.getLogger();
    private final MemoryGameApp app;
    private GameOverView gameOverView;
    private final String winner;
    private final int Score1;
    private final int Score2;

    public GameOverController(MemoryGameApp app, String winner, int Score1, int Score2) {
        this.app = app;
        this.Score1 = Score1;
        this.Score2 = Score2;
        this.winner = winner;
        createView();
    }
    /*Access*/

    public Parent getView(){
        return gameOverView.getView();
    }
    /*Initialise view*/

    private void createView() {
  
        int highScore = app.getPrefs().getInt("high score", 0);
        //sends the score data differently depending on gamemode selected
        if (app.isTwoPlayerMode()) {
            gameOverView = new GameOverView(winner, Score1, Score2, highScore);
        } else {
            gameOverView = new GameOverView(winner, Score1, highScore);
        }

        setRestartButtonHandler();
        setQuitButtonHandler();
    }

    /*UI handlers*/
    private void onRestartButtonClick(){
        app.showStartScreen();
    }
    private void setRestartButtonHandler(){
        gameOverView.setRestartClickHandler(_ ->onRestartButtonClick());
    }


    private void setQuitButtonHandler() {
        gameOverView.setQuitClickHandler(_ -> onQuitButtonClick());
    }
    private void onQuitButtonClick(){
        Platform.exit();
    }
}