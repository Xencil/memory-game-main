package uk.ac.gold.memorygame.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.Parent;
import javafx.scene.media.AudioClip;
import uk.ac.gold.memorygame.MemoryGameApp;
import uk.ac.gold.memorygame.model.Board;
import uk.ac.gold.memorygame.model.Card;
import uk.ac.gold.memorygame.model.GameModel;
import uk.ac.gold.memorygame.model.MoveBasedScoring;
import uk.ac.gold.memorygame.model.User;
import uk.ac.gold.memorygame.view.GamePlayView;
import uk.ac.gold.memorygame.config.CardDeck;
import uk.ac.gold.memorygame.observer.GameModelObserver;

public class GamePlayController implements GameModelObserver {
    private static final Logger LOGGER =LogManager.getLogger();
    private final MemoryGameApp app;
    private GameModel gameModel;
    private GamePlayView gamePlayView;
    private AudioClip incorrect;
    private AudioClip correct;
    private final boolean tPlayerMode;
    public GamePlayController(MemoryGameApp app, CardDeck<?> cardSet, int numberOfPairs,boolean tPlayerMode){

        this.app = app;
        this.tPlayerMode =tPlayerMode;

        initialiseModel(numberOfPairs);
        createView(cardSet);
        setCardsClickHandler();
        gameModel.addObserver(this);
        //gets the audio files 
        correct = new AudioClip(getClass().getResource("/audio/correct.mp3").toExternalForm());
    	incorrect = new AudioClip(getClass().getResource("/audio/cat-meows/bbc_cats-and-k_07045175.mp3").toExternalForm());

    }

    /*Access*/

    public Parent getView() {
        return gamePlayView.getRoot();
    }

    /*Initialise dependencies*/

    private void initialiseModel(int numberOfPairs) {
        // number of card pairs
        Board b =new Board(numberOfPairs);
        
        //create the scoring system
        MoveBasedScoring scoring =new MoveBasedScoring(2,1);
        gameModel = new GameModel(b,scoring,tPlayerMode);
    }

    private void createView(CardDeck<?>cardSet) {
        gamePlayView = new GamePlayView(gameModel, cardSet,tPlayerMode);
    }

    /*UI handlers*/

    private void setCardsClickHandler() {
        gamePlayView.setCardClickHandler(card -> onCardClick(card));
    }

    private void onCardClick(Card card) {
    	//used to ignore clicks during invalid states
        try {
            if(!gameModel.isGameOver()){
                gameModel.selectCard(card);
            }
        }catch(IllegalStateException e){}
    }
    /*GameModelObserver method*/

    @Override
    public void onCardFlipUp(Card card) {
        gamePlayView.updateCard(card);
    }

    @Override
    public void onMatch(java.util.List<Card> cards){
        gamePlayView.matchCards(cards);
        correct.play();// plays sound when correct
    }

    @Override
    public void onMismatch(java.util.List<Card> cards){
        gamePlayView.updateCards(cards);
        incorrect.play(); // plays sound when incorrect
        //quickly flips cards back
        for (Card c : cards) {
            c.flipDown();
            gamePlayView.updateCard(c);
        }
   
    }
    
    @Override
    public void onStateChange() {
        gamePlayView.update();
        if(tPlayerMode) {
        	gamePlayView.updatePlayers(gameModel.getPlayer1(),gameModel.getPlayer2(),gameModel.getPlayerInTurn());
        }
    }

    @Override
    public void onGameOver() {
        gameModel.removeObserver(this);
        User wUser = gameModel.getWinner();
        String winner =(wUser!= null)? wUser.getName():"Draw";
        int Score1 = gameModel.getPlayer1().getScore();
        
        int Score2 =tPlayerMode && gameModel.getPlayer2() != null? gameModel.getPlayer2().getScore(): 0;
        app.showGameOverScreen(winner, Score1, Score2);
    }
 
}