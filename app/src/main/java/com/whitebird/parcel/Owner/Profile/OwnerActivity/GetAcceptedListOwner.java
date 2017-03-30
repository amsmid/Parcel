package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import java.util.ArrayList;

/**
 * Created by girish on 22/3/17.
 */

class GetAcceptedListOwner {
    public ArrayList<AcceptedListItem> acceptedListItems = new ArrayList<>();
    private static final GetAcceptedListOwner ourInstance = new GetAcceptedListOwner();

    static GetAcceptedListOwner getInstance() {
        return ourInstance;
    }

    private GetAcceptedListOwner() {
    }
}
