package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QouteActivity extends AppCompatActivity {

    private DBHelper emotionsDB;

    private String action;
    private String[] to_yellow_qoutes, to_green_qoutes;
    private TextView qouteTextView;
    private int currentQoute = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qoute);

        // Setting up Action bar
        getSupportActionBar().hide();

        // Construct database
        emotionsDB = new DBHelper(this);

        // Retrieve intent data
        action = getIntent().getExtras().getString("ACTION");
        final String selectedColor = getIntent().getExtras().getString("SELECTED_COLOR");
        final String selectedEmotion = getIntent().getExtras().getString("SELECTED_EMOTION");
        final String date = getIntent().getExtras().getString("DATE");
        final String reason = getIntent().getExtras().getString("REASON");
        final String group = getIntent().getExtras().getString("GROUP");

        // Bind widgets to corresponding objects
        final RelativeLayout qouteLayout = (RelativeLayout) findViewById(R.id.qouteLayout);
        qouteTextView = (TextView) findViewById(R.id.qouteTextView);
        final Button nextQouteButton = (Button) findViewById(R.id.nextQouteBtn);
        final Button prevQouteButton = (Button) findViewById(R.id.prevQouteBtn);
        final Button doneButton = (Button) findViewById(R.id.doneButton);
        final Button backButton = (Button) findViewById(R.id.backButton);

        // Get string resources
        to_green_qoutes = getResources().getStringArray(R.array.to_green_qout);
        to_yellow_qoutes = getResources().getStringArray(R.array.to_yellow_qout);

        switch (action)
        {
            case "ACTION_GO_GREEN":
                qouteLayout.setBackgroundResource(R.drawable.green_bg);
                break;
            case "ACTION_GO_YELLOW":
                qouteLayout.setBackgroundResource(R.drawable.yellow_bg);
        }

        // Text view initilization
        setCurrentQoute(currentQoute);
        prevQouteButton.setEnabled(false);
        nextQouteButton.setEnabled(true);

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

                Date tempDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                String month = sdf.format(tempDate);

                // On android api 18 and earlier following code does not provide month number
//                // Get month number
//                final String month = DateUtils.formatDateTime(getBaseContext(),
//                        System.currentTimeMillis(), DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_NUMERIC_DATE);

                boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                        selectedEmotion, date, reason, action, group, selectedColor);

                Intent doneIntent = new Intent(QouteActivity.this, DoneActivity.class);
                startActivity(doneIntent);
                onBackPressed();
            }
        });

        nextQouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQoute++;

                if(currentQoute >= 21)
                    nextQouteButton.setEnabled(false);

                prevQouteButton.setEnabled(true);

                setCurrentQoute(currentQoute);
            }
        });

        prevQouteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentQoute--;

                if(currentQoute <= 0)
                    prevQouteButton.setEnabled(false);

                nextQouteButton.setEnabled(true);

                setCurrentQoute(currentQoute);
            }
        });
    }

    private void setCurrentQoute(int qouteId) {

        switch (action)
        {
            case "ACTION_GO_GREEN":
                qouteTextView.setText(to_green_qoutes[qouteId]);
                break;
            case "ACTION_GO_YELLOW":
                qouteTextView.setText(to_yellow_qoutes[qouteId]);
        }
    }
}
