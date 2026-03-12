package uk.ac.gold.memorygame.view.components;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import uk.ac.gold.memorygame.model.Card;

public class TextCardButton extends CardButton {

    private static final Logger LOGGER = LogManager.getLogger();

    private final Label label;

    public TextCardButton(Card cardModel, String text) {
        super(cardModel);

        label = createLabel(text);

        // Assign face as the button graphic after the layout has been
        // initialised so that auto-scaling works.
        Platform.runLater(() -> setGraphic(label));
    }

    private Label createLabel(String text) {
        LOGGER.debug("Creating button label {} for {}", text, this);

        Label label = new Label(text);
        label.setAlignment(Pos.CENTER);
        label.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);

        // Scale the font size according to button size.
        label.fontProperty().bind(
                Bindings.createObjectBinding(
                        () -> Font.font(Math.min(getWidth(), getHeight()) / 3),
                    widthProperty(), heightProperty()));

        // Initially hide label.
        label.setVisible(false);

        return label;
    }

    protected void updateText(String text) {
        label.setText(text);
    }

    @Override
    public boolean isFaceUp() {
        return label.isVisible();
    }

    @Override
    protected void show() {
        LOGGER.debug("Show face {}", cardModel);

        // Update button state.
        label.setVisible(true);
    }

    @Override
    protected void hide() {
        LOGGER.debug("Hide face {}", cardModel);

        // Update button state.
        label.setVisible(false);
    }
}
