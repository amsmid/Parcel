package com.whitebird.parcel.Transporter;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.whitebird.parcel.R;
import com.whitebird.parcel.SharedPreferenceUserData;

public class TransActManageParcelDelivery extends AppCompatActivity {

    SharedPreferenceUserData sharedPreferenceUserData;
    ViewPager viewPagerForMainFragments;
    TabLayout tabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_act_manage_parcel_delivery);
        tabLayout = (TabLayout) findViewById(R.id.trans_manage_tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("Accepted Parcel List"));
        tabLayout.addTab(tabLayout.newTab().setText("Delivering Parcel List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPagerForMainFragments = (ViewPager)findViewById(R.id.trans_manage_view_pager);
        viewPagerForMainFragments.setAdapter(new TransManageCustomPagerAdapter(getSupportFragmentManager(),tabLayout.getTabCount()));
        viewPagerForMainFragments.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPagerForMainFragments.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.trans_manage_main_activity_parcel_refresh_on_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.trans_manage_refresh_manage) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
