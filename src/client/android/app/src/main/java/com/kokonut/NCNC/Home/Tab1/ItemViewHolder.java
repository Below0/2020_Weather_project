package com.kokonut.NCNC.Home.Tab1;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.R;


public class ItemViewHolder extends RecyclerView.ViewHolder {

    LinearLayout llInfoBox;
    RatingBar rbBoxStar;
    TextView tvBoxScore;
    ImageView imageView;
    TextView name;
    TextView address;
    TextView wash, time;
//    TextView time1, time2, time3;

    public ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        llInfoBox = itemView.findViewById(R.id.carwashlist_layout);
        rbBoxStar = itemView.findViewById(R.id.carwashlist_rating_bar);
        tvBoxScore = itemView.findViewById(R.id.carwashlist_score);
        imageView = itemView.findViewById(R.id.carwashlist_image);
        name = itemView.findViewById(R.id.carwashlist_name);
//        address = itemView.findViewById(R.id.carwashlist_address);
        wash = itemView.findViewById(R.id.carwashlist_wash);
//        time1 = itemView.findViewById(R.id.carwashlist_time1);
//        time2 = itemView.findViewById(R.id.carwashlist_time2);
//        time3 = itemView.findViewById(R.id.carwashlist_time3);
        time = itemView.findViewById(R.id.carwashlist_time);


    }
}
