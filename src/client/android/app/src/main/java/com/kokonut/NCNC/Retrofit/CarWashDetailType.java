package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarWashDetailType {
    @SerializedName("tid")
    @Expose
    private int tid;

    @SerializedName("name")
    @Expose
    private String name;

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
