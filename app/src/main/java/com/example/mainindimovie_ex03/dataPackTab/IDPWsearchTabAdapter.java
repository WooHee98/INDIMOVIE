package com.example.mainindimovie_ex03.dataPackTab;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.mainindimovie_ex03.activitys.IDPWSearchActivity;
import com.example.mainindimovie_ex03.fragments.IdsearchFragment;
import com.example.mainindimovie_ex03.fragments.PasswordsearchFragment;

public class IDPWsearchTabAdapter extends FragmentPagerAdapter {

    public IDPWsearchTabAdapter(IDPWSearchActivity idPasswordSearchActivity, FragmentManager fm) {
        super(fm);
    }

    public Fragment getItem(int i) {
        switch (i){
            case 0:
                return new IdsearchFragment();
            case 1:
                return new PasswordsearchFragment();

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
                return "아이디 찾기";
            case 1:
                return "비밀번호 찾기";

        }
        return null;
    }
}