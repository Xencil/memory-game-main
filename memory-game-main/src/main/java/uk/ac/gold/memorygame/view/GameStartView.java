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
    private final Text ShowHighScore;
    private final Button pButton;
    private final Button sPlayer;
    private final Button tPlayer;
    private final Button AIButton;

    private final Text title;

    public GameStartView() {
        root = new VBox();
        root.setSpacing(20);
        root.setAlignment(Pos.CENTER);

        title = new Text("select the game mode you wish to play");
        lButton = new Button("letters");
        nButton = new Button("Numbers");
        eButton =new Button("emoji");
        ShowHighScore = new Text("");
        pButton = new Button("pictures");
        sPlayer = new Button("single player");
        tPlayer = new Button("two player");
        AIButton = new Button("play against an AI");

        root.getChildren().addAll(title,sPlayer,tPlayer, AIButton);
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
    
    public void setHighScore(int score) {
    	ShowHighScore.setText("High Score: " + score);
    }
    public void setPictureHandler(EventHandler<ActionEvent>handler) {
    	pButton.setOnAction(handler);
    }
    
    public void setSinglePlayerHandler(EventHandler<ActionEvent>handler) {
    	sPlayer.setOnAction(handler);
    }
    
    public void setTwoPlayerHandler(EventHandler<ActionEvent>handler) {
    	tPlayer.setOnAction(handler);
    }
    
    public void setAIHandler(EventHandler<ActionEvent> handler) {
        AIButton.setOnAction(handler);
    }
    //this is the next screen after the gamemode has been selected
    public void showDeckButtons() {
        title.setText("select deck");
        root.getChildren().clear();
        root.getChildren().addAll(title,lButton,nButton,eButton, pButton,ShowHighScore);
    }
}

