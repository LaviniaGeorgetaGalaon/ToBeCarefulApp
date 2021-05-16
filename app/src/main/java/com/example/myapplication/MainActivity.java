package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.LinearLayout;

import com.example.myapplication.adapter.ViewPagerAdapter;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    ViewPagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        addTabs();


    }

    private void init() {
        Toolbar toolbar =findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);
        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabLayout);

    }

    private void addTabs() {
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.information));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.to_do_list));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.calendar));
        tabLayout.addTab(tabLayout.newTab().setIcon(R.drawable.profile));
        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        pagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), tabLayout.getTabCount());
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.getTabAt(0).setIcon(R.drawable.information_fill);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());

                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.information_fill);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.to_do_list_fill);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.calendar_fill);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.profile__fill);
                        break;


                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.information);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.to_do_list);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.calendar);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.profile);
                        break;


                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        tabLayout.getTabAt(0).setIcon(R.drawable.information_fill);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setIcon(R.drawable.to_do_list_fill);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setIcon(R.drawable.calendar_fill);
                        break;
                    case 3:
                        tabLayout.getTabAt(3).setIcon(R.drawable.profile__fill);
                        break;


                }
            }
        });





    }
}