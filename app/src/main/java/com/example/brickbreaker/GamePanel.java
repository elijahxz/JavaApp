package com.example.brickbreaker;
import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.RED;
import static android.graphics.Color.WHITE;

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
import android.widget.TextView;

/*
*   This class is where all of the fun stuff happens!
*   It creates all objects seen on the screen!
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
    private MediaPlayer startSound = MediaPlayer.create(getContext(), R.raw.startsound); //Sounds
    private MediaPlayer paddleSound = MediaPlayer.create(getContext(), R.raw.paddlehit);
    private MediaPlayer deathSound = MediaPlayer.create(getContext(), R.raw.deathsound);
    private MediaPlayer wallSound = MediaPlayer.create(getContext(), R.raw.wallhit);
    private MediaPlayer hitSound = MediaPlayer.create(getContext(), R.raw.hitsound);
    private MediaPlayer gameOverSound = MediaPlayer.create(getContext(), R.raw.gameoversound);
    private MediaPlayer winSound = MediaPlayer.create(getContext(), R.raw.winsound);

    Paint brickPaint = new Paint();
    int numBricks = 18;
    Brick[] bricks = new Brick[numBricks];

    MainActivity game;

    // Only constructor used for this class.
    // It does quite a bit of setup.
    public GamePanel(Context context, MainActivity game) {
        super(context);

        // Set the main activity so we can use it later
        this.game = game;

        // This starts the game. We pulled this from a youtube video. It works like a thread.
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

        // Set the screen dimensions as a static variable
        GamePanel.screenHeight = displayMetrics.heightPixels;
        GamePanel.screenWidth = displayMetrics.widthPixels;

        // Creates the bricks seen on the screen.
        createBricks();

        // Creates the paddle at the bottom of the screen.
        player = new RectPlayer(new Rect(0, 0, this.playerWidth, this.playerHeight), Color.rgb(255,0,0));

        // Creates the ball.
        mainBall = new Ball(50, Color.rgb(255,0,0), this);

        // Sets the paddles player point.
        playerPoint = player.setPoint(this.screenHeight, this.screenWidth);

        // setFocusable mainly used for enable/disable view's focus event on both touch mode and keypad mode( using up/down/next key).
        setFocusable(true);

        // The the game sound start up
        if(startSound.isPlaying())
            startSound.stop();
        else
            startSound.start();

        // Set the game to sleep for three seconds in order to let the game startup sound to play
        SystemClock.sleep(3000);

        // Create the pause button
        pauseButton = new Rect(screenWidth-100,0,screenWidth,60);
    }

    // This is what controls what happens when the user touches the screen.
    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        // Get the players touch coords.
        float x = event.getX();
        float y = event.getY();

        // Check to see what the user is trying to do
        switch (event.getAction())
        {
            // Similar to OnClick
            case MotionEvent.ACTION_DOWN:
                // This calls the movement for the paddle
                player.touchWrapper(event, playerPoint, 0);

                // This works the pause menu
                if(x > pauseButton.left && y < pauseButton.bottom) {
                    if(pause == true) {
                        pause = false;
                        mainBall.GetSpeed();
                        mainBall.GetSpeed();
                    }
                    else if(pause == false && x > pauseButton.left && y < pauseButton.bottom) {
                        pause = true;
                        mainBall.SetSpeed();
                    }
                }
                break;
            // States that the user is attempting to slide their finger across the screen
            // We should only move the paddle in this case
            case MotionEvent.ACTION_MOVE:
                //This calls the movement for the paddle
                player.touchWrapper(event, playerPoint, 1);
                break;

            // On any of these cases, the user is releasing/not touching the screen.
            case MotionEvent.ACTION_UP:
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_POINTER_UP:
                player.setTouch(false);
                break;
        }
        return true;
    }
    // Update the player, and ball
    public void update(){
        player.update(playerPoint);

        // This method updates the ball if it is touching the paddle or the walls
        mainBall.update(player, this, playerScore, paddleSound, wallSound, deathSound);

        // This method upated the ball if it is touching a brick
        mainBall.update(bricks,numBricks, playerScore, hitSound);
    }

    // This draws all of the objects onto the screen.
    @Override
    public void onDraw(Canvas canvas )
    {
        // First, call the update method before we draw anything
        update();

        // Call the superclass.
        super.onDraw(canvas);

        // Set the background as black
        canvas.drawColor(BLACK);

        // Call the player, ball, and brick draw methods
        player.draw(canvas);
        mainBall.draw(canvas);
        for (int i = 0; i < numBricks; i ++) {
            bricks[i].draw(canvas);
        }

        // Draw the scoreboard
        Paint paint = new Paint();
        paint.setTextSize(44);
        paint.setColor(Color.argb(255,0,255,0));
        canvas.drawText("Score: " + playerScore.getScore() + "   Lives: " + playerScore.getLives(), 10, 50, paint);

        // Draw the pause button
        paint = new Paint();
        paint.setColor(RED);
        canvas.drawRect(pauseButton, paint);

        // Draw the || on the pause button.
        paint = new Paint();
        paint.setColor(WHITE);
        paint.setTextSize(50);

        canvas.drawText("| |", screenWidth-65 , 45, paint);

        // Not sure what exactly this does, it was also on the youtube video we watched
        handler.postDelayed(runnable, UPDATE_MILLIS);
    }

    // Create and initialize the bricks
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

    // This finishes the game and goes to the GameOver class
    public void finishGame()
    {
        // We found this on stack overflow and youtube.
        getHandler().removeCallbacksAndMessages(null);
        Intent intent = new Intent(getContext(), GameOver.class);
        intent.putExtra("points", playerScore.getScore());

        // If the score is over 170, that's a win
        if(playerScore.getScore() >= 170){
            if(winSound.isPlaying())
                winSound.seekTo(0);
            else
                winSound.start();
        }
        // If it is under 170, its a loss
        else{
            if(gameOverSound.isPlaying())
                gameOverSound.seekTo(0);
            else
                gameOverSound.start();
        }

        // Finish MainActivity
        game.finish();

        // Start GameOver
        getContext().startActivity(intent);

        // Finish GamePanel
        ((Activity) getContext()).finish();
    }
}
