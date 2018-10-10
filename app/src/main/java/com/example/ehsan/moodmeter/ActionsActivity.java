package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

public class ActionsActivity extends AppCompatActivity {

    private String[] to_green_qoutes, to_yellow_qoutes, to_green_strategies, to_yellow_strategies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actions);

        // Setting up the action bar
        getSupportActionBar().hide();

        // Retrieve intent data
        final String selectedColor = getIntent().getExtras().getString("SELECTED_COLOR");
        final String selectedEmotion = getIntent().getExtras().getString("SELECTED_EMOTION");
        final String date = getIntent().getExtras().getString("DATE");
        final String reason = getIntent().getExtras().getString("REASON");
        final String group = getIntent().getExtras().getString("GROUP");
        final String action = getIntent().getExtras().getString("ACTION");

        // Bind widgets to corresponding objects
        RelativeLayout mainLayout = (RelativeLayout) findViewById(R.id.mainLayout);
        RelativeLayout imageLayout = (RelativeLayout) findViewById(R.id.imageLayout);
        RelativeLayout qouteLayout = (RelativeLayout) findViewById(R.id.qouteLayout);
        RelativeLayout strategyLayout = (RelativeLayout) findViewById(R.id.strategyLayout);
        ImageView picImageView = (ImageView) findViewById(R.id.picImageView);
        TextView qouteTextView = (TextView) findViewById(R.id.qouteTextView);
        TextView strategyTextView = (TextView) findViewById(R.id.strategyTextView);

        // Get string resources
        to_green_qoutes = getResources().getStringArray(R.array.to_green_qout);
        to_yellow_qoutes = getResources().getStringArray(R.array.to_yellow_qout);
        to_green_strategies = getResources().getStringArray(R.array.to_green_strategy);
        to_yellow_strategies = getResources().getStringArray(R.array.to_yellow_strategy);

        // Setting up background view
        Random r = new Random();
        int rndIdx;
        switch (action)
        {

            case "ACTION_GO_GREEN":
                mainLayout.setBackgroundResource(R.drawable.green_bg);
                picImageView.setBackgroundResource(R.drawable.g1);

                r = new Random();
                rndIdx = r.nextInt(to_green_qoutes.length);
                qouteTextView.setText(to_green_qoutes[rndIdx]);

                rndIdx = r.nextInt(to_green_strategies.length);
                strategyTextView.setText(to_green_strategies[rndIdx]);
                break;
            case "ACTION_GO_YELLOW":
                mainLayout.setBackgroundResource(R.drawable.yellow_bg);
                picImageView.setBackgroundResource(R.drawable.y1);

                r = new Random();
                rndIdx = r.nextInt(to_yellow_qoutes.length);
                qouteTextView.setText(to_yellow_qoutes[rndIdx]);

                rndIdx = r.nextInt(to_yellow_strategies.length);
                strategyTextView.setText(to_yellow_strategies[rndIdx]);
                break;
        }

        // Setting up listeners
        //Image layout listener
        imageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent PictureActivityIntent = new Intent(ActionsActivity.this, PictureActivity.class);
                PictureActivityIntent.putExtra("ACTION", action);
                PictureActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                PictureActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                PictureActivityIntent.putExtra("DATE", date);
                PictureActivityIntent.putExtra("REASON", reason);
                PictureActivityIntent.putExtra("GROUP", group);
                startActivity(PictureActivityIntent);

            }
        });

        // Qoute layout listener
        qouteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent QouteActivityIntent = new Intent(ActionsActivity.this, QouteActivity.class);
                QouteActivityIntent.putExtra("ACTION", action);
                QouteActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                QouteActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                QouteActivityIntent.putExtra("DATE", date);
                QouteActivityIntent.putExtra("REASON", reason);
                QouteActivityIntent.putExtra("GROUP", group);
                startActivity(QouteActivityIntent);
            }
        });

        // Strategies layout listener
        strategyLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent StrategyActivityIntent = new Intent(ActionsActivity.this, StrategyActivity.class);
                StrategyActivityIntent.putExtra("ACTION", action);
                StrategyActivityIntent.putExtra("SELECTED_COLOR", selectedColor);
                StrategyActivityIntent.putExtra("SELECTED_EMOTION", selectedEmotion);
                StrategyActivityIntent.putExtra("DATE", date);
                StrategyActivityIntent.putExtra("REASON", reason);
                StrategyActivityIntent.putExtra("GROUP", group);
                startActivity(StrategyActivityIntent);
            }
        });
    }
}
