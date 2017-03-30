package com.whitebird.parcel.Transporter.TransHistoryPage;

import java.util.ArrayList;

/**
 * Created by girish on 25/3/17.
 */

class GetTransHistoryList {
    ArrayList<TransHistoryListItem> transHistoryListItems = new ArrayList<>();
    private static final GetTransHistoryList ourInstance = new GetTransHistoryList();

    static GetTransHistoryList getInstance() {
        return ourInstance;
    }

    private GetTransHistoryList() {
    }
}
