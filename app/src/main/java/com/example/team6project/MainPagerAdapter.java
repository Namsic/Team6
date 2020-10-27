package com.example.team6project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.team6project.tab_movement.MapFragment;
import com.example.team6project.tab_news.NewsFragment;

public class MainPagerAdapter extends FragmentStatePagerAdapter {
    int numOfTabs;

    public MainPagerAdapter(FragmentManager fm, int num){
        super(fm);
        numOfTabs = num;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new NewsFragment();
            case 1:
                return new NewsFragment();
            case 2:
                return new MapFragment();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
