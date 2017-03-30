package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import java.util.ArrayList;

/**
 * Created by girish on 25/3/17.
 */

class GetReceiveHistoryList {
    ArrayList<ItemsInListReceive> itemsInListReceives = new ArrayList<>();
    private static final GetReceiveHistoryList ourInstance = new GetReceiveHistoryList();

    static GetReceiveHistoryList getInstance() {
        return ourInstance;
    }

    private GetReceiveHistoryList() {
    }
}
