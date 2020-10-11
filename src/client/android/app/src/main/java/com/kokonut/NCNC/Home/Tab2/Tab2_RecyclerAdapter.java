package com.kokonut.NCNC.Home.Tab2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Home.Tab1.ItemViewHolder;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab2_RecyclerAdapter extends RecyclerView.Adapter<Tab2_ItemViewHolder> {

    private ArrayList<CarWashInfoData> mydatalist = null;
    public Tab2_RecyclerAdapter(ArrayList<CarWashInfoData> datalist){
        this.mydatalist = datalist;
    }


    @NonNull
    @Override
    public Tab2_ItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_searchlist, null);

        Tab2_ItemViewHolder itemViewHolder = new Tab2_ItemViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Tab2_ItemViewHolder viewholder, int position) {
        viewholder.name.setText(mydatalist.get(position).getName());
        viewholder.address.setText(mydatalist.get(position).getAddress());
        viewholder.wash.setText(mydatalist.get(position).getWash());
        viewholder.time1.setText(mydatalist.get(position).getOpenSat());
        viewholder.time2.setText(mydatalist.get(position).getOpenSun());
        viewholder.time3.setText(mydatalist.get(position).getOpenWeek());
    }

    @Override
    public int getItemCount() {
        //adapter가 관리하는 전체 데이터 개수
        return (mydatalist==null) ? 0 : mydatalist.size();
    }
}
