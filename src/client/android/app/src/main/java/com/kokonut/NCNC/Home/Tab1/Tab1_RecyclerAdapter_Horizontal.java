package com.kokonut.NCNC.Home.Tab1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.R;

import java.util.ArrayList;

public class Tab1_RecyclerAdapter_Horizontal extends RecyclerView.Adapter<Tab1_RecyclerAdapter_Horizontal.CustomViewHolder> {

        private ArrayList<CarWashInfoData> mydatalist1 = null;
        private Activity context = null;
        private Tab1_RecyclerAdapter_Horizontal.OnItemClickListener mListener = null;

        public Tab1_RecyclerAdapter_Horizontal(ArrayList<CarWashInfoData> datalist1){

                this.mydatalist1 = datalist1;
        }

        public interface OnItemClickListener{
                void onItemClick(View v, int pos);
        }

        public void setOnItemClickListener(OnItemClickListener listener){
                this.mListener = listener;
        }

        @NonNull
        @Override
        public Tab1_RecyclerAdapter_Horizontal.CustomViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

                View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_carwashlist2, null);
                Tab1_RecyclerAdapter_Horizontal.CustomViewHolder viewHolder = new Tab1_RecyclerAdapter_Horizontal.CustomViewHolder(view);

                return viewHolder;
        }


        @Override
        public void onBindViewHolder(@NonNull Tab1_RecyclerAdapter_Horizontal.CustomViewHolder viewholder, int position) {

                viewholder.name.setText(mydatalist1.get(position).getName());
                viewholder.address.setText(mydatalist1.get(position).getAddress());

                if(mydatalist1.get(position).getOpenWeek().equals("99:99-99:99")) viewholder.time3.setText("평일 : 휴무");
                else if(mydatalist1.get(position).getOpenWeek().equals("00:00-24:00")) viewholder.time3.setText("평일 : 24시간 운영");
                else viewholder.time3.setText("평일 : "+mydatalist1.get(position).getOpenWeek());
                if(mydatalist1.get(position).getOpenSat().equals("99:99-99:99")) viewholder.time1.setText("토 : 휴무");
                else if(mydatalist1.get(position).getOpenSat().equals("00:00-24:00")) viewholder.time1.setText("토 : 24시간 운영");
                else viewholder.time1.setText("토 : "+mydatalist1.get(position).getOpenSat());
                if(mydatalist1.get(position).getOpenSun().equals("99:99-99:99")) viewholder.time2.setText("일 : 휴무");
                else if(mydatalist1.get(position).getOpenSun().equals("00:00-24:00")) viewholder.time2.setText("일 : 24시간 운영");
                else viewholder.time2.setText("일 : "+mydatalist1.get(position).getOpenSun());

                viewholder.wash.setText(stringReplace(mydatalist1.get(position).getWash()));

        }

        @Override
        public int getItemCount() {
                //adapter가 관리하는 전체 데이터 개수
                return (mydatalist1==null) ? 0 : mydatalist1.size();
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

        class CustomViewHolder extends RecyclerView.ViewHolder {
                protected ImageView imageView;
                protected TextView name;
                protected TextView address;
                protected TextView wash;
                protected TextView time1, time2, time3;

                public CustomViewHolder(@NonNull View itemView) {
                        super(itemView);

                        this.imageView = itemView.findViewById(R.id.carwashlist_image2);
                        this.name = itemView.findViewById(R.id.carwashlist_name2);
                        this.address = itemView.findViewById(R.id.carwashlist_address2);
                        this.wash = itemView.findViewById(R.id.carwashlist_wash2);
                        this.time1 = itemView.findViewById(R.id.carwashlist_time12);
                        this.time2 = itemView.findViewById(R.id.carwashlist_time22);
                        this.time3 = itemView.findViewById(R.id.carwashlist_time32);

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
