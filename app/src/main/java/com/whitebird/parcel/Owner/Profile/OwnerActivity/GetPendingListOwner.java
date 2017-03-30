package com.whitebird.parcel.Owner.Profile.OwnerActivity;


import java.util.ArrayList;

/**
 * Created by girish on 18/3/17.
 */

class GetPendingListOwner {

    public ArrayList<PendingListItem> pendingListItems = new ArrayList<>();
    private static final GetPendingListOwner ourInstance = new GetPendingListOwner();

    static GetPendingListOwner getInstance() {
        return ourInstance;
    }

    private GetPendingListOwner() {
    }
}
