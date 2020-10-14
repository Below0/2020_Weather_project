package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RealTimeWeatherContents {
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("data")
    @Expose
    private Data data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    public class Attribution {

        @SerializedName("url")
        @Expose
        private String url;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("logo")
        @Expose
        private String logo;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

    }

    public class City {

        @SerializedName("geo")
        @Expose
        private List<Double> geo = null;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("url")
        @Expose
        private String url;

        public List<Double> getGeo() {
            return geo;
        }

        public void setGeo(List<Double> geo) {
            this.geo = geo;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

    }

    public class Co {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class Daily {

        @SerializedName("o3")
        @Expose
        private List<O3_> o3 = null;
        @SerializedName("pm10")
        @Expose
        private List<Pm10_> pm10 = null;
        @SerializedName("pm25")
        @Expose
        private List<Pm25_> pm25 = null;
        @SerializedName("uvi")
        @Expose
        private List<Uvus> uvi = null;

        public List<O3_> getO3() {
            return o3;
        }

        public void setO3(List<O3_> o3) {
            this.o3 = o3;
        }

        public List<Pm10_> getPm10() {
            return pm10;
        }

        public void setPm10(List<Pm10_> pm10) {
            this.pm10 = pm10;
        }

        public List<Pm25_> getPm25() {
            return pm25;
        }

        public void setPm25(List<Pm25_> pm25) {
            this.pm25 = pm25;
        }

        public List<Uvus> getUvi() {
            return uvi;
        }

        public void setUvi(List<Uvus> uvi) {
            this.uvi = uvi;
        }

    }

    public class Data {

        @SerializedName("aqi")
        @Expose
        private Integer aqi;
        @SerializedName("idx")
        @Expose
        private Integer idx;
        @SerializedName("attributions")
        @Expose
        private List<Attribution> attributions = null;
        @SerializedName("city")
        @Expose
        private City city;
        @SerializedName("dominentpol")
        @Expose
        private String dominentpol;
        @SerializedName("iaqi")
        @Expose
        private Iaqi iaqi;
        @SerializedName("time")
        @Expose
        private Time time;
        @SerializedName("forecast")
        @Expose
        private Forecast forecast;
        @SerializedName("debug")
        @Expose
        private Debug debug;

        public Integer getAqi() {
            return aqi;
        }

        public void setAqi(Integer aqi) {
            this.aqi = aqi;
        }

        public Integer getIdx() {
            return idx;
        }

        public void setIdx(Integer idx) {
            this.idx = idx;
        }

        public List<Attribution> getAttributions() {
            return attributions;
        }

        public void setAttributions(List<Attribution> attributions) {
            this.attributions = attributions;
        }

        public City getCity() {
            return city;
        }

        public void setCity(City city) {
            this.city = city;
        }

        public String getDominentpol() {
            return dominentpol;
        }

        public void setDominentpol(String dominentpol) {
            this.dominentpol = dominentpol;
        }

        public Iaqi getIaqi() {
            return iaqi;
        }

        public void setIaqi(Iaqi iaqi) {
            this.iaqi = iaqi;
        }

        public Time getTime() {
            return time;
        }

        public void setTime(Time time) {
            this.time = time;
        }

        public Forecast getForecast() {
            return forecast;
        }

        public void setForecast(Forecast forecast) {
            this.forecast = forecast;
        }

        public Debug getDebug() {
            return debug;
        }

        public void setDebug(Debug debug) {
            this.debug = debug;
        }

    }

    public class Debug {

        @SerializedName("sync")
        @Expose
        private String sync;

        public String getSync() {
            return sync;
        }

        public void setSync(String sync) {
            this.sync = sync;
        }

    }

    public class Forecast {

        @SerializedName("daily")
        @Expose
        private Daily daily;

        public Daily getDaily() {
            return daily;
        }

        public void setDaily(Daily daily) {
            this.daily = daily;
        }

    }

    public class H {

        @SerializedName("v")
        @Expose
        private Integer v;

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    public class Iaqi {

        @SerializedName("co")
        @Expose
        private Co co;
        @SerializedName("h")
        @Expose
        private H h; //강수량
        @SerializedName("no2")
        @Expose
        private No2 no2;
        @SerializedName("o3")
        @Expose
        private O3 o3;
        @SerializedName("p")
        @Expose
        private P p;
        @SerializedName("pm10")
        @Expose
        private Pm10 pm10;
        @SerializedName("pm25")
        @Expose
        private Pm25 pm25;
        @SerializedName("r")
        @Expose
        private R r;
        @SerializedName("so2")
        @Expose
        private So2 so2;
        @SerializedName("t")
        @Expose
        private T t; //기온
        @SerializedName("w")
        @Expose
        private W w;
        @SerializedName("wd")
        @Expose
        private Wd wd;

        public Co getCo() {
            return co;
        }

        public void setCo(Co co) {
            this.co = co;
        }

        public H getH() {
            return h;
        }

        public void setH(H h) {
            this.h = h;
        }

        public No2 getNo2() {
            return no2;
        }

        public void setNo2(No2 no2) {
            this.no2 = no2;
        }

        public O3 getO3() {
            return o3;
        }

        public void setO3(O3 o3) {
            this.o3 = o3;
        }

        public P getP() {
            return p;
        }

        public void setP(P p) {
            this.p = p;
        }

        public Pm10 getPm10() {
            return pm10;
        }

        public void setPm10(Pm10 pm10) {
            this.pm10 = pm10;
        }

        public Pm25 getPm25() {
            return pm25;
        }

        public void setPm25(Pm25 pm25) {
            this.pm25 = pm25;
        }

        public R getR() {
            return r;
        }

        public void setR(R r) {
            this.r = r;
        }

        public So2 getSo2() {
            return so2;
        }

        public void setSo2(So2 so2) {
            this.so2 = so2;
        }

        public T getT() {
            return t;
        }

        public void setT(T t) {
            this.t = t;
        }

        public W getW() {
            return w;
        }

        public void setW(W w) {
            this.w = w;
        }

        public Wd getWd() {
            return wd;
        }

        public void setWd(Wd wd) {
            this.wd = wd;
        }

    }

    public class No2 {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class O3 {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class O3_ {

        @SerializedName("avg")
        @Expose
        private Integer avg;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("min")
        @Expose
        private Integer min;

        public Integer getAvg() {
            return avg;
        }

        public void setAvg(Integer avg) {
            this.avg = avg;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

    }

    public class P {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class Pm10 {

        @SerializedName("v")
        @Expose
        private Integer v;

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    public class Pm10_ {

        @SerializedName("avg")
        @Expose
        private Integer avg;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("min")
        @Expose
        private Integer min;

        public Integer getAvg() {
            return avg;
        }

        public void setAvg(Integer avg) {
            this.avg = avg;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

    }

    public class Pm25 {

        @SerializedName("v")
        @Expose
        private Integer v;

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

    }

    public class Pm25_ {

        @SerializedName("avg")
        @Expose
        private Integer avg;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("min")
        @Expose
        private Integer min;

        public Integer getAvg() {
            return avg;
        }

        public void setAvg(Integer avg) {
            this.avg = avg;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

    }

    public class R {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class So2 {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class T {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class Time {

        @SerializedName("s")
        @Expose
        private String s;
        @SerializedName("tz")
        @Expose
        private String tz;
        @SerializedName("v")
        @Expose
        private Integer v;
        @SerializedName("iso")
        @Expose
        private String iso;

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }

        public Integer getV() {
            return v;
        }

        public void setV(Integer v) {
            this.v = v;
        }

        public String getIso() {
            return iso;
        }

        public void setIso(String iso) {
            this.iso = iso;
        }

    }

    public class Uvus {

        @SerializedName("avg")
        @Expose
        private Integer avg;
        @SerializedName("day")
        @Expose
        private String day;
        @SerializedName("max")
        @Expose
        private Integer max;
        @SerializedName("min")
        @Expose
        private Integer min;

        public Integer getAvg() {
            return avg;
        }

        public void setAvg(Integer avg) {
            this.avg = avg;
        }

        public String getDay() {
            return day;
        }

        public void setDay(String day) {
            this.day = day;
        }

        public Integer getMax() {
            return max;
        }

        public void setMax(Integer max) {
            this.max = max;
        }

        public Integer getMin() {
            return min;
        }

        public void setMin(Integer min) {
            this.min = min;
        }

    }

    public class W {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }

    public class Wd {

        @SerializedName("v")
        @Expose
        private Double v;

        public Double getV() {
            return v;
        }

        public void setV(Double v) {
            this.v = v;
        }

    }
}
