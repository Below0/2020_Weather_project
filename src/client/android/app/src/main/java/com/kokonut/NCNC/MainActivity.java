package com.kokonut.NCNC;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;


import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import android.os.Build;
import android.os.Bundle;

import android.widget.TextView;
import android.widget.Toast;
import android.content.Context;


import com.google.gson.Gson;
import com.google.android.material.tabs.TabLayout;

import com.google.gson.Gson;
import com.kokonut.NCNC.Calendar.CalendarDBHelper;

import com.kokonut.NCNC.Calendar.CalendarFragment;
import com.kokonut.NCNC.Calendar.Calendar_PopupFragment;
import com.kokonut.NCNC.Home.HomeFragment;

import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewContents;
import com.kokonut.NCNC.Retrofit.ScoreContents;
import com.kokonut.NCNC.Retrofit.WeatherContents;

import com.kokonut.NCNC.Map.MapFragment;
import com.kokonut.NCNC.MyPage.AlarmActivity;
import com.kokonut.NCNC.MyPage.MypageFragment;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;


import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import androidx.fragment.app.Fragment;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.xml.transform.Result;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements Calendar_PopupFragment.uploadDialogInterface{
    private RetrofitAPI retrofitAPI;
    List<WeatherContents.Content> mmlist;
    public String[] scoreList = new String[8];
    int maxScore = 0, maxScoreDay = 0;

    HomeFragment homeFragment;
    CalendarFragment calendarFragment;
    MapFragment mapFragment;
    MypageFragment mypageFragment;
    KakaoAdapter kakaoAdapter;
    BottomNavigationView bottomNavigationBar;

    TabLayout tabLayout;
    ViewPager2 viewPager2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        kakaoAdapter = KakaoAdapter.getInstance(MainActivity.this);

        kakaoAdapter.kakaoLogin();
        viewPager2 = findViewById(R.id.home_viewpager2);
        tabLayout = findViewById(R.id.home_tablayout);
        bottomNavigationBar = findViewById(R.id.bottom_navigation_bar);
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) bottomNavigationBar.getChildAt(0);
        Log.d("hash_key", getKeyHash(MainActivity.this));

        for (int i = 0; i < menuView.getChildCount(); i++) {
            final View iconView = menuView.getChildAt(i).findViewById(R.id.icon);
            final ViewGroup.LayoutParams layoutParams = iconView.getLayoutParams();
            iconView.setLayoutParams(layoutParams);
        }



        //서버 통신 - 세차점수

        retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
        retrofitAPI.fetchScore().enqueue(new Callback<ScoreContents>() {
            @Override
            public void onResponse(Call<ScoreContents> call, Response<ScoreContents> response) {
                Log.d("Retrofit_Score", "Success: "+new Gson().toJson(response.body().getContents()));

                List<ScoreContents.Content> mlist = response.body().getContents();
                if(mlist==null){ //서버에 해당정보 없을 때
                    Log.e("Retrofit_Score", "Success: NULL");
                }
                else{
                    scoreList= new String[8]; //초기화
                    for(int i=0; i<7; i++){
                        scoreList[i] = makeScoreList(mlist.get(i).getRnLv(), mlist.get(i).getTaLv());
                        if(maxScore<Integer.parseInt(scoreList[i])){
                            maxScore = Integer.parseInt(scoreList[i]);
                            maxScoreDay = i;
                        }
                    }

                    Log.d("11111122", "onResponse: " + maxScoreDay);
                    Log.d("11111122", "onResponse: " + maxScore);

                }
            }

            @Override
            public void onFailure(Call<ScoreContents> call, Throwable t) {
                Log.e("Retrofit_Score", "failure: "+t.toString());
            }


        });

        homeFragment = new HomeFragment();
        calendarFragment = new CalendarFragment();
        mapFragment = new MapFragment();
        mypageFragment = new MypageFragment();

        //첫 화면 HomeFragment로 설정
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.main_layout, homeFragment).commit();

        bottomNavigationBar.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.tab1: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,homeFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab2: {

                        Bundle bundle = new Bundle();
                        bundle.putInt("maxScoreDay", maxScoreDay);
                        calendarFragment.setArguments(bundle);

                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,calendarFragment).commitAllowingStateLoss();
                        return true;
                    }
                    case R.id.tab3: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,mapFragment).commitAllowingStateLoss();
                        return true;
                    }/*
                    case R.id.tab4: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,castFragment).commitAllowingStateLoss();
                        return true;
                    }*/
                    case R.id.tab5: {
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_layout,mypageFragment).commitAllowingStateLoss();
                        return true;
                    }

                    //default: return false;
                }
                return true;
            }
        });


    }


    @Override
    public void senddatatoCalendarFragment(int popupResult) {
        Log.d("senddatdatoCael", "senddatatoCalendarFragment: "+ popupResult);
        if (popupResult != 4) //내부세차 외부세차 리스트일 경우
            calendarFragment.devidepopupValue(popupResult);
        else if (popupResult == 4)
            Log.d("((((((TAG))))))))))))))", "senddatatoCalendarFragment: ");
            calendarFragment.removeCustomDecorator(popupResult);
    }

    public static String getKeyHash(final Context context) {
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo packageInfo = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            if (packageInfo == null)
                return null;

            for (Signature signature : packageInfo.signatures) {
                try {
                    MessageDigest md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    return android.util.Base64.encodeToString(md.digest(), android.util.Base64.NO_WRAP);
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }



    public String makeScoreList(int rn_lv, int ta_lv){
        String str = String.valueOf(rn_lv*9 + ta_lv*2);
        return str;
    }

    public int getScoreDay(){
        return maxScoreDay;
    }
}
