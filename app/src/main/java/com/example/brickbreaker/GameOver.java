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
import android.media.MediaPlayer;

import androidx.appcompat.app.AppCompatActivity;
public class GameOver extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Store points from play to variable for later use.
        int pointsEarned = (int)getIntent().getExtras().get("points");

        // More than 170 points indicates all bricks were broken,
        // thus the game was won
        if (pointsEarned >= 170) {
            // Display win screen xml layout
            setContentView(R.layout.win_screen);
            // Create text box to display how many points were earned.
            TextView pointsWin = findViewById(R.id.points_holderWin);
            // 180 points means no lives were lost.
            if (pointsEarned == 180)
                pointsWin.setText("Score: " + pointsEarned + " Perfect");
            // Still win but not perfect score changes text box
            else
                pointsWin.setText("Score: " + pointsEarned + "\nTry Again!\n180 is Perfect");

            // Create button to return to main menu
            final Button button3 = findViewById(R.id.button_main_menu_win);
            // Set listener to restart game
            button3.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Return to main menu
                    restart_game();
                }
            });

            // Create button to close the app
            final Button button4 = findViewById(R.id.button_exit_win);
            button4.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Integrated function to close all processes
                    // in the stack
                    finishAffinity();
                }
            });
        }

        // Display game over screen if not all bricks were broken.
        else {
            // Set content view to game over xml layout
            setContentView(R.layout.game_over);
            // Create text box that will display number of points earned
            TextView pointsLoss = findViewById(R.id.points_holderLoss);
            // Display points earned
            pointsLoss.setText("Score: " + pointsEarned);
            // Create button to return to main menu
            final Button button = findViewById(R.id.button_main_menu_loss);
            // Set button functionality
            button.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Return to main menu
                    restart_game();
                }
            });

            // Create button to close app
            final Button button1 = findViewById(R.id.button_exit_loss);
            button1.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Integrated function to close all stack processes
                    finishAffinity();
                }
            });
        }
    }

    // Function to return to game's main menu.
    public void restart_game() {
        // Specify intent for the activity to start at.
        Intent intent = new Intent(GameOver.this, MainActivity.class);
        // Move to the intent
        startActivity(intent);
        // Close current process
        finish();
    }

}
