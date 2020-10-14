package com.kokonut.NCNC.Map;

import java.util.ArrayList;

public class CarWashData {
    private final String id;
    private final String name;
    private final String latitude;
    private final String longitude;
    private final String address;
    private final String phone;
    private final String city;
    private final String district;
    private final String dong;
    private final ArrayList<String> washtype_id;

    public CarWashData(String id, String name, String latitude, String longitude,
                       String address, String phone, String city, String district,
                       String dong, ArrayList<String> washtype_id) {
        this.id = id;
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
        this.address = address;
        this.phone = phone;
        this.city = city;
        this.district = district;
        this.dong = dong;
        this.washtype_id = washtype_id;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getAddress() {
        return address;
    }
    public String getPhone() {
        return phone;
    }
    public String getCity() {
        return city;
    }
    public String getDistrict() {
        return district;
    }
    public String getDong() {
        return dong;
    }
    public ArrayList<String> getWashTypeId() {
        return washtype_id;
    }
}

