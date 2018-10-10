package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PictureActivity extends AppCompatActivity {

    private DBHelper emotionsDB;

    private String action;

    private String greenImages[] = {"http://vaknegar.ir/g1.png",
                                    "http://vaknegar.ir/g2.png",
                                    "http://vaknegar.ir/g3.png",
                                    "http://vaknegar.ir/g4.png",
                                    "http://vaknegar.ir/g5.png",
                                    "http://vaknegar.ir/g6.png"};

    private String yellowImages[] = {"http://vaknegar.ir/y1.png",
                                     "http://vaknegar.ir/y2.png",
                                     "http://vaknegar.ir/y3.png",
                                     "http://vaknegar.ir/y4.png",
                                     "http://vaknegar.ir/y5.png",
                                     "http://vaknegar.ir/y6.png"};

    private int currImage = 0;

    private Display display;

    private ProgressBar progressbar;

    ImageView imageView;
    ImageLoader imgLoader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        // Setting up the action bar
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

        // Bind wifgets to corresponding objects
        imageView = (ImageView) findViewById(R.id.imageView);
        progressbar = (ProgressBar) findViewById(R.id.progressBar);
        progressbar.setVisibility(View.INVISIBLE);

        final Button nextPicButton = (Button) findViewById(R.id.nextPicButton);
        final Button prevPicButton = (Button) findViewById(R.id.prevPicButton);
        final Button doneButton = (Button) findViewById(R.id.doneButton);
        final Button backButton = (Button) findViewById(R.id.backButton);


        // image view initialization
        setCurrentImage(currImage);
        prevPicButton.setEnabled(false);
        nextPicButton.setEnabled(true);

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

                Intent doneIntent = new Intent(PictureActivity.this, DoneActivity.class);
                startActivity(doneIntent);
                onBackPressed();
            }
        });

        nextPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currImage++;

                if(currImage >= 5)
                    nextPicButton.setEnabled(false);

                prevPicButton.setEnabled(true);

                setCurrentImage(currImage);
            }
        });

        prevPicButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currImage--;

                if(currImage <= 0)
                    prevPicButton.setEnabled(false);

                nextPicButton.setEnabled(true);

                setCurrentImage(currImage);
            }
        });
    }

    private void setCurrentImage(int imageId) {

        // Get display size
        display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);

        imgLoader = new ImageLoader(this);

        switch (action)
        {
            case "ACTION_GO_GREEN":
                progressbar.setVisibility(View.INVISIBLE);
                imgLoader.DisplayImage(greenImages[imageId], imageView, progressbar);
                break;
            case "ACTION_GO_YELLOW":
                progressbar.setVisibility(View.INVISIBLE);
                imgLoader.DisplayImage(yellowImages[imageId], imageView, progressbar);
        }
    }
}
