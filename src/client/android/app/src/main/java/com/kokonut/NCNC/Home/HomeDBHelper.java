/*
package com.kokonut.NCNC.Home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class HomeDBHelper extends SQLiteOpenHelper {
    public static HomeDBHelper CalendarDbHelper = null;
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    Context context;

    public static HomeDBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        if(CalendarDbHelper == null){
            CalendarDbHelper = new HomeDBHelper(context);
        }
        return CalendarDbHelper; //이미 있다면 기존의 객체 리턴
    }

    private HomeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_CREATE_TABLE); // 캘린더 테이블 생성
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_CREATE_TABLE); // 홈 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        // 테이블을 없애고 새로 만든다
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_DELETE_TABLE);
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_DELETE_TABLE);

        onCreate(sqLiteDatabase);
    }

    public void insertRecord(int temperature, int rain, int dust) {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("!@!@", "insertRecord: " + rain);
        ContentValues values = new ContentValues();
        //primary key(BaseColumns._ID) 는 업데이트 필요 없음
        values.put(HomeContract.homeEntry.COLUMN_TEMPERATURE, temperature);
        values.put(HomeContract.homeEntry.COLUMN_RAIN, rain);
        values.put(HomeContract.homeEntry.COLUMN_DUST, dust);

        //for test

        db.insert(HomeContract.homeEntry.TABLE_NAME, null, values);
    }

    public Cursor readRecordOrderByID() {
        SQLiteDatabase db = getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, //Primary Key
                HomeContract.homeEntry.COLUMN_TEMPERATURE,
                HomeContract.homeEntry.COLUMN_RAIN,
                HomeContract.homeEntry.COLUMN_DUST //for test
        };

        String sortOrder = HomeContract.homeEntry._ID + " DESC"; //id 순서로 정렬

        Cursor cursor = db.query(
                HomeContract.homeEntry.TABLE_NAME,   // The table to query
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

package com.kokonut.NCNC.Home;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.kokonut.NCNC.Calendar.CalendarContract;

public class HomeDBHelper extends SQLiteOpenHelper {
    public static HomeDBHelper HomeDbHelper = null;
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    Context context;

    public static HomeDBHelper getInstance(Context context){ // 싱글턴 패턴으로 구현하였다.
        Log.d("homedb헬퍼-1", "getInstance: ");
        if(HomeDbHelper == null){
            Log.d("homedb헬퍼-2", "getInstance: ");
            HomeDbHelper = new HomeDBHelper(context);
            HomeDbHelper.getReadableDatabase();
        }
        Log.d("homedb헬퍼-3", "getInstance: ");

        return HomeDbHelper; //이미 있다면 기존의 객체 리턴
    }

    private HomeDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        Log.d("홈 테이블 생성", "onCreate: ");
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_CREATE_TABLE); // 홈 테이블 생성
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        // 단순히 데이터를 삭제하고 다시 시작하는 정책이 적용될 경우
        // 테이블을 없애고 새로 만든다
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_DELETE_TABLE);
        onCreate(sqLiteDatabase);

    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.setVersion(oldVersion);
    }

    //임시
    public void deleteAllrecord(SQLiteDatabase sqLiteDatabase) {
       // SQLiteDatabase db = getWritableDatabase();
       // db.execSQL("DELETE FROM " + HomeContract.homeEntry.TABLE_NAME);
        sqLiteDatabase.execSQL(HomeContract.homeEntry.SQL_DELETE_TABLE);

    }

    public void insertRecord(int temperature, int rain, int dust) {
        SQLiteDatabase db = getReadableDatabase();

        Log.d("!@!@", "insertRecord: " + rain);
        ContentValues values = new ContentValues();
        //primary key(BaseColumns._ID) 는 업데이트 필요 없음
        values.put(HomeContract.homeEntry.COLUMN_TEMPERATURE, temperature);
        values.put(HomeContract.homeEntry.COLUMN_RAIN, rain);
        values.put(HomeContract.homeEntry.COLUMN_DUST, dust);

        //for test

        db.insert(HomeContract.homeEntry.TABLE_NAME, null, values);
    }
    /*
        public void deleteRecord(String date){
            SQLiteDatabase db = getWritableDatabase();
            db.execSQL("DELETE FROM " + HomeContract.homeEntry.TABLE_NAME + " WHERE name=" + "\"" + date + "\"");
            //db.delete(CalendarContract.CalendarEntry.TABLE_NAME, ,);
        }
    */
    public Cursor readRecordOrderByID() {
        SQLiteDatabase db = HomeDbHelper.getReadableDatabase();
        String[] projection = {
                BaseColumns._ID, //Primary Key
                HomeContract.homeEntry.COLUMN_TEMPERATURE,
                HomeContract.homeEntry.COLUMN_RAIN,
                HomeContract.homeEntry.COLUMN_DUST //for test
        };

        String sortOrder = HomeContract.homeEntry._ID + " DESC"; //id 순서로 정렬

        Cursor cursor = db.query(
                HomeContract.homeEntry.TABLE_NAME,   // The table to query
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


