package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class ReserMovieTimeSlideAdapter extends RecyclerView.Adapter<ReserMovieTimeSlideAdapter.ReserMovieTimeHolder> {
    private ArrayList<String> buttons;
    private ArrayList<String> ids;
    private OnClickSlidebuttonsListener listener;



    public interface OnClickSlidebuttonsListener{
        void OnClicked(Button button);
    }
    public void setOnClickSlidebuttonsListener(OnClickSlidebuttonsListener listener){
        this.listener = listener;
    }

    public ReserMovieTimeSlideAdapter(ArrayList<String> buttons, ArrayList<String> ids){
        this.ids= ids;
        this.buttons=buttons;


    }


    public class ReserMovieTimeHolder extends RecyclerView.ViewHolder {
        Button button;

        public ReserMovieTimeHolder(View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.reser_movie_time_item);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && listener !=null) {
                        listener.OnClicked(button);
                    }
                }
            });
        }
    }



    @NonNull
    @Override
    public ReserMovieTimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reser_movietime, parent, false);
        return new  ReserMovieTimeHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReserMovieTimeHolder holder, int position) {
        String s_button = buttons.get(position);
        holder.button.setText(s_button);
        holder.button.setTag(ids.get(position));
    }

    @Override
    public int getItemCount() {
        return buttons.size();
    }


}
