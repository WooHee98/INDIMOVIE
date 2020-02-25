package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.MovieDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.aApi.Api;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShowDataAdapter extends RecyclerView.Adapter<ShowDataAdapter.ShowDataHolder> {
    private Context context;
    private ArrayList<MovieDataDo> list;
    private onClickShowViewListener listener;
    private Api api;
    public interface onClickShowViewListener{
        void onclick(View view, MovieDataDo item);
    }

    public void setonClickShowViewListener(onClickShowViewListener list) {
        this.listener  =list;
    }

    public ShowDataAdapter(ArrayList<MovieDataDo> list, Context context){
        this.context = context;
        this.list =list;
    }

    class ShowDataHolder extends  RecyclerView.ViewHolder{
        ImageView show_image;
        TextView show_title;

        public ShowDataHolder(final View showitemView){
            super(showitemView);
            show_image =showitemView.findViewById(R.id.item_show_image);
            show_title = showitemView.findViewById(R.id.item_show_title);
            showitemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, list.get(position));

                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public ShowDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_main_home_show, viewGroup, false);
        return new ShowDataHolder(view);
    }


    public void onBindViewHolder(ShowDataHolder showDataHolder, int i) {
        MovieDataDo item = list.get(i);
        showDataHolder.show_title.setText(item.getM_title());
        Picasso.get().load(api.API_URL+"/static/img/movie/"+item.getM_image_url()).into(showDataHolder.show_image);


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
