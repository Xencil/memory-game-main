package uk.ac.gold.memorygame.view;

import java.util.List;
import java.util.function.Consumer;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.animation.Animation;
import javafx.animation.PauseTransition;
import javafx.scene.Parent;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import uk.ac.gold.memorygame.config.CardDeck;
import uk.ac.gold.memorygame.model.Card;
import uk.ac.gold.memorygame.model.GameModel;
import uk.ac.gold.memorygame.view.components.BoardView;
import uk.ac.gold.memorygame.view.components.ScoreView;
import javafx.scene.text.Text;
import uk.ac.gold.memorygame.model.User;

public class GamePlayView {

    private static final Logger LOGGER = LogManager.getLogger();

    // Root JavaFX parent node.
    private final VBox root;

    // View has a reference to the model for querying data.
    private final GameModel model;

    // GameView UI components.
    private final BoardView boardView;
    private final ScoreView scoreView;
    private final Text playerTurnText;
    private final Text scoreText;
    
    // used for distinction in game mode
    private final boolean tPlayerMode;
    
    // Timer to handle UI update of mismatched cards.
    private PauseTransition mismatchPause = new PauseTransition(Duration.seconds(2));;

    public GamePlayView(GameModel model, CardDeck cardDeck, boolean tPlayerMode) {
        this.model = model;
        this.tPlayerMode = tPlayerMode;
        root = new VBox();
        root.setSpacing(10);

        scoreView = new ScoreView();
        boardView = new BoardView();
        playerTurnText = new Text();
        scoreText = new Text();

        // Pass only the list of card model instances to the board, it doesn't
        // need access to the entire game model.
        boardView.buildCards(model.getCards(), cardDeck);

        // Allow board to fill all vertical space.
        VBox.setVgrow(boardView.getRoot(), Priority.ALWAYS);
        
        //changes the Ui based on gamemode chosen
        if(tPlayerMode){
        	//2 player ui
            playerTurnText.setText("player 1 turn");
            scoreText.setText("p1: 0 - p2: 0");

            root.getChildren().addAll(playerTurnText,scoreText,scoreView,boardView.getRoot());
        } else{
        	//single player
            scoreText.setText("score: 0");
            root.getChildren().addAll(scoreText,scoreView, boardView.getRoot());
        }

    }

    public Parent getRoot() {
        return root;
    }

    public void setCardClickHandler(Consumer<Card> handler) {
        LOGGER.debug("Passing card click handler to board component");
        boardView.setCardClickHandler(handler);
    }

    public void hideCardsDelayed(Runnable hideAction) {
        LOGGER.debug("Start mismatch pause transition");
        mismatchPause.setOnFinished(_ -> hideAction.run());
        mismatchPause.playFromStart();
    }

    public void cancelMismatchedPause() {
        if (mismatchPause.getStatus().equals(Animation.Status.RUNNING)) {
            LOGGER.debug("Cancelling mismatch pause timer");
            mismatchPause.jumpTo("end");
        }
    }

    public void updateCards(List<Card> cards) {
        for (Card card : cards) {
            updateCard(card);
        }
    }

    public void updateCard(Card card) {
        LOGGER.debug("Update card {}", card);
        boardView.getCardButton(card).update();
    }

    public void matchCards(List<Card> cards) {
        for (Card card : cards) {
            matchCard(card);
        }
    }

    public void matchCard(Card card) {
        LOGGER.debug("Update matching cards");
        boardView.getCardButton(card).match();
    }

    public void update() {
        LOGGER.debug("Updating game view components");
        scoreView.update(model.getScore());
    }
    
    //updates the players data for score and for turns
    public void updatePlayers(User p1, User p2, User InTurn) {
    	// singleplayer
        if(!tPlayerMode){
            scoreText.setText("score: " +p1.getScore());
            return;
        }
        // 2 player
        playerTurnText.setText(InTurn.getName() + " turn");
        scoreText.setText("p1: " + p1.getScore() + " - p2: "+p2.getScore());
    }
}
