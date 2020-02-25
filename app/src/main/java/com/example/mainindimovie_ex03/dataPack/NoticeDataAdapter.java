package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.NoticeDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class NoticeDataAdapter extends RecyclerView.Adapter<NoticeDataAdapter.NoticeDataHolder> {
    private ArrayList<NoticeDataDo> noticeDatas;
    private onClickNoticeDataListener listener;

    public interface onClickNoticeDataListener {
        void onclick(View view, NoticeDataDo item);
    }

    public void setonClickNoticeDataListener(onClickNoticeDataListener listener) {
        this.listener = listener;
    }

    public NoticeDataAdapter(ArrayList<NoticeDataDo> list) {
        this.noticeDatas = list;
    }

    class NoticeDataHolder extends RecyclerView.ViewHolder {
        TextView text_regdate;
        TextView text_title;

        public NoticeDataHolder( final View itemView) {
            super(itemView);
            text_regdate = itemView.findViewById(R.id.notice_regdate_text);
            text_title = itemView.findViewById(R.id.notice_title_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, noticeDatas.get(position));
                    }
                }
            });
        }
    }
    @NonNull
    @Override
    public NoticeDataHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_notice, viewGroup, false);
        return new NoticeDataHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeDataHolder noticeDataHolder, int i) {
        NoticeDataDo item = noticeDatas.get(i);
        noticeDataHolder.text_regdate.setText(item.getN_regdate());
        noticeDataHolder.text_title.setText(item.getN_title());

    }

    @Override
    public int getItemCount() {
        return noticeDatas.size();
    }
}
