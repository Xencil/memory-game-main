package uk.ac.gold.memorygame.view;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.event.EventHandler;
import javafx.event.ActionEvent;

public class GameOverView {
    private static final Logger LOGGER =LogManager.getLogger();

    private final VBox root;
    private final Text EndScore;
    private final Button restartButton;
    private final Button exitButton;

    public GameOverView(int score) {
        root =new VBox();
        root.setSpacing(10);
        EndScore =new Text("final score: " + score);
        restartButton = new Button("restart");
        exitButton = new Button("exit");

        root.getChildren().addAll(EndScore, restartButton, exitButton);
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