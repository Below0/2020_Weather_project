package com.kokonut.NCNC.Home.Tab2;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Home.Tab1.Tab1_RecyclerAdapter_Horizontal;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab2_RecyclerAdapter extends RecyclerView.Adapter<Tab2_RecyclerAdapter.CustomViewHolder> {

    private ArrayList<CarWashInfoData> mydatalist = null;
    private Activity context = null;
    private Tab2_RecyclerAdapter.OnItemClickListener mListener = null;

    public Tab2_RecyclerAdapter(ArrayList<CarWashInfoData> datalist){
        this.mydatalist = datalist;
    }

    public interface OnItemClickListener{
        void onItemClick(View v, int pos);
    }

    public void setOnItemClickListener(Tab2_RecyclerAdapter.OnItemClickListener listener){
        //this.context = context;
        this.mListener = listener;
    }

    @NonNull
    @Override
    public Tab2_RecyclerAdapter.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_searchlist, null);
        Tab2_RecyclerAdapter.CustomViewHolder itemViewHolder = new Tab2_RecyclerAdapter.CustomViewHolder(view);

        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Tab2_RecyclerAdapter.CustomViewHolder viewholder, int position) {
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

    public Activity getContext(){
        return context;
    }

    public void setContext(Activity context){
        this.context = context;
    }


    public class CustomViewHolder extends RecyclerView.ViewHolder{
        ImageView imageView;
        TextView name;
        TextView address;
        TextView wash;
        TextView time1, time2, time3;

        public CustomViewHolder(@NonNull View itemView) {
            super(itemView);

            this.imageView = itemView.findViewById(R.id.searchlist_image);
            this.name = itemView.findViewById(R.id.searchlist_name);
            this.address = itemView.findViewById(R.id.searchlist_address);
            this.wash = itemView.findViewById(R.id.searchlist_wash);
            this.time1 = itemView.findViewById(R.id.searchlist_time1);
            this.time2 = itemView.findViewById(R.id.searchlist_time2);
            this.time3 = itemView.findViewById(R.id.searchlist_time3);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition(); //아이템 위치(position)
                    if(pos != RecyclerView.NO_POSITION){
                        //데이터 리스트로부터 아이템 데이터 참조
                        //리스너 객체의 메서드 호출
                        if(mListener != null){
                            mListener.onItemClick(view, pos);
                        }

                    }
                }
            });
        }
    }
}
