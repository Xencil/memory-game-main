package uk.ac.gold.memorygame.model;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.ac.gold.memorygame.observer.GameModelObserver;
import uk.ac.gold.memorygame.observer.ObservableGameModel;

public class GameModel implements ObservableGameModel {

    private static final Logger LOGGER = LogManager.getLogger();

    private Board board;
    private ScoringStrategy scoring;
    private int moves = 0;
    private GameState currentState;

    private final List<GameModelObserver> observers = new CopyOnWriteArrayList<>();

    public GameModel(Board board, ScoringStrategy scoring) {
        initialise(board, scoring);
    }

    /*
     * -----------------------------
     * Public API (used by Controller)
     * -----------------------------
     */

    public void initialise(Board board, ScoringStrategy scoring) {
        this.board = board;
        this.scoring = scoring;
        this.moves = 0;

        // Set first game state.
        setState(new WaitingForFirstCardState(this));
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

    public boolean isGameOver() {
    	return board.allCardsMatched();
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

    void setState(GameState state) {
        LOGGER.debug("State {}", state);
        currentState = state;
        notifyStateChange();
        currentState.onEnter();
    }

    GameState getState() {
        return currentState;
    }

    void incrementMoves() {
        moves++;
    }

    /*
     * -----------------------------
     * Scoring
     * -----------------------------
     */

    void updateScore(boolean isMatch) {
        scoring.updateScore(isMatch);
        LOGGER.info("Score: {}", getScore());
        if(board.allCardsMatched()){
            setState(new GameOverState(this));
            notifyGameOver();
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
    public void notifyGameOver() {
        for (GameModelObserver o : observers) {
            o.onGameOver();
        }
    }

    @Override
    public void notifyStateChange() {
        for (GameModelObserver o : observers) {
            o.onStateChange();
        }
    }
}
