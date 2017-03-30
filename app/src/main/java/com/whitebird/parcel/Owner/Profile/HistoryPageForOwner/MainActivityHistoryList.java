package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;


import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import com.whitebird.parcel.R;


public class MainActivityHistoryList extends AppCompatActivity {

    ViewPager viewPager;
    PagerTabStrip pagerTabStrip;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_history_list);

        viewPager = (ViewPager)findViewById(R.id.view_pager_history_tabs);
        pagerTabStrip = (PagerTabStrip)findViewById(R.id.pager_tab_strip_id);
        viewPager.setAdapter(new CustomFragmentPagerAdapter(getSupportFragmentManager()));


    }
}
