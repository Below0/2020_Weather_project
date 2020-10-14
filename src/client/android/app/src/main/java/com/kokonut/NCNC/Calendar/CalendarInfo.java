package com.kokonut.NCNC.Calendar;
import com.prolificinteractive.materialcalendarview.CalendarDay;


public class CalendarInfo{

    customDecorator CustomDecorator;
    CalendarDay date;
    int part;

    public CalendarInfo(customDecorator CustomDecorator, CalendarDay date, int part){
        this.CustomDecorator = CustomDecorator;
        this.date = date;
        this.part = part;

    }

    public customDecorator getCustomDecorator() {
        return CustomDecorator;
    }

    public String getDate() {
        return date.toString();
    }

    public int getPart() {
        return part;
    }


}
