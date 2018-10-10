package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EmotionActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getNumberInstance();
    private DBHelper emotionsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);

        // Setting up Action bar
        getSupportActionBar().hide();

        // Get database
        emotionsDB = new DBHelper(this);

        // Binding widgets to objects
        final Button doneButton = (Button) findViewById(R.id.doneButton);
        final Button nextButton = (Button) findViewById(R.id.nextButton);
        final EditText reasonEditText = (EditText) findViewById(R.id.reasonEditText);
        final TextView emotionTextView = (TextView) findViewById(R.id.emotionTextView);
        final TextView dateTextView = (TextView) findViewById(R.id.dateTextView);
        final RelativeLayout backLayout = (RelativeLayout) findViewById(R.id.backLayout);

        // Retrieve intent data
        final String selectedColor = getIntent().getExtras().getString("SELECTED_COLOR");
        final String selectedEmotion = getIntent().getExtras().getString("SELECTED_EMOTION");
        final int selectedRow = getIntent().getExtras().getInt("SELECTED_ROW");
        final int selectedCol = getIntent().getExtras().getInt("SELECTED_COL");


        // Setting up data text view
        final String date = DateUtils.formatDateTime(getBaseContext(),
                System.currentTimeMillis(),
                DateUtils.FORMAT_SHOW_TIME | DateUtils.FORMAT_SHOW_DATE | DateUtils.FORMAT_NUMERIC_DATE);

        dateTextView.setText(date);

        // Setting up the emotion textview and its background
        emotionTextView.setText(selectedEmotion);
        backLayout.setBackgroundColor(Color.parseColor(selectedColor));

        // On Done button pressed
        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reason = reasonEditText.getText().toString();
                String group;

                if(selectedRow < 5)
                {
                    if(selectedCol < 5)
                        group = "red";
                    else
                        group = "yellow";
                }
                else
                {
                    if(selectedCol < 5)
                        group = "blue";
                    else
                        group = "green";
                }

                Date tempDate = new Date();
                SimpleDateFormat sdf = new SimpleDateFormat("MM");
                String month = sdf.format(tempDate);

                // On android api 18 and earlier following code does not provide month number
//                final String month = DateUtils.formatDateTime(getBaseContext(),
//                        System.currentTimeMillis(), DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_NUMERIC_DATE);

//                Toast.makeText(getBaseContext(), month, Toast.LENGTH_LONG).show();
                boolean result = emotionsDB.insertEmotion(Integer.parseInt(month),
                        selectedEmotion, date, reason, "ACTION_DONE", group, selectedColor);

                Intent doneActivityIntent = new Intent(EmotionActivity.this, DoneActivity.class);
                startActivity(doneActivityIntent);

                onBackPressed();
            }
        });

        // On Next button pressed
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String reason = reasonEditText.getText().toString();
                String group;

                if(selectedRow < 5)
                {
                    if(selectedCol < 5)
                        group = "red";
                    else
                        group = "yellow";
                }
                else
                {
                    if(selectedCol < 5)
                        group = "blue";
                    else
                        group = "green";
                }

                Intent shiftActivityIntent = new Intent(EmotionActivity.this, ShiftActivity.class);
                shiftActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                shiftActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                shiftActivityIntent.putExtra("DATE", date);
                shiftActivityIntent.putExtra("REASON", reason);
                shiftActivityIntent.putExtra("GROUP", group);
                startActivity(shiftActivityIntent);

                onBackPressed();
            }
        });
    }
}
