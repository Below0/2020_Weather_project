package com.kokonut.NCNC.Home.Retrofit;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.MainActivity;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static final String BaseURL = "http://3.131.33.128:8000/";
    private static Retrofit mRetrofit = null;


    public static Retrofit getClient(){
        Gson gson = new GsonBuilder().setLenient().create();

        if(mRetrofit == null){
            mRetrofit = new Retrofit.Builder()
                    .baseUrl(BaseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return mRetrofit;
    }

}
