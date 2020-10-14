package com.kokonut.NCNC.Home.Tab2;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kokonut.NCNC.R;


public class Tab2ForTest extends AppCompatActivity {
    protected LinearLayout llInfoBox1, llInfoBox2, llInfoBox3, llInfoBox4;
    protected ImageView imageView1, imageView2, imageView3, imageView4;
    protected TextView name1, name2, name3, name4;
    protected RatingBar rbBoxStar1, rbBoxStar2, rbBoxStar3, rbBoxStar4;
    protected TextView tvBoxScore1, tvBoxScore2, tvBoxScore3, tvBoxScore4;
    protected TextView wash1, wash2, wash3, wash4;
    protected TextView time1, time2, time3, time4;
    protected ImageView ivBackArrow;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();

        setContentView(R.layout.tab2_for_test);
        initView();

        ivBackArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d("state", "---------- OnStart--------");
    }

    @Override
    public void onStop() {
        Log.d("state", "---------- OnStop--------");
        super.onStop();
    }



    protected void onPause() {
        Log.d("state", "---------- OnPause--------");
        super.onPause();
        finish();
    }

    @Override
    public void onResume() {
        Log.d("state", "---------- OnResume--------");
        super.onResume();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onDestroy() {
        Log.d("state", "---------- OnDestroy--------");
        super.onDestroy();
    }


    void initView(){

        ivBackArrow = findViewById(R.id.tab2_searchlist_back_arrow);

        llInfoBox1 = findViewById(R.id.carwashlist_layout);
        imageView1 = findViewById(R.id.carwashlist_image);
        name1 = findViewById(R.id.carwashlist_name);
        rbBoxStar1 = findViewById(R.id.carwashlist_rating_bar);
        tvBoxScore1 = findViewById(R.id.carwashlist_score);
        wash1 = findViewById(R.id.carwashlist_wash);
        time1 = findViewById(R.id.carwashlist_time);

        llInfoBox2 = findViewById(R.id.carwashlist_layout);
        imageView2 = findViewById(R.id.carwashlist_image);
        name2 = findViewById(R.id.carwashlist_name);
        rbBoxStar2 = findViewById(R.id.carwashlist_rating_bar);
        tvBoxScore2 = findViewById(R.id.carwashlist_score);
        wash2 = findViewById(R.id.carwashlist_wash);
        time2 = findViewById(R.id.carwashlist_time);

        llInfoBox3 = findViewById(R.id.carwashlist_layout);
        imageView3 = findViewById(R.id.carwashlist_image);
        name3 = findViewById(R.id.carwashlist_name);
        rbBoxStar3 = findViewById(R.id.carwashlist_rating_bar);
        tvBoxScore3 = findViewById(R.id.carwashlist_score);
        wash3 = findViewById(R.id.carwashlist_wash);
        time3 = findViewById(R.id.carwashlist_time);

        llInfoBox4 = findViewById(R.id.carwashlist_layout);
        imageView4 = findViewById(R.id.carwashlist_image);
        name4 = findViewById(R.id.carwashlist_name);
        rbBoxStar4 = findViewById(R.id.carwashlist_rating_bar);
        tvBoxScore4 = findViewById(R.id.carwashlist_score);
        wash4 = findViewById(R.id.carwashlist_wash);
        time4 = findViewById(R.id.carwashlist_time);
    }

}
