package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.SinarioRewardDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class ScenarioMoneyAdapter extends RecyclerView.Adapter<ScenarioMoneyAdapter.ScenarioMoneyHolder>  {
    private ArrayList<SinarioRewardDo> sinarioRewardDos;
    private onClickSinarioRewardListener listener;


    public interface onClickSinarioRewardListener {
        void onclick(View view, SinarioRewardDo item);
    }

    public void setonClickSinarioRewardListener(onClickSinarioRewardListener listener) {
        this.listener = listener;
    }

    public ScenarioMoneyAdapter(ArrayList<SinarioRewardDo> list) {
        this.sinarioRewardDos = list;

    }

    @NonNull
    @Override
    public ScenarioMoneyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_scenario_money, parent, false);
        return new ScenarioMoneyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ScenarioMoneyHolder holder, int position) {
        SinarioRewardDo item = sinarioRewardDos.get(position);
        holder.money.setText(item.getMoney());
        holder.reward.setText(item.getReward());
    }

    @Override
    public int getItemCount() {
        return sinarioRewardDos.size();
    }

    public class ScenarioMoneyHolder extends RecyclerView.ViewHolder {
        TextView money;
        TextView reward;

        public ScenarioMoneyHolder(final View itemView) {
            super(itemView);
            money=itemView.findViewById(R.id.money);
            reward=itemView.findViewById(R.id.reward);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, sinarioRewardDos.get(position));
                    }
                }
            });
        }
    }
}
