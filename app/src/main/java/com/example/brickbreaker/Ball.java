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

    // Used for pause button
    public void GetSpeed() {
        ballSpeedX2 = ballSpeedX;
        ballSpeedY2 = ballSpeedY;
        ballSpeedX = 0;
        ballSpeedY = 0;
    }

    // Used for pause button
    public void SetSpeed() {
        ballSpeedX = ballSpeedX2;
        ballSpeedY = ballSpeedY2;
    }

    public Ball(int radius, int color, GamePanel game)
    {
        this.radius = radius;
        this.color = color;
        this.game = game;
    }

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

    //Has Sounds
    public void update(RectPlayer player, GamePanel game, Score score, MediaPlayer paddle, MediaPlayer wall, MediaPlayer death) {
        int left = player.getLeftSide();
        int right = player.getRightSide();
        int bottom = player.getBottomSide();
        int top = player.getTopSide();
        int bTop = y - radius;
        int bBottom = y + radius;
        int bRight = x + radius;
        int bLeft = x - radius;
        if (paddleUpdated != 0)
        {
            paddleUpdated --;
        }
        else {
           //A hit on the paddle
            if (bRight - (radius * .8) >= left && bRight - (radius * .8) <= left + 20 && bBottom > top && bBottom < bottom) {
                if (ballSpeedX > 0) {
                    ballSpeedX = -ballSpeedX;
                }
                ballSpeedY = -ballSpeedY;
                paddleUpdated = 20;

                triggerPaddleSound(paddle);

            }
            else if (bLeft + (radius * .8) >= right && bLeft + (radius * .8) <= right + 20 && bBottom > top && bBottom < bottom) {
                if (ballSpeedX < 0) {
                    ballSpeedX = -ballSpeedX;
                }
                ballSpeedY = -ballSpeedY;
                paddleUpdated = 20;

                triggerPaddleSound(paddle);

            }
            else if (bRight >= left && bRight <= right &&
                    y >= top && y <= bottom) {
                ballSpeedX = -ballSpeedX;
                paddleUpdated = 20;
            }
            else if (bLeft >= left && bLeft <= right &&
                    y >= top && y <= bottom) {
                ballSpeedX = -ballSpeedX;
                paddleUpdated = 20;
            }
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
        if (bLeft < 0){
            ballSpeedX = -ballSpeedX;
            if(wall.isPlaying()) {
                wall.seekTo(0);
            }
            else {
                wall.start();
            }
        }

        x += ballSpeedX;
        y += ballSpeedY;
    }

    public void update(Brick bricks[], int numBricks, Score score, MediaPlayer hit) {
        int bLeft = x - radius;
        int bRight = x + radius;
        int bTop = y - radius;
        int bBottom = y + radius;

        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bTop >= bricks[i].getTop() && bTop <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;
                    if(hit.isPlaying())
                        hit.seekTo(0);
                    else
                        hit.start();

                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                } else if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bBottom >= bricks[i].getTop() && bBottom <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;
                    if(hit.isPlaying())
                        hit.seekTo(0);
                    else
                        hit.start();

                    bricks[i].setInvisible();
                    score.addScore();
                    break;
                } else if (bRight >= bricks[i].getLeft() && bRight <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;
                    if(hit.isPlaying())
                        hit.seekTo(0);
                    else
                        hit.start();

                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                } else if (bLeft >= bricks[i].getLeft() && bLeft <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;
                    if(hit.isPlaying())
                        hit.seekTo(0);
                    else
                        hit.start();

                    score.addScore();
                    bricks[i].setInvisible();
                    break;
                }
            }
        }
        if (Brick.numBricks == 0)
        {
            game.finishGame();
        }
    }
    private void triggerPaddleSound(MediaPlayer paddle)
    {
        if(paddle.isPlaying())
            paddle.seekTo(0);
        else
            paddle.start();
    }
}
