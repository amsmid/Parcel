package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.whitebird.parcel.ItemsInListSend;

import java.util.ArrayList;

/**
 * Created by girish on 14/3/17.
 */

class CustomFragmentPagerAdapter extends FragmentPagerAdapter {

    private ArrayList<ItemsInListSend> arrayListSend;
    private ArrayList<ItemsInListSend> arrayListReceive;
    private String[] stringsTabs = {"Send","Receive"};
    CustomFragmentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                GetSendHistoryList.getInstance().itemsInListSends.clear();
                return new FragmentSendList();
            case 1:
                GetReceiveHistoryList.getInstance().itemsInListReceives.clear();
                return new FragmentReceiveList();
        }
        return null;
    }

    @Override
    public int getCount() {
        return stringsTabs.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return stringsTabs[position];
    }
}
