package uk.ac.gold.memorygame.model;
// used to store the players name and score 
public class User {
    private final String name;
    private int score =0;

    public User(String name){
        this.name = name;
    }
    public int getScore(){
        return score;
    }
    
    public String getName(){
        return name;
    }
    public void addScore(int points){
        score +=points;
    }
}