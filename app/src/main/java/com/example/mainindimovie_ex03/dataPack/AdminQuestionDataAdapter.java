package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.mainindimovie_ex03.Do.AdminQuestionDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class AdminQuestionDataAdapter extends RecyclerView.Adapter<AdminQuestionDataAdapter.AdminQuestionDataAdapterHolder> {
    private ArrayList<AdminQuestionDataDo> adminQuestionDatas;
    private onClickAdminQuestionDataListener listener;


    public interface onClickAdminQuestionDataListener {
        void onclick(View view, AdminQuestionDataDo item);
    }

    public void setonClickAdminQuestionDataListener(onClickAdminQuestionDataListener listener) {
        this.listener = listener;
    }

    public AdminQuestionDataAdapter(ArrayList<AdminQuestionDataDo> list) {
        this.adminQuestionDatas = list;
    }

    public class AdminQuestionDataAdapterHolder extends RecyclerView.ViewHolder {
        TextView text_regdate;
        TextView text_title;

        public AdminQuestionDataAdapterHolder(final View itemView) {
            super(itemView);
            text_regdate = itemView.findViewById(R.id.admin_question_regdate_text);
            text_title = itemView.findViewById(R.id.admin_question_title_text);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, adminQuestionDatas.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public AdminQuestionDataAdapterHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_admin_question_exit, viewGroup, false);
        return new AdminQuestionDataAdapterHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminQuestionDataAdapterHolder adminQuestionDataAdapterHolder, int i) {
        AdminQuestionDataDo item = adminQuestionDatas.get(i);
        adminQuestionDataAdapterHolder.text_regdate.setText(item.getAq_regdate());
        adminQuestionDataAdapterHolder.text_title.setText(item.getAq_title());
    }

    @Override
    public int getItemCount() {
        return adminQuestionDatas.size();
    }

}
