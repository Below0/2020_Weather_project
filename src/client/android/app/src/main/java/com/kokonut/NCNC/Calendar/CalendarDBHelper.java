/*
package com.kokonut.NCNC.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.kokonut.NCNC.Home.HomeContract;

public class CalendarDBHelper extends SQLiteOpenHelper {
    public static CalendarDBHelper CalendarDbHelper = null;
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 2;
    Context context;

    public static CalendarDBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        Log.d("겟인스턴스", "getInstance: ");
        if(CalendarDbHelper == null){
            CalendarDbHelper = new CalendarDBHelper(context);
        }
        return CalendarDbHelper; //이미 있다면 기존의 객체 리턴
    }

    private CalendarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("캘린더 테이블 생성", "onCreate: ");
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_CREATE_TABLE); // 캘린더 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        // 테이블을 없애고 새로 만든다
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_DELETE_TABLE);

        onCreate(sqLiteDatabase);
    }

    void insertRecord(String date, int part, int color) {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("!@!@", "insertRecord: " + date);
        ContentValues values = new ContentValues();
        //primary key(BaseColumns._ID) 는 업데이트 필요 없음
        values.put(CalendarContract.CalendarEntry.COLUMN_DATE, date);
        values.put(CalendarContract.CalendarEntry.COLUMN_PART, part);
        //for test

        db.insert(CalendarContract.CalendarEntry.TABLE_NAME, null, values);
    }

    void deleteRecord(String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CalendarContract.CalendarEntry.TABLE_NAME + " WHERE name=" + "\"" + date + "\"");
        //db.delete(CalendarContract.CalendarEntry.TABLE_NAME, ,);
    }

    void deleteAllrecord(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CalendarContract.CalendarEntry.TABLE_NAME);

    }

    public Cursor readRecordOrderByAge() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, //Primary Key
                CalendarContract.CalendarEntry.COLUMN_DATE,
                CalendarContract.CalendarEntry.COLUMN_PART,
                CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT //for test
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
*/

package com.kokonut.NCNC.Calendar;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.kokonut.NCNC.Home.HomeContract;

public class CalendarDBHelper extends SQLiteOpenHelper {
    public static CalendarDBHelper CalendarDbHelper = null;
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 2;
    Context context;

    public static CalendarDBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        Log.d("겟인스턴스", "getInstance: ");
        if(CalendarDbHelper == null){
            CalendarDbHelper = new CalendarDBHelper(context);
        }
        return CalendarDbHelper; //이미 있다면 기존의 객체 리턴
    }

    private CalendarDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("캘린더 테이블 생성", "onCreate: ");
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_CREATE_TABLE); // 캘린더 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        // 테이블을 없애고 새로 만든다
        sqLiteDatabase.execSQL(CalendarContract.CalendarEntry.SQL_DELETE_TABLE);

        onCreate(sqLiteDatabase);
    }

    void insertRecord(String date, int part, int color) {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("!@!@", "insertRecord: " + date);
        ContentValues values = new ContentValues();
        //primary key(BaseColumns._ID) 는 업데이트 필요 없음
        values.put(CalendarContract.CalendarEntry.COLUMN_DATE, date);
        values.put(CalendarContract.CalendarEntry.COLUMN_PART, part);
        //for test

        db.insert(CalendarContract.CalendarEntry.TABLE_NAME, null, values);
    }

    void deleteRecord(String date){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CalendarContract.CalendarEntry.TABLE_NAME + " WHERE name=" + "\"" + date + "\"");
        //db.delete(CalendarContract.CalendarEntry.TABLE_NAME, ,);
    }

    void deleteAllrecord(){
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + CalendarContract.CalendarEntry.TABLE_NAME);
    }

    public Cursor readRecordOrderByAge() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, //Primary Key
                CalendarContract.CalendarEntry.COLUMN_DATE,
                CalendarContract.CalendarEntry.COLUMN_PART,
                CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT //for test
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
/*
    void insertRecord_Home(String date, int part, int color, String calendarObject) {
        SQLiteDatabase db = getReadableDatabase();

        ContentValues values = new ContentValues();
        //primary key(BaseColumns._ID) 는 업데이트 필요 없음
        values.put(HomeContract.homeEntry.COLUMN_DUST, date);
        values.put(HomeContract.homeEntry.COLUMN_RAIN, part);
        //for test
        values.put(HomeContract.homeEntry.COLUMN_TEMPERATURE, calendarObject);

        db.insert(HomeContract.homeEntry.TABLE_NAME, null, values);
    }

    public Cursor readRecordOrderByAge_Home() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, //Primary Key
                CalendarContract.CalendarEntry.COLUMN_DATE,
                CalendarContract.CalendarEntry.COLUMN_PART,
                CalendarContract.CalendarEntry.COLUMN_CALENDAROBJECT //for test
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
    */

}
