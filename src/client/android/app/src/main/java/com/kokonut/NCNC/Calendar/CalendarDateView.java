package com.kokonut.NCNC.Calendar;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class CalendarDateView {
    long in_maxmilli;
    long out_maxmilli;
    Calendar caltoday; //static 변수

    public CalendarDateView(long[] maxmilli){
        this.caltoday = CalendarFragment.calToday;
    }


    public void findAdjacentDateFromToday(int[] dates_info, int gotPart, long[] maxmilli) {
        //가장 최근 날짜를 찾음
        //milisecond 로 변환하기 위해 calendar type 설정
        Calendar calTemp = caltoday;
        long calmilli = 0;

        //가장 최근 날짜를 찾음
        //milisecond 로 변환하기 위해 calendar type 설정
        calTemp.set(Calendar.YEAR, dates_info[0]);
        calTemp.set(Calendar.MONTH, dates_info[1] - 1);
        calTemp.set(Calendar.DATE, dates_info[2]);

        calmilli = calTemp.getTimeInMillis(); //millisecond 형태로 비교

        Log.d("db 안에 있는 날짜들", "initCalendarDB: ");
        Log.d("쭉", "initCalendarDB: " + maxmilli[0]);
        Date date2 = new Date(calmilli);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("포맷 쭉", "getGapDay: " + format2.format(date2));
        Log.d("포맷 쭉", "getGapDay: " + calmilli);

        //과거 날짜일때만 나의 세차일정에 ~일 경과를 계산할 수 있음
        if (isPastDate(calmilli)) {
            Log.d("got part ", " : "+gotPart);

            if (gotPart == 1) {
                if (calmilli > maxmilli[0]) {
                    maxmilli[0] = calmilli;
                    Log.d("백 - 내부", "initCalendarDB: " + maxmilli[0]);
                }
            }

            if (gotPart == 2) {
                //Log.d("외부세차", "initCalendarDB: " + out_maxmilli);
                if (calmilli > maxmilli[1]) {
                    maxmilli[1] = calmilli;
                    Log.d("백 - 외부", "initCalendarDB: " + maxmilli[1]);
                }
            }
        } else {
            Log.d("오늘로부터미래", "initCalendarDB: "+format2.format(date2));
            Date date3 = new Date(calmilli);
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
        }
    }

        //////////////
        /*
        Calendar calTemp;
        calTemp = CalendarFragment.calToday;

        calTemp.set(Calendar.YEAR , dates_info[0]);
        calTemp.set(Calendar.MONTH, dates_info[1]-1);
        calTemp.set(Calendar.DATE, dates_info[2]);


        calmilli = calTemp.getTimeInMillis(); //millisecond 형태로 비교

        Log.d("db 안에 있는 날짜들", "initCalendarDB: ");
        Log.d("쭉", "initCalendarDB: " + in_maxmilli);
        Date date2 = new Date(calmilli);
        SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("포맷 쭉", "getGapDay: " + format2.format(date2));
        Log.d("포맷 쭉", "getGapDay: " + calmilli);

        //과거 날짜일때만 나의 세차일정에 ~일 경과를 계산할 수 있음
        if(isPastDate(calmilli)) {

            if (gotPart == 1) {
                Log.d("내부세차", "initCalendarDB: " + maxmilli[0]);
                if (calmilli > maxmilli[0]) {
                    maxmilli[0] = calmilli;
                    Log.d("내부", "initCalendarDB: " + maxmilli[0]);
                }
            }

            if (gotPart == 2) {
                Log.d("외부세차", "initCalendarDB: " + maxmilli[1]);
                if (calmilli > maxmilli[1]) {
                    maxmilli[1] = calmilli;
                    Log.d("외부", "initCalendarDB: " + maxmilli[1]);
                }
            }
        }else{
            Log.d("오늘로부터미래", "initCalendarDB: ");
            Log.d("쭉", "initCalendarDB: " + maxmilli[0]);
            Date date3 = new Date(calmilli);
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format3.format(date3));
            Log.d("포맷 쭉", "getGapDay: " + calmilli);
        }
    }
*/

    private boolean isPastDate(long maxmilli){

        Log.d("파데", "isPastDate: " + maxmilli);
        Calendar calendarToday = Calendar.getInstance();
        Date date = calendarToday.getTime();
        long today = date.getTime();

        Log.d("파데오늘", "isPastDate: " + today);


        Date date7 = new Date(today);
        SimpleDateFormat format7 = new SimpleDateFormat("yyyy-MM-dd");
        Log.d("포맷 쭉", "getGapDay: " + format7.format(date7));


        if(maxmilli < today) {

            Log.d("과거다", "initCalendarDB: ");
            Log.d("쭉", "initCalendarDB: " + maxmilli);
            Date date3 = new Date(maxmilli);
            SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format3.format(date3));
            Log.d("포맷 쭉", "getGapDay: " + maxmilli);


            return true;
        }
        else {

            Log.d("미래다", "initCalendarDB: ");
            Log.d("쭉", "initCalendarDB: " + maxmilli);
            Date date4 = new Date(maxmilli);
            SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM-dd");
            Log.d("포맷 쭉", "getGapDay: " + format4.format(date4));
            Log.d("포맷 쭉", "getGapDay: " + maxmilli);
            return false;
        }
    }


}
