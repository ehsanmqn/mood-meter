package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StrategyActivity extends AppCompatActivity {

    private DBHelper emotionsDB;

    private String action;
    private String[] to_yellow_strategies, to_green_strategies;
    private TextView strategyTextView;
    private int currentStrategy = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_strategy);

        // Construct database
        emotionsDB = new DBHelper(this);

        // Setting up Action bar
        getSupportActionBar().hide();

        // Retrieve intent data
        action = getIntent().getExtras().getString("ACTION");
        final String selectedColor = getIntent().getExtras().getString("SELECTED_COLOR");
        final String selectedEmotion = getIntent().getExtras().getString("SELECTED_EMOTION");
        final String date = getIntent().getExtras().getString("DATE");
        final String reason = getIntent().getExtras().getString("REASON");
        final String group = getIntent().getExtras().getString("GROUP");

        // Bind widgets to corresponding objects
        final RelativeLayout qouteLayout = (RelativeLayout) findViewById(R.id.strategyLayout);
        strategyTextView = (TextView) findViewById(R.id.strategyTextView);
        final Button nextStButton = (Button) findViewById(R.id.nextStBtn);
        final Button prevStButton = (Button) findViewById(R.id.prevStBtn);
        final Button doneButton = (Button) findViewById(R.id.doneButton);
        final Button backButton = (Button) findViewById(R.id.backButton);

        // Get string resources
        to_green_strategies = getResources().getStringArray(R.array.to_green_strategy);
        to_yellow_strategies = getResources().getStringArray(R.array.to_yellow_strategy);

        switch (action)
        {
            case "ACTION_GO_GREEN":
                qouteLayout.setBackgroundResource(R.drawable.green_bg);
                break;
            case "ACTION_GO_YELLOW":
                qouteLayout.setBackgroundResource(R.drawable.yellow_bg);
        }

        // Text view initilization
        setCurrentQoute(currentStrategy);
        prevStButton.setEnabled(false);
        nextStButton.setEnabled(true);

        // Setting up listeners
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Get month number
                Date tempDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                String month = sdf.format(tempDate);

                // On android api 18 and earlier following code does not provide month number
//                final String month = DateUtils.formatDateTime(getBaseContext(),
//                        System.currentTimeMillis(), DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_NUMERIC_DATE);

                boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                        selectedEmotion, date, reason, action, group, selectedColor);

                Intent doneIntent = new Intent(StrategyActivity.this, DoneActivity.class);
                startActivity(doneIntent);
                onBackPressed();
            }
        });

        nextStButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStrategy++;

                if(currentStrategy >= 5)
                    nextStButton.setEnabled(false);

                prevStButton.setEnabled(true);

                setCurrentQoute(currentStrategy);
            }
        });

        prevStButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentStrategy--;

                if(currentStrategy <= 0)
                    prevStButton.setEnabled(false);

                nextStButton.setEnabled(true);

                setCurrentQoute(currentStrategy);
            }
        });
    }

    private void setCurrentQoute(int strategyId) {

        switch (action)
        {
            case "ACTION_GO_GREEN":
                strategyTextView.setText(to_green_strategies[strategyId]);
                break;
            case "ACTION_GO_YELLOW":
                strategyTextView.setText(to_yellow_strategies[strategyId]);
        }
    }
}
