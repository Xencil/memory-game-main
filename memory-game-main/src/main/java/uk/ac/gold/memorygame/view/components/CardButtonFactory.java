package uk.ac.gold.memorygame.view.components;

import uk.ac.gold.memorygame.config.CardDeck;
import uk.ac.gold.memorygame.model.Card;

public class CardButtonFactory{

    public static <E> CardButton create(CardDeck<E> cardDeck, Card cardModel){
        E value =cardDeck.get(cardModel.getPairId());
        String name =(String) value;
        
        if(value instanceof String){
            // makes a check to see if its a image file
            if(name.endsWith(".png")){
                return new PictureButton(cardModel,name);
            }
            return new TextCardButton(cardModel,(String)value);
        }
        throw new IllegalArgumentException(
                "unkown card type: " +value.getClass().getName()
        );
    }
}