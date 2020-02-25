package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class MovieDetailPhotoDataAdapter extends RecyclerView.Adapter<MovieDetailPhotoDataAdapter.MovieDetailPhotoHolder> {
    private ArrayList<MovieDataDo> moviephotodatas;
    private onClickmoviephotoListener listener;


    public interface onClickmoviephotoListener {
        void onClick(View view, MovieDataDo item);
    }

    public void setonClickmoviephotoListener(onClickmoviephotoListener listener) {
        this.listener = listener;
    }


    public MovieDetailPhotoDataAdapter(ArrayList<MovieDataDo> moviephotodatas) {
        this.moviephotodatas = moviephotodatas;
    }

    class MovieDetailPhotoHolder extends RecyclerView.ViewHolder {
        ImageView movie_detail_photo; //이미지

        public MovieDetailPhotoHolder(final View itemView) {
            super(itemView);
            movie_detail_photo = itemView.findViewById(R.id.item_movie_photo_image);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, moviephotodatas.get(position));

                    }

                }
            });
        }
    }


    @NonNull
    @Override
    public MovieDetailPhotoHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_detail_photo, viewGroup, false);
        return new MovieDetailPhotoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDetailPhotoHolder movieDetailPhotoHolder, int i) {
        MovieDataDo item = moviephotodatas.get(i);

        /*        Glide.with(context)
                .load(item.getM_image_url())
                //centerCrop 로 이미지 크게
                .centerCrop()
                //내가 이 미지를 쓰겠다라는 것
                .into(movieDataHolder.reserlist_image);*/

    }

    @Override
    public int getItemCount() {
        return moviephotodatas.size();
    }
}
