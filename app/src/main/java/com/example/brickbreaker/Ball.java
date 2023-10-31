package com.example.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.MediaPlayer;

public class Ball implements GameObject{
    private int radius;
    private int color;
    private int x = 800;
    private int y = 800;
    private int ballSpeedX = 20;
    private int ballSpeedY = -20;
    public Ball(int radius, int color)
    {
        this.radius = radius;
        this.color = color;
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

    public void update(RectPlayer player)
    {
        int left = player.getLeftSide();
        int right = player.getRightSide();
        int bottom = player.getBottomSide();
        int top = player.getTopSide();
        int projX = x + ballSpeedX;
        int projY = y + ballSpeedY;

        // A hit on the paddle
        if (projX > left && projX < right && projY < top && projY > bottom)
        {
            ballSpeedY = -ballSpeedY;
        }

        // Checks to see if the ball is interacting with the screen.
        if (projX > GamePanel.screenWidth)
        {
            ballSpeedX = -ballSpeedX;
        }
        else if (projY > GamePanel.screenHeight)
        {
            ballSpeedY = -ballSpeedY;
        }
        else if (projY < 0)
        {
            ballSpeedY = -ballSpeedY;
        }
        else if (projX < 0){
            ballSpeedX = -ballSpeedX;
        }

        x += ballSpeedX;
        y += ballSpeedY;
    }

    public void update(Brick bricks[], int numBricks, Score score) {
        int bLeft = x - radius;
        int bRight = x + radius;
        int bTop = y - radius;
        int bBottom = y + radius;

        for (int i = 0; i < numBricks; i++) {
            if (bricks[i].getVisibility()) {
                if (bRight >= bricks[i].getLeft() && bRight <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;
                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                } else if (bLeft >= bricks[i].getLeft() && bLeft <= bricks[i].getRight() &&
                        y >= bricks[i].getTop() && y <= bricks[i].getBottom()) {
                    ballSpeedX = -ballSpeedX;
                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                } else if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bTop >= bricks[i].getTop() && bTop <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;
                    score.addScore();
                    bricks[i].setInvisible();
                    break;

                } else if (x >= bricks[i].getLeft() && x <= bricks[i].getRight() &&
                        bBottom >= bricks[i].getTop() && bBottom <= bricks[i].getBottom()) {
                    ballSpeedY = -ballSpeedY;
                    bricks[i].setInvisible();
                    score.addScore();
                    break;

                }
            }
        }
    }



    public int getRadius(){
        return radius;
    }
}
