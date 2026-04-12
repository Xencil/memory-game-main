package uk.ac.gold.memorygame.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.Parent;
import javafx.scene.media.AudioClip;
import uk.ac.gold.memorygame.MemoryGameApp;
import uk.ac.gold.memorygame.model.Board;
import uk.ac.gold.memorygame.model.Card;
import uk.ac.gold.memorygame.model.GameModel;
import uk.ac.gold.memorygame.model.GameOverState;
import uk.ac.gold.memorygame.model.MoveBasedScoring;
import uk.ac.gold.memorygame.model.User;
import uk.ac.gold.memorygame.model.WaitingForFirstCardState;
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
    private final boolean AIgameMode;
    private boolean AIinTurn = false;
    
    private java.util.Map<Integer,java.util.List<Card>> memory =new java.util.HashMap<>();
    public GamePlayController(MemoryGameApp app, CardDeck<?> cardSet, int numberOfPairs,boolean tPlayerMode, boolean AIgameMode){

        this.app = app;
        this.tPlayerMode =tPlayerMode;
        this.AIgameMode = AIgameMode;

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
        MoveBasedScoring scoring =new MoveBasedScoring(1,0);
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
        try {
            if(gameModel.islocked()){
            	return;
            }; 
            if(AIgameMode &&gameModel.getPlayerInTurn()==gameModel.getPlayer2()){
                return;
            }

            if(!gameModel.isGameOver()){
                gameModel.selectCard(card);
            }

        } catch (IllegalStateException e){}
    }
    
    private void playAITurn() {
        new Thread(() ->{
            try {
                Thread.sleep(450); 
                Card firstC = null;
                Card secondC = null;
                // tries to use memory for card pairings
                for(var entry : memory.entrySet()){
                    java.util.List<Card> seen = entry.getValue();
                    if(seen.size() >=2){
                        Card c1 = seen.get(0);
                        Card c2 = seen.get(1);

                        if (!c1.isMatched() && !c2.isMatched()) {
                            firstC = c1;
                            secondC = c2;
                            break;
                        }
                    }
                }
                // random guess if nothing is known
                if(firstC ==null ||secondC ==null){
                    java.util.List<Card> deck =new java.util.ArrayList<>();

                    for(Card c:gameModel.getCards()){
                        if(!c.isMatched() &&!c.isFaceUp()){
                            deck.add(c);
                        }
                    }
                    java.util.Collections.shuffle(deck);

                    if (deck.size() >= 2) {
                        firstC = deck.get(0);

                        for (Card c : deck) {
                            if (c != firstC) {
                                secondC = c;
                                break;
                            }
                        }
                    }
                }
                Card s=secondC;
                Card f =firstC;
                javafx.application.Platform.runLater(() ->{
                    try {
                        if(f == null || s == null){
                            AIinTurn = false;
                            return;
                        }
                        gameModel.selectCard(f);
                        gameModel.selectCard(s);
                    } catch (Exception e) {}

                    //stops the ai from playing multiple times
                    AIinTurn =false;
                    if(AIgameMode && gameModel.getPlayerInTurn() == gameModel.getPlayer2()){
                        playAITurn();
                    }
                });
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }
    /*GameModelObserver method*/

    @Override
    public void onCardFlipUp(Card card) {
        gamePlayView.updateCard(card);
        //store the card for the ai to use to guess
        memory.putIfAbsent(card.getPairId(),new java.util.ArrayList<>());
        if(!memory.get(card.getPairId()).contains(card)){
            memory.get(card.getPairId()).add(card);
        }
    }
    @Override
    public void onMatch(java.util.List<Card> cards){
        gamePlayView.matchCards(cards);
        correct.play();// plays sound when correct
        //removes the cards from the memory 
        int pairId = cards.get(0).getPairId();
        memory.remove(pairId);
    }

    @Override
    public void onMismatch(List<Card> cards){
        gamePlayView.updateCards(cards);
        incorrect.play();
        new Thread(() ->{
            try{
                Thread.sleep(900);
                javafx.application.Platform.runLater(() ->{
                    for(Card c:cards){
                        c.flipDown();
                        gamePlayView.updateCard(c);
                    }
                    gameModel.setlocked(false);
                    if(gameModel.isGameOver()){
                        gameModel.setState(new GameOverState(gameModel));
                    } else{
                        gameModel.setState(new WaitingForFirstCardState(gameModel));
                    }
                });
            } catch(InterruptedException e){
                e.printStackTrace();
            }
        }).start();
    }
    @Override
    public void onStateChange() {
        gamePlayView.update();
        if(tPlayerMode) {
        	gamePlayView.updatePlayers(gameModel.getPlayer1(),gameModel.getPlayer2(),gameModel.getPlayerInTurn());
        }
        if(AIgameMode &&tPlayerMode &&gameModel.getPlayerInTurn() == gameModel.getPlayer2() &&gameModel.getState() instanceof WaitingForFirstCardState &&!AIinTurn){
        	    AIinTurn =true;
        	    playAITurn();
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