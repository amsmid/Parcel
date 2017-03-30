package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by girish on 18/3/17.
 */

class CustomPagerAdapterForOwner extends FragmentPagerAdapter {
    int numOfTabs;
    public CustomPagerAdapterForOwner(FragmentManager fm,int numOfTabs) {
        super(fm);
        this.numOfTabs = numOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GetPendingListOwner.getInstance().pendingListItems.clear();
                return new TabFragmentPending();
            case 1:
                GetAcceptedListOwner.getInstance().acceptedListItems.clear();
                return new TabFragmentAccepted();
            case 2:
                GetReceivedListOwner.getInstance().receivedListItems.clear();
                return new TabFragmentReceived();
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return numOfTabs;
    }
}
