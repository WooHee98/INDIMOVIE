package com.example.mainindimovie_ex03.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mainindimovie_ex03.Do.ReservationDataDo;
import com.example.mainindimovie_ex03.R;
import com.example.mainindimovie_ex03.dataPack.ReservationListDataAdapter;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationListFragment extends Fragment {

    View view;
    private Context context;
    private RecyclerView recyclerView;

    private ReservationListDataAdapter adapter;


    private OnClickListItemListener listener;

    public void setOnClickListItemLister(final OnClickListItemListener listener) {
        this.listener = listener;
    }

    public interface OnClickListItemListener {
        void onItemSelected(View view, ReservationDataDo item);
    }


    public ReservationListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = (View) inflater.inflate(R.layout.fragment_reservation_list, container, false);

        ArrayList<ReservationDataDo> temp = new ArrayList<>();
        for (int i = 1; i <= 3; i++) {
            ReservationDataDo item = new ReservationDataDo();
            item.setR_id("cld" + i);
            item.setM_title("제목" + i);
            item.setMt_date("날짜" + i);
            item.setT_name("영화관" + i);
            item.setR_seat("좌석" + i);


            temp.add(item);

        }


        adapter = new ReservationListDataAdapter(temp, getContext());
        adapter.setOnClickviewListener(new ReservationListDataAdapter.onClickviewListener() {
            @Override
            public void onClick(View view, ReservationDataDo item) {
                Toast.makeText(getContext(), "ReservationList" + item.getR_id(), Toast.LENGTH_SHORT).show();
            }
        });


        recyclerView = (RecyclerView) view.findViewById(R.id.movie_reservation_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayout.VERTICAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);


        return view;


    }

}
