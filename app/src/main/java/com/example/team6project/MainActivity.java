package com.example.team6project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.example.team6project.tab_clinic.ClinicFragment;
import com.example.team6project.tab_movement.MapFragment;
import com.example.team6project.tab_news.NewsFragment;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

// ViewPager2, TabLayout 활용. 3개의 Fragment 표시
// Editor - 김남재
public class MainActivity extends AppCompatActivity {
    private ViewPager2 viewPager;
    private MainPagerAdapter adapter;
    private TabLayout tabLayout;
    private NewsFragment newsFragment;
    private ClinicFragment clinicFragment;
    private MapFragment mapFragment;

    // 각 탭에 해당하는 Fragment 생성 & ViewPager 및 TabLayout 연결
    // Editor - 김남재
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsFragment = new NewsFragment();
        clinicFragment = new ClinicFragment();
        mapFragment = new MapFragment();

        viewPager = (ViewPager2) findViewById(R.id.viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        adapter = new MainPagerAdapter(this, newsFragment, clinicFragment, mapFragment);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager,
                new TabLayoutMediator.TabConfigurationStrategy() {
                    @Override
                    public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                        tab.setText("tab_" + (position+1));
                    }
                }).attach();
    }

    // 입력받은 위치에 해당하는 화면으로 전환
    // Editor - 김남재
    public void changeView(int position) {
        viewPager.setCurrentItem(position, true);
    }

    // 입력받은 위치에 해당하는 Fragment 반환
    // Editor - 김남재
    public Fragment getTabFragment(int position){
        switch (position){
            case 0:
                return newsFragment;
            case 1:
                return clinicFragment;
            case 2:
                return mapFragment;
            default:
                return null;
        }
    }
}