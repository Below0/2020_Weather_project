package com.kokonut.NCNC.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewContents {

    @SerializedName("id")
    @Expose
    private Integer id;

    @SerializedName("reviews")
    @Expose
    private List<Content> reviews = null;

    public ReviewContents(Integer id, List<Content> reviews){
        this.id = id;
        this.reviews = reviews;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Content> getReviews() { return reviews; }

    public void setReviews(List<Content> reviews) {
        this.reviews = reviews;
    }

    public class Content {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("wash_id")
        @Expose
        private String wash_id;
        @SerializedName("user_id")
        @Expose
        private String user_id;
        @SerializedName("score")
        @Expose
        private Integer score;
        @SerializedName("user_name")
        @Expose
        private String user_name;
        @SerializedName("content")
        @Expose
        private String content;
        @SerializedName("created_date")
        @Expose
        private String date;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String id) {
            this.user_id = user_id;
        }

        public Integer getScore() {
            return score;
        }

        public void setScore(Integer score) {
            this.score = score;
        }

        public String getUser_name() {
            return user_name;
        }

        public void setUser_name(String user_name){
            this.user_name = user_name;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getDate() {
            return date;
        }

        public void setDate() {
            this.date = date;
        }

        public String getWash_id() { return wash_id;}

        public void setWash_id(){
            this.wash_id = wash_id;
        }
    }
}