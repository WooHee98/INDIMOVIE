package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class ScenarioNewsAdapter extends RecyclerView.Adapter<ScenarioNewsAdapter.ScenarioNewsHolder>{
    private ArrayList<SinarioDataDo> newsDatas;
    private onClicScenarionewsListener listener;

    public interface onClicScenarionewsListener {
        void onclick(View view, SinarioDataDo item);
    }

    public void setonClicScenarionewsListener(onClicScenarionewsListener listener) {
        this.listener = listener;
    }

    public ScenarioNewsAdapter(ArrayList<SinarioDataDo> list) {
        this.newsDatas = list;
    }

    @NonNull
    @Override
    public ScenarioNewsHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notice, parent, false);
        return new ScenarioNewsHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScenarioNewsHolder holder, int position) {
        SinarioDataDo item = newsDatas.get(position);
        holder.text_regdate.setText(item.getSn_regdate());
        holder.text_title.setText(item.getSn_title());
    }

    @Override
    public int getItemCount() {
        return newsDatas.size();
    }

    public class ScenarioNewsHolder extends RecyclerView.ViewHolder {
        TextView text_regdate;
        TextView text_title;
        public ScenarioNewsHolder(final View itemView) {
            super(itemView);
            text_regdate = itemView.findViewById(R.id.notice_regdate_text);
            text_title = itemView.findViewById(R.id.notice_title_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, newsDatas.get(position));
                    }
                }
            });
        }
    }
}
