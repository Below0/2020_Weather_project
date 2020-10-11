package com.kokonut.NCNC.Calendar;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.style.LineBackgroundSpan;
import android.util.Log;

import com.kokonut.NCNC.R;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.logging.Handler;

public class customDecorator implements DayViewDecorator {

    //private CalendarDay date;
    private CalendarDay date;

    private String date_string;
    private Drawable drawable;

    int checkedList;
    private Activity activity;
    private MaterialCalendarView materialCalendarView;
    private int cleancar_color;
    private CalendarDBHelper CalendardbHelper;

    public customDecorator(CalendarDay date){

        this.date = date;
    }

    public customDecorator(Activity context, Drawable drawable, CalendarDay date, int checkedList) {


        this.activity = context;
        this.date = date;
        this.date_string = date.toString();
        this.checkedList = checkedList;
        this.CalendardbHelper = CalendardbHelper;

        Log.d("\n\n\n알았다", "customDecorator: -" + date);

        decideCircle();
    }

    private void decideCircle(){
        /*동그라미를 치기 위한 for문*/
        Log.d("wow", "customDecorator: is null 1177");

        //초기화 안하면 에러뜸

        if(checkedList == 0) return;

        if(checkedList == 1){ //내부세차
            cleancar_color = 1;
            drawable = activity.getResources().getDrawable(R.drawable.circle_light_purple);
        }
        else if(checkedList == 2) { //외부세차
            cleancar_color = 1;
            drawable = activity.getResources().getDrawable(R.drawable.circle_light_purple);
        }
        else if(checkedList == 3) { //전체 세차
            cleancar_color = 2;
            drawable = activity.getResources().getDrawable(R.drawable.circle_light_purple);
        }
        else if(checkedList == 5) { //세차추천일
            cleancar_color = 5;
            drawable = activity.getResources().getDrawable(R.drawable.calendar_fullcircle);

        }


    }

    public int getpart(){
        return checkedList; //return : 4 -> 데이터 삭제, 이하-->데이터 추가
    }

    public int getColor(){
        return cleancar_color;
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        Log.d("1. 꾸미는 날짜", "shouldDecorate: " +day);
        Log.d("2/ 꾸미는 날짜", "shouldDecorate: " +date.toString());
        return date != null && day.equals(date);
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.setBackgroundDrawable(drawable);
        view.addSpan(new customSelectedDate(activity, checkedList));
        Log.d("데코", "decorate: ");
    }


}

class customSelectedDate implements LineBackgroundSpan{

    String flag_text;
    int color_light_purple;
    Activity activity;

    public customSelectedDate(Activity activity, int cleancar_part){
        this.activity = activity;
        color_light_purple = activity.getColor(R.color.color_calender_lightpurple);

        if(cleancar_part == 1){
            //내부세차
            this.flag_text = "내부";
        }
        else if(cleancar_part == 2){
            //외부세차
            this.flag_text = "외부";
        }
        else if(cleancar_part == 3){
            //전체세차
            this.flag_text = "전체";
        }
        else if(cleancar_part == 5){
            //전체세차
            this.flag_text = "";
        }

    }

    @Override
    public void drawBackground(Canvas canvas, Paint paint, int left, int right, int top,
                               int baseline, int bottom, CharSequence text, int start, int end, int lnum) {

        Log.d("드로 백그라운드 ", "override: ");

        paint.setColor(color_light_purple);
        paint.setTextSize(36);
        canvas.drawText(flag_text,(left+right)/4, (bottom+24),paint);

        //paint.setColor(Color.RED); 선택된 날짜 색깔
    }

}