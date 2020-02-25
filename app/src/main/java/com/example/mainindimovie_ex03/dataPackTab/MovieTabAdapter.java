package com.example.mainindimovie_ex03.dataPackTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mainindimovie_ex03.activitys.MovieActivity;
import com.example.mainindimovie_ex03.fragments.MoviePresentFragment;
import com.example.mainindimovie_ex03.fragments.MovieReleaseFragment;


public class MovieTabAdapter extends FragmentPagerAdapter {
    public MovieTabAdapter(MovieActivity movieActivity, FragmentManager fm) {
        super(fm);
    }
    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new MoviePresentFragment();
            case 1:
                return new MovieReleaseFragment();

            default:
                return null;
        }
    }

    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position) {
        switch (position) {
            case 0:
                return "현재상영작";
            case 1:
                return "계봉예정작";


        }
        return null;
    }

}
