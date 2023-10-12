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
        canvas.drawRect(3,3,3, 3,paint);

    }

    @Override
    public void update() {

    }
}
