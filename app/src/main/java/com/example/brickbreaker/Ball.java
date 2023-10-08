package com.example.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Ball implements GameObject{
    private int radius;
    private int color;
    private int x = 400;
    private int y = 400;
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
}
