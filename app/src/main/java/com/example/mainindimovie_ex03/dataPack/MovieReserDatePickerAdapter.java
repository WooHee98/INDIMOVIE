package com.example.mainindimovie_ex03.dataPack;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.mainindimovie_ex03.Do.DatePickerDataDo;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;

import java.util.ArrayList;

public class MovieReserDatePickerAdapter extends RecyclerView.Adapter<MovieReserDatePickerAdapter.MovieDatePickerHolder> {
//    private ArrayList<DatePickerDataDo> listdata = new ArrayList<>();
//    private DatePickerDataDo li = new DatePickerDataDo();
//    private onClickTimeViewListener listener;

//
//    public interface onClickTimeViewListener {
//        void onclick(View view, DatePickerDataDo listdata);
//    }
//
//    public void setonClickTimeViewListener(onClickTimeViewListener list) {
//        this.listener = list;
//    }
//
//    public MovieReserDatePickerAdapter(DatePickerDataDo li) {
//        this.li = li;
//    }
//
//    @NonNull
//    @Override
//    public MovieDatePickerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_reser_date_picker_recycler, parent, false);
//        return new MovieDatePickerHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull MovieDatePickerHolder holder, int position) {
//        DatePickerDataDo item = listdata.get(position);
//        holder.picker.setText(item.getDate());
//    }
//
//    @Override
//    public int getItemCount() {
//        return listdata.size();
//    }
//
//    public void addItem(DatePickerDataDo data){
//        listdata.add(data);
//    }
//
//    public class MovieDatePickerHolder extends RecyclerView.ViewHolder {
//        Button picker;
//        public MovieDatePickerHolder(final View itemView) {
//            super(itemView);
//
//            picker = itemView.findViewById(R.id.picker1);
//            picker.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION) {
//                        listener.onclick(itemView, listdata.get(position));
//                    }
//                }
//            });
//        }
//    }
//
    private ArrayList<DatePickerDataDo> list;
    private onClickTimeViewListener listener;


    public interface onClickTimeViewListener {
        void onclick(View view, DatePickerDataDo item);
    }

    public void setonClickTimeViewListener(onClickTimeViewListener list) {
        this.listener = list;
    }

    public MovieReserDatePickerAdapter(ArrayList<DatePickerDataDo> list) {
        this.list = list;
    }

    class MovieDatePickerHolder extends RecyclerView.ViewHolder {
        Button picker;

        public MovieDatePickerHolder(final View timepickerView) {
            super(timepickerView);
            picker = timepickerView.findViewById(R.id.picker1);
            picker.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onclick(itemView, list.get(position));
                    }
                }
            });
        }
    }

    @NonNull
    @Override
    public MovieDatePickerHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_reser_date_picker_recycler, viewGroup, false);
        return new MovieDatePickerHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDatePickerHolder movieDatePickerHolder, int i) {
        DatePickerDataDo item = list.get(i);
        movieDatePickerHolder.picker.setText(item.getDate() + "");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
