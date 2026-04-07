package uk.ac.gold.memorygame.config;
import java.util.List;

public class PictureCardDeck implements CardDeck<String> {
    private final List<String> items;
    private final String name;

    public PictureCardDeck(String name,List<String>items){
        this.name= name;
        this.items= items;
    }

    @Override
    public int numberOfItems(){
        return items.size();
    }

    @Override
    public String name(){
        return name;
    }
    @Override
    public List<String>getItems(){
        return items;
    }
    @Override
    public String get(int pairId){
        return items.get(pairId);
    }
}