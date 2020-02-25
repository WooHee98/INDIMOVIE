package com.example.mainindimovie_ex03.dataPack;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;
import java.util.Date;

public class MySinarioDataAdapter  extends RecyclerView.Adapter<MySinarioDataAdapter.MySinarioDataHolder> {
    private ArrayList<SinarioDataDo> sinarioData;
    private onClickMYSinarioDataListener listener;


    public interface onClickMYSinarioDataListener {
        void onclick(View view, SinarioDataDo item);
    }

    public void setonClickMYSinarioDataListener(onClickMYSinarioDataListener listener) {
        this.listener = listener;
    }

    public MySinarioDataAdapter(ArrayList<SinarioDataDo> list) {
        this.sinarioData = list;
    }
    @NonNull
    @Override
    public MySinarioDataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sinario, parent, false);
        return new MySinarioDataHolder(view);
    }

    @Override
    public void onBindViewHolder(MySinarioDataHolder holder, int position) {
        SinarioDataDo item = sinarioData.get(position);
        holder.text_jang.setText(item.getS_jang());
        holder.text_title.setText(item.getS_title());
        holder.text_writer.setText(item.getS_spon_date());
        holder.text_regdate.setText(item.getS_amount());
    }

    @Override
    public int getItemCount() {
        return sinarioData.size();
    }

    public class MySinarioDataHolder extends RecyclerView.ViewHolder {
        TextView text_jang;
        TextView text_title;
        TextView text_writer;
        TextView text_regdate;

        public MySinarioDataHolder(final View itemView) {
            super(itemView);

            text_jang = itemView.findViewById(R.id.sinario_jang_text);
            text_title = itemView.findViewById(R.id.sinario_title_text);
            text_writer = itemView.findViewById(R.id.sinario_writer_text);
            text_regdate = itemView.findViewById(R.id.sinario_regdate_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, sinarioData.get(position));
                    }
                }
            });
        }
    }
}
