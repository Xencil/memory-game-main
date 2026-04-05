package uk.ac.gold.memorygame.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.scene.layout.VBox;

public class GameStartView {

    private static final Logger LOGGER = LogManager.getLogger();

    // Root JavaFX parent node.
    private final VBox root;
    
    private final Button eButton;
    private final Button lButton;
    private final Button nButton;
    
    private final Text title;

    public GameStartView() {
        root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        title = new Text("select the game mode you wish to play");
        lButton = new Button("letters");
        nButton = new Button("Numbers");
        eButton =new Button("emoji");

        root.getChildren().addAll(title,lButton,nButton,eButton);
    }

    public Parent getRoot() {
        return root;
    }
    
    public void setLettersHandler(EventHandler<ActionEvent>handler) {
    	lButton.setOnAction(handler);
    }

    public void setNumbersHandler(EventHandler<ActionEvent>handler) {
    	nButton.setOnAction(handler);
    }

    public void setEmojiHandler(EventHandler<ActionEvent> handler) {
    	eButton.setOnAction(handler);
    }
}

