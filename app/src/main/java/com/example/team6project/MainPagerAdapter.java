package com.example.team6project;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.team6project.tab_clinic.ClinicFragment;
import com.example.team6project.tab_movement.MapFragment;
import com.example.team6project.tab_news.NewsFragment;


// ViewPager2에서 사용할 Adapter
// Editor - 김남재
public class MainPagerAdapter extends FragmentStateAdapter {
    int numOfTabs;
    NewsFragment newsFragment;
    ClinicFragment clinicFragment;
    MapFragment mapFragment;

    // 각 탭에서 사용할 Fragment 받아와서 Adapter 생성
    // Editor - 김남재
    public MainPagerAdapter(FragmentActivity fa, NewsFragment nf, ClinicFragment hf, MapFragment mf){
        super(fa);
        newsFragment = nf;
        clinicFragment = hf;
        mapFragment = mf;
        numOfTabs = 3;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return newsFragment;
            case 1:
                return clinicFragment;
            case 2:
                return mapFragment;
            default:
                return new Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return numOfTabs;
    }

}
