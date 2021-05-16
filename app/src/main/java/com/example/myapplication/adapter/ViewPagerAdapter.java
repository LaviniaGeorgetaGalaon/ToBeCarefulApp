package com.example.myapplication.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapplication.fragments.Calendar;
import com.example.myapplication.fragments.Home;
import com.example.myapplication.fragments.Pastile;
import com.example.myapplication.fragments.Profil;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    int noOfTabs;

    public ViewPagerAdapter(@NonNull FragmentManager fm, int noOfTabs) {
        super(fm);
        this.noOfTabs = noOfTabs;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                return new Home();
            case 1:
                return new Pastile();
            case 2:
                return new Calendar();
            case 3 :
                return new Profil();
            default:
                return null;

        }

    }

    @Override
    public int getCount() {
        return noOfTabs;
    }
}
