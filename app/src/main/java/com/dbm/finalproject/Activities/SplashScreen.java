package com.dbm.finalproject.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashScreen extends AppCompatActivity {

    private static final int SPLASH_DISPLAY_LENGTH = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(() -> {
            FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
            if (currentUser != null) {
                Intent homeIntent = new Intent(SplashScreen.this, HomeScreen.class);
                startActivity(homeIntent);
                finish();
            } else {
                Intent loginIntent = new Intent(SplashScreen.this, OnBoarding_Activity.class);
                startActivity(loginIntent);
                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}