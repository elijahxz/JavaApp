package com.example.brickbreaker;

// Score class keeps track of the current amount of lives
// and the score the player has thus far.
public class Score {
    private int score;
    private int lives;

    // Constructor that initializes score and lives.
    public Score(){
        score = 0;
        lives = 3;
    }

    // Update score. Called when a block is hit.
    public void addScore(){
        score += 10;
    }

    // Decrease lives. Also decreases score.
    public void lostLife() {
        if(score < 5){
            score = 0;
        }
        else {
            score -= 5;
        }
        lives -= 1;
    }

    // Refresh lives and score to default value
    public void reset(){
        score = 0;
        lives = 3;
    }

    // Return score and and live count
    public int getScore(){
        return score;
    }
    public int getLives(){
        return lives;
    }

}
