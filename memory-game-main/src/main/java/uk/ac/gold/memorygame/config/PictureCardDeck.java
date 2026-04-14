package uk.ac.gold.memorygame.config;
import java.util.List;

public class PictureCardDeck implements CardDeck<String> {
    private final List<String> items;
    private final String name;
   
    public PictureCardDeck(String name,List<String>items){
        this.name= name;
        this.items= items;
    }
    // gives the number of pairs in the deck
    @Override
    public int numberOfItems(){
        return items.size();
    }

    @Override
    public String name(){
        return name;
    }
    // gives the full list of cards in the deck
    @Override
    public List<String>getItems(){
        return items;
    }
    // gives back a specific item based on the index
    @Override
    public String get(int pairId){
        return items.get(pairId);
    }
}