package com.example.brickbreaker;

import android.graphics.Canvas;

/*
    This is an interface, it just states that any
    class that implements this interface MUST have
    a draw and update method
*/
public interface GameObject {
    public void draw(Canvas canvas);
    public void update();
}
