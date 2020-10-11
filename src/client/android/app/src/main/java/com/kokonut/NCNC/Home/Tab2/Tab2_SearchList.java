package com.kokonut.NCNC.Home.Tab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Home.Tab1.Tab1_RecyclerAdapter;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;

import java.util.ArrayList;
import java.util.Collections;

public class Tab2_SearchList extends AppCompatActivity {
    private RetrofitAPI retrofitAPI;

    ImageButton tab2_prevButton;

    RecyclerView recyclerView;
    Tab2_RecyclerAdapter tab2_recyclerAdapter;
    public ArrayList<SelectedSearchInfo> selectedSearchInfo; //검색 조건 - 받아온거
    //private ArrayList<CarWashInfoData> condition; //검색 조건 - carwashinfodata 형식으로 바꾼거
    private ArrayList<CarWashInfoData> datalist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2__search_list);

        Intent intent = getIntent();
        selectedSearchInfo = (ArrayList<SelectedSearchInfo>) intent.getSerializableExtra("selectedinfodata");
        if(selectedSearchInfo!=null){
            Log.d("selectedinfodata", "조건 받아옴");


            //조건에 맞는 세차장 리스트 (서버 통신)
            retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);


        }
        else
            Log.d("selectedinfodata", "실패");



        //뒤로버튼 클릭시
        tab2_prevButton = findViewById(R.id.tab2_searchlist_back_arrow);
        tab2_prevButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}