package com.kokonut.NCNC.Home.Tab1;

import android.content.Context;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.appcompat.view.menu.MenuView;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab1_RecyclerAdapter extends RecyclerView.Adapter<ItemViewHolder>{

    private ArrayList<CarWashInfoData> mydatalist = null;
    public Tab1_RecyclerAdapter(ArrayList<CarWashInfoData> datalist){
        this.mydatalist = datalist;
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
        //viewholder.imageView.setImageResource(R.drawable.box_image);
        viewholder.name.setText(mydatalist.get(position).getName());
        viewholder.address.setText(mydatalist.get(position).getAddress());

        if(mydatalist.get(position).getOpenWeek().equals("99:99-99:99")) viewholder.time3.setText("평일 : 휴무");
        else if(mydatalist.get(position).getOpenWeek().equals("00:00-24:00")) viewholder.time3.setText("평일 : 24시간 운영");
        else viewholder.time3.setText("평일 : "+mydatalist.get(position).getOpenWeek());
        if(mydatalist.get(position).getOpenSat().equals("99:99-99:99")) viewholder.time1.setText("토 : 휴무");
        else if(mydatalist.get(position).getOpenSat().equals("00:00-24:00")) viewholder.time1.setText("토 : 24시간 운영");
        else viewholder.time1.setText("토 : "+mydatalist.get(position).getOpenSat());
        if(mydatalist.get(position).getOpenSun().equals("99:99-99:99")) viewholder.time2.setText("일 : 휴무");
        else if(mydatalist.get(position).getOpenSun().equals("00:00-24:00")) viewholder.time2.setText("일 : 24시간 운영");
        else viewholder.time2.setText("일 : "+mydatalist.get(position).getOpenSun());

        viewholder.wash.setText(stringReplace(mydatalist.get(position).getWash()));
    }

    @Override
    public int getItemCount() {
        //adapter가 관리하는 전체 데이터 개수
        return (mydatalist==null) ? 0 : mydatalist.size();
    }

    public static String stringReplace(String str){
        str = str.replaceAll("\\[", "");
        str = str.replaceAll("\\]", "");
        return str;
    }
}
