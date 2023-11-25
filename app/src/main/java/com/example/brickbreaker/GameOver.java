package com.example.brickbreaker;

import static android.graphics.Color.BLACK;

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
        int pointsEarned = (int)getIntent().getExtras().get("points");

        if (pointsEarned >= 170) {
            setContentView(R.layout.win_screen);
            TextView pointsWin = findViewById(R.id.points_holderWin);
            if (pointsEarned == 180)
                pointsWin.setText("Score: " + pointsEarned + " Perfect");
            else
                pointsWin.setText("Score: " + pointsEarned + "\nTry Again!\n180 is Perfect");
        }
        else {
            setContentView(R.layout.game_over); //Will eventually display a menu...
            TextView pointsLoss = findViewById(R.id.points_holderLoss);
            pointsLoss.setText("Score: " + pointsEarned);
        }


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
