package uk.ac.gold.memorygame.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.Parent;
import uk.ac.gold.memorygame.MemoryGameApp;
import uk.ac.gold.memorygame.model.Board;
import uk.ac.gold.memorygame.model.Card;
import uk.ac.gold.memorygame.model.GameModel;
import uk.ac.gold.memorygame.model.MoveBasedScoring;
import uk.ac.gold.memorygame.view.GamePlayView;
import uk.ac.gold.memorygame.config.CardDeck;
import uk.ac.gold.memorygame.observer.GameModelObserver;

public class GamePlayController implements GameModelObserver {
    private static final Logger LOGGER =LogManager.getLogger();
    private final MemoryGameApp app;
    private GameModel gameModel;
    private GamePlayView gamePlayView;
    public GamePlayController(MemoryGameApp app, CardDeck<?> cardSet, int numberOfPairs){

        this.app = app;

        initialiseModel(numberOfPairs);
        createView(cardSet);
        setCardsClickHandler();
        gameModel.addObserver(this);
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
        gameModel = new GameModel(b,scoring);
    }

    private void createView(CardDeck<?>cardSet) {
        gamePlayView = new GamePlayView(gameModel, cardSet);
    }

    /*UI handlers*/

    private void setCardsClickHandler() {
        gamePlayView.setCardClickHandler(card -> onCardClick(card));
    }

    private void onCardClick(Card card) {
        if(!gameModel.isGameOver()) {
            gameModel.selectCard(card);
        }
    }

    /*GameModelObserver method*/

    @Override
    public void onCardFlipUp(Card card) {
        gamePlayView.updateCard(card);
    }

    @Override
    public void onMatch(java.util.List<Card> cards){
        gamePlayView.matchCards(cards);
    }

    @Override
    public void onMismatch(java.util.List<Card> cards){
        gamePlayView.updateCards(cards);
   
    }

    @Override
    public void onStateChange() {

        gamePlayView.update();

    }

    @Override
    public void onGameOver(){

        gameModel.removeObserver(this);
        app.showGameOverScreen(gameModel.getScore());
    }
}