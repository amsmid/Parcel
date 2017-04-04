package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.whitebird.parcel.R;
import com.whitebird.parcel.SharedPreferenceUserData;

import java.io.File;
import java.util.ArrayList;

public class ActMainManage extends AppCompatActivity {

    SharedPreferenceUserData sharedPreferenceUserData;
    ViewPager viewPagerForMainFragments;
    TabLayout tabLayout;
    ArrayList<PendingListItem> pendingListItemArrayList;
    ArrayList<AcceptedListItem> acceptedListItemArrayList;
    ArrayList<ReceivedListItem> receivedListItemArrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_act_manage);

        pendingListItemArrayList = new ArrayList<>();
        acceptedListItemArrayList = new ArrayList<>();
        receivedListItemArrayList = new ArrayList<>();
        GetPendingListOwner.getInstance().pendingListItems.removeAll(pendingListItemArrayList);
        GetAcceptedListOwner.getInstance().acceptedListItems.removeAll(acceptedListItemArrayList);
        GetReceivedListOwner.getInstance().receivedListItems.removeAll(receivedListItemArrayList);

        tabLayout = (TabLayout) findViewById(R.id.tab_layout_on_main_owner_manage_screen);
        tabLayout.addTab(tabLayout.newTab().setText("Pending Parcel List"));
        tabLayout.addTab(tabLayout.newTab().setText("Accepted Parcel List"));
        tabLayout.addTab(tabLayout.newTab().setText("Receiving Parcel List"));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        viewPagerForMainFragments = (ViewPager)findViewById(R.id.view_pager_for_main_owner_manage_view);
        viewPagerForMainFragments.setAdapter(new CustomPagerAdapterForOwner(getSupportFragmentManager(),tabLayout.getTabCount()));
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode==4){
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_parcel_refresh_on_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.refresh_manage) {
            Intent intent = getIntent();
            finish();
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /*@Override
    public void onBackPressed() {
        super.onBackPressed();
        for (File cacheFile : getCacheDir().listFiles()) {
            if (cacheFile.isFile() && cacheFile.length() > 1000000) cacheFile.delete();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        for (File cacheFile : getCacheDir().listFiles()) {
            if (cacheFile.isFile() && cacheFile.length() > 1000000) cacheFile.delete();
        }
    }*/
}
