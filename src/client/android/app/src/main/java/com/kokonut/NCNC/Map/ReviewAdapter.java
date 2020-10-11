package com.kokonut.NCNC.Map;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.media.Rating;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.kokonut.NCNC.R;
import com.kokonut.NCNC.Retrofit.ReviewContents;

import java.util.ArrayList;
import java.util.List;


public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.CustomViewHolder> {

    private List<ReviewContents.Content> mList = null;

    public ReviewAdapter(List<ReviewContents.Content> list) {
        this.mList = list;
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {
        protected TextView tvUserId;
        protected TextView tvDate;
        protected TextView tvReviewText;
        protected RatingBar rbRatingBar;

        public CustomViewHolder(View view) {
            super(view);
            this.tvUserId = (TextView) view.findViewById(R.id.review_user_id);
            this.tvDate = (TextView) view.findViewById(R.id.review_date);
            this.tvReviewText = (TextView) view.findViewById(R.id.review_text_view);
            this.rbRatingBar = (RatingBar) view.findViewById(R.id.review_rating_bar);
        }
    }


    @Override
    public ReviewAdapter.CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        View view = LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.item_review, null);

        ReviewAdapter.CustomViewHolder viewHolder = new ReviewAdapter.CustomViewHolder(view);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(@NonNull CustomViewHolder viewholder, int position) {

        viewholder.tvUserId.setText(mList.get(position).getUser_name());
        String reviewDate = mList.get(position).getDate();
        String reviewDateList[] = reviewDate.split("T");

        viewholder.tvDate.setText(mList.get(position).getDate());
        viewholder.tvDate.setText(reviewDateList[0]);
        viewholder.rbRatingBar.setRating(mList.get(position).getScore());
        viewholder.tvReviewText.setText(mList.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return (null != mList ? mList.size() : 0);
    }

    public List<ReviewContents.Content> getmList() {
        return mList;
    }

    public void setmList(List<ReviewContents.Content> mList) {
        this.mList = mList;
    }

}