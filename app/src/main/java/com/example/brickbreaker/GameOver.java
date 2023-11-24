package com.example.brickbreaker;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.view.View.OnClickListener;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class GameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Show the game over screen
        setContentView(R.layout.game_over); //Will eventually display a menu...

        TextView points = findViewById(R.id.points_holder);
        points.setText("Score: " + getIntent().getExtras().get("points"));

        final Button button = findViewById(R.id.button_main_menu);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                restart_game();
            }

        });
        final Button button1 = findViewById(R.id.button_exit);
        button1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(1);
            }

        });
    }
    public void restart_game()
    {
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

}
