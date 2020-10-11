package com.kokonut.NCNC.Home.Tab1;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.R;

public class Tab1_ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView name;
    TextView address;
    TextView wash;
    TextView time1, time2, time3;

    public Tab1_ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.carwashlist_image2);
        name = itemView.findViewById(R.id.carwashlist_name2);
        address = itemView.findViewById(R.id.carwashlist_address2);
        wash = itemView.findViewById(R.id.carwashlist_wash2);
        time1 = itemView.findViewById(R.id.carwashlist_time12);
        time2 = itemView.findViewById(R.id.carwashlist_time22);
        time3 = itemView.findViewById(R.id.carwashlist_time32);

    }
}