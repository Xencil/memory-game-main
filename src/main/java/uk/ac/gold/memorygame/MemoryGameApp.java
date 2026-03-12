package uk.ac.gold.memorygame;

import java.util.prefs.Preferences;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import uk.ac.gold.memorygame.controller.GameStartController;

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

    public void showGameScreen() {
        LOGGER.debug("Creating game screen");
    }

    public Preferences getPrefs() {
        return prefs;
    }

    public static void main(String[] args) {
        launch();
    }
}
