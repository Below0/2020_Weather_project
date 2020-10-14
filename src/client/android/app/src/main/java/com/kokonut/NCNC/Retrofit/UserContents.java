package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserContents {

    public UserContents(String name, String id){
        this.id = id;
        this.name = name;
    }

    @SerializedName("status")
    @Expose
    private String status = null;

    @SerializedName("name")
    @Expose
    private String name = null;

    @SerializedName("id")
    @Expose
    private String id = null;

    public String getStatus() {
        return status;
    }

    public void setName(String name) { this.name = name; }

    public void setId(String id) { this.id = id; }

}