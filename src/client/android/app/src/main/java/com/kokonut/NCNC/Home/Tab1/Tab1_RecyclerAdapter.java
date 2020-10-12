package com.kokonut.NCNC.Home.Tab1;

import android.content.Context;
import android.content.Intent;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Map.CarWashInfoActivity;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.CarWashContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tab1_RecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private List<CarWashContents> carWashContentsList = null;
    private String washType;

    public Tab1_RecyclerAdapter(List<CarWashContents> carWashContentsList){
        this.carWashContentsList = carWashContentsList;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carwashlist, null);

        ItemViewHolder itemViewHolder = new ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder viewholder, int position) {
        washType = carWashContentsList.get(position).getWash().get(0);
        if(carWashContentsList.get(position).getWash().size() > 1){
            for(int j = 1; j < carWashContentsList.get(position).getWash().size(); j++) {
                washType = washType + ", " + carWashContentsList.get(position).getWash().get(j);
            }
        }
        viewholder.name.setText(carWashContentsList.get(position).getName());
        viewholder.rbBoxStar.setRating(carWashContentsList.get(position).getScore());
        viewholder.tvBoxScore.setText(Float.toString(carWashContentsList.get(position).getScore()));
        viewholder.wash.setText(washType);
        viewholder.time.setText(carWashContentsList.get(position).getOpenWeek());

        viewholder.llInfoBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), CarWashInfoActivity.class);

//                                Intent intent = new Intent(getContext(), CarWashInfoActivity.class);
                intent.putExtra("id", Integer.toString(carWashContentsList.get(position).getId()));
                intent.putExtra("name", carWashContentsList.get(position).getName());

                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        //adapter가 관리하는 전체 데이터 개수
        return (carWashContentsList==null) ? 0 : carWashContentsList.size();
    }
}
