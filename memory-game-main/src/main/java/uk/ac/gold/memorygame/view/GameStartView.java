package uk.ac.gold.memorygame.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

public class GameStartView {

    private static final Logger LOGGER = LogManager.getLogger();

    // Root JavaFX parent node.
    private final VBox root;

    private final Button startButton;

    public GameStartView() {
        root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);

        startButton = new Button("Start Game");
        root.getChildren().add(startButton);
    }

    public Parent getRoot() {
        return root;
    }

    public void setStartClickHandler(EventHandler<ActionEvent> handler) {
        LOGGER.debug("Setting start click handler");
        startButton.setOnAction(handler);
    }
}
