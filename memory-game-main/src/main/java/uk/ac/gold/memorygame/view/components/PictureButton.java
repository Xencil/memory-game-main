package uk.ac.gold.memorygame.view.components;


import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import uk.ac.gold.memorygame.model.Card;

public class PictureButton extends CardButton {

    private final ImageView imageView;
    private boolean faceUp= false;

    public PictureButton(Card card, String imagePath) {
        super(card);
        
        //gets the picture files
        Image imageFiles = new Image(getClass().getResourceAsStream("/images/flowers/" + imagePath));

        imageView = new ImageView(imageFiles);
        imageView.setFitWidth(80);
        imageView.setFitHeight(80);

        setGraphic(imageView);
        // starts the game face down
        hide();
    }

    @Override
    protected boolean isFaceUp() {
        return faceUp;
    }

    @Override
    protected void show() {
        imageView.setVisible(true);
        faceUp = true;
    }

    @Override
    protected void hide() {
        imageView.setVisible(false);
        faceUp = false;
    }
}