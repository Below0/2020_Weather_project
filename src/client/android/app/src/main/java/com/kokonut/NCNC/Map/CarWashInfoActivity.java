package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kokonut.NCNC.MainActivity;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.Retrofit.CarWashDetail;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewContents;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarWashInfoActivity extends AppCompatActivity {
    ImageView ivBack, ivOnButton, ivOnButton2, ivOnButton3, ivReview;
    TextView tvReviewType, tvReviewScore, tvReviewName, tvReviewAddress, tvReviewTime1, tvReviewTime2,
            tvReviewTime3, tvReviewPrice1, tvReviewPrice2, tvReviewEvent1, tvReviewEvent2,
            tvReviewFacilities1, tvReviewFacilities2, tvReviewFacilities3;

    RatingBar rbReviewScore;
    String typeStr;
    float score;

    public static final int sub = 1001;
    MapFragment mapFragment = new MapFragment();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_wash_info);
        initView();

        Intent intent = getIntent();

        String id = intent.getExtras().getString("id");
        String name = intent.getExtras().getString("name");
//        String latitude = intent.getExtras().getString("latitude");
//        String longitude = intent.getExtras().getString("longitude");
//        String address = intent.getExtras().getString("address");
//        String phone = intent.getExtras().getString("phone");
//        String city = intent.getExtras().getString("city");
//        String type = intent.getExtras().getString("type");
//        String open_week = intent.getExtras().getString("open_week");
//        String open_sat = intent.getExtras().getString("open_sat");
//        String open_sun = intent.getExtras().getString("open_sun");

        System.out.println("CarWashInfoActivity + " + id);
        fetchCarWash(id);


//        tvReviewType.setText(type);
//        tvReviewName.setText(name);
//        tvReviewAddress.setText(address);
//        tvReviewTime1.setText(open_week);
//        tvReviewTime2.setText(open_sat);
//        tvReviewTime3.setText(open_sun);
//        tvReviewPrice1.setText(open_sun);
//        tvReviewPrice2.setText(longitude);
//        tvReviewEvent1.setText(phone);
//        tvReviewFacilities.setText(city);


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                getSupportFragmentManager().beginTransaction().replace(R.id.layout_car_wash_info, mapFragment).commitAllowingStateLoss();
                finish();
            }
        });

        ivReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
//                intent.putExtra("latitude", latitude);
//                intent.putExtra("longitude", longitude);
//                intent.putExtra("address", address);
//                intent.putExtra("phone",phone);
//                intent.putExtra("city", city);
//                intent.putExtra("type", type);
//                intent.putExtra("open_week", open_week);
//                intent.putExtra("open_sat", open_sat);
//                intent.putExtra("open_sun", open_sun);
                startActivityForResult(intent, sub);
            }
        });

    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("state", "----------CarWashInfoActivity OnStart--------");
    }

    @Override
    public void onStop() {
        Log.d("state", "----------CarWashInfoActivity OnStop--------");
        super.onStop();
    }



    protected void onPause() {
        Log.d("state", "----------CarWashInfoActivity OnPause--------");
        super.onPause();
        finish();
    }

    @Override
    public void onResume() {
        Log.d("state", "----------CarWashInfoActivity OnResume--------");
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d("state", "----------CarWashInfoActivity OnDestroy--------");
        super.onDestroy();
    }

    void initView(){
        ivBack = findViewById(R.id.car_wash_info_back_arrow);
        ivReview = findViewById(R.id.car_wash_info_review_box);

        tvReviewScore = findViewById(R.id.tv_car_wash_info_score);
        rbReviewScore = findViewById(R.id.car_wash_info_rating_bar);

        tvReviewType = findViewById(R.id.car_wash_info_type);
        tvReviewName = findViewById(R.id.car_wash_info_name);
        tvReviewAddress = findViewById(R.id.car_wash_info_address);
        tvReviewTime1 = findViewById(R.id.car_wash_info_time_1);
        tvReviewTime2 = findViewById(R.id.car_wash_info_time_2);
        tvReviewTime3 = findViewById(R.id.car_wash_info_time_3);
        tvReviewPrice1 = findViewById(R.id.car_wash_info_price_1);
        tvReviewPrice2 = findViewById(R.id.car_wash_info_price_2);
        tvReviewEvent1 = findViewById(R.id.car_wash_info_event_1);
        tvReviewEvent2 = findViewById(R.id.car_wash_info_event_2);
        tvReviewFacilities1 = findViewById(R.id.car_wash_info_facilities_1);
        tvReviewFacilities2 = findViewById(R.id.car_wash_info_facilities_2);
        tvReviewFacilities3 = findViewById(R.id.car_wash_info_facilities_3);

    }

    public void fetchCarWash(String washerId){
        RetrofitAPI retrofitAPI = RetrofitAPI.retrofit.create(RetrofitAPI.class);
        RetrofitAPI retrofitAPI2 = RetrofitAPI.retrofit.create(RetrofitAPI.class);

        retrofitAPI.fetchCarWash().enqueue(new Callback<List<CarWashContents>>() {
            @Override
            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                Log.d("fetch_review", "Success: "+new Gson().toJson(response.body()));
                List<CarWashContents> carWashContentsList = response.body();

                for (CarWashContents carWashContents : carWashContentsList) {
                    System.out.println("washerId : " + washerId);
                    if (carWashContents.getId() == Integer.parseInt(washerId)) {
                        retrofitAPI2.getCarWashType(carWashContents.getId()).enqueue(new Callback<CarWashDetail>() {
                            public void onResponse(Call<CarWashDetail> call, Response<CarWashDetail> response) {
                                Log.d("fetch_review", "Success: " + new Gson().toJson(response.body()));
                                CarWashDetail carWashDetail = response.body();
                                score = (float) carWashDetail.getScore();

                                typeStr = carWashDetail.getType().get(0).getName();
                                System.out.println("첫번째 typeStr : " + typeStr);
                                if(carWashDetail.getType().size() > 1){
                                    for(int j = 1; j < carWashDetail.getType().size(); j++) {
                                        typeStr = typeStr + ", " + carWashDetail.getType().get(j).getName();
                                    }
                                }

                                setInfo(carWashContents, score, typeStr);
                            }

                            @Override
                            public void onFailure(Call<CarWashDetail> call, Throwable t) {
                                Log.e("fetch_review", "failure: "+t.toString());
                            }
                        });
                    }
                }
            }

            @Override
            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                Log.e("fetch_review", "failure: "+t.toString());
            }
        });
    }

    public void setInfo(CarWashContents carWashContents, float score, String typeStr){
        final String[] eventStrList = {"30% 세일 행사", "카샴푸 20% 세일 행사", "첫 방문고객 무료 자동세차",
                                        "10회 이용 시 1회 무료 이용권 증정", "자동 세차 15% 할인"};
        final String[] priceStrList1 = {"12000원", "13000원", "14000원", "15000원", "18000원"};
        final String[] priceStrList2 = {"20000원", "21000원", "22000원", "24000원", "28000원"};
        final String[] facStrList = {"카샴푸 구비", "셀프 세차 부스 6개", "폼건 사용 가능", "개인 용품 사용 가능",
                                    "버핑 타올, 드라이 타올 구비"};


        String open_week = carWashContents.getOpenWeek();
        String open_sat = carWashContents.getOpenSat();
        String open_sun = carWashContents.getOpenSun();
        int randVal = (int)(Math.random() * 5);


        if(open_week.equals("99:99-99:99")){
            tvReviewTime1.setText("휴무");
        }
        else{
            tvReviewTime1.setText(open_week);
        }

        if(open_sat.equals("99:99-99:99")){
            tvReviewTime2.setText("휴무");
        }
        else{
            tvReviewTime2.setText(open_sat);
        }


        if(open_sun.equals("99:99-99:99")){
            tvReviewTime3.setText("휴무");
        }
        else{
            tvReviewTime3.setText(open_sun);
        }

        tvReviewName.setText(carWashContents.getName());
        tvReviewAddress.setText(carWashContents.getAddress());
        tvReviewPrice1.setText(priceStrList1[randVal]);
        tvReviewPrice2.setText(priceStrList2[randVal]);
        tvReviewEvent1.setText(eventStrList[randVal]);
        tvReviewEvent2.setText(eventStrList[(randVal + 1) % 5]);
        tvReviewFacilities1.setText(facStrList[randVal]);
        tvReviewFacilities2.setText(facStrList[(randVal + 1) % 5]);
        tvReviewFacilities3.setText(facStrList[(randVal + 2) % 5]);

        rbReviewScore.setRating(score);
        tvReviewScore.setText(Float.toString(score));
        tvReviewType.setText(typeStr);
    }
}
