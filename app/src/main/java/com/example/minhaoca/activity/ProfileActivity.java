package com.example.minhaoca.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.minhaoca.R;
import com.example.minhaoca.model.User;
import com.example.minhaoca.service.FirebaseAuthService;
import com.example.minhaoca.service.FirebaseDatabaseService;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

public class ProfileActivity extends BaseActivity {

    private FirebaseAuthService firebaseAuthService;
    private FirebaseDatabaseService firebaseDatabaseService;
    private User user;
    private String title;

    private TextView activityTitle;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        startActivityInitProcess();
    }

    @Override
    protected void onCreateViewInstances() {
        firebaseAuthService = new FirebaseAuthService();
        firebaseDatabaseService = new FirebaseDatabaseService();
        firebaseDatabaseService.getCurrentUser().addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                title = user.getUsername();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        activityTitle = findViewById(R.id.activity_title);
        activityTitle.setText(title);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    protected void onBindViewMode() {
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_user, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                callMainActivity();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void logout() {
        firebaseAuthService.logout();
    }

    private void callMainActivity() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        finish();
        startActivity(intent);
    }
}
