package com.example.brickbreaker;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements Runnable {
    private Thread gameThread;
    private SurfaceHolder surfaceHolder;
    private boolean isPlaying = true;

    // Define variables for the ball position and speed
    private float ballX, ballY;
    private float ballSpeedX;
    private final int BALL_RADIUS = 50;

    public GameView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        ballX = 50;
        ballSpeedX = 5;
        //surfaceHolder.addCallback(this); // Add this line to register the callback
    }

    @Override
    public void run() {
        while (isPlaying) {
            update(); // Update the game logic
            draw();   // Render the game
        }

//        long TARGET_FRAME_TIME = 16;
//        long startTime = System.currentTimeMillis();
//        long endTime = System.currentTimeMillis();
//        long frameTime = endTime - startTime;
//
//        // Add a delay to achieve a desired frame rate (e.g., 60 FPS)
//        if (frameTime < TARGET_FRAME_TIME) {
//            try {
//                Thread.sleep(TARGET_FRAME_TIME - frameTime);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }
    }

    public void update() {
        // Update the ball's position
        ballX += ballSpeedX;

        // Check for collision with screen edges and reverse the ball's direction if necessary
        if (ballX <= 0 || ballX >= getWidth()) {
            ballSpeedX = -ballSpeedX;
        }

    }

    public void draw() {
        if (surfaceHolder.getSurface().isValid()) {
            Canvas canvas = surfaceHolder.lockCanvas();
            // Clear the canvas
            canvas.drawColor(Color.BLACK);

            // Draw the ball
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            canvas.drawCircle(500, 500, BALL_RADIUS, paint);

            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void pause() {
        isPlaying = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void resume() {
        isPlaying = true;
        gameThread = new Thread(this);
        gameThread.start();
    }
}
