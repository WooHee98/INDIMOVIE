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
import com.example.mainindimovie_ex03.aApi.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieDetailReviewDataAdapter extends RecyclerView.Adapter<MovieDetailReviewDataAdapter.MovieDetailReviewHolder>{
    private ArrayList<MovieReviewDataDo> moviereviewdatas;
    private onClickmoviereviewListener listener;

    private Api api;


    public interface onClickmoviereviewListener {
        void onClick(View view, MovieReviewDataDo item);
    }

    public void setonClickmoviereviewListener(onClickmoviereviewListener listener) {
        this.listener = listener;
    }


    public MovieDetailReviewDataAdapter(ArrayList<MovieReviewDataDo> moviereviewdatas) {
        this.moviereviewdatas = moviereviewdatas;
    }

    public class MovieDetailReviewHolder extends RecyclerView.ViewHolder{
        TextView nick, content, regdate;
        ImageView imageView;
        public MovieDetailReviewHolder(final View itemView) {
            super(itemView);
            nick = itemView.findViewById(R.id.movie_detail_review_nick);
            content =itemView.findViewById(R.id.movie_detail_review_content);
            regdate = itemView.findViewById(R.id.movie_detail_review_regdate);
            imageView = itemView.findViewById(R.id.review_icon);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, moviereviewdatas.get(position));

                    }

                }
            });
        }
    }

    @NonNull
    @Override
    public MovieDetailReviewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_detail_review, viewGroup, false);
        return new MovieDetailReviewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailReviewHolder movieDetailReviewHolder, int i) {
        MovieReviewDataDo item = moviereviewdatas.get(i);
        movieDetailReviewHolder.nick.setText(item.getMr_nick());
        movieDetailReviewHolder.content.setText(item.getMr_content());
        movieDetailReviewHolder.regdate.setText(item.getMr_regdate());
        Picasso.get().load(api.API_URL+"/static/img/review/"+item.getMr_icon()+".jpg").into(movieDetailReviewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return moviereviewdatas.size();
    }
}
