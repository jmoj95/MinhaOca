package com.example.minhaoca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.minhaoca.service.FirebaseAuthService;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent;
        FirebaseAuthService firebaseAuthService = new FirebaseAuthService();

        if (firebaseAuthService.getCurrentUser() == null) {
            intent = new Intent(getApplicationContext(), SplashActivity.class);
        } else {
            intent = new Intent(getApplicationContext(), ProfileActivity.class);
        }

        finish();
        startActivity(intent);
    }
}
