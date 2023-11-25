package com.example.brickbreaker;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.SystemClock;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.media.MediaPlayer;

/*
*   This class is where all of the fun stuff happens!
*   It creates all of the threads and objects seen on the screen!
*/
public class GamePanel extends View {
    private RectPlayer player;
    private Ball mainBall;
    private Point playerPoint;
    private Score playerScore = new Score();

    Handler handler;
    Runnable runnable;
    private final long UPDATE_MILLIS = 30;
    public final int MARGIN = 65;
    public static int screenHeight = 0;
    public static int screenWidth = 0;
    public static final int playerWidth = 250;
    public static final int playerHeight = 40;
    boolean pause = true;
    public Rect pauseButton;
    private MediaPlayer startSound = MediaPlayer.create(getContext(), R.raw.startsound);
    private MediaPlayer paddleSound = MediaPlayer.create(getContext(), R.raw.paddlehit);
    private MediaPlayer deathSound = MediaPlayer.create(getContext(), R.raw.deathsound);
    private MediaPlayer wallSound = MediaPlayer.create(getContext(), R.raw.wallhit);
    private MediaPlayer hitSound = MediaPlayer.create(getContext(), R.raw.hitsound);

    Paint brickPaint = new Paint();
    int numBricks = 18;
    Brick[] bricks = new Brick[numBricks];

    MainActivity game;

    public GamePanel(Context context, MainActivity game) /*throws InterruptedException*/ {
        super(context);

        this.game = game;

        handler = new Handler();
        runnable = new Runnable()
        {
            public void run()
            {
                invalidate();
            }
        };

        // Gets the dimensions of the screen
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) getContext()).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);

        GamePanel.screenHeight = displayMetrics.heightPixels;
        GamePanel.screenWidth = displayMetrics.widthPixels;

        brickPaint.setColor(1);

        createBricks();

        player = new RectPlayer(new Rect(0, 0, this.playerWidth, this.playerHeight), Color.rgb(255,0,0));

        mainBall = new Ball(50, Color.rgb(255,0,0), this);

        playerPoint = player.setPoint(this.screenHeight, this.screenWidth);

        setFocusable(true);

        if(startSound.isPlaying())
            startSound.stop();
        else
            startSound.start();

        SystemClock.sleep(3000);

        pauseButton = new Rect(screenWidth-100,0,screenWidth,60);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                // this calls the movement for the paddle
                player.touchWrapper(event, playerPoint, 0);

                // This works the pause menu
                if(x > pauseButton.left && y < pauseButton.bottom) {
                    if(pause == true) {
                        mainBall.GetSpeed();
                        pause = false;

                    }
                    else if(pause == false && x > pauseButton.left && y < pauseButton.bottom) {
                        pause = true;
                        mainBall.SetSpeed();
                    }
                }
                break;
            case MotionEvent.ACTION_MOVE:
                //This calls the movement for the paddle
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
    public void update(){
        player.update(playerPoint);
        mainBall.update(player, this, playerScore, paddleSound, wallSound, deathSound);
        mainBall.update(bricks,numBricks, playerScore, hitSound);
    }

    @Override
    public void onDraw(Canvas canvas )
    {
        update();
        super.onDraw(canvas);
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

        paint = new Paint();
        paint.setColor(RED);

        canvas.drawRect(pauseButton, paint);
        handler.postDelayed(runnable, UPDATE_MILLIS);
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
        Brick.numBricks = numBricks;

    }

    public void finishGame()
    {
        getHandler().removeCallbacksAndMessages(null);
        Intent intent = new Intent(getContext(), GameOver.class);
        intent.putExtra("points", playerScore.getScore());
        game.finish();
        getContext().startActivity(intent);
        ((Activity) getContext()).finish();
    }
}
