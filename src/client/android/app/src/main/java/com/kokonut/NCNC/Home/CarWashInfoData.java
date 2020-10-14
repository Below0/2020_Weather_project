package com.kokonut.NCNC.Home;

import org.jetbrains.annotations.NotNull;

import java.io.Serializable;
import java.util.List;

public class CarWashInfoData implements Comparable<CarWashInfoData>, Serializable {
    private Integer id;
    private String name;
    //private Double lat;
    //private Double lon;
    private String address;
    private String phone;
    private String city;
    private String district;
    private String dong;
    private String open_sat;
    private String open_sun;
    private String open_week;
    private Double distance;
    private String wash;
    private Float score;
    //private String opentime;
    //private List<String> wash = null;

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
/*
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
 */

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

    public Double getDistance(){
        return distance;
    }

    public void setDistance(Double distance){
        this.distance = distance;
    }

    public String getWash(){
        return wash;
    }

    public void setWash(String wash){
        this.wash = wash;
    }

    public Float getScore() { return score; }

    public void setScore(Float score) { this.score = score; }


    /*public String getOpentime(){ return opentime;}

    public void setOpentime(String opentime){
        this.opentime = opentime;
    }

     */

    /*public List<String> getWash() {
        return wash;
    }

    public void setWash(List<String> wash) {
        this.wash = wash;
    }

     */

    public CarWashInfoData(Integer id, String name, String address, String phone, String city,
                           String district, String dong, String open_sat, String open_sun,
                           String open_week, Double distance, String wash, Float score){
        this.id = id;
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.dong = dong;
        this.open_sat = open_sat;
        this.open_sun = open_sun;
        this.open_week = open_week;
        this.distance = distance;
        this.wash = wash;
        this.score = score;
        //this.opentime = opentime;
    }

    public int compareTo(@NotNull CarWashInfoData carWashInfoData){ //오름차순 정렬....인데 내림차순으로 했더니 된당...
        return (int)(carWashInfoData.distance - this.distance);
        //return (int)(this.distance - carWashInfoData.distance); //오름차순
        //return distance.compareTo(((CarWashInfoData) carWashInfoData).distance); //오름차순
}

}
