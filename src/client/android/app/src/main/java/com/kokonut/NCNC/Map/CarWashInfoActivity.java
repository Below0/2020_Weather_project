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
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CarWashInfoActivity extends AppCompatActivity {
    ImageView ivBack, ivOnButton, ivOnButton2, ivOnButton3, ivReview;
    TextView tvReviewType, tvReviewName, tvReviewAddress, tvReviewTime1, tvReviewTime2, tvReviewScore,
            tvReviewTime3, tvReviewPrice1, tvReviewPrice2, tvReviewEvent, tvReviewFacilities;

    RatingBar rbReviewScore;
    String score, typeStr;


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
        String latitude = intent.getExtras().getString("latitude");
        String longitude = intent.getExtras().getString("longitude");
        String address = intent.getExtras().getString("address");
        String phone = intent.getExtras().getString("phone");
        String city = intent.getExtras().getString("city");
//        String type = intent.getExtras().getString("type");
        String open_week = intent.getExtras().getString("open_week");
        String open_sat = intent.getExtras().getString("open_sat");
        String open_sun = intent.getExtras().getString("open_sun");

        System.out.println("CarWashInfoActivity + " + id);
        fetchCarWash(id);

//        tvReviewType.setText(type);
        tvReviewName.setText(name);
        tvReviewAddress.setText(address);
        tvReviewTime1.setText(open_week);
        tvReviewTime2.setText(open_sat);
        tvReviewTime3.setText(open_sun);
        tvReviewPrice1.setText(open_sun);
        tvReviewPrice2.setText(longitude);
        tvReviewEvent.setText(phone);
        tvReviewFacilities.setText(city);


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
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                intent.putExtra("phone",phone);
                intent.putExtra("city", city);
//                intent.putExtra("type", type);
                intent.putExtra("open_week", open_week);
                intent.putExtra("open_sat", open_sat);
                intent.putExtra("open_sun", open_sun);
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
        tvReviewEvent = findViewById(R.id.car_wash_info_event);
        tvReviewFacilities = findViewById(R.id.car_wash_info_facilities);

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
                                float updated_score = (float) carWashDetail.getScore();
                                score = Float.toString(updated_score);

                                typeStr = carWashDetail.getType().get(0).getName();
                                System.out.println("첫번째 typeStr : " + typeStr);
                                if(carWashDetail.getType().size() > 1){
                                    for(int j = 1; j < carWashDetail.getType().size(); j++) {
                                        typeStr = typeStr + ", " + carWashDetail.getType().get(j).getName();
                                    }
                                }

                                rbReviewScore.setRating(Float.parseFloat(score));
                                tvReviewScore.setText(score);
                                tvReviewType.setText(typeStr);
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
}
