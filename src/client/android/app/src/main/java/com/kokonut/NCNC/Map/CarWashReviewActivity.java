package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewContents;
import com.kokonut.NCNC.Retrofit.ReviewResponse;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.kokonut.NCNC.KakaoAdapter.getInstance;

public class CarWashReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivWriteReview;
    TextView tvCarWashName;

    ReviewContents reviewContents; // 리뷰 콘텐츠가 저장되는 리스트
    List<ReviewContents.Content> reviewList;
    private ReviewAdapter mAdapter;
    private LinearLayoutManager mLinearLayoutManager;
    private RecyclerView mRecyclerView;

    String baseUrl = "http://3.131.33.128:8000/";
    Retrofit retrofit;


    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Intent 수신
        Intent intent = getIntent();

        String id = intent.getExtras().getString("id");
        String name = intent.getExtras().getString("name");
        String latitude = intent.getExtras().getString("latitude");
        String longitude = intent.getExtras().getString("longitude");
        String address = intent.getExtras().getString("address");
        String phone = intent.getExtras().getString("phone");
        String city = intent.getExtras().getString("city");
        String type = intent.getExtras().getString("type");
        String open_week = intent.getExtras().getString("open_week");
        String open_sat = intent.getExtras().getString("open_sat");
        String open_sun = intent.getExtras().getString("open_sun");

        setContentView(R.layout.activity_car_wash_review);
        initView();
        tvCarWashName.setText(name);

        // 이 안에 washer의 id를 넣으면 해당 세차장의 리뷰들을 가져옵니다.
        fetchReview(id);
        System.out.println("세차장 id :   " + id);

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashInfoActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                intent.putExtra("phone",phone);
                intent.putExtra("city", city);
                intent.putExtra("type", type);
                intent.putExtra("open_week", open_week);
                intent.putExtra("open_sat", open_sat);
                intent.putExtra("open_sun", open_sun);

                startActivityForResult(intent, sub);
            }
        });

        ivWriteReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!KakaoAdapter.getInstance().isLogin()){
                    Toast.makeText(getApplicationContext(),"로그인이 필요합니다.",Toast.LENGTH_SHORT).show();
                    return;
                }

                Intent intent = new Intent(getApplicationContext(), WriteReviewActivity.class);
                intent.putExtra("id", id);
                intent.putExtra("name", name);
                intent.putExtra("latitude", latitude);
                intent.putExtra("longitude", longitude);
                intent.putExtra("address", address);
                intent.putExtra("phone",phone);
                intent.putExtra("city", city);
                intent.putExtra("type", type);
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
        Log.d("state", "----------CarWashReviewActivity OnStart--------");
    }

    @Override
    public void onStop() {
        Log.d("state", "----------CarWashReviewActivity OnStop--------");
        super.onStop();
    }



    protected void onPause() {
        Log.d("state", "----------CarWashReviewActivity OnPause--------");
        super.onPause();
        finish();
    }

    @Override
    public void onResume() {
        Log.d("state", "----------CarWashReviewActivity OnResume--------");
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d("state", "----------CarWashReviewActivity OnDestroy--------");
        super.onDestroy();
    }


    void initView(){
        ivBack = findViewById(R.id.car_wash_review_back_arrow);
        ivWriteReview = findViewById(R.id.car_wash_review_write_button);
        mRecyclerView = findViewById(R.id.recycler_view_review);
        tvCarWashName = findViewById(R.id.car_wash_review_name);
    }


    public void fetchReview(String washerId){
        RetrofitAPI retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);

        retrofitAPI.fetchReview(washerId).enqueue(new Callback<ReviewContents>() {
            @Override
            public void onResponse(Call<ReviewContents> call, Response<ReviewContents> response) {
                Log.d("fetch_review", "Success: "+new Gson().toJson(response.body()));
                reviewContents = response.body();
                reviewList = reviewContents.getReviews();

                mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view_review);
                mLinearLayoutManager = new LinearLayoutManager(getApplicationContext());
                mRecyclerView.setLayoutManager(mLinearLayoutManager);
                mAdapter = new ReviewAdapter(reviewList);
                mRecyclerView.setAdapter(mAdapter);
                ReviewRecyclerDecoration reviewRecyclerDecoration = new ReviewRecyclerDecoration(10);
                mRecyclerView.addItemDecoration(reviewRecyclerDecoration);

            }

            @Override
            public void onFailure(Call<ReviewContents> call, Throwable t) {
                Log.e("fetch_review", "failure: "+t.toString());
            }
        });
    }
}