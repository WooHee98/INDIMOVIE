package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SinarioDataAdapter extends RecyclerView.Adapter<SinarioDataAdapter.SinarioDataHolder> {
    private ArrayList<SinarioDataDo> sinarioData;
    private onClickSinarioDataListener listener;


    public interface onClickSinarioDataListener {
        void onclick(View view, SinarioDataDo item);
    }

    public void setonClickSinarioDataListener(onClickSinarioDataListener listener) {
        this.listener = listener;
    }

    public SinarioDataAdapter(ArrayList<SinarioDataDo> list) {
        this.sinarioData = list;


    }


    class SinarioDataHolder extends RecyclerView.ViewHolder {
        TextView text_jang;
        TextView text_title;
        TextView text_writer;
        TextView text_regdate;


        public SinarioDataHolder(final View itemView) {
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

    public SinarioDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sinario, viewGroup, false);
        return new SinarioDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SinarioDataHolder sinarioDataHolder, int i) {
        SinarioDataDo item = sinarioData.get(i);
        sinarioDataHolder.text_jang.setText(item.getS_jang());
        sinarioDataHolder.text_title.setText(item.getS_title());
        sinarioDataHolder.text_writer.setText(item.getU_name());
        sinarioDataHolder.text_regdate.setText(item.getS_regdate());
    }

    @Override
    public int getItemCount() {
        return sinarioData.size();
    }


}
