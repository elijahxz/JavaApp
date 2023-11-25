package com.example.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;
import android.os.SystemClock;
import android.provider.MediaStore;

import java.io.IOException;

public class Ball implements GameObject{
    private int radius;
    private int color;
    private GamePanel game;
    private int x = (int) (Math.random() * (800 - 400)) + 400;
    private int y = 1000;
    private int ballSpeedX = 20;
    private int ballSpeedY = 20;
    private int paddleUpdated = 0;
    public int ballSpeedX2 = 0;
    public int ballSpeedY2 = 0;

    // Ball constructor (used by GamePanel).
    public Ball(int radius, int color, GamePanel game)
    {
        this.radius = radius;
        this.color = color;
        this.game = game;
    }

    // This method draws the ball onto the canvas.
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawCircle(x, y, radius, paint);
    }

    @Override
    public void update()
    {

    }

    // Updates the ball based upon paddle hits and screen edge hits.
    public void update(RectPlayer player, GamePanel game, Score score, MediaPlayer paddle, MediaPlayer wall, MediaPlayer death) {
        int left = player.getLeftSide();
        int right = player.getRightSide();
        int bottom = player.getBottomSide();
        int top = player.getTopSide();
        int bTop = y - radius;
        int bBottom = y + radius;
        int bRight = x + radius;
        int bLeft = x - radius;

        /*
        *   Explination: We were having issues with the ball glitching inside of the paddel.
        *       To avoid this, we created the paddleUpdated variable. It gets set to an integer value
        *       when the paddle is updated and will get decremented every time the ball is updated on the screen.
        *       This allows for the redirect functionality desired.
        *
        *       You can also view it as a wait() function.
        */
        if (paddleUpdated != 0)
        {
            paddleUpdated --;
        }
        else {
            // A hit on the right side of the paddles corner.
            // The logic here is not the cleanest but we wanted for the x and y direction to change on corner hits.
            if (bRight - (radius * .8) >= left && bRight - (radius * .8) <= left + 20 && bBottom > top && bBottom < bottom) {
                if (ballSpeedX > 0) {
                    ballSpeedX = -ballSpeedX;
                }
                ballSpeedY = -ballSpeedY;
                paddleUpdated = 20;

                triggerPaddleSound(paddle);
            }
            // A hit on the left side of the paddles corner.
            // The logic here is not the cleanest but we wanted for the x and y direction to change on corner hits.
            else if (bLeft + (radius * .8) >= right && bLeft + (radius * .8) <= right + 20 && bBottom > top && bBottom < bottom) {
                if (ballSpeedX < 0) {
                    ballSpeedX = -ballSpeedX;
                }
                ballSpeedY = -ballSpeedY;
                paddleUpdated = 20;

                triggerPaddleSound(paddle);
            }
            // A hit on the left side of the paddle
            // Instead of the ball glitching through the paddle, this will redirect it.
            else if (bRight >= left && bRight <= right &&
                    y >= top && y <= bottom) {
                ballSpeedX = -ballSpeedX;
                paddleUpdated = 20;
            }
            // A hit on the left side of the paddle
            // Instead of the ball glitching through the paddle, this will redirect it.
            else if (bLeft >= left && bLeft <= right &&
                    y >= top && y <= bottom) {
                ballSpeedX = -ballSpeedX;
                paddleUpdated = 20;
            }
            // A hit on the top of the paddle. This is the most generic case.
            else if (bBottom > top && bBottom < bottom && x > left && x < right) {
                ballSpeedY = -ballSpeedY;
                if (!(ballSpeedX > 40 || ballSpeedX < -40)) {
                    ballSpeedX *= 1.1;
                    ballSpeedX *= 1.1;
                }
                if (!(ballSpeedY > 40 || ballSpeedY < -40)) {
                    ballSpeedY *= 1.1;
                    ballSpeedY *= 1.1;
                }
                paddleUpdated = 20;
                triggerPaddleSound(paddle);
            }
        }

        // Checks to see if the ball is interacting with the screen.
        // Checks the right side of the screen
        if (bRight > GamePanel.screenWidth)
        {
            ballSpeedX = -ballSpeedX;
            if(wall.isPlaying()) {
                wall.seekTo(0);
            }
            else {
                wall.start();
            }
        }
        // Checks the bottom of the screen.
        if (bBottom > GamePanel.screenHeight)
        {
            ballSpeedY = -ballSpeedY;

            score.lostLife();

            if(score.getLives() != 0) {
                if (death.isPlaying())
                    death.seekTo(0);
                else {
                    death.start();
                }
            }

            if(score.getLives() <= 0){
                game.finishGame();
            }
            else{
                try{
                    SystemClock.sleep(3000);
                }catch(Exception e){e.printStackTrace();}
                x = (int) (Math.random() * (800 - 400)) + 400;
                y = 1000;
            }
        }
        // Checks the top of the screen.
        // Here we have a margin for the scoreboard and pause button.
        if (bTop < game.MARGIN)
        {
            ballSpeedY = -ballSpeedY;
            if(wall.isPlaying()) {
                wall.seekTo(0);
            }
            else {
                wall.start();
            }
        }
        // Checks the left side of the screen.
        if (bLeft < 0){
            ballSpeedX = -ballSpeedX;
            if(wall.isPlaying()) {
                wall.seekTo(0);
            }
            else {
                wall.start();
            }
        }

        // On this update function, we increase the new position.
        x += ballSpeedX;
        y += ballSpeedY;
    }

    // Updates the ball based on brick hits.
    public void update(Brick bricks[], int numBricks, Score score, MediaPlayer hit) {
        int bLeft = x - radius;
        int bRight = x + radius;
        int bTop = y - radius;
        int bBottom = y + radius;

        // First, iterate through each brick.
        for (int i = 0; i < numBricks; i++) {

            // If the brick is not visible, we can skip it.
            if (bricks[i].getVisibility()) {

                // Checks if the top of the ball is hitting the bottom of the brick
                if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bTop >= bricks[i].getTop() && bTop <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;

                    triggerBrickSound(hit);

                    score.addScore();
                    bricks[i].setInvisible();
                    break;
                }
                // Checks if the bottom of the ball is hitting the top  of the brick
                else if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bBottom >= bricks[i].getTop() && bBottom <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;
                    triggerBrickSound(hit);

                    bricks[i].setInvisible();
                    score.addScore();
                    break;
                }
                // Checks if the right side of the ball is hitting the left side of the brick
                else if (bRight >= bricks[i].getLeft() && bRight <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;
                    triggerBrickSound(hit);

                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                }
                // Checks if the left side of the ball is hitting the right side of the brick
                else if (bLeft >= bricks[i].getLeft() && bLeft <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;

                    triggerBrickSound(hit);

                    score.addScore();
                    bricks[i].setInvisible();
                    break;
                }
            }
        }
        // Checks if we have zero bricks left, if yes, finish the game.
        // This static variable gets updated in the setInvisible method
        if (Brick.numBricks == 0)
        {
            game.finishGame();
        }
    }

    // Used for pause button
    // Gets the last speed and then sets the current speed to zero
    public void GetSpeed() {
        ballSpeedX2 = ballSpeedX;
        ballSpeedY2 = ballSpeedY;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    // Used for pause button
    // Sets the speed as the last documented speed
    public void SetSpeed() {
        ballSpeedX = ballSpeedX2;
        ballSpeedY = ballSpeedY2;
    }

    // triggers the paddle hit sound
    private void triggerPaddleSound(MediaPlayer paddle)
    {
        if(paddle.isPlaying())
            paddle.seekTo(0);
        else
            paddle.start();
    }
    // Triggers the brick hit sound
    private void triggerBrickSound(MediaPlayer hit)
    {
        if(hit.isPlaying())
            hit.seekTo(0);
        else
            hit.start();
    }
}
