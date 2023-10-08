package com.example.brickbreaker;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;

/*
    This is the class implementation for the
    slider (paddle) at the bottom of the screen.
*/
public class RectPlayer implements GameObject{
    private Rect rectangle;
    private int color;
    public static final int playerWidth = 400;
    public static final int playerHeight = 40;

    private int leftSide;
    private int rightSide;
    private int PLAYER_LOCK_Y;
    public RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;
    }
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }

    public void update(Point point)
    {
        // Check if paddle is gonna hit the edges
        if (point.x + rectangle.width()/2 > GamePanel.screenWidth)
        {
            point.x = GamePanel.screenWidth - rectangle.width()/2;
        }
        else if(point.x - rectangle.width()/2 < 0)
        {
            point.x = rectangle.width()/2;
        }
        // This should not change, (unless we add a power up that increases the rectangle size!)
        rectangle.set(point.x - rectangle.width()/2,
                      PLAYER_LOCK_Y - rectangle.height()/2,
                point.x + rectangle.width()/2,
                   PLAYER_LOCK_Y + rectangle.height()/2);

        // gets the paddles edges
        leftSide = point.x - rectangle.width()/2;
        rightSide = point.x + rectangle.width()/2;
    }
    // Sets the player.
    public Point setPoint(int height, int width)
    {
        int x=0, y=0;

        x = (width/2);
        y = height - (this.playerHeight/2) - 100;

        this.PLAYER_LOCK_Y = y;

        leftSide = x - rectangle.width()/2;
        rightSide = x + rectangle.width()/2;

        return new Point(x, y);
    }
    public int getLeftSide()
    {
        return this.leftSide;
    }
    public int getRightSide()
    {
        return this.rightSide;
    }
    // Returns the top border of the paddles box
    public int getTopSide()
    {
        return this.PLAYER_LOCK_Y + (playerHeight/2);
    }
    public int getBottomSide()
    {
        return this.PLAYER_LOCK_Y - (playerHeight/2);
    }


}
