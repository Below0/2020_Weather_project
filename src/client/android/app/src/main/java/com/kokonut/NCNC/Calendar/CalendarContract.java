package com.kokonut.NCNC.Calendar;

import android.provider.BaseColumns;

public class CalendarContract {
    private CalendarContract() {
    }

    public static class CalendarEntry implements BaseColumns {
        public static final String TABLE_NAME = "calendar";
        public static final String COLUMN_DATE = "name";
        public static final String COLUMN_PART = "part";
        public static final String COLUMN_COLOR = "color";
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " (" +
                        _ID + " INTEGER PRIMARY KEY," +
                        COLUMN_DATE + " TEXT," +
                        COLUMN_PART + " INTEGER," +
                        COLUMN_COLOR + " INTEGER)";
        public static final String SQL_DELETE_TABLE =
                "DROP TABLE IF EXISTS " + TABLE_NAME;
    }
}
