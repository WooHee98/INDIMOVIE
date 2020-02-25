package com.example.mainindimovie_ex03.dataPackTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mainindimovie_ex03.activitys.ReservationListActivity;
import com.example.mainindimovie_ex03.fragments.ReservationDeleteFragment;
import com.example.mainindimovie_ex03.fragments.ReservationListFragment;
import com.example.mainindimovie_ex03.fragments.ReservationPastFragment;

public class ReservaionListTabAdapter extends FragmentPagerAdapter {

    public ReservaionListTabAdapter(ReservationListActivity reservationListActivity, FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new ReservationListFragment();
            case 1:
                return new ReservationPastFragment();
            case 2:
                return new ReservationDeleteFragment();
            default:
                return null;
        }
    }

    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "예매내역";
            case 1:
                return "지난내역";
            case 2:
                return "취소내역";

        }
        return null;
    }

}
