package com.example.ehsan.moodmeter;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class DoneActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);

        // Setting up the action bar
        getSupportActionBar().hide();
    }

}
