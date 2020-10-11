package com.kokonut.NCNC.Home;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.kokonut.NCNC.Retrofit.ScoreContents;

import java.io.Serializable;

public class ScoreInfoData  {

    private String date;
    private String regID;
    private Integer rnLv;
    private Integer taLv;
    private Integer pm10Lv;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getRegID() {
        return regID;
    }

    public void setRegID(String regID) {
        this.regID = regID;
    }

    public Integer getRnLv() {
        return rnLv;
    }

    public void setRnLv(Integer rnLv) {
        this.rnLv = rnLv;
    }

    public Integer getTaLv() {
        return taLv;
    }

    public void setTaLv(Integer taLv) {
        this.taLv = taLv;
    }

    public Integer getPm10Lv() {
        return pm10Lv;
    }

    public void setPm10Lv(Integer taLv) {
        this.pm10Lv = pm10Lv;
    }


    public ScoreInfoData(String date, String regID, Integer rnLv, Integer taLv, Integer pm10Lv){
        this.date = date;
        this.regID = regID;
        this.rnLv = rnLv;
        this.taLv = taLv;
        this.pm10Lv = pm10Lv;
    }

}
