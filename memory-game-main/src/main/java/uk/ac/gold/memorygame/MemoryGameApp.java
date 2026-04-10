package uk.ac.gold.memorygame;

import java.util.List;
import java.util.prefs.Preferences;
import uk.ac.gold.memorygame.config.TextCardDeck;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.gold.memorygame.controller.GameStartController;
import uk.ac.gold.memorygame.controller.GameOverController;
import uk.ac.gold.memorygame.controller.GamePlayController;

import uk.ac.gold.memorygame.config.CardDeck;

/**
 * JavaFX App: MemoryGame
 *
 * MemoryGame, a subclass of JavaFX Application, is the bridge between the
 * game implementation and the JavaFX framework. It defines three methods that
 * correspond to distinct modes of user interaction with the application:
 *
 * - showStartScreen()
 * - showGameScreen()
 * - showGameOverScreen()
 *
 * Each method instantiates a controller, which is the entry point into the MVC
 * structured code.
 *
 * The abstraction boundary here is the javafx.stage.Stage class. The
 * application is responsible for managing the stage. Controllers are
 * responsible for deciding what should be placed on the stage, but they have
 * no direct access to the stage itself. Instead, controllers call the public
 * application methods when they want the stage to be updated.
 */
public class MemoryGameApp extends Application {

    private static final Logger LOGGER = LogManager.getLogger();

    private Stage primaryStage;
    private boolean twoPlayerMode;

    // Application/user-related state: single instance that persists for the
    // lifetime of the application. Could also be saved and reloaded at
    // start-up to restore the user's game history or preferences.
    private Preferences prefs = Preferences.userRoot().node("memorygame");

    @Override
    public void start(Stage stage) {
        LOGGER.debug("Application starting");

        primaryStage = stage;

        // Show the start screen when the application starts.
        showStartScreen();
        primaryStage.show();
    }

    public void showStartScreen() {
        LOGGER.debug("Creating start screen");

        GameStartController controller = new GameStartController(this);
        primaryStage.setScene(new Scene(controller.getView(), 640, 480));
    }

    public void showGameScreen(CardDeck<?> cardDeck) {
        // grid size
        int numOfPairs =cardDeck.numberOfItems();
        // game controller creation
        GamePlayController controller=new GamePlayController(this,cardDeck,numOfPairs, twoPlayerMode);

        primaryStage.setScene(new Scene(controller.getView(),640, 480));
    }

    public void showGameOverScreen(String winner, int p1Score, int p2Score) {

        int savedhighScore = prefs.getInt("high score", 0);

        // Use the higher of both players for high score
        int bestScore = Math.max(p1Score, p2Score);

        if (bestScore > savedhighScore) {
            prefs.putInt("high score", bestScore);
            savedhighScore = bestScore;
        }

        GameOverController controller =
            new GameOverController(this, winner, p1Score, p2Score);

        primaryStage.setScene(new Scene(controller.getView(), 640, 480));
    }
    public Preferences getPrefs() {
        return prefs;
    }

    public static void main(String[] args) {
        launch();
    }
    
    public void setTwoPlayerMode(boolean mode) {
        this.twoPlayerMode = mode;
    }

    public boolean isTwoPlayerMode() {
        return twoPlayerMode;
    }
}
