package com.example.mainindimovie_ex03.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mainindimovie_ex03.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ReservationPastFragment extends Fragment {


    public ReservationPastFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reservation_past, container, false);
    }

}
