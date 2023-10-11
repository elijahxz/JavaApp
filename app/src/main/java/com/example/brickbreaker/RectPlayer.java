package com.example.brickbreaker;

import android.app.Activity;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;

/*
    This is the class implementation for the
    slider (paddle) at the bottom of the screen.
*/
public class RectPlayer implements GameObject{
    public static final int playerWidth = 400;
    public static final int playerHeight = 40;
    private Rect rectangle;
    private int color;
    private boolean initialization = true, playerTouch = false;
    private int leftSide, rightSide, offsetX;
    private int PLAYER_LOCK_Y;
    public RectPlayer(Rect rectangle, int color)
    {
        this.rectangle = rectangle;
        this.color = color;
    }
    /*
    *   Used for initialization of the rectangles location
    */
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
    /*
    * Draws the rectangle onto the screen
    */
    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(color);
        canvas.drawRect(rectangle, paint);
    }

    @Override
    public void update() {

    }
    /*
     * This updates the players position. This is called inside of GamePanel's update method
     */
    public void update(Point point)
    {
        if (playerTouch == false )
        {
            if (initialization == true)
            {
                initialization = false;
            }
            else
            {
                return;
            }
        }
        // Check if paddle is gonna hit the edges
        if (point.x + rectangle.width()/2 > GamePanel.screenWidth)
        {
            point.x = GamePanel.screenWidth - rectangle.width()/2;
        }
        else if(point.x - rectangle.width()/2 < 0)
        {
            point.x = rectangle.width()/2;
        }
        // This should not change
        rectangle.set(point.x - rectangle.width()/2,
                      PLAYER_LOCK_Y - rectangle.height()/2,
                point.x + rectangle.width()/2,
                   PLAYER_LOCK_Y + rectangle.height()/2);

        // gets the paddles edges
        leftSide = point.x - rectangle.width()/2;
        rightSide = point.x + rectangle.width()/2;
    }
    /*
     * This wrapper activates movement if the touch is near the rectangle
     */
    public void touchWrapper(MotionEvent event, Point playerPoint, int touchType)
    {
        /*
        * If the user just put their finger on the screen, ensure the touch is registered on
        * the paddle then account for the offset between their finger and the middle of the paddle.
        * This ensures that he paddle does not teleport to the user's finger location :)
        */
        if (touchType == 0 && checkCoords((int) event.getX(), (int) event.getY()) == true)
        {
            offsetX = playerPoint.x - (int)event.getX();
        }

        /*
         * Handles the normal touch events, moves the paddle to a location via the users request.
         */
        if (playerTouch == true)
        {
            playerPoint.set((int) event.getX() + offsetX, (int) event.getY());
        }
        else if(checkCoords((int) event.getX(), (int) event.getY()) == true)
        {
            playerTouch = true;
            playerPoint.set((int) event.getX() + offsetX, (int) event.getY());
        }
    }
    /*
     * Gets the left border of the paddle (X coord)
     */
    public int getLeftSide()
    {
        return this.leftSide;
    }
    /*
     * Gets the right border of the paddle (X coord)
     */
    public int getRightSide()
    {
        return this.rightSide;
    }
    /*
     * Gets the top border of the paddle (Y coord)
     */
    public int getTopSide()
    {
        return this.PLAYER_LOCK_Y + (playerHeight/2);
    }
    /*
     * Gets the bottom border of the paddle (Y coord)
     */
    public int getBottomSide()
    {
        return this.PLAYER_LOCK_Y - (playerHeight/2);
    }
    /*
     * Sets whether the player is currently touching the paddle
     * Some cases the GamePanel needs to specify this.
     */
    public void setTouch(boolean touch)
    {
        this.playerTouch = touch;
    }
    /*
     * Checks to see if the player is touching the paddle
     */
    public boolean activeTouch()
    {
        return this.playerTouch;
    }
    /*
     * This checks to see if the player is attempting to move the paddle
     * Currently has a 20px padding --- may need to increase this!
     */
    public boolean checkCoords(int x, int y)
    {
        if (y < PLAYER_LOCK_Y + 20 && y > PLAYER_LOCK_Y - 20 && x < rightSide + 20 && x > leftSide - 20)
        {
            return true;
        }
        return false;
    }

}
