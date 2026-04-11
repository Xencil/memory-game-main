package uk.ac.gold.memorygame.view;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import uk.ac.gold.memorygame.model.GameModel;
import uk.ac.gold.memorygame.model.User;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;

public class GameOverView {
    private static final Logger LOGGER =LogManager.getLogger();
    private GameModel gameModel;
    private final VBox root;
    private final Button restartButton;
    private final Button exitButton;
    private final Text ShowHighScore;
    private final Text ShowWinner;
    // Display for two player mode 
    public GameOverView(String winner, int score1, int score2,int highScore){
        root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        //displays who the winner is 
        if(winner.equals("Draw")){
            ShowWinner =new Text("The game is a draw");
        } else{
            ShowWinner =new Text("The winner is: " +winner);
        }
        //shows the final scores of both players
        Text p1EndScore =new Text("p 1: " + score1);
        Text p2EndScore =new Text("p 2: " +score2);

        //gets the best score between the players
        int highestScore = Math.max(score1,score2);
        if(highestScore >= highScore){
            ShowHighScore = new Text("new pr: " + highestScore);
        } else{
            ShowHighScore =new Text("High score: " + highScore);
        }

        restartButton =new Button("restart");
        exitButton =new Button("exit");
        root.getChildren().addAll(ShowWinner,p1EndScore,p2EndScore,ShowHighScore,restartButton,exitButton
        );
    }
    //Display for single player
    public GameOverView(String winner, int score,int highScore) {
        root = new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        //no need to show who the winner is in singleplayer
        ShowWinner =new Text("");
        
        Text EndScore =new Text("final score: " + score);
        if(score >= highScore){
            ShowHighScore = new Text("new pr: " + score);
        } else {
            ShowHighScore =new Text("High score: " + highScore);
        }
        restartButton = new Button("restart");
        exitButton = new Button("exit");

        root.getChildren().addAll(ShowWinner,EndScore,ShowHighScore,restartButton,exitButton);
    }
    

    public Parent getView(){
        return root;
    }

    public void setRestartClickHandler(EventHandler<ActionEvent> handler){
        restartButton.setOnAction(handler);
    }

    public void setQuitClickHandler(EventHandler<ActionEvent> handler){
        exitButton.setOnAction(handler);
    }
}