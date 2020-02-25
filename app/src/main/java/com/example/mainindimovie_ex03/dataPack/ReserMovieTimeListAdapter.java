package com.example.mainindimovie_ex03.dataPack;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class ReserMovieTimeListAdapter extends RecyclerView.Adapter<ReserMovieTimeListAdapter.ReserMovieTimeListHolder> {
    private ArrayList<ReservationDataDo> reserdata;
    private Context context;
    private OnReserMovieTimeListListener listener;

    public ReserMovieTimeListAdapter(Context context, ArrayList<ReservationDataDo> reserdata) {
        this.context = context;
        this.reserdata = reserdata;
    }


    public interface OnReserMovieTimeListListener {
        void OnClicked(Button button);
    }

    public void setOnReserMovieTimeListListener(OnReserMovieTimeListListener listener) {
        this.listener = listener;
    }


    public class ReserMovieTimeListHolder extends RecyclerView.ViewHolder {
        TextView title;
        RecyclerView subslide;

        public ReserMovieTimeListHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.movie_theater_data_view_title);
            subslide = itemView.findViewById(R.id.theater_category_list);
            subslide.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            subslide.setHasFixedSize(true);
        }
    }


    @NonNull
    @Override
    public ReserMovieTimeListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theater_list_view, parent, false);
        return new ReserMovieTimeListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserMovieTimeListHolder holder, final int position) {
        ReservationDataDo item = reserdata.get(position);
        holder.title.setText(item.getst_name());
        ReserMovieTimeSlideAdapter reserMovieTimeSlideAdapter = new ReserMovieTimeSlideAdapter(item.getst_time(), item.getMt_id());
        reserMovieTimeSlideAdapter.setOnClickSlidebuttonsListener(new ReserMovieTimeSlideAdapter.OnClickSlidebuttonsListener() {
            @Override
            public void OnClicked(Button button) {
                if (position != RecyclerView.NO_POSITION && listener != null) {
                    listener.OnClicked(button);
                }
            }
        });
        holder.subslide.setAdapter(reserMovieTimeSlideAdapter);
    }

    @Override
    public int getItemCount() {
        return reserdata.size();
    }


}
