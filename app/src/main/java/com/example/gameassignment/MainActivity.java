package com.example.gameassignment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    TextView tvScore;
    View[] boxes;
    int[] originalColors;
    private boolean doubleBackToExit = false;
    int score = 0;
    int greyIndex = -1;
    boolean tapped = false;

    Handler handler = new Handler();
    Runnable gameRunnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        showStartPopup();
    }

    private void init() {

        tvScore = findViewById(R.id.tvScore);

        boxes = new View[]{
                findViewById(R.id.boxRed),
                findViewById(R.id.boxBlue),
                findViewById(R.id.boxYellow),
                findViewById(R.id.boxGreen)
        };

        originalColors = new int[]{
                Color.RED,
                Color.BLUE,
                Color.YELLOW,
                Color.GREEN
        };

        for (int i = 0; i < boxes.length; i++) {
            int index = i;
            boxes[i].setOnClickListener(v -> handleTap(index));
        }


        OnBackPressedCallback callback = new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                if (doubleBackToExit) {
                    finishAffinity();
                    return;
                }

                doubleBackToExit = true;
                Toast.makeText(MainActivity.this, "Press back again to exit", Toast.LENGTH_SHORT).show();

                new Handler(Looper.getMainLooper()).postDelayed(() ->
                        doubleBackToExit = false, 2000);
            }
        };

        getOnBackPressedDispatcher().addCallback(this, callback);

    }

    private void showStartPopup() {
        new AlertDialog.Builder(this)
                .setTitle("Tap The Grey Box")
                .setMessage("Start the game?")
                .setCancelable(false)
                .setPositiveButton("Start", (d, w) -> startGame())
                .setNegativeButton("Exit", (d, w) -> finish())
                .show();
    }

    private void startGame() {
        score = 0;
        tvScore.setText("Score: 0");
        handler.removeCallbacksAndMessages(null);
        startRound();
    }

    private void startRound() {
        tapped = false;
        resetColors();

        Random random = new Random();
        greyIndex = random.nextInt(4);
        boxes[greyIndex].setBackgroundColor(Color.GRAY);

        gameRunnable = () -> {
            if (!tapped) {
                gameOver();
            } else {
                startRound();
            }
        };

        handler.postDelayed(gameRunnable, 1000);
    }

    private void handleTap(int index) {
        if (index == greyIndex) {
            tapped = true;
            score++;
            tvScore.setText("Score: " + score);
        } else {
            gameOver();
        }
    }

    private void gameOver() {
        handler.removeCallbacksAndMessages(null);
        resetColors();

        new AlertDialog.Builder(this)
                .setTitle("Game Over")
                .setMessage("Your score is: " + score)
                .setCancelable(false)
                .setPositiveButton("Restart", (d, w) -> startGame())
                .setNegativeButton("Exit", (d, w) -> finish())
                .show();
    }

    private void resetColors() {
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].setBackgroundColor(originalColors[i]);
        }
    }
}
