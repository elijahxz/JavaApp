package com.example.brickbreaker;

import static android.graphics.Color.BLACK;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick implements GameObject{
    public static int numBricks = 0;
    private boolean isVisible = true;
    public int row, column, width, height;
    private int left = 0, top  = 0, right, bottom;

    public Brick(int row, int column, int width, int height) {
        isVisible = true;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    public void setInvisible()
    {
        isVisible = false;
        Brick.numBricks --;
    }

    public boolean getVisibility() {
        return isVisible;
    }

    @Override
    public void draw(Canvas canvas) {
        if (isVisible == false)
        {
            return;
        }
        Paint paint = new Paint();
        paint.setColor(BLACK);

        left = 40 + (20 * column) + (width * column);
        top = 70 + (20 * row) + (height  * row);
        right = left + width;
        bottom = top + height;

        canvas.drawRect((left),
                (top),
                (right),
                (bottom),
                paint);

    }

    public int getTop() {
        return this.top;
    }
    public int getBottom() {
        return this.bottom;
    }
    public int getLeft() {
        return this.left;
    }
    public int getRight() {
        return this.right;
    }

    @Override
    public void update() {

    }
}