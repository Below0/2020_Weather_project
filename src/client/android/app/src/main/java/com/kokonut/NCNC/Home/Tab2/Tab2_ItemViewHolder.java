package com.kokonut.NCNC.Home.Tab2;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kokonut.NCNC.R;

public class Tab2_ItemViewHolder extends RecyclerView.ViewHolder {

    ImageView imageView;
    TextView name;
    TextView address;
    TextView wash;
    TextView time1, time2, time3;

    public Tab2_ItemViewHolder(@NonNull View itemView) {
        super(itemView);

        imageView = itemView.findViewById(R.id.searchlist_image);
        name = itemView.findViewById(R.id.searchlist_name);
        address = itemView.findViewById(R.id.searchlist_address);
        wash = itemView.findViewById(R.id.searchlist_wash);
        time1 = itemView.findViewById(R.id.searchlist_time1);
        time2 = itemView.findViewById(R.id.searchlist_time2);
        time3 = itemView.findViewById(R.id.searchlist_time3);

    }

}
