package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MovieSeenDataAdapter extends RecyclerView.Adapter<MovieSeenDataAdapter.MovieDataHolder> {
    private Context context;
    private LayoutInflater mInflate;
    private ArrayList<ReservationDataDo> seendatas;
    private onClickmovieseenListener listener;
    private Api api;

    public interface onClickmovieseenListener {
        void onClick(View view, ReservationDataDo item);
    }

    public void setonClickmovieseenListener(onClickmovieseenListener listener) {
        this.listener = listener;
    }


    public MovieSeenDataAdapter(ArrayList<ReservationDataDo> seendatas, Context context) {
        this.seendatas = seendatas;
        this.context = context;
    }

    class MovieDataHolder extends RecyclerView.ViewHolder {
        ImageView movie_seen_image; //이미지
        TextView movie_seent_title;//제목
        TextView movie_seen_date;//날짜

        TextView movie_seen_theater_name;//영화관

        public MovieDataHolder(final View itemview) {
            super(itemview);
            movie_seen_image = itemview.findViewById(R.id.item_movie_seen_image);
            movie_seent_title = itemview.findViewById(R.id.item_movie_seen_title);
            movie_seen_date = itemview.findViewById(R.id.item_movie_seen_date);

            movie_seen_theater_name = itemview.findViewById(R.id.item_movie_seen_theater);

            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, seendatas.get(position));

                    }

                }
            });
        }
    }

    @Override
    public MovieDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_movie_seen, viewGroup, false);
        return new MovieDataHolder(view);
    }


    //get 데이터!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void onBindViewHolder(@NonNull MovieDataHolder movieDataHolder, int i) {
        ReservationDataDo item = seendatas.get(i);

        movieDataHolder.movie_seent_title.setText(item.getM_title());
        movieDataHolder.movie_seen_theater_name.setText(item.getT_name());
        movieDataHolder.movie_seen_date.setText(item.getMt_date());
        Picasso.get().load(api.API_URL+"/static/img/movie/"+item.getM_image_url()).into(movieDataHolder.movie_seen_image);
    }

    @Override
    public int getItemCount() {
        return seendatas.size();
    }


}
