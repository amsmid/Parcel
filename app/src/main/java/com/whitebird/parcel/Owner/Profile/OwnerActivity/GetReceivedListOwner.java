package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import java.util.ArrayList;

/**
 * Created by girish on 25/3/17.
 */

class GetReceivedListOwner {
    public ArrayList<ReceivedListItem> receivedListItems = new ArrayList<>();
    private static final GetReceivedListOwner ourInstance = new GetReceivedListOwner();

    static GetReceivedListOwner getInstance() {
        return ourInstance;
    }

    private GetReceivedListOwner() {
    }
}
