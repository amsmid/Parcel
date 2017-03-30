package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import com.whitebird.parcel.ItemsInListSend;

import java.util.ArrayList;

/**
 * Created by girish on 25/3/17.
 */

class GetSendHistoryList {
    ArrayList<ItemsInListSend> itemsInListSends = new ArrayList<>();
    private static final GetSendHistoryList ourInstance = new GetSendHistoryList();

    static GetSendHistoryList getInstance() {
        return ourInstance;
    }

    private GetSendHistoryList() {
    }
}
