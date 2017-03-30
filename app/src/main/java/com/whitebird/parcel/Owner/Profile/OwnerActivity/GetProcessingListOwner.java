package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import java.util.ArrayList;

/**
 * Created by girish on 19/3/17.
 */

class GetProcessingListOwner {

    public ArrayList<ProcessingListItem> processingListItems = new ArrayList<>();
    private static final GetProcessingListOwner ourInstance = new GetProcessingListOwner();

    static GetProcessingListOwner getInstance() {
        return ourInstance;
    }

    private GetProcessingListOwner() {
    }
}
