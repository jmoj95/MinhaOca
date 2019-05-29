package com.example.minhaoca.activity;

import android.support.v7.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    protected void startActivityInitProcess() {
        onCreateViewInstances();
        onBindViewMode();
        onViewInit();
        onActivityInitFinished();
    }

    protected void onCreateViewInstances() {

    }

    protected void onBindViewMode() {

    }

    protected void onViewInit() {

    }

    protected void onActivityInitFinished() {

    }

}
