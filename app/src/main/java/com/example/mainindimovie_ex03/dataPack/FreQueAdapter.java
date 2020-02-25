package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.FreQueDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class FreQueAdapter extends RecyclerView.Adapter<FreQueAdapter.FreQueHolder>{

    private ArrayList<FreQueDataDo> freQueDataDos;
    private onClickFreQueListener listener;


    public interface onClickFreQueListener {
        void onclick(View view, FreQueDataDo item);
    }

    public void setonClickFreQueListener(onClickFreQueListener listener) {
        this.listener = listener;
    }

    public FreQueAdapter(ArrayList<FreQueDataDo> list) {
        this.freQueDataDos = list;
    }

    @NonNull
    @Override
    public FreQueHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_freque, parent, false);
        return new FreQueHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FreQueHolder holder, int position) {
        FreQueDataDo item = freQueDataDos.get(position);
        holder.text_title.setText(item.getFaq_title());
    }

    @Override
    public int getItemCount() {
            return freQueDataDos.size();
    }

    public class FreQueHolder extends RecyclerView.ViewHolder {
        TextView text_title;
        public FreQueHolder(final View itemView) {
            super(itemView);
            text_title = itemView.findViewById(R.id.freque_title_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, freQueDataDos.get(position));
                    }
                }
            });
        }
    }
}
