package com.kokonut.NCNC.Retrofit;

import android.content.Context;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.MainActivity;

import java.util.HashMap;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;
//import retrofit2.converter.simplexml.SimpleXmlConverterFactory;


public class RetrofitClient {
    public static final String BaseURL1 = "http://3.131.33.128:8000/";
    public static final String BaseURL2 = "https://api.waqi.info/feed/";
    public static final String BaseURL3 = "http://openapi.epost.go.kr/postal/retrieveLotNumberAdressAreaCdService/retrieveLotNumberAdressAreaCdService/";

    public static RetrofitClient retrofitClient;
    private static Retrofit retrofit1 = null;
    private static Retrofit retrofit2 = null;
    private static Retrofit retrofit3 = null;


    public static RetrofitClient getInstance(){
        if(retrofitClient == null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public Retrofit getClient1() {
        return getClient1(null);
    }

    public static Retrofit getClient2() {
        return getClient2(null);
    }

    public static Retrofit getClient3() {
        return getClient3(null);
    }


    public static Retrofit getClient1(final Context context){
        Gson gson1 = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client = new OkHttpClient.Builder();
        client.readTimeout(60, TimeUnit.SECONDS);
        client.writeTimeout(60, TimeUnit.SECONDS);
        client.connectTimeout(60, TimeUnit.SECONDS);
        client.addInterceptor(interceptor);
        client.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        if(retrofit1 == null){
            retrofit1 = new Retrofit.Builder()
                    .baseUrl(BaseURL1)
                    .client(client.build())
                    .addConverterFactory(GsonConverterFactory.create(gson1))
                    .build();
        }
        return retrofit1;
    }

    public static Retrofit getClient2(final Context context){
        Gson gson2 = new GsonBuilder().setLenient().create();

        HttpLoggingInterceptor interceptor2 = new HttpLoggingInterceptor();
        interceptor2.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client2 = new OkHttpClient.Builder();
        client2.readTimeout(60, TimeUnit.SECONDS);
        client2.writeTimeout(60, TimeUnit.SECONDS);
        client2.connectTimeout(60, TimeUnit.SECONDS);
        client2.addInterceptor(interceptor2);
        client2.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        if(retrofit2 == null){
            retrofit2 = new Retrofit.Builder()
                    .baseUrl(BaseURL2)
                    .client(client2.build())
                    .addConverterFactory(GsonConverterFactory.create(gson2))
                    .build();
        }
        return retrofit2;
    }

    public static Retrofit getClient3(final Context context){

        HttpLoggingInterceptor interceptor3 = new HttpLoggingInterceptor();
        interceptor3.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder client3 = new OkHttpClient.Builder();
        client3.readTimeout(60, TimeUnit.SECONDS);
        client3.writeTimeout(60, TimeUnit.SECONDS);
        client3.connectTimeout(60, TimeUnit.SECONDS);
        client3.addInterceptor(interceptor3);
        client3.addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Interceptor.Chain chain) throws IOException {
                Request request = chain.request();

                return chain.proceed(request);
            }
        });

        if(retrofit3 == null){
            retrofit3 = new Retrofit.Builder()
                    .baseUrl(BaseURL3)
                    .client(client3.build())
                    .addConverterFactory(SimpleXmlConverterFactory.create())
                    .build();
        }
        return retrofit3;
    }
}
