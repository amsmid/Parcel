package com.whitebird.parcel.Transporter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

/**
 * Created by girish on 22/3/17.
 */

class TransManageCustomPagerAdapter extends FragmentPagerAdapter {
    int numOfTabs;
    public TransManageCustomPagerAdapter(FragmentManager fm, int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.clear();
                return new TransManageTabFragmentAccepted();
            case 1:
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.clear();
                return new TransManageTabFragmentDelivering();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
