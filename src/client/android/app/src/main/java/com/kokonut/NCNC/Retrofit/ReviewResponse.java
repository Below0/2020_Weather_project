package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewResponse {

    @SerializedName("status")
    @Expose
    private Integer status;

    @SerializedName("id")
    @Expose
    private Integer id;

//    @SerializedName("score")
//    private Integer score;
//
//    @SerializedName("content")
//    private String content;
//
//    public ReviewResponse(Integer id, Integer score, String content) {
//        this.id = id;
//        this.score = score;
//        this.content = content;
//        this.status=300; //before return
//    }

    public Integer getStatus() {
        return status;
    }

    public Integer getId() {

        return id;
    }

//    public Integer getScore(){
//        return score;
//    }
//
//    public String getContent(){
//        return content;
//    }



}