package com.example.ehsan.moodmeter;

import android.database.Cursor;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.format.DateUtils;
import android.util.Log;
import android.widget.SeekBar;
import android.widget.TextView;
import android.graphics.*;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.androidplot.Plot;
import com.androidplot.xy.XYPlot;
import com.androidplot.pie.PieChart;
import com.androidplot.pie.PieRenderer;
import com.androidplot.pie.Segment;
import com.androidplot.pie.SegmentFormatter;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class ReportActivity extends AppCompatActivity {

    DBHelper emotionsDB;
    private PieChart pie;
    private Segment s1, s2, s3, s4;
    private int resCount, redCount, blueCount, greenCount, yellowCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.xy_plot);

        // Configuring Action bar
        getSupportActionBar().hide();

        // Construct database
        emotionsDB = new DBHelper(this);

        // Get data from DB
        Date tempDate = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("MM");
        String month = sdf.format(tempDate);

        // On android api 18 and earlier following code does not provide month number
//        final String month = DateUtils.formatDateTime(getBaseContext(),
//                System.currentTimeMillis(), DateUtils.FORMAT_NO_MONTH_DAY | DateUtils.FORMAT_NUMERIC_DATE);

        Cursor res = emotionsDB.getDataByMonth(Integer.parseInt(month));

        if ((res.moveToFirst()) || res.getCount() == 1) {
            res.moveToFirst();

            resCount = res.getCount();

            redCount = blueCount = yellowCount = greenCount = 0;

            do
            {
                Log.d("DATABASE_TAG", res.getString(6));
                switch (res.getString(6))
                {
                    case "blue":
//                        Log.d("DATABASE_TAG", "BLUE!!!");
                        blueCount++;
                        break;
                    case "red":
//                        Log.d("DATABASE_TAG", "RED!!!");
                        redCount++;
                        break;
                    case "yellow":
//                        Log.d("DATABASE_TAG", "YELLOW!!!");
                        yellowCount++;
                        break;
                    case "green":
//                        Log.d("DATABASE_TAG", "GREEN!!!");
                        greenCount++;
                        break;
                }
            }while (res.moveToNext());
        }

        // initialize our PieChart reference:
        pie = (PieChart) findViewById(R.id.piChart);

        // detect segment clicks:
        pie.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                PointF click = new PointF(motionEvent.getX(), motionEvent.getY());
                if(pie.getPieWidget().containsPoint(click)) {
                    Segment segment = pie.getRenderer(PieRenderer.class).getContainingSegment(click);
                    if(segment != null) {
                        Toast.makeText(getBaseContext(), segment.getTitle(), Toast.LENGTH_SHORT).show();
                    }
                }
                return false;
            }
        });

        s1 = new Segment(Integer.toString((int) Math.round((double) greenCount / resCount * 100)) + " %", greenCount * 1.0 / resCount);
        s2 = new Segment(Integer.toString((int) Math.round((double) yellowCount / resCount * 100)) + " %", yellowCount * 1.0 / resCount);
        s3 = new Segment(Integer.toString((int) Math.round((double) redCount / resCount * 100)) + " %", redCount * 1.0 / resCount);
        s4 = new Segment(Integer.toString((int) Math.round((double) blueCount / resCount * 100)) + " %", blueCount * 1.0 / resCount);

        EmbossMaskFilter emf = new EmbossMaskFilter(
                new float[]{1, 1, 1}, 10, 10, 1);

        SegmentFormatter sf1 = new SegmentFormatter(getResources().getColor(R.color.colorGreen));
        sf1.getFillPaint().setMaskFilter(emf);
        sf1.getLabelPaint().setColor(Color.BLACK);

        SegmentFormatter sf2 = new SegmentFormatter(getResources().getColor(R.color.colorYellow));
        sf2.getFillPaint().setMaskFilter(emf);
        sf2.getLabelPaint().setColor(Color.BLACK);

        SegmentFormatter sf3 = new SegmentFormatter(getResources().getColor(R.color.colorRed));
        sf3.getFillPaint().setMaskFilter(emf);
        sf3.getLabelPaint().setColor(Color.BLACK);

        SegmentFormatter sf4 = new SegmentFormatter(getResources().getColor(R.color.colorBlue));
        sf4.getFillPaint().setMaskFilter(emf);
        sf4.getLabelPaint().setColor(Color.BLACK);

        pie.addSeries(s1, sf1);
        pie.addSeries(s2, sf2);
        pie.addSeries(s3, sf3);
        pie.addSeries(s4, sf4);


        pie.getBackgroundPaint().setColor(Color.TRANSPARENT);

        pie.getBorderPaint().setColor(Color.TRANSPARENT);
        pie.getBorderPaint().clearShadowLayer();
        pie.setBorderStyle(Plot.BorderStyle.NONE, null, null);

        pie.getRenderer(PieRenderer.class).setDonutSize(0/100f, PieRenderer.DonutMode.PERCENT);

    }
}
