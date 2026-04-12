package uk.ac.gold.memorygame.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.ac.gold.memorygame.observer.GameModelObserver;
import uk.ac.gold.memorygame.observer.ObservableGameModel;
import uk.ac.gold.memorygame.model.User;

public class GameModel implements ObservableGameModel {

    private static final Logger LOGGER = LogManager.getLogger();

    private Board board;
    private ScoringStrategy scoring;
    private GameState currentState;
    private int moves = 0;
    private User p1;
    private User p2;
    private User PlayerInTurn;
    private boolean tPlayerMode;
    private boolean locked = false;

    private final List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

    public GameModel(Board board, ScoringStrategy scoring, boolean tPlayerMode) {
    	this.tPlayerMode = tPlayerMode;
        initialise(board, scoring);
    }

    private void initialise(Board board, ScoringStrategy scoring) {
        this.board = board;
        this.scoring = scoring;
        this.moves = 0;
        
        //changes if theres 1 or 2 players depending on gamemode
        if(tPlayerMode){
            p1= new User("Player 1");
            p2= new User("Player 2");
        } else{
            p1= new User("Player");
            p2= null;
        }
        // Reset all cards
        for (Card c : board.getCards()) {
            c.flipDown();
            c.setMatched(false);
        }
        PlayerInTurn = p1; 

        setState(new WaitingForFirstCardState(this));
    }

    /*
     * -----------------------------
     * Access
     * -----------------------------
     */

    
    public GameModel(Board board,ScoringStrategy scoring){
        this(board,scoring, false);
    }
    
    public Board getBoard() {
        return board;
    }

    public List<Card> getCards() {
        return board.getCards();
    }

    public int getScore() {
        return scoring.getScore();
    }

    public int getMoves() {
        return moves;
    }
    //decides on which player won the game based on their score
    public User getWinner() {
        if(!tPlayerMode){
            return p1;
        }
        if(p1.getScore()> p2.getScore()){
        	 return p1;
        } 
        if(p2.getScore()> p1.getScore()){
        	return p2;
        }
        
        return null;
    }
    
    public void switchPlayer() {
        if (!tPlayerMode) return; //makes this function do nothing if singleplayer
        PlayerInTurn =(PlayerInTurn == p1) ? p2 : p1;
    }
    
    public User getPlayerInTurn() {
        return PlayerInTurn;
    }
    
    public User getPlayer1() {
        return p1;
    }

    public User getPlayer2() {
        return p2;
    }
    
    public boolean isGameOver() {
        return board.allCardsMatched();
    }
  
    public boolean istwoPlayerMode() {
        return tPlayerMode;
    }
    
    /*
     * -----------------------------
     * Controller input
     * -----------------------------
     */

    public void selectCard(Card card) {
        LOGGER.info("New card selected {}", card);
        currentState.selectCard(card);
    }

    /*
     * -----------------------------
     * State management
     * -----------------------------
     */

    public void setState(GameState state) {
        LOGGER.debug("State {}", state);
        currentState = state;
        notifyStateChange();
        currentState.onEnter();
    }

    public GameState getState() {
        return currentState;
    }

    void incrementMoves() {
        moves++;
    }
    

	public boolean islocked() {
	    return locked;
	}
	
	public void setlocked(boolean locked) {
	    this.locked = locked;
	}

    /*
     * -----------------------------
     * Scoring
     * -----------------------------
     */

    void updateScore(boolean isMatch) {
        int old = scoring.getScore();
        scoring.updateScore(isMatch);
        LOGGER.info("Score: {}", getScore());
        int x = scoring.getScore() -old;
        if(x > 0){
            PlayerInTurn.addScore(x);
        }
    }

    /*
     * -----------------------------
     * Observable interface
     * -----------------------------
     */

    @Override
    public void addObserver(GameModelObserver observer) {
        LOGGER.debug("Adding observer {}", observer);
        observers.add(observer);
    }

    @Override
    public void removeObserver(GameModelObserver observer) {
        LOGGER.debug("Removing observer {}", observer);
        observers.remove(observer);
    }

    @Override
    public void notifyCardFlipUp(Card card) {
        for (GameModelObserver o : observers) {
            o.onCardFlipUp(card);
        }
    }

    @Override
    public void notifyMatch(List<Card> cards) {
        for (GameModelObserver o : observers) {
            o.onMatch(cards);
        }
    }

    @Override
    public void notifyMismatch(List<Card> cards) {
        for (GameModelObserver o : observers) {
            o.onMismatch(cards);
        }
    }

    @Override
    public void notifyStateChange() {
        for (GameModelObserver o : observers) {
            o.onStateChange();
        }
    }

    @Override
    public void notifyGameOver() {
        for (GameModelObserver o : observers) {
            o.onGameOver();
        }
    }
    

    
}
