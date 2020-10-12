package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;
import java.io.Serializable;

public class CarWashContents implements Serializable{

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("lat")
    @Expose
    private Double lat;
    @SerializedName("lon")
    @Expose
    private Double lon;
    @SerializedName("address")
    @Expose
    private String address;
    @SerializedName("phone")
    @Expose
    private String phone;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("district")
    @Expose
    private String district;
    @SerializedName("dong")
    @Expose
    private String dong;
    @SerializedName("open_sat")
    @Expose
    private String open_sat;
    @SerializedName("open_sun")
    @Expose
    private String open_sun;
    @SerializedName("open_week")
    @Expose
    private String open_week;
    @SerializedName("wash")
    @Expose
    private List<String> wash = null;
    @SerializedName("score")
    @Expose
    private Float score;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getDong() {
        return dong;
    }

    public void setDong(String dong) {
        this.dong = dong;
    }

    public String getOpenSat() {
        return open_sat;
    }

    public void setOpenSat(String open_sat) {
        this.open_sat = open_sat;
    }

    public String getOpenSun() {
        return open_sun;
    }

    public void setOpenSun(String open_sun) {
        this.open_sun = open_sun;
    }

    public String getOpenWeek() {
        return open_week;
    }

    public void setOpenWeek(String open_week) {
        this.open_week = open_week;
    }

    public List<String> getWash() {
        return wash;
    }

    public void setWash(List<String> wash) {
        this.wash = wash;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

}
