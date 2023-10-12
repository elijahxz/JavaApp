package com.example.brickbreaker;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/*
*   This class is where all of the fun stuff happens!
*   It creates all of the threads and objects seen on the screen!
*/
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private RectPlayer player;
    private Ball mainBall;
    private Point playerPoint;
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static final int playerWidth = 400;
    public static final int playerHeight = 40;

    //bricks

    Paint brickPaint = new Paint();

    Brick[] bricks = new Brick[30];
    int numBricks = 0;
    int brokenBricks = 0;

    public GamePanel(Context context){
        super(context);

        // Gets the dimensions of the screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GamePanel.screenHeight = displayMetrics.heightPixels;
        GamePanel.screenWidth = displayMetrics.widthPixels;

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(0, 0, this.playerWidth, this.playerHeight), Color.rgb(255,0,0));

        mainBall = new Ball(50, Color.rgb(255,0,0));

        playerPoint = player.setPoint(this.screenHeight, this.screenWidth);

        setFocusable(true);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
    {
    }
    @Override
    public void surfaceCreated(SurfaceHolder holder)
    {
        thread = new MainThread(getHolder(), this);

        thread.setRunning(true);
        thread.start();
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder)
    {
        boolean retry = true;
        while(true)
        {
            try
            {
                thread.setRunning(false);
                thread.join();
            }catch(Exception e)
            {
                e.printStackTrace();
            }
            retry = false;
        }
    }
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                player.touchWrapper(event, playerPoint, 0);
                break;
            case MotionEvent.ACTION_MOVE:
                player.touchWrapper(event, playerPoint, 1);
                break;
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                player.setTouch(false);
                break;
        }
        return true;
        //return super.onTouchEvent(event);
    }
    public void update()
    {
        player.update(playerPoint);
        mainBall.update(player);
        mainBall.update(bricks,numBricks);
    }

    @Override
    public void draw(Canvas canvas )
    {
        super.draw(canvas);

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        mainBall.draw(canvas);
        for (Brick brick : bricks) {
            brick.draw(canvas);
        }


        //added
        for(int i =0; i<numBricks;i++) {
            if(bricks[i].getVisibility()){
                canvas.drawRect(bricks[i].column * bricks[i].width + 1, bricks[i].row * bricks[i].height + 1, bricks[i].column * bricks[i].width + bricks[i].width -1, bricks[i].row * bricks[i].height + bricks[i].height -1,brickPaint);
            }
            for(int j=0; j<numBricks;j++) {
                if(bricks[i].getVisibility()) {
                    mainBall.update(bricks, numBricks);
                }
            }
        }
    }

    //added
    private void createBricks() {
        int brickWidth = 50/8;
        int brickHeight = 50/16;
        for(int column = 0; column < 8; column++) {
            for(int row= 0; row < 3; row++) {
                bricks[numBricks] = new Brick(row, column, brickWidth, brickHeight);
            }
        }


    }


}
