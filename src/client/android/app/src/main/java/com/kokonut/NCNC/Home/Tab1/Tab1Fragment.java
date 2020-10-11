package com.kokonut.NCNC.Home.Tab1;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.database.Cursor;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.text.PrecomputedText;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.fasterxml.jackson.databind.ser.std.CollectionSerializer;
import com.google.gson.Gson;
import com.kokonut.NCNC.GpsTracker;
import com.kokonut.NCNC.Home.HomeContract;
import com.kokonut.NCNC.Home.HomeDBHelper;
import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Home.ScoreInfoData;
import com.kokonut.NCNC.Map.CarWashInfoActivity;
import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.Retrofit.RealTimeWeatherContents;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ScoreContents;

import com.kokonut.NCNC.R;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class Tab1Fragment extends Fragment implements ActivityCompat.OnRequestPermissionsResultCallback{
    Tab1_RecyclerAdapter_Horizontal tab1_recyclerAdapter_horizontal;
    private static final int MSG_POPUP_CHANGED = 1;
    PopupHandler popupHandler = new PopupHandler();

    public scoreTask sct;

    public Boolean popupchecked = false;

    private RetrofitAPI retrofitAPI;
    private List<ScoreContents.Content> scoreContentsList;
    public ArrayList<ScoreInfoData> scoreInfoData;
    int maxScore=0, maxScoreDay=0;

    public ArrayList<CarWashInfoData> carWashInfoData;
    private List<CarWashContents> carWashContentsList;

    RecyclerView recyclerView;

    private GpsTracker gpsTracker;
    Geocoder geocoder;
    public Double myLat, myLon;
    String str1, str2, str3;

    //TextView tvLocation;

    ViewGroup viewGroup;
    LinearLayout popupButton;


    TextView date1, date2, date3, date4, date5, date6, date7;
    TextView todayScore, score1, score2, score3, score4, score5, score6, score7, goodDay;
    TextView thermometer, rain, mask;
    HomeDBHelper HomedbHelper;
    int getTemp, getRain, getDust;

    Button testButton;

    public Tab1Fragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("Home_Tab1", "onCreate: 1");

    }


    @Nullable @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_tab1, container, false);
        recyclerView = viewGroup.findViewById(R.id.tab1_recycler_view);
        popupButton = viewGroup.findViewById(R.id.home_popupButton);

//        tvLocation = viewGroup.findViewById(R.id.tab1_tv_location);

        date1 = viewGroup.findViewById(R.id.home_tab1_day1);
        date2 = viewGroup.findViewById(R.id.home_tab1_day2);
        date3 = viewGroup.findViewById(R.id.home_tab1_day3);
        date4 = viewGroup.findViewById(R.id.home_tab1_day4);
        date5 = viewGroup.findViewById(R.id.home_tab1_day5);
        date6 = viewGroup.findViewById(R.id.home_tab1_day6);
        date7 = viewGroup.findViewById(R.id.home_tab1_day7);

        todayScore = viewGroup.findViewById(R.id.home_score);
        score1 = viewGroup.findViewById(R.id.home_tab1_day1_score);
        score2 = viewGroup.findViewById(R.id.home_tab1_day2_score);
        score3 = viewGroup.findViewById(R.id.home_tab1_day3_score);
        score4 = viewGroup.findViewById(R.id.home_tab1_day4_score);
        score5 = viewGroup.findViewById(R.id.home_tab1_day5_score);
        score6 = viewGroup.findViewById(R.id.home_tab1_day6_score);
        score7 = viewGroup.findViewById(R.id.home_tab1_day7_score);
        goodDay = viewGroup.findViewById(R.id.home_tab1_goodday_text);

        thermometer = viewGroup.findViewById(R.id.thermometer);
        rain = viewGroup.findViewById(R.id.rain);
        mask = viewGroup.findViewById(R.id.mask);

        testButton = viewGroup .findViewById(R.id.test);

        //현재 위치
        gpsTracker = new GpsTracker(getContext());
        myLat = gpsTracker.getLatitude();
        myLon = gpsTracker.getLongitude();
        //GetAddress(myLat, myLon);
        //tvLocation.setText(str1+" "+str2+" "+str3+" 기준");




        //서버 통신 - 세차장 정보 리스트, 세차 점수
        new AsyncTask<Void, Void, String>(){
            @Override
            protected void onPreExecute(){
                Log.i("AsyncTask_carwashlist", "onPreExecute()");
            }

            @Override
            protected String doInBackground(Void... params) {
                retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
                Call<List<CarWashContents>> call = retrofitAPI.fetchCarWash();

                try {
                    carWashContentsList = call.execute().body();
                    carWashInfoData = new ArrayList<>();
                    for (int i = 0; i < 29; i++) {
                        carWashInfoData.add(new CarWashInfoData(carWashContentsList.get(i).getName(), carWashContentsList.get(i).getAddress(),
                                carWashContentsList.get(i).getPhone(), carWashContentsList.get(i).getCity(), carWashContentsList.get(i).getDistrict(),
                                carWashContentsList.get(i).getDong(), carWashContentsList.get(i).getOpenSat(), carWashContentsList.get(i).getOpenSun(),
                                carWashContentsList.get(i).getOpenWeek(), makeDistance(carWashContentsList.get(i).getLat(), carWashContentsList.get(i).getLon()),
                                carWashContentsList.get(i).getWash().toString()));
                    }
                    Collections.sort(carWashInfoData);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return null;
            }
            @Override
            protected void onPostExecute(String s){
                super.onPostExecute(s);

                recyclerView.setHasFixedSize(true);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                recyclerView.setLayoutManager(linearLayoutManager);
                tab1_recyclerAdapter_horizontal = new Tab1_RecyclerAdapter_Horizontal(carWashInfoData);
                recyclerView.setAdapter(tab1_recyclerAdapter_horizontal);

            }
        }.execute();



        Log.d("팝업에서 돌아옴!! -- 탭1 ", "onCreateView: ");

        //서버 통신 - 현재 날씨
        retrofitAPI = RetrofitClient.getInstance().getClient2().create(RetrofitAPI.class);
        retrofitAPI.fetchRealtimeWeather().enqueue(new Callback<RealTimeWeatherContents>() {
            @Override
            public void onResponse(Call<RealTimeWeatherContents> call, Response<RealTimeWeatherContents> response) {
                RealTimeWeatherContents.Data result = response.body().getData();
                if(result==null){
                    Log.e("Retrofit_Realtime", "Success: NULL");
                }
                else{
                    thermometer.setText(result.getIaqi().getT().getV()+"℃");
                    rain.setText((result.getIaqi().getH().getV()).intValue()+"%");
                    mask.setText(makeAQI((result.getAqi()).intValue()));
                }
            }

            @Override
            public void onFailure(Call<RealTimeWeatherContents> call, Throwable t) {
                Log.e("Retrofit_Realtime", "failure: "+t.toString());
            }
        });

        //'맞춤형 세차점수 설정하기' 버튼 클릭 시
        popupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("11111", "onCreateView: 2");
                Tab1_PopupFragment dialog = new Tab1_PopupFragment();
                //dialog.setTargetFragment(dialog, 1); //

                dialog.show(getActivity().getSupportFragmentManager(), "tab1");

                getActivity().getSupportFragmentManager().executePendingTransactions();
                dialog.getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialogInterface) {
                        //sct.cancel(true);
                        //final scoreTask newSct = new scoreTask(getActivity());
                        //newSct.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                        //popupchecked = true;
                        //sct.selfRestart();
                        //System.out.println(getDust+" "+getRain+" "+getTemp);
                    }
                });

            }
        });

        //오늘로부터 일주일 날짜
        date1.setText(getDate(0)); //오늘
        date2.setText(getDate(1));
        date3.setText(getDate(2));
        date4.setText(getDate(3));
        date5.setText(getDate(4));
        date6.setText(getDate(5));
        date7.setText(getDate(6));

        //더보기 버튼 클릭
        testButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Tab1_CarWashList.class);
                intent.putExtra("carwashinfodata", (Serializable)carWashInfoData);
                startActivity(intent);
            }
        });

        if(popupchecked == true){
            sct.selfRestart();
            popupchecked = false;
        }


        return viewGroup;
    }


    @Override
    public void onStart(){
        super.onStart();
        sct = new scoreTask(getActivity());
        sct.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        //new scoreTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR); //세차점수
    }

    @Override
    public void onResume(){ super.onResume(); }

    @Override
    public void onPause(){
        super.onPause();
    }

    @Override
    public void onDestroy(){
        super.onDestroy();
    }


    public class scoreTask extends AsyncTask<Void, String, ArrayList<ScoreInfoData>>{
        private FragmentActivity activity;
        public scoreTask(FragmentActivity activity) {
            this.activity = activity;
        }

        @Override
        protected ArrayList<ScoreInfoData> doInBackground(Void... params) {
            while(!isCancelled()){
                retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
                Call<ScoreContents> call2 = retrofitAPI.fetchScore();
                try {
                    scoreContentsList = call2.execute().body().getContents();
                    scoreInfoData = new ArrayList<>();
                    for (int i = 0; i < 7; i++) {
                        scoreInfoData.add(new ScoreInfoData(scoreContentsList.get(i).getDate(), scoreContentsList.get(i).getRegID(),
                                scoreContentsList.get(i).getPm10Lv(), scoreContentsList.get(i).getRnLv(), scoreContentsList.get(i).getTaLv()));
                    }
                    return scoreInfoData;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(String... values){ //this only runs when doInBackground() calls it with publishProgress()
        }

        @Override
        protected void onPostExecute(ArrayList<ScoreInfoData>result){ //runs after doInBackground() completes
            super.onPostExecute(result);
/*
            if(popupchecked == true){
                new scoreTask(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
                popupchecked = false;
            }

/*            todayScore = viewGroup.findViewById(R.id.home_score);
            score1 = viewGroup.findViewById(R.id.home_tab1_day1_score);
            score2 = viewGroup.findViewById(R.id.home_tab1_day2_score);
            score3 = viewGroup.findViewById(R.id.home_tab1_day3_score);
            score4 = viewGroup.findViewById(R.id.home_tab1_day4_score);
            score5 = viewGroup.findViewById(R.id.home_tab1_day5_score);
            score6 = viewGroup.findViewById(R.id.home_tab1_day6_score);
            score7 = viewGroup.findViewById(R.id.home_tab1_day7_score);
            goodDay = viewGroup.findViewById(R.id.home_tab1_goodday_text);
*/
            updateScoreDate(result);
        }
        public void selfRestart(){
            sct.cancel(true);
            //System.out.println(getDust+" "+getRain+" "+getTemp);
            new scoreTask(getActivity()).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        }
    }


    @NotNull
    public static String getDate(int weekday){
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("d");
        Calendar calendar = Calendar.getInstance(); //현재 날짜
        calendar.add(Calendar.DAY_OF_MONTH, weekday); //오늘로부터 일주일일때
        String day = format.format(calendar.getTime());
        return day;
    }

    public String makeScoreList(int rn_lv, int ta_lv, int pm10_lv){
        String str;
        double val1=0.33, val2=0.33, val3=0.33;
        int total = getTemp + getRain + getDust;
        if(total>0){
            val1 = getTemp / total;
            val2 = getRain / total;
            val3 = getDust / total;

            str = String.valueOf((int)Math.abs((ta_lv*2*val1 + rn_lv*8*val2 + pm10_lv*2.5*val3)*3));
        }
        else
            str = String.valueOf((int)Math.abs((ta_lv*2*val1 + rn_lv*8*val2 + pm10_lv*2.5*val3)*3));

        //String str = String.valueOf(rn_lv*9 + ta_lv*2);
        return str;
    }

    public String makeAQI(int aqi){
        String aqiResult;
        if(aqi>=0 && aqi<=30)
            aqiResult="미세먼지 좋음";
        else if(aqi>=31 && aqi<=80)
            aqiResult="미세먼지 보통";
        else if(aqi>=81 && aqi<=150)
            aqiResult="미세먼지 나쁨";
        else
            aqiResult="미세먼지 매우나쁨";
        return aqiResult;
    }

    //위도,경도 -> 시,구,동 변환
    private void GetAddress(Double latitude, Double longitude){
        List<Address> address = null;
        geocoder = new Geocoder(getActivity(), Locale.getDefault());

        try {
            address = geocoder.getFromLocation(latitude, longitude, 1);
        }
        catch(IOException e){
            e.printStackTrace();
            Log.d("getaddress","input/output error");
        }

        if(!address.isEmpty()){
            str1 = address.get(0).getLocality(); //시 - 서울시
            //str1 = address.get(0).getAdminArea(); //시 - 서울특별시
            str2 = address.get(0).getSubLocality(); //구
            //str2 = address.get(0).getSubAdminArea(); //구
            str3 = address.get(0).getThoroughfare(); //동
            Log.d("Home_GetAddress", str1+" "+str2+" "+str3);
        }
    }

    //interface 메소드 , 팝업으로부터 신호를 받음
    public void startDB(int resultcode){
        if(resultcode == 1) {
            Log.d("전달 완료 ", "startDB: ");

            HomedbHelper = HomedbHelper.getInstance(getActivity()); //dialog 데이터를 받기 위해 db 객체 생성

            if(HomedbHelper == null) {
                Log.d("tab1: 디비헬퍼가 null 임 ", "startDB: ");
                return;
            }

            Cursor cursor = HomedbHelper.readRecordOrderByID();

            int i = 0;
            while (cursor.moveToNext()) {
                getTemp = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_TEMPERATURE));
                getRain = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_RAIN));
                getDust = cursor.getInt(cursor.getColumnIndexOrThrow(HomeContract.homeEntry.COLUMN_DUST));

                /** 수연언니! get__ 쓰면 됨!! **/
                Log.d("전달 후 ", "onClick: "+getTemp);
                Log.d("전달 후", "onClick: "+getRain);
                Log.d("전달 후", "onClick: "+getDust);

            }

            //sct.selfRestart();

            //popupchecked = true;
        }
    }

    //현재 내위치~세차장 위치 거리
    private double makeDistance(double carwashLat, double carwashLon){
        double d = 6371 * Math.acos((Math.cos(Math.toRadians(myLat))*Math.cos(Math.toRadians(carwashLat))*
                Math.cos(Math.toRadians(carwashLon)-Math.toRadians(myLon))+Math.sin(Math.toRadians(myLat))
                *Math.sin(Math.toRadians(carwashLat))));

        return d;
    }

    //세차점수 띄우기
    private void updateScoreDate(ArrayList<ScoreInfoData> scoreInfoData){
        if(scoreInfoData != null){

            String[] scorelist = new String[7];
            maxScore = 0; maxScoreDay = 0;
            for(int i=0; i<7; i++){
                scorelist[i] = makeScoreList(scoreInfoData.get(i).getRnLv(),scoreInfoData.get(i).getTaLv(),scoreInfoData.get(i).getPm10Lv());

                if(maxScore < Integer.parseInt(scorelist[i])){
                    maxScore = Integer.parseInt(scorelist[i]);
                    maxScoreDay = i;
                }
            }

            todayScore.setText(scorelist[0] +"점");
            score1.setText(scorelist[0] +"점");
            score2.setText(scorelist[1] +"점");
            score3.setText(scorelist[2] +"점");
            score4.setText(scorelist[3] +"점");
            score5.setText(scorelist[4] +"점");
            score6.setText(scorelist[5] +"점");
            score7.setText(scorelist[6] +"점");
            goodDay.setText("이번 주 세차하기 좋은 날은 "+getDate(maxScoreDay)+"일 입니다");

            switch (maxScoreDay){
                case 0:
                    date1.setBackgroundResource(R.drawable.home_daybox_color);
                    date1.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score1.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 1:
                    date2.setBackgroundResource(R.drawable.home_daybox_color);
                    date2.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score2.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 2:
                    date3.setBackgroundResource(R.drawable.home_daybox_color);
                    date3.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score3.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 3:
                    date4.setBackgroundResource(R.drawable.home_daybox_color);
                    date4.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score4.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 4:
                    date5.setBackgroundResource(R.drawable.home_daybox_color);
                    date5.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score5.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 5:
                    date6.setBackgroundResource(R.drawable.home_daybox_color);
                    date6.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score6.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
                case 6:
                    date7.setBackgroundResource(R.drawable.home_daybox_color);
                    date7.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_white));
                    score7.setTextColor(ContextCompat.getColor(getActivity().getApplicationContext(), R.color.color_main));
                    break;
            }
        }

    }

    private class PopupHandler extends Handler{
        public void handleMessage(Message msg){
                switch(msg.what){
                    case MSG_POPUP_CHANGED:
                        Toast.makeText(getActivity(), "popup_changed", Toast.LENGTH_LONG);
                        //메시지 처리할 코드
                        break;
                }
            }
        }
}
