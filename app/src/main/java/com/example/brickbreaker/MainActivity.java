package com.example.brickbreaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.Random;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This sets the game as full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // Display main menu by setting content view.
        setContentView(R.layout.activity_main);

        // Create button to start the game
        final Button button = findViewById(R.id.button);

        // Make butotn call goToGame() function,
        // which changes content view.
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               goToGame();
            }
        });

        // Create button to close the app entirely.
        final Button button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }

    // Set the content view by creating our main
    // game panel and switching it to that.
    public void goToGame() {
        setContentView(new GamePanel(this, this));
    }
}