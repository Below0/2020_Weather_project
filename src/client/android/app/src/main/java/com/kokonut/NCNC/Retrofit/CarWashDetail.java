package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CarWashDetail {
    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private List<CarWashDetailType> type;

    @SerializedName("score")
    @Expose
    private double score;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<CarWashDetailType> getType() {
        return type;
    }

    public void setType(List<CarWashDetailType> type) {
        this.type = type;
    }

    public double getScore() {return score;}

    public void setScore(double score) {this.score = score;}

}
