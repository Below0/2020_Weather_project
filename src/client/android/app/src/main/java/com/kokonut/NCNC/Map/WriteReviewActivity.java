package com.kokonut.NCNC.Map;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.Retrofit.CarWashDetail;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;
import com.kokonut.NCNC.Retrofit.ReviewResponse;
import com.kokonut.NCNC.KakaoAdapter;
import com.kokonut.NCNC.Map.CarWashReviewActivity;
import com.kokonut.NCNC.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WriteReviewActivity extends AppCompatActivity {
    ImageView ivBack, ivCommitReview;
    RatingBar ratingBar;
    EditText reviewContent;

    public static final int sub = 1001;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String washer_id = intent.getExtras().getString("id");
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

        setContentView(R.layout.activity_write_review);
        initView();

        ratingBar.setOnRatingBarChangeListener(new Listener());
        System.out.println("WriteReviewActivity에서 washer_id는" + washer_id);
        System.out.println(Long.toString(KakaoAdapter.getInstance().getUser().getId()));


        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                intent.putExtra("id", washer_id);
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

        ivCommitReview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), CarWashReviewActivity.class);
                String review_score = Integer.toString((int)ratingBar.getRating());
                System.out.println("사용자가 선택한 별점은 : " + review_score);
                writeReview(washer_id, reviewContent.getText().toString(), review_score);
                System.out.println("ratingBar값은    :  " + ratingBar.getRating());
                intent.putExtra("id", washer_id);
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
        Log.d("state", "----------WriteReviewActivity OnStart--------");
    }

    @Override
    public void onStop() {
        Log.d("state", "----------WriteReviewActivity OnStop--------");
        super.onStop();
    }



    protected void onPause() {
        Log.d("state", "----------WriteReviewActivity OnPause--------");
        super.onPause();
        finish();
    }

    @Override
    public void onResume() {
        Log.d("state", "----------WriteReviewActivity OnResume--------");
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d("state", "----------WriteReviewActivity OnDestroy--------");
        super.onDestroy();
    }


    void initView(){
        ivBack = findViewById(R.id.write_review_back_arrow);
        ivCommitReview = findViewById(R.id.write_review_commit_button);
        ratingBar = findViewById(R.id.write_review_rating_bar);
        reviewContent = findViewById(R.id.write_review_content);
    }

    public void writeReview(String washerId, String content, String score) {
        if (!KakaoAdapter.getInstance().isLogin()) {
            Toast.makeText(getApplicationContext(), "로그인이 필요합니다.", Toast.LENGTH_SHORT).show();
            return;
        }

        RetrofitAPI retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
        String id = Long.toString(KakaoAdapter.getInstance().getUser().getId());
        HashMap<String,String> param = new HashMap<>();
        param.put("id",id);
        param.put("content",content);
        param.put("score", score);

        retrofitAPI.writeReview(washerId, param).enqueue(new Callback<ReviewResponse>() {
            @Override
            public void onResponse(Call<ReviewResponse> call, Response<ReviewResponse> response) {
                Log.d("user_check", "Success: "+new Gson().toJson(response.body().getStatus()));

            }

            @Override
            public void onFailure(Call<ReviewResponse> call, Throwable t) {
                Log.e("user_check", "failure: "+t.toString());
            }
        });
    }

    class Listener implements RatingBar.OnRatingBarChangeListener
    {
        @Override
        public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
            ratingBar.setRating(rating);
        }
    }
}