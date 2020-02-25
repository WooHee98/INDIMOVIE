package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class RecommKmAdapter extends RecyclerView.Adapter<RecommKmAdapter.RecommKmHolder>  {

    private Context context;
    private ArrayList<TheaterDataDo> list;
    private onClickRecommKmListener listener;

    public interface onClickRecommKmListener {
        void onClick(View view, TheaterDataDo item);
    }

    public void setonClickRecommKmListener(onClickRecommKmListener listener) {
        this.listener = listener;
    }


    public RecommKmAdapter(ArrayList<TheaterDataDo> list, Context context) {
        this.context = context;
        this.list = list;
    }


    @NonNull
    @Override
    public RecommKmHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater_km, parent, false);
        return new RecommKmHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecommKmHolder holder, int position) {
        TheaterDataDo item = list.get(position);
        holder.theater.setText(item.getT_name1());
        holder.km.setText(item.getT_km());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class RecommKmHolder extends RecyclerView.ViewHolder {

        TextView theater;//영화관
        TextView km;//km

        public RecommKmHolder(final  View itemView) {
            super(itemView);
            theater =itemView.findViewById(R.id.theater);
            km =itemView.findViewById(R.id.km);

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
