package com.example.brickbreaker;

public class Score {
    private int score;
    private int lives;

    public Score(){
        score = 0;
        lives = 3;
    }
    public void addScore(){
        score += 10;
    }
    public void lostLife() {
        score -= 5;
        lives -= 0;
    }
    public void reset(){
        score = 0;
        lives = 3;
    }
    public int getScore(){
        return score;
    }
    public int getLives(){
        return lives;
    }

}
