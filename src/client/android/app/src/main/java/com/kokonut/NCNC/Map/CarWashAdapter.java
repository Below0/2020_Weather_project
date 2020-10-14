package com.kokonut.NCNC.Map;

import android.app.Activity;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kokonut.NCNC.Retrofit.CarWashContents;
import com.kokonut.NCNC.R;

import java.util.ArrayList;
import java.util.List;


public class CarWashAdapter extends RecyclerView.Adapter<CarWashAdapter.CustomViewHolder> {

    private List<CarWashContents> mList = null;
    private Activity context = null;
    private CarWashAdapter.OnItemClickListener mListener = null;


    public CarWashAdapter(Activity context, List<CarWashContents> list) {
        this.context = context;
        this.mList = list;
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position) ;
    }

    // OnItemClickListener 리스너 객체 참조를 어댑터에 전달하는 메서드
    public void setOnItemClickListener(CarWashAdapter.OnItemClickListener listener) {
        this.mListener = listener ;
    }


    class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView id;
        protected TextView latitude;
        protected TextView longitude;
        protected TextView name;
        protected TextView address;
        protected TextView phone;
        protected TextView city;
        protected TextView district;
        protected TextView dong;
        protected TextView type;



        public CustomViewHolder(View view) {
            super(view);
            this.id = (TextView) view.findViewById(R.id.textView_list_id);
//            this.latitude = (TextView) view.findViewById(R.id.textView_list_latitude);
//            this.longitude = (TextView) view.findViewById(R.id.textView_list_longitude);
            this.name = (TextView) view.findViewById(R.id.textView_list_name);
            this.address = (TextView) view.findViewById(R.id.textView_list_address);
            this.phone = (TextView) view.findViewById(R.id.textView_list_phone);
//            this.city = (TextView) view.findViewById(R.id.textView_list_city);
//            this.district = (TextView) view.findViewById(R.id.textView_list_district);
//            this.dong = (TextView) view.findViewById(R.id.textView_list_dong);
//            this.type = (Textview) view.findViewById(R.id.textView_list_type);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition() ;
                    if (pos != RecyclerView.NO_POSITION) {
                        // 리스너 객체의 메서드 호출.
                        if (mListener != null) {
                            mListener.onItemClick(v, pos) ;
                        }
                    }
                }
            });


        }
    }


    @Override
    public CarWashAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_list_output, null);
        CarWashAdapter.CustomViewHolder viewHolder = new CarWashAdapter.CustomViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CarWashAdapter.CustomViewHolder viewholder, int position) {

        viewholder.id.setText(mList.get(position).getId());
        viewholder.name.setText(mList.get(position).getName());
        viewholder.address.setText(mList.get(position).getAddress());
        viewholder.phone.setText(mList.get(position).getPhone());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public Activity getContext() {
        return context;
    }

    public List<CarWashContents> getmList() {
        return mList;
    }

    public void setContext(Activity context) {
        this.context = context;
    }

    public void setmList(List<CarWashContents> mList) {
        this.mList = mList;
    }

}