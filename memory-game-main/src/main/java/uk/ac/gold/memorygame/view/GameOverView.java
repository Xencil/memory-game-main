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
    private final Text EndScore;
    private final Button restartButton;
    private final Button exitButton;
    private final Text ShowHighScore;
    private final Text ShowWinner;

    public GameOverView(String winner, int score, int highScore) {
        root =new VBox();
        root.setSpacing(10);
        root.setAlignment(Pos.CENTER);
        ShowWinner = new Text();

        if(winner.equals("Draw")){
            ShowWinner.setText("The game is a draw");
        } else {
            ShowWinner.setText("the winner is: " + winner);
        }
        EndScore =new Text("final score: " + score);
        if(score >= highScore){
        	ShowHighScore = new Text("new pr: "+ score);
        }else{
        	ShowHighScore =new Text("high score: "+ highScore);
        }
        restartButton = new Button("restart");
        exitButton = new Button("exit");



        root.getChildren().addAll(ShowWinner,EndScore, restartButton, exitButton, ShowHighScore);
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