package com.kokonut.NCNC.Home.Tab2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Home.Tab1.Tab1_RecyclerAdapter;
import com.kokonut.NCNC.Home.Tab1.Tab1_RecyclerAdapter_Horizontal;
import com.kokonut.NCNC.Map.CarWashInfoActivity;
import com.kokonut.NCNC.Map.ReviewAdapter;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.Retrofit.RetrofitAPI;
import com.kokonut.NCNC.Retrofit.RetrofitClient;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Tab2_SearchList extends AppCompatActivity {
    private RetrofitAPI retrofitAPI;

    ImageButton tab2_prevButton;
    TextView emptyText;

    RecyclerView recyclerView;
    Tab2_RecyclerAdapter tab2_recyclerAdapter;
    public ArrayList<SelectedSearchInfo> selectedSearchInfo; //검색 조건 - 받아온거
    //private ArrayList<CarWashInfoData> condition; //검색 조건 - carwashinfodata 형식으로 바꾼거
//    private ArrayList<CarWashInfoData> datalist;
    private List<CarWashContents> carWashContentsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tab2__search_list);

        recyclerView = findViewById(R.id.recycler_view_searchlist);
        //emptyText = findViewById(R.id.emptyText);

        Intent intent = getIntent();
        selectedSearchInfo = (ArrayList<SelectedSearchInfo>) intent.getSerializableExtra("selectedinfodata");
        if(selectedSearchInfo!=null){
            Log.d("selectedinfodata", "조건 받아옴");

            //조건에 맞는 세차장 리스트 (서버 통신)
            retrofitAPI = RetrofitClient.getInstance().getClient1().create(RetrofitAPI.class);
            switch (selectedSearchInfo.get(0).getDay()){
                case "평일":
                    if(selectedSearchInfo.get(0).getKind().toString() == "선택안함"){
                        retrofitAPI.SearchCarWash_week_nokind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
//                                 List<CarWashContents> result = response.body();
//                                 datalist = new ArrayList<>();
//                                 for(int i=0; i<result.size(); i++){
//                                     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
//                                 }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }
                    else {
                        retrofitAPI.SearchCarWash_week_kind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime(),selectedSearchInfo.get(0).getKind()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }

                case "토요일":
                    if(selectedSearchInfo.get(0).getKind().toString() == "선택안함"){
                        retrofitAPI.SearchCarWash_sat_nokind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }

//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }
                    else {
                        retrofitAPI.SearchCarWash_sat_kind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime(),selectedSearchInfo.get(0).getKind()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }

                case "일요일":
                    if(selectedSearchInfo.get(0).getKind().toString() == "선택안함"){
                        retrofitAPI.SearchCarWash_sun_nokind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }
                    else {
                        retrofitAPI.SearchCarWash_sun_kind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getTime(),selectedSearchInfo.get(0).getKind()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }

                case "선택안함":
                    if(selectedSearchInfo.get(0).getKind().toString() == "선택안함"){
                        retrofitAPI.SearchCarWash_onlyaddress(selectedSearchInfo.get(0).getDong()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
//                                List<CarWashContents> result = response.body();

                                carWashContentsList = response.body();

                                if(carWashContentsList.size()<1)
                                    emptyText.setVisibility(View.VISIBLE);


                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);

                                tab2_recyclerAdapter.setOnItemClickListener(new Tab2_RecyclerAdapter.OnItemClickListener() {
                                    @Override
                                    public void onItemClick(View v, int pos) {
                                        Intent intent = new Intent(getApplicationContext(), CarWashInfoActivity.class);
                                        intent.putExtra("carwashinfoactivity", tab2_recyclerAdapter.getItemId(pos));
                                        startActivity(intent);
                                    }
                                });
                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }
                    else {
                        retrofitAPI.SearchCarWash_notime_kind(selectedSearchInfo.get(0).getDong(),
                                selectedSearchInfo.get(0).getKind()).enqueue(new Callback<List<CarWashContents>>() {
                            @Override
                            public void onResponse(Call<List<CarWashContents>> call, Response<List<CarWashContents>> response) {
                                // List<CarWashContents> result = response.body();
                                // datalist = new ArrayList<>();
                                // for(int i=0; i<result.size(); i++){
                                //     datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
                                //             result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
                                //             result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString()));
                                // }
//                                List<CarWashContents> result = response.body();
//                                datalist = new ArrayList<>();
//                                for(int i=0; i<result.size(); i++){
//                                    datalist.add(new CarWashInfoData(result.get(i).getId(), result.get(i).getName(), result.get(i).getAddress(), result.get(i).getPhone(),
//                                            result.get(i).getCity(), result.get(i).getDistrict(), result.get(i).getDong(), result.get(i).getOpenSat(),
//                                            result.get(i).getOpenSun(), result.get(i).getOpenWeek(), Double.valueOf(i), result.get(i).getWash().toString(),
//                                            result.get(i).getScore()));
//                                }

                                carWashContentsList = response.body();

                                recyclerView.setHasFixedSize(true);
                                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
                                recyclerView.setLayoutManager(linearLayoutManager);
                                tab2_recyclerAdapter = new Tab2_RecyclerAdapter(carWashContentsList);
                                recyclerView.setAdapter(tab2_recyclerAdapter);


                            }
                            @Override
                            public void onFailure(Call<List<CarWashContents>> call, Throwable t) {
                            }
                        });
                    }
            }
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