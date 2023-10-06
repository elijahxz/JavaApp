package com.example.brickbreaker;

import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Color;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.graphics.*;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //super.onCreate(savedInstanceState;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setContentView(new MyView(this));
        gameView = new GameView(this);
        //gameView = findViewById(R.id.gameView);

        Button changeColor = new Button(this);
        changeColor.setText("Color");
        changeColor.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        changeColor.setOnClickListener(new View.OnClickListener() {
            int[] colors = {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW};
            @Override
            public void onClick(View view) {
                final Random random = new Random();
                view.setBackgroundColor(colors[random.nextInt(colors.length - 1) + 1]);
            }
        });

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

