package com.example.team6project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.team6project.tab_hospital.HospitalFragment;
import com.example.team6project.tab_movement.MapFragment;
import com.example.team6project.tab_news.NewsFragment;

public class MainPagerAdapter extends FragmentStateAdapter {
    int numOfTabs;

    public MainPagerAdapter(FragmentActivity fa, int num){
        super(fa);
        numOfTabs = num;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new NewsFragment();
            case 1:
                return new HospitalFragment();
            case 2:
                return new MapFragment();
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }

}
