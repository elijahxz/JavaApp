package com.example.brickbreaker;

import static android.graphics.Color.BLACK;

import android.graphics.Canvas;
import android.graphics.Paint;

public class Brick implements GameObject{

    private boolean isVisible;
    public int row, column, width, height;

    public Brick(int row, int column, int width, int height) {
        isVisible = true;
        this.row = row;
        this.column = column;
        this.width = width;
        this.height = height;
    }

    public void setInvisible() {
        isVisible = false;
    }

    public boolean getVisibility() {
        return isVisible;
    }


    @Override
    public void draw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(BLACK);
        int left = 20;
        int right = width + 20;
        int bottom = height + 20;
        int top = 20;
        if (column % 2 == 0)
        {
            right -= 10;
        }
        else
        {
            left += 10;
        }
        if (row % 2 == 0)
        {
            bottom -= 10;
        }
        else
        {
            top += 10;
        }
        canvas.drawRect(left + (width * column),top + (height*row), right + (width * column), bottom + (height*row),paint);

    }

    @Override
    public void update() {

    }
}
