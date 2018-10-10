package com.example.ehsan.moodmeter;

/**
 * Created by ehsan on 11/06/16.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.Toast;

public class DBHelper extends SQLiteOpenHelper{

    public static final String DATABASE_NAME = "Mood.db";
    public static final String MOOD_TABLE_NAME = "records";
    public static final String MOOD_COLUMN_ID = "id";
    public static final String MOOD_COLUMN_MONTH = "month";
    public static final String MOOD_COLUMN_EMOTION = "emotion";
    public static final String MOOD_COLUMN_DATE = "date";
    public static final String MOOD_COLUMN_REASON = "reason";
    public static final String MOOD_COLUMN_ACTION = "action";
    public static final String MOOD_COLUMN_COLOR = "color";
    public static final String MOOD_COLUMN_GROUP = "gp";

    private HashMap hp;

    public DBHelper(Context context)
    {
        super(context, DATABASE_NAME , null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                "create table records " +
                        "(id integer primary key, month integer, emotion text, date text, reason text, action text, gp text, color text)"
        );
}

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // TODO Auto-generated method stub
        db.execSQL("DROP TABLE IF EXISTS records");
        onCreate(db);
    }

    public boolean insertEmotion(int month, String emotion, String date, String reason, String action, String gp, String color)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(MOOD_COLUMN_MONTH, month);
        contentValues.put(MOOD_COLUMN_DATE, date);
        contentValues.put(MOOD_COLUMN_EMOTION, emotion);
        contentValues.put(MOOD_COLUMN_REASON, reason);
        contentValues.put(MOOD_COLUMN_ACTION, action);
        contentValues.put(MOOD_COLUMN_COLOR, color);
        contentValues.put(MOOD_COLUMN_GROUP, gp);
        db.insert("records", null, contentValues);
        return true;
    }

    public Cursor getDataById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from records where id =" + id + "", null );
        return res;
    }

    public Cursor getDataByMonth(int month){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from records where month =" + month + "", null );
        return res;
    }

    public int numberOfRows(){
        SQLiteDatabase db = this.getReadableDatabase();
        int numRows = (int) DatabaseUtils.queryNumEntries(db, MOOD_TABLE_NAME);
        return numRows;
    }

    public ArrayList<String> getAllEmotions()
    {
        ArrayList<String> array_list = new ArrayList<String>();

        //hp = new HashMap();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor res =  db.rawQuery( "select * from records", null );
        res.moveToFirst();

        while(res.isAfterLast() == false){
            array_list.add(res.getString(res.getColumnIndex(MOOD_COLUMN_EMOTION)));
            res.moveToNext();
        }
        return array_list;
    }
}
