package com.kokonut.NCNC;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kakao.sdk.auth.LoginClient;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.KakaoSdk;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.AccessTokenInfo;
import com.kakao.sdk.user.model.User;

import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewResponse;
import com.kokonut.NCNC.Retrofit.ScoreContents;
import com.kokonut.NCNC.Retrofit.UserContents;

import java.util.HashMap;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class KakaoAdapter {
    Context mContext = null;
    OAuthToken oAuthToken;
    String appKey = "161c3ebdf4c87831197714a8529765a4";
    String TAG = "OAUTH";
    User user = null;
    Boolean isLogin = false;
    AccessTokenInfo accessTokenInfo = null;


    private static KakaoAdapter kakaoAdapter = null;

    public KakaoAdapter(Context mContext){
        this.mContext = mContext;
        KakaoSdk.init(mContext,this.appKey);
    }

    public static KakaoAdapter getInstance(Context mContext){
        if(kakaoAdapter == null) kakaoAdapter = new KakaoAdapter(mContext);
        return kakaoAdapter;
    }
    public static KakaoAdapter getInstance(){
        return kakaoAdapter;
    }

    private void loadTokenInfo(){
        UserApiClient.getInstance().accessTokenInfo((accessTokenInfo, throwable1) -> {
            if (throwable1 != null) {
                Log.e(TAG, "토큰 정보 보기 실패", throwable1);
            }
            else if (accessTokenInfo != null) {
                this.accessTokenInfo = accessTokenInfo;
                Log.i(TAG, "토큰 정보 보기 성공 \n회원번호: "+this.accessTokenInfo.getId());
            }
            return null;
        });
    }

    private void callback1(OAuthToken oAuthToken, Throwable throwable){
        if (throwable != null) {
            Log.e(TAG, "로그인 실패", throwable);
        }
        else if (oAuthToken != null) {
            Log.i(TAG, "로그인 성공 "+oAuthToken);
            this.isLogin = true;
            this.oAuthToken = oAuthToken;
            loadProfile();
        }
    }

    public boolean isLogin(){return isLogin;}

    public void kakaoLogin(){

        if (LoginClient.getInstance().isKakaoTalkLoginAvailable(mContext)) {
            LoginClient.getInstance().loginWithKakaoTalk(mContext, (oAuthToken1, throwable) -> {
                callback1(oAuthToken1,throwable);
                return null;
            });
        } else {
            LoginClient.getInstance().loginWithKakaoAccount(mContext, (oAuthToken1, throwable) -> {
                callback1(oAuthToken1,throwable);
                return null;
            });
        }

    }

    public void loadProfile(){
        if(this.accessTokenInfo == null) loadTokenInfo();
        UserApiClient.getInstance().me(((user, throwable) -> {
            if (throwable != null) {
                Log.e(TAG, "사용자 정보 요청 실패", throwable);
            }
            else if (user != null) {
                this.user = user;
                Log.i(TAG, "사용자 정보 요청 성공" +
                        "\n회원번호:" +user.getId()+
                        "\n이메일: " +user.getKakaoAccount().getProfile().getNickname());
                register_server();
            }
            return null;
        }));
    }

    private void register_server(){
        RetrofitAPI retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
        String name = this.user.getKakaoAccount().getProfile().getNickname();
        String id = Long.toString(this.user.getId());

        HashMap<String,String> param = new HashMap<>();
        param.put("id",id);
        param.put("name",name);

        retrofitAPI.fetchUser(param).enqueue(new Callback<UserContents>() {
            @Override
            public void onResponse(Call<UserContents> call, Response<UserContents> response) {
                Log.d("user_check", "Success: "+new Gson().toJson(response.body().getStatus()));
            }

            @Override
            public void onFailure(Call<UserContents> call, Throwable t) {
                Log.e("user_check", "failure: "+t.toString());
            }
        });
    }


    public User getUser() {
        return user;
    }

    public OAuthToken getoAuthToken() {
        return oAuthToken;
    }
}
