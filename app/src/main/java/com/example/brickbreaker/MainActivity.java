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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId() == R.id.settings){
            Toast.makeText(this, "You have clicked on settings...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId() == R.id.aboutus){
            Toast.makeText(this, "You have clicked on about us...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId() == R.id.share){
            Toast.makeText(this, "You have clicked on share...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else if(item.getItemId() == R.id.logout){
            Toast.makeText(this, "You have clicked on logout...", Toast.LENGTH_SHORT).show();
            return true;
        }
        else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // This sets the game as full screen
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        // This starts the game.
        //setContentView(new GamePanel(this));
        //setContentView(R.layout.activity_main); //Will eventually display a menu...
        setContentView(new GamePanel(this));
    }
}