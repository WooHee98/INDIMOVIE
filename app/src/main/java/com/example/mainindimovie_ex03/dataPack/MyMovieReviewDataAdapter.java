package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.MovieReviewDataDo;
import com.example.mainindimovie_ex03.R;


import java.util.ArrayList;

public class MyMovieReviewDataAdapter extends RecyclerView.Adapter<MyMovieReviewDataAdapter.MyMovieReviewDataHolder> {
    private ArrayList<MovieReviewDataDo> reviewDatas;
    private onClickMyMovieReviewDataListener listener;


    public interface onClickMyMovieReviewDataListener {
        void onclick(View view, MovieReviewDataDo item);
    }

    public void setonClickMyMovieReviewDataListener(onClickMyMovieReviewDataListener listener) {
        this.listener = listener;
    }

    public MyMovieReviewDataAdapter(ArrayList<MovieReviewDataDo> list) {
        this.reviewDatas = list;
    }

    public class MyMovieReviewDataHolder extends RecyclerView.ViewHolder {
        TextView text_regdate;
        TextView text_title;
        TextView text_content;

        public MyMovieReviewDataHolder( final View itemView) {
            super(itemView);
            text_regdate = itemView.findViewById(R.id.my_movie_detail_review_regdate);
            text_title = itemView.findViewById(R.id.my_movie_detail_review_title);
            text_content = itemView.findViewById(R.id.my_movie_detail_review_content);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, reviewDatas.get(position));
                    }
                }
            });
        }


    }
    @NonNull
    @Override
    public MyMovieReviewDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_my_movie_review, viewGroup, false);
        return new MyMovieReviewDataHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull MyMovieReviewDataHolder myMovieReviewDataHolder, int i) {
        MovieReviewDataDo item = reviewDatas.get(i);
        myMovieReviewDataHolder.text_regdate.setText(item.getMr_regdate());
        myMovieReviewDataHolder.text_title.setText(item.getMr_mtitle());
        myMovieReviewDataHolder.text_content.setText(item.getMr_content());

    }

    @Override
    public int getItemCount() {
        return reviewDatas.size();
    }
}


