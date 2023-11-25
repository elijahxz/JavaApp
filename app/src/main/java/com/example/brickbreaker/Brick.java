package com.example.brickbreaker;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick implements GameObject{
    public static int numBricks = 0;
    private boolean isVisible = true;
    public int row, column, width, height;
    private int left = 0, top  = 0, right, bottom;

    // Brick constructor
    public Brick(int row, int column, int width, int height) {
        isVisible = true;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    // Sets the brick as invisible and decrements the number of bricks.
    public void setInvisible()
    {
        isVisible = false;
        Brick.numBricks --;
    }

    // Checks if a brick is visible.
    public boolean getVisibility() {
        return isVisible;
    }

    // This draws the brick onto the canvas.
    @Override
    public void draw(Canvas canvas) {
        // If the brick is not visible, skip it
        if (isVisible == false)
        {
            return;
        }

        // Randomize the brick paint on each update
        Paint paint = new Paint();
        int randomColor = (int) ((Math.random() * (4 - 0)) + 0);

        // Check the random number and assign it a color.
        if(randomColor == 0)
            paint.setColor(YELLOW);
        if(randomColor == 1)
            paint.setColor(RED);
        if(randomColor == 2)
            paint.setColor(GREEN);
        if(randomColor == 3)
            paint.setColor(BLUE);
        if(randomColor == 4)
            paint.setColor(BLACK);

        // This sets the brick placement.
        // The static numbers are for the padding.
        // 40, set a padding on the top
        // 70 set a padding on the left side
        // 20 for top and left set the padding in between the bricks
        left = 40 + (20 * column) + (width * column);
        top = 70 + (20 * row) + (height  * row);
        right = left + width;
        bottom = top + height;

        // Draws the rectangle on the screen.
        canvas.drawRect((left),
                (top),
                (right),
                (bottom),
                paint);
    }

    // Gets the top of the brick
    public int getTop() {
        return this.top;
    }
    // Gets the bottom of the brick
    public int getBottom() {
        return this.bottom;
    }
    // Gets the left side of the brick
    public int getLeft() {
        return this.left;
    }
    // Gets the right side of the brick
    public int getRight() {
        return this.right;
    }

    // unused
    @Override
    public void update() {

    }
}