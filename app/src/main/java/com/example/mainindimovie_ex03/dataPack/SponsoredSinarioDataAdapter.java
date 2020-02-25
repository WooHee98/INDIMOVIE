package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.Funding;
import com.example.mainindimovie_ex03.Do.SponsoredSinarioDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class SponsoredSinarioDataAdapter extends RecyclerView.Adapter<SponsoredSinarioDataAdapter.SponsoredSinarioDataHolder> {
    private ArrayList<Funding> sponsoredSinarioDatas;
    private onClickSponsoredSinarioDataListener listener;


    public interface onClickSponsoredSinarioDataListener {
        void onclick(View view, Funding item);
    }

    public void setonClickSponsoredSinarioDataListener(onClickSponsoredSinarioDataListener listener) {
        this.listener = listener;
    }

    public SponsoredSinarioDataAdapter(ArrayList<Funding> list) {
        this.sponsoredSinarioDatas = list;
    }

    class SponsoredSinarioDataHolder extends RecyclerView.ViewHolder {
        TextView text_jang;
        TextView text_title;
        TextView text_writer;
        TextView text_price;
        public SponsoredSinarioDataHolder(final View itemView) {
            super(itemView);
            text_jang = itemView.findViewById(R.id.sponsored_sinario_jang_text);
            text_title = itemView.findViewById(R.id.sponsored_sinario_title_text);
            text_writer = itemView.findViewById(R.id.sponsored_sinario_writer_text);
            text_price = itemView.findViewById(R.id.sponsored_sinario_price_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, sponsoredSinarioDatas.get(position));
                    }
                }
            });
        }


    }

    @NonNull
    @Override
    public SponsoredSinarioDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_sponsored_sinario, viewGroup, false);
        return new SponsoredSinarioDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SponsoredSinarioDataHolder sponsoredSinarioDataHolder, int i) {
        Funding item = sponsoredSinarioDatas.get(i);
        sponsoredSinarioDataHolder.text_jang.setText(item.getS_jang());
        sponsoredSinarioDataHolder.text_title.setText(item.getS_title());
        sponsoredSinarioDataHolder.text_writer.setText(item.getU_idtext());
        sponsoredSinarioDataHolder.text_price.setText(item.getF_amount()+"");
    }

    @Override
    public int getItemCount() {
        return sponsoredSinarioDatas.size();
    }


}
