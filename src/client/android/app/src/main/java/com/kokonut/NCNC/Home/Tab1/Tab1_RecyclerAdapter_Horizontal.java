package com.kokonut.NCNC.Home.Tab1;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.Home.CarWashInfoData;
import com.kokonut.NCNC.Map.CarWashInfoActivity;
import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.CarWashContents;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Tab1_RecyclerAdapter_Horizontal extends RecyclerView.Adapter<Tab1_RecyclerAdapter_Horizontal.CustomViewHolder> {

        private List<CarWashContents> carWashContentsList = null;
        private Activity context = null;
        private Tab1_RecyclerAdapter_Horizontal.OnItemClickListener mListener = null;

        public static final int sub = 1001;
        private String washType;

        public Tab1_RecyclerAdapter_Horizontal(List<CarWashContents> carWashContentsList){

                this.carWashContentsList = carWashContentsList;
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

        public Activity getContext(){
                return context;
        }

        public void setContext(Activity context){
                this.context = context;
        }

        class CustomViewHolder extends RecyclerView.ViewHolder {
                protected LinearLayout llInfoBox;
                protected ImageView imageView;
                protected TextView name;
                protected RatingBar rbBoxStar;
                protected TextView tvBoxScore;
                protected TextView wash;
                protected TextView time;

                public CustomViewHolder(@NonNull View itemView) {
                        super(itemView);

                        this.llInfoBox = itemView.findViewById(R.id.carwashlist_layout2);
                        this.imageView = itemView.findViewById(R.id.carwashlist_image2);
                        this.name = itemView.findViewById(R.id.carwashlist_name2);
                        this.rbBoxStar = itemView.findViewById(R.id.carwashlist_rating_bar2);
                        this.tvBoxScore = itemView.findViewById(R.id.carwashlist_score2);
                        this.wash = itemView.findViewById(R.id.carwashlist_wash2);
                        this.time = itemView.findViewById(R.id.carwashlist_time2);


                }
        }
}
