package com.kokonut.NCNC.Retrofit;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RetrofitAPI {

    String URL = "http://3.131.33.128:8000/";

    @GET("score/11B10101")
    Call<ScoreContents> fetchScore();

    @GET("weather/11B10101")
    Call<WeatherContents> fetchWeather();

    @GET("car_wash/list")
    Call<List<CarWashContents>> fetchCarWash();

    @GET("car_wash/detail/{carWashId}")
    Call<CarWashDetail> getCarWashType(@Path("carWashId") int carWashId);

    @GET("seoul/?token=0610c5df7c85fe03547cb636204d1f4c6e87bd28")
    Call<RealTimeWeatherContents> fetchRealtimeWeather();

    @GET("getSiGunGuList?ServiceKey=GXmd7CyHCZHX4C42RPWEDDQ7k3vAV6JfoKhWYloFs0iCho%2BxNaXY1Eod1FpctxTHNoJqtajSqpgbfUiyFltYVQ%3D%3D&brtcCd=서울")
    Call<Location2Contents> fetchLocation2();

    @FormUrlEncoded
    @POST("car_wash/review/{id}")
    Call<ReviewResponse> writeReview (@Path("id") String id, @FieldMap HashMap<String, String> params);

    @GET("car_wash/review/{id}")
    Call<ReviewContents> fetchReview (@Path("id") String id);

    @FormUrlEncoded
    @POST("account/check")
    Call<UserContents> fetchUser(@FieldMap HashMap<String, String> params);

    public static final Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(RetrofitAPI.URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_week_kind(
                                              @Query("dong") String dong, @Query("open_week") String week,
                                              @Query("wash") List<String> wash);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_sat_kind(@Query("dong") String dong, @Query("open_sat") String sat,
                                                   @Query("wash") List<String> wash);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_sun_kind(
                                                   @Query("dong") String dong, @Query("open_sun") String sun,
                                                   @Query("wash") List<String> wash);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_notime_kind(
                                                   @Query("dong") String dong, @Query("wash") List<String> wash);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_week_nokind(
                                                          @Query("dong") String dong, @Query("open_week") String week);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_sat_nokind(
                                                          @Query("dong") String dong, @Query("open_sat") String sat);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_sun_nokind(
                                                          @Query("dong") String dong, @Query("open_sun") String sun);

    @GET("car_wash/list/?")
    Call<List<CarWashContents>> SearchCarWash_onlyaddress(@Query("dong") String dong);

}