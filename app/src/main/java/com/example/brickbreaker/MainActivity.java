package com.example.brickbreaker;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< Updated upstream
=======

import android.content.Context;
>>>>>>> Stashed changes
import android.graphics.Color;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.graphics.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
<<<<<<< Updated upstream
        //super.onCreate(savedInstanceState;
=======
>>>>>>> Stashed changes
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new MyView(this));
        gameView = new GameView(this);
        //gameView = findViewById(R.id.gameView);

        // This sets the game as full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

<<<<<<< Updated upstream
        changeColor.setOnClickListener(new View.OnClickListener() {
            int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            @Override
            public void onClick(View view) {
                final Random random = new Random();
                view.setBackgroundColor(colors[random.nextInt(colors.length - 1) + 1]);
            }
        });

=======
        // This starts the game.
        setContentView(new GamePanel(this));
>>>>>>> Stashed changes
    }

    public void buttonClick(View view) {
        setContentView(gameView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        gameView.resume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        gameView.pause();
    }
}

