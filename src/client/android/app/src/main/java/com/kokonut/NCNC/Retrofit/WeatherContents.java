package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherContents {
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("contents")
    @Expose
    private List<Content> contents = null;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Content> getContents() {
        return contents;
    }

    public void setContents(List<Content> contents) {
        this.contents = contents;
    }

    public class Content {

        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("regID")
        @Expose
        private String regID;
        @SerializedName("rnStPm")
        @Expose
        private Integer rnStPm; //오후 강수확률
        @SerializedName("taMax")
        @Expose
        private Integer taMax; //최고 온도
        @SerializedName("wfPm")
        @Expose
        private String wfPm; //오후 날씨 "흐림"
        @SerializedName("rnStAm")
        @Expose
        private Integer rnStAm; //오전 강수확률
        @SerializedName("taMin")
        @Expose
        private Integer taMin; //최저 온도
        @SerializedName("wfAm")
        @Expose
        private String wfAm; //오전 날씨

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

        public Integer getRnStPm() {
            return rnStPm;
        }

        public void setRnStPm(Integer rnStPm) {
            this.rnStPm = rnStPm;
        }

        public Integer getTaMax() {
            return taMax;
        }

        public void setTaMax(Integer taMax) {
            this.taMax = taMax;
        }

        public String getWfPm() {
            return wfPm;
        }

        public void setWfPm(String wfPm) {
            this.wfPm = wfPm;
        }

        public Integer getRnStAm() {
            return rnStAm;
        }

        public void setRnStAm(Integer rnStAm) {
            this.rnStAm = rnStAm;
        }

        public Integer getTaMin() {
            return taMin;
        }

        public void setTaMin(Integer taMin) {
            this.taMin = taMin;
        }

        public String getWfAm() {
            return wfAm;
        }

        public void setWfAm(String wfAm) {
            this.wfAm = wfAm;
        }
    }
}
