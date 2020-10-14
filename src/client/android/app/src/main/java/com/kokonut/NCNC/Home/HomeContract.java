package com.kokonut.NCNC.Home;

import android.provider.BaseColumns;

public class HomeContract {
    //강수량 기온 미세먼지
    public static class homeEntry implements BaseColumns {
        public static final String TABLE_NAME = "homeTable";
        public static final String COLUMN_TEMPERATURE = "temperature";
        public static final String COLUMN_RAIN = "rain";
        public static final String COLUMN_DUST = "dust";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_TEMPERATURE + " INTEGER," +
                        COLUMN_RAIN + " INTEGER,"+
                        COLUMN_DUST + " INTEGER)"; //for test

        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }


}

