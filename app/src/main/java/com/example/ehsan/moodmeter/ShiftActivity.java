package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ShiftActivity extends AppCompatActivity {

    private DBHelper emotionsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shift);

        // Setting up the action bar
        getSupportActionBar().hide();

        // Construct database
        emotionsDB = new DBHelper(this);

        // Binding widgets to objects
        final LinearLayout feelLayout = (LinearLayout) findViewById(R.id.feelLayout);
        final LinearLayout redLayout = (LinearLayout) findViewById(R.id.redLayout);
        final LinearLayout blueLayout = (LinearLayout) findViewById(R.id.blueLayout);
        final LinearLayout greenLayout = (LinearLayout) findViewById(R.id.greenLayout);
        final LinearLayout yellowLayout = (LinearLayout) findViewById(R.id.yelloLayout);

        final TextView feelTextView = (TextView) findViewById(R.id.feelTextView);
        final TextView redTextView = (TextView) findViewById(R.id.redTextView);
        final TextView blueTextView = (TextView) findViewById(R.id.blueTextView);
        final TextView greenTextView = (TextView) findViewById(R.id.greenTextView);
        final TextView yellowTextView = (TextView) findViewById(R.id.yellowTextView);

        // Retrieve intent data
        final String selectedColor = getIntent().getExtras().getString("SELECTED_COLOR");
        final String selectedEmotion = getIntent().getExtras().getString("SELECTED_EMOTION");
        final String date = getIntent().getExtras().getString("DATE");
        final String reason = getIntent().getExtras().getString("REASON");
        final String group = getIntent().getExtras().getString("GROUP");


        // Setting up text and colors
        feelLayout.setBackgroundColor(Color.parseColor(selectedColor));
        feelTextView.setText(selectedEmotion);

        // Get month number
        Date tempDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        final String month = sdf.format(tempDate);

//        // On android api 18 and earlier following code does not provide month number
//        // Get month number
//        final String month = DateUtils.formatDateTime(getBaseContext(),
//                System.currentTimeMillis(), DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_NUMERIC_DATE);

        switch (group)
        {
            case "red":
                redTextView.setText(R.string.stay_here);
                yellowTextView.setText(R.string.shift_to);
                greenTextView.setText(R.string.shift_to);
                blueTextView.setText("");

                redLayout.setBackgroundColor(Color.parseColor(selectedColor));

                redTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                                selectedEmotion, date, reason, "ACTION_STAY_RED", group, selectedColor);

                        Intent doneActivityIntent = new Intent(ShiftActivity.this, DoneActivity.class);
                        startActivity(doneActivityIntent);

                        onBackPressed();
                    }
                });

                break;

            case "blue":
                redTextView.setText("");
                yellowTextView.setText(R.string.shift_to);
                greenTextView.setText(R.string.shift_to);
                blueTextView.setText(R.string.stay_here);

                blueLayout.setBackgroundColor(Color.parseColor(selectedColor));

                blueTextView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                                selectedEmotion, date, reason, "ACTION_STAY_BLUE", group, selectedColor);

                        Intent doneActivityIntent = new Intent(ShiftActivity.this, DoneActivity.class);
                        startActivity(doneActivityIntent);

                        onBackPressed();
                    }
                });

                break;

            case "green":
                redTextView.setText("");
                yellowTextView.setText(R.string.shift_to);
                greenTextView.setText(R.string.stay_here);
                blueTextView.setText("");

                greenLayout.setBackgroundColor(Color.parseColor(selectedColor));

                break;

            case "yellow":
                redTextView.setText("");
                yellowTextView.setText(R.string.stay_here);
                greenTextView.setText(R.string.shift_to);
                blueTextView.setText("");

                yellowLayout.setBackgroundColor(Color.parseColor(selectedColor));
        }

        // Setting up yellow text view listener
        yellowTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (group) {
                    case "yellow":
                        boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                                selectedEmotion, date, reason, "ACTION_STAY_YELLOW", group, selectedColor);

                        Intent doneActivityIntent = new Intent(ShiftActivity.this, DoneActivity.class);
                        startActivity(doneActivityIntent);

                        onBackPressed();
                        break;
                    default:
                        Intent actionsActivityIntent = new Intent(ShiftActivity.this, ActionsActivity.class);
                        actionsActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                        actionsActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                        actionsActivityIntent.putExtra("DATE", date);
                        actionsActivityIntent.putExtra("REASON", reason);
                        actionsActivityIntent.putExtra("GROUP", group);
                        actionsActivityIntent.putExtra("ACTION", "ACTION_GO_YELLOW");
                        startActivity(actionsActivityIntent);

                        onBackPressed();
                }
            }
        });

        // Setting up green text view listener
        greenTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (group)
                {
                    case "green":
                        boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                                selectedEmotion, date, reason, "ACTION_STAY_GREEN", group, selectedColor);

                        Intent doneActivityIntent = new Intent(ShiftActivity.this, DoneActivity.class);
                        startActivity(doneActivityIntent);

                        onBackPressed();
                        break;
                    default:
                        Intent actionsActivityIntent = new Intent(ShiftActivity.this, ActionsActivity.class);
                        actionsActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                        actionsActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                        actionsActivityIntent.putExtra("DATE", date);
                        actionsActivityIntent.putExtra("REASON", reason);
                        actionsActivityIntent.putExtra("GROUP", group);
                        actionsActivityIntent.putExtra("ACTION", "ACTION_GO_GREEN");
                        startActivity(actionsActivityIntent);

                        onBackPressed();
                }
            }
        });

    }
}
