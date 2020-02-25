package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ReservationListDataAdapter extends RecyclerView.Adapter<ReservationListDataAdapter.MovieDataHolder> {
    private Context context;
    private ArrayList<ReservationDataDo> list;
    private onClickviewListener listener;
    private Api api;

    public interface onClickviewListener {
        void onClick(View view, ReservationDataDo item);
    }

    public void setOnClickviewListener(onClickviewListener listener) {
        this.listener = listener;
    }


    public ReservationListDataAdapter(ArrayList<ReservationDataDo> list, Context context) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MovieDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reservation_list, viewGroup, false);
        return new MovieDataHolder(view);
    }




    //get 데이터!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
    public void onBindViewHolder(@NonNull MovieDataHolder movieDataHolder, int i) {
        ReservationDataDo item = list.get(i);

        movieDataHolder.reserlist_title.setText(item.getM_title());
        movieDataHolder.reserlist_theater_name.setText(item.getT_name());
        movieDataHolder.reserlist_date.setText(item.getMt_date());
        movieDataHolder.reserlist_seat.setText(item.getR_seat_count());
        movieDataHolder.reserlist_time.setText(item.getSt_time1());
        movieDataHolder.reserlist_screentheater.setText(item.getst_name());
        Picasso.get().load(api.API_URL+"/static/img/movie/"+item.getM_image_url()).into(movieDataHolder.reserlist_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    class MovieDataHolder extends RecyclerView.ViewHolder {
        ImageView reserlist_image; //이미지
        TextView reserlist_title;//제목
        TextView reserlist_date;//날짜
        TextView reserlist_time;//시간
        TextView reserlist_theater_name;//영화관
        TextView  reserlist_screentheater;//상영관
        TextView reserlist_seat;//예매좌석수



        public MovieDataHolder(final View itemview) {
            super(itemview);
            reserlist_image = itemview.findViewById(R.id.item_reservation_list_image);
            reserlist_title = itemview.findViewById(R.id.item_reservation_list_title);
            reserlist_date = itemview.findViewById(R.id.item_reservation_list_date);
            reserlist_theater_name = itemview.findViewById(R.id.item_reservation_list_theater);
            reserlist_time = itemview.findViewById(R.id.item_reservation_list_time);
            reserlist_screentheater= itemview.findViewById(R.id.item_reservation_list_screentheater);

            reserlist_seat = itemview.findViewById(R.id.item_reservation_list_seat);



            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //position을 받아온다.----------------------------------------------
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onClick(itemView, list.get(position));

                    }

                }
            });
        }
    }
}
