package uk.ac.gold.memorygame.controller;

import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import uk.ac.gold.memorygame.config.PictureCardDeck;
import javafx.scene.Parent;
import uk.ac.gold.memorygame.MemoryGameApp;
import uk.ac.gold.memorygame.view.GameStartView;
import uk.ac.gold.memorygame.config.TextCardDeck;
import uk.ac.gold.memorygame.config.CardDeck;

public class GameStartController {

    private static final Logger LOGGER = LogManager.getLogger();

    private final MemoryGameApp app;

    
    private GameStartView gameStartView;
    
    private boolean choosingGameMode = true;
    
    
    

    public GameStartController(MemoryGameApp app) {
        this.app = app;
        createView();
        int savedhighScore = app.getPrefs().getInt("high score", 0);
        gameStartView.setHighScore(savedhighScore);

    }

    /*
     * -----------------------------
     * Access
     * -----------------------------
     */

    public Parent getView() {
        LOGGER.debug("Getting start screen view");
        return gameStartView.getRoot();
    }

    /*
     * -----------------------------
     * Initialise view
     * -----------------------------
     */

    private void createView() {
        gameStartView = new GameStartView();
        setGameModeHandlers();
    }
    
    // sets the handlers for selecting gamemodes
    private void setGameModeHandlers() {
    	//ai
    	gameStartView.setAIHandler(_ -> {
    	    app.setTwoPlayerMode(true);
    	    app.setAIgameMode(true);
    	    setDeckHandlers();
    	});
    	//single player
        gameStartView.setSinglePlayerHandler(_ -> {
            app.setTwoPlayerMode(false);
            app.setAIgameMode(false);
            setDeckHandlers();
        });
        //2 player
        gameStartView.setTwoPlayerHandler(_ -> {
            app.setTwoPlayerMode(true);
            app.setAIgameMode(false);
            setDeckHandlers();
        });
    }

    // used selection of gamemode
    private void setDeckHandlers() {
    	gameStartView.showDeckButtons();
    	
        gameStartView.setLettersHandler(_-> {CardDeck<?>deck = new TextCardDeck("letters",List.of("A","B","C","D","E","F","G","H"));app.showGameScreen(deck);});

        gameStartView.setNumbersHandler(_-> {CardDeck<?> deck = new TextCardDeck("Numbers",List.of("1","2","3","4","5","6","7","8"));app.showGameScreen(deck);});

        gameStartView.setEmojiHandler(_-> {CardDeck<?>deck = new TextCardDeck("emoji",List.of("😀","🐶","🍎","🚗","⚽","🎵","🔥","🌟"));app.showGameScreen(deck);});
        
        gameStartView.setPictureHandler(_-> {CardDeck<?>deck = new PictureCardDeck("picture",List.of("alpine-forget-me-not.png","anthurium.png","bluebell.png","cactus.png","dahlia.png","lavender.png","lily.png","nasturtium.png"));app.showGameScreen(deck);});
    }

}
