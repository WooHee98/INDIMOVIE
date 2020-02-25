package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.TheaterDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;


public class TheaterListAdapter extends RecyclerView.Adapter<TheaterListAdapter.TheaterListHolder> {
    private ArrayList<TheaterDataDo> list;
    private Context context;
    private OnTheaterListTextListener listener;

    public TheaterListAdapter(Context context, ArrayList<TheaterDataDo> list) {
        this.context = context;
        this.list = list;
    }


    public interface OnTheaterListTextListener {
        void OnClicked(TextView textView);
    }

    public void setOnTheaterListTextListener(OnTheaterListTextListener listener) {
        this.listener = listener;
    }

    public class TheaterListHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView subslide;

        public TheaterListHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_theater_data_view_title );

            subslide = itemView.findViewById(R.id.theater_category_list);
            subslide.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            subslide.setHasFixedSize(true);
        }
    }

    @NonNull
    @Override
    public TheaterListHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_theater_list_view, viewGroup, false);
        return new TheaterListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TheaterListHolder theaterListHolder,final int i) {
        TheaterDataDo item = list.get(i);
        theaterListHolder.title.setText(item.getT_area());
        TheaterListSubSlideAdapter adapter = new TheaterListSubSlideAdapter(item.getT_name());
        adapter.setOnClickSlidetextsListener(new TheaterListSubSlideAdapter.OnClickSlidetextsListener() {
            @Override
            public void OnClicked(TextView textView ) {
                if (i != RecyclerView.NO_POSITION && listener != null) {
                    listener.OnClicked(textView);
                }
            }
        });
        theaterListHolder.subslide.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}