package com.example.brickbreaker;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

/*
*   This class is where all of the fun stuff happens!
*   It creates all of the threads and objects seen on the screen!
*/
public class GamePanel extends SurfaceView implements SurfaceHolder.Callback {
    private MainThread thread;
    private RectPlayer player;
    private Ball mainBall;
    private Point playerPoint;
    private Score playerScore = new Score();
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static final int playerWidth = 400;
    public static final int playerHeight = 40;

    //private Button button2;

    //bricks

    Paint brickPaint = new Paint();
    int numBricks = 18;
    Brick[] bricks = new Brick[numBricks];
    int brokenBricks = 0;

    public GamePanel(Context context){
        super(context);

        // Gets the dimensions of the screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        GamePanel.screenHeight = displayMetrics.heightPixels;
        GamePanel.screenWidth = displayMetrics.widthPixels;
        brickPaint.setColor(1);
        createBricks();

        getHolder().addCallback(this);

        thread = new MainThread(getHolder(), this);

        player = new RectPlayer(new Rect(0, 0, this.playerWidth, this.playerHeight), Color.rgb(255,0,0));

        mainBall = new Ball(50, Color.rgb(255,0,0));

        playerPoint = player.setPoint(this.screenHeight, this.screenWidth);

        setFocusable(true);

//        Button button2 = new Button(this.getContext()); // "this" refers to the current Activity's context
//        button2.setText("Exit");
//
//        // Set button properties
//        button2.setX(400.0F);
//        button2.setY(50.0F);
//        button2.setTextColor(BLUE);
//
//        button2.setOnClickListener(new OnClickListener() {
//            public void onClick(View v) {
//                System.exit(0);
//            }
//        });
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
    public void update() throws InterruptedException {
        player.update(playerPoint);
        mainBall.update(player, thread, playerScore);
        mainBall.update(bricks,numBricks, playerScore);
    }

    @Override
    public void draw(Canvas canvas )
    {
        super.draw(canvas);

        Paint paint = new Paint();
        paint.setTextSize(44);
        paint.setColor(Color.argb(255,0,0,255));

        canvas.drawColor(Color.WHITE);

        player.draw(canvas);
        mainBall.draw(canvas);
        for (int i = 0; i < numBricks; i ++) {
            bricks[i].draw(canvas);
        }

        int score = 0;
        int lives = 3;

        canvas.drawText("Score: " + playerScore.getScore() + "   Lives: " + playerScore.getLives(), 10, 50, paint);

        //button2.draw(canvas);

//<<<<<<< HEAD
//=======
//
//
//
//>>>>>>> 5f979daf4b3cab8c90df3d67a8cb4b0839aadc2b
    }

    private void addContentView(Button button2, ViewGroup.LayoutParams layoutParams) {
    }

    //added
    private void createBricks() {
        int brickWidth = 2400/16;
        int brickHeight = 1600/16;
        int counter = 0;
        for(int column = 0; column < 6; column++) {
            for (int row = 0; row < 3; row++) {
                bricks[counter] = new Brick(row, column, brickWidth, brickHeight);
                counter++;
            }
        }

    }


}
