package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.example.mainindimovie_ex03.activitys.SeatSelectedActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class RecommMovieAdapter extends RecyclerView.Adapter<RecommMovieAdapter.RecommMovieHolder>  {
    private Context context;
    private ArrayList<ReservationDataDo> list;
    private onClickRecommListener listener;
    private Api api;

    String t_name="";
    String st_id="";
    String mt_day="";
    String m_title="";
    String st_time="";
    String t_adult="";
    String t_kid="";
    String mt_id="";

    public interface onClickRecommListener {
        void onClick(View view, ReservationDataDo item);
    }

    public void setonClickRecommListener(onClickRecommListener listener) {
        this.listener = listener;
    }


    public RecommMovieAdapter(ArrayList<ReservationDataDo> list, Context context) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public RecommMovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recomm_movie, parent, false);
        return new RecommMovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommMovieHolder holder, int position) {
        ReservationDataDo item = list.get(position);
        holder.item_movie_wishi_title.setText(item.getM_title());
        holder.item_movie_jang.setText(item.getJang());
        holder.item_movie_runtime.setText(item.getRuntime());
        holder.item_movie_theater.setText(item.getT_name());
        holder.item_movie_time.setText(item.getSt_time1());
        ArrayList<String> tag_data = new ArrayList<String>();
        tag_data.add(item.getT_name()); //영화관 이름
        tag_data.add(item.getMt_date()); //날짜
        tag_data.add(item.getM_title()); //영화이름
        tag_data.add(item.getSt_time1()); //영화시간
        tag_data.add(item.getT_adult()); //성인
        tag_data.add(item.getT_kid()); //청소년
        tag_data.add(item.getMt_id1()); //mt_id

        holder.btn_reser.setTag(tag_data);
        Picasso.get().load(api.API_URL+"/static/img/movie/"+item.getM_image_url()).into(holder.item_movie_image);

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecommMovieHolder extends RecyclerView.ViewHolder {

        ImageView item_movie_image; //이미지
        TextView item_movie_wishi_title;//제목
        TextView item_movie_jang;//장르
        TextView item_movie_runtime;//런타임
        TextView item_movie_theater;//영화관
        TextView  item_movie_time;//상영관
        Button btn_reser;

        public RecommMovieHolder(final View itemView) {
            super(itemView);
            item_movie_image= itemView.findViewById(R.id.item_movie_image);
            item_movie_wishi_title= itemView.findViewById(R.id.item_movie_wishi_title);
            item_movie_jang= itemView.findViewById(R.id.item_movie_jang);
            item_movie_runtime= itemView.findViewById(R.id.item_movie_runtime);
            item_movie_theater= itemView.findViewById(R.id.item_movie_theater);
            item_movie_time= itemView.findViewById(R.id.item_movie_time);
            btn_reser= itemView.findViewById(R.id.btn_reser);
            btn_reser.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ArrayList<String> data = (ArrayList<String>) view.getTag();
                    t_name = data.get(0);
                    mt_day = data.get(1);
                    m_title = data.get(2);
                    st_time = data.get(3);
                    t_adult = data.get(4);
                    t_kid= data.get(5);
                    mt_id= data.get(6);
                    Log.d("은주",t_name+"+"+ st_id+"+"+mt_day+"+"+ m_title+"+"+ st_time+"+"+t_adult+"+"+ t_kid+"+"+mt_id);
                    Intent intent = new Intent(context, SeatSelectedActivity.class);
                    intent.putExtra("t_name",t_name.trim());
                    intent.putExtra("t_adult",t_adult);
                    intent.putExtra("t_kid",t_kid);
                    intent.putExtra("mt_id",mt_id);
                    intent.putExtra("day",mt_day);
                    intent.putExtra("movietitle",m_title);
                    intent.putExtra("movietime",st_time);
                    context.startActivity(intent);
                }
            });
            itemView.setOnClickListener(new View.OnClickListener() {
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
