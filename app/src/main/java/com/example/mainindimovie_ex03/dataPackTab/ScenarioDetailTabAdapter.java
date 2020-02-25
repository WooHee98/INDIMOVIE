package com.example.mainindimovie_ex03.dataPackTab;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mainindimovie_ex03.activitiesScenario.ScenarioDetailActivity;
import com.example.mainindimovie_ex03.fragments.ScenarioDetailNewsFragment;
import com.example.mainindimovie_ex03.fragments.ScenarioDetailSinarioFragment;

public class ScenarioDetailTabAdapter extends FragmentPagerAdapter {
    public String s_id;
    public String u_id;

    public ScenarioDetailTabAdapter(ScenarioDetailActivity sinarioDetailActivity, FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int i) {
        Bundle bundle = new Bundle(2);
        bundle.putString("s_id", s_id);
        bundle.putString("u_id", u_id);

        switch (i){
            case 0:
                ScenarioDetailSinarioFragment  fragment1 = new ScenarioDetailSinarioFragment();
                fragment1.setArguments(bundle);
                return  fragment1;
            case 1:
                ScenarioDetailNewsFragment fragment2 = new ScenarioDetailNewsFragment();
                fragment2.setArguments(bundle);
                return fragment2;

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
                return "시나리오";
            case 1:
                return "소식";

        }
        return null;
    }

}
