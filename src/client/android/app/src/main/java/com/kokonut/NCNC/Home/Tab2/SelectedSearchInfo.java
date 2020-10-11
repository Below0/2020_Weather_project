package com.kokonut.NCNC.Home.Tab2;

import java.io.Serializable;
import java.util.ArrayList;

public class SelectedSearchInfo implements Serializable {
    private String si; //시
    private String gu; //구
    private String dong; //동
    private String day; //요일
    private String time; //시간
    private ArrayList<String> kind = new ArrayList(); //세차종류

    public String getSi(){
        return si;
    }

    public void setSi(String si){
        this.si = si;
    }

    public String getGu(){
        return gu;
    }

    public void setGu(String gu){
        this.gu = gu;
    }

    public String getDong(){
        return dong;
    }

    public void setDong(String dong){
        this.dong = dong;
    }

    public String getDay(){
        return day;
    }

    public void setDay(String day){
        this.day = day;
    }

    public String getTime(){
        return time;
    }

    public void setTime(String time){
        this.time = time;
    }

    public ArrayList<String> getKind(){
        return kind;
    }

    public void setKind(ArrayList<String> kind){
        this.kind = kind;
    }

    public SelectedSearchInfo(String si, String gu, String dong, String day, String time, ArrayList<String> kind) {
        this.si = si;
        this.gu = gu;
        this.dong = dong;
        this.day = day;
        this.time = time;
        this.kind = kind;
    }
}
