package com.example.todoapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 3000; // Splash screen duration in milliseconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Delayed execution of the next activity
        new Handler().postDelayed(() -> {
            // Start the main activity
            Intent intent = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(intent);

            // Close the splash activity
            finish();
        }, SPLASH_DURATION);
    }
}
