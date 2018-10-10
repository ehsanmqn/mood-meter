package com.example.ehsan.moodmeter;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

public class MainActivity extends AppCompatActivity {

    private static final NumberFormat currencyFormat = NumberFormat.getNumberInstance();

    private int selectedRow, selectedColumn;
    private RadioGroup col1, col2, col3, col4, col5, col6, col7, col8, col9, col10;
    private TextView table00, table01, table02, table10, table11, table12, table20, table21, table22;
    private RadioButton rb55;

    private String[] emotionsArray, colorArray;
    private String[][] emotionsMatrix, colorMatrix;

    DBHelper emotionsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Construct database
        emotionsDB = new DBHelper(this);
        int dbSize = emotionsDB.numberOfRows();

        if(dbSize == 0)
        {
            Intent helpIntent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(helpIntent);
        }

        // Configuring Action bar
//        getSupportActionBar().hide();
//        getSupportActionBar().setElevation(0);
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#212121")));

//        //Setting Status bar color
//        int version = Integer.valueOf(android.os.Build.VERSION.SDK);
//        if(version >= 21)
//        {
//            Window window = this.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(this.getResources().getColor(R.color.colorBlack));
//        }

        // Bind radio groups widgets to the corresponding objects
        col1 = (RadioGroup) findViewById(R.id.col1RadioGroup);
        col2 = (RadioGroup) findViewById(R.id.col2RadioGroup);
        col3 = (RadioGroup) findViewById(R.id.col3RadioGroup);
        col4 = (RadioGroup) findViewById(R.id.col4RadioGroup);
        col5 = (RadioGroup) findViewById(R.id.col5RadioGroup);
        col6 = (RadioGroup) findViewById(R.id.col6RadioGroup);
        col7 = (RadioGroup) findViewById(R.id.col7RadioGroup);
        col8 = (RadioGroup) findViewById(R.id.col8RadioGroup);
        col9 = (RadioGroup) findViewById(R.id.col9RadioGroup);
        col10 = (RadioGroup) findViewById(R.id.col10RadioGroup);

        // Bind text view wifgets to the corresponding objects
        table00 = (TextView) findViewById(R.id.table00);
        table01 = (TextView) findViewById(R.id.table01);
        table02 = (TextView) findViewById(R.id.table02);
        table10 = (TextView) findViewById(R.id.table10);
        table11 = (TextView) findViewById(R.id.table11);
        table12 = (TextView) findViewById(R.id.table12);
        table20 = (TextView) findViewById(R.id.table20);
        table21 = (TextView) findViewById(R.id.table21);
        table22 = (TextView) findViewById(R.id.table22);


        // Retrieve emotions array then using it to construct emotions matrix
        emotionsArray = getResources().getStringArray(R.array.emotions_array);

        emotionsMatrix = new String[10][10];
        for(int col = 0, cnt = 0; col < 10; col++)
            for(int row = 0; row < 10; row++)
                emotionsMatrix[row][col] = emotionsArray[cnt++];

        // Retrieve colors array then using it to construct color matrix
        colorArray = getResources().getStringArray(R.array.colors);

        colorMatrix = new String[10][10];
        for(int col = 0, cnt = 0; col < 10; col++)
            for(int row = 0; row < 10; row++)
                colorMatrix[row][col] = colorArray[cnt++];

        // Bind radio button 5 5 to the corresponding object
        rb55 = (RadioButton) findViewById(R.id.radio55);
        rb55.setChecked(true);
        updateEmotionsTable(5, 5);

        TextView middleTable = (TextView) findViewById(R.id.table11);
        middleTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent emotionActivityIntent = new Intent(MainActivity.this, EmotionActivity.class);

                emotionActivityIntent.putExtra("SELECTED_ROW", selectedRow);
                emotionActivityIntent.putExtra("SELECTED_COL", selectedColumn);
                emotionActivityIntent.putExtra("SELECTED_COLOR", colorMatrix[selectedRow][selectedColumn]);
                emotionActivityIntent.putExtra("SELECTED_EMOTION", emotionsMatrix[selectedRow][selectedColumn]);
                startActivity(emotionActivityIntent);
            }
        });

        // Set listener to radio groups
        col1.setOnCheckedChangeListener(col1Listener);
        col2.setOnCheckedChangeListener(col2Listener);
        col3.setOnCheckedChangeListener(col3Listener);
        col4.setOnCheckedChangeListener(col4Listener);
        col5.setOnCheckedChangeListener(col5Listener);
        col6.setOnCheckedChangeListener(col6Listener);
        col7.setOnCheckedChangeListener(col7Listener);
        col8.setOnCheckedChangeListener(col8Listener);
        col9.setOnCheckedChangeListener(col9Listener);
        col10.setOnCheckedChangeListener(col10Listener);
    }

    private RadioGroup.OnCheckedChangeListener col1Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 0;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col2Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 1;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col3Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 2;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col4Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 3;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col5Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 4;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col6Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 5;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col7Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 6;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col8Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 7;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col9Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 8;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            col10.setOnCheckedChangeListener(null);
            col10.clearCheck();
            col10.setOnCheckedChangeListener(col10Listener);

            updateEmotionsTable(row, col);
        }
    };

    private RadioGroup.OnCheckedChangeListener col10Listener = new RadioGroup.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(RadioGroup radioGroup, int i) {

            View mRadioButton = (RadioButton) findViewById(radioGroup.getCheckedRadioButtonId());
            int row = radioGroup.indexOfChild(mRadioButton);
            int col = 9;

//            Toast.makeText(getApplicationContext(), currencyFormat.format(row) + " " + currencyFormat.format(col), Toast.LENGTH_SHORT).show();

            col2.setOnCheckedChangeListener(null);
            col2.clearCheck();
            col2.setOnCheckedChangeListener(col2Listener);

            col3.setOnCheckedChangeListener(null);
            col3.clearCheck();
            col3.setOnCheckedChangeListener(col3Listener);

            col4.setOnCheckedChangeListener(null);
            col4.clearCheck();
            col4.setOnCheckedChangeListener(col4Listener);

            col5.setOnCheckedChangeListener(null);
            col5.clearCheck();
            col5.setOnCheckedChangeListener(col5Listener);

            col6.setOnCheckedChangeListener(null);
            col6.clearCheck();
            col6.setOnCheckedChangeListener(col6Listener);

            col7.setOnCheckedChangeListener(null);
            col7.clearCheck();
            col7.setOnCheckedChangeListener(col7Listener);

            col8.setOnCheckedChangeListener(null);
            col8.clearCheck();
            col8.setOnCheckedChangeListener(col8Listener);

            col9.setOnCheckedChangeListener(null);
            col9.clearCheck();
            col9.setOnCheckedChangeListener(col9Listener);

            col1.setOnCheckedChangeListener(null);
            col1.clearCheck();
            col1.setOnCheckedChangeListener(col1Listener);

            updateEmotionsTable(row, col);
        }
    };

    private void updateEmotionsTable(int row, int col){

        selectedRow = row;
        selectedColumn = col;

        // Get screen size for change font size
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        double x = Math.pow(dm.widthPixels/dm.xdpi,2);
        double y = Math.pow(dm.heightPixels/dm.ydpi,2);
        double screenInches = Math.sqrt(x+y);
//        Log.d("debug","Screen inches : " + screenInches);
        if ((screenInches <= 4.5 && screenInches > 4))
        {
            table00.setTextSize(8);
            table01.setTextSize(8);
            table02.setTextSize(8);
            table10.setTextSize(8);
            table11.setTextSize(14);
            table12.setTextSize(8);
            table20.setTextSize(8);
            table21.setTextSize(8);
            table22.setTextSize(8);
        } else if(screenInches <= 4)
        {
            table00.setTextSize(6);
            table01.setTextSize(6);
            table02.setTextSize(6);
            table10.setTextSize(6);
            table11.setTextSize(9);
            table12.setTextSize(6);
            table20.setTextSize(6);
            table21.setTextSize(6);
            table22.setTextSize(6);
        }

        table00.setText("-");
        table01.setText("-");
        table02.setText("-");
        table10.setText("-");
        table11.setText(emotionsMatrix[row][col]);
        table12.setText("-");
        table20.setText("-");
        table21.setText("-");
        table22.setText("-");

        table11.setTextColor(Color.parseColor(colorMatrix[row][col]));

        int upRow = row - 1;
        int bottomRow = row + 1;
        int leftCol = col - 1;
        int rightCol = col + 1;

        if(upRow >= 0)      //Then we can allocate string to table 01
        {
            table01.setText(emotionsMatrix[upRow][col]);
//            table01.setTextColor(Color.parseColor(colorMatrix[upRow][col]));

            if(leftCol >= 0)    //Then we can allocate string to table 10 and table 00
            {
//                Toast.makeText(getApplicationContext(), currencyFormat.format(upRow) + " " + currencyFormat.format(leftCol), Toast.LENGTH_SHORT).show();

                table10.setText(emotionsMatrix[row][leftCol]);
//                table10.setTextColor(Color.parseColor(colorMatrix[row][leftCol]));

                table00.setText(emotionsMatrix[upRow][leftCol]);
//                table00.setTextColor(Color.parseColor(colorMatrix[upRow][leftCol]));
            }

            if(rightCol <= 9)   //Then we can allocate string to table 02 and table 12
            {
                table02.setText(emotionsMatrix[upRow][rightCol]);
//                table02.setTextColor(Color.parseColor(colorMatrix[upRow][rightCol]));

                table12.setText(emotionsMatrix[row][rightCol]);
//                table12.setTextColor(Color.parseColor(colorMatrix[row][rightCol]));
            }
        }

        if(bottomRow <= 9)  //Then we can allocate string to table 21
        {
            table21.setText(emotionsMatrix[bottomRow][col]);
//            table21.setTextColor(Color.parseColor(colorMatrix[bottomRow][col]));

            if(rightCol <= 9)   //Then we can allocate string to table 12 and table 22
            {
                table12.setText(emotionsMatrix[row][rightCol]);
//                table12.setTextColor(Color.parseColor(colorMatrix[row][rightCol]));

                table22.setText(emotionsMatrix[bottomRow][rightCol]);
//                table22.setTextColor(Color.parseColor(colorMatrix[bottomRow][rightCol]));
            }

            if(leftCol >= 0)    //Then we can allocate string to table 10 and table 20
            {
                table10.setText(emotionsMatrix[row][leftCol]);
//                table10.setTextColor(Color.parseColor(colorMatrix[row][leftCol]));

                table20.setText(emotionsMatrix[bottomRow][leftCol]);
//                table20.setTextColor(Color.parseColor(colorMatrix[bottomRow][leftCol]));
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.report, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                Intent reportIntent = new Intent(MainActivity.this, ReportActivity.class);
                startActivity(reportIntent);
                return true;

            case R.id.action_about:
                Intent aboutIntent = new Intent(MainActivity.this, AboutActivity.class);
                startActivity(aboutIntent);
                return true;

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                return super.onOptionsItemSelected(item);

        }
    }
}


