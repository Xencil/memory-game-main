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

    public GameOverController(MemoryGameApp app,int score){
        this.app =app;
        createView(score);
    }

    /*Access*/

    public Parent getView(){
        return gameOverView.getView();
    }
    /*Initialise view*/

    private void createView(int score) {
        gameOverView =new GameOverView(score);
        setRestartButtonHandler();
        setQuitButtonHandler();
    }

    /*UI handlers*/
    private void onRestartButtonClick(){
        app.showGameScreen();
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