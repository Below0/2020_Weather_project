package com.kokonut.NCNC.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class CalendarDBHelper extends SQLiteOpenHelper {
    public static CalendarDBHelper CalendarDbHelper = null;
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;

    public static CalendarDBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.

        Log.d("###1111111", "getInstance: ");
        if(CalendarDbHelper == null){
            CalendarDbHelper = new CalendarDBHelper(context);
        }
        return CalendarDbHelper;
    }

    private CalendarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        Log.d("##1#", "getInstance: ");

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_CREATE_TABLE); // 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);
    }

    void insertRecord(String date, int part, int color) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        values.put(CalendarContract.CalendarEntry.COLUMN_DATE, date);
        values.put(CalendarContract.CalendarEntry.COLUMN_PART, part);
        values.put(CalendarContract.CalendarEntry.COLUMN_COLOR, color);

        db.insert(CalendarContract.CalendarEntry.TABLE_NAME, null, values);
    }

    public Cursor readRecordOrderByAge() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID,
                CalendarContract.CalendarEntry.COLUMN_DATE,
                CalendarContract.CalendarEntry.COLUMN_PART,
                CalendarContract.CalendarEntry.COLUMN_COLOR
        };

        String sortOrder = CalendarContract.CalendarEntry.COLUMN_DATE + " DESC";

        Cursor cursor = db.query(
                CalendarContract.CalendarEntry.TABLE_NAME,   // The table to query
                projection,   // The array of columns to return (pass null to get all)
                null,   // where 문에 필요한 column
                null,   // where 문에 필요한 value
                null,   // group by를 적용할 column
                null,   // having 절
                sortOrder   // 정렬 방식
        );

        return cursor;
    }
}