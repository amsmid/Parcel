package com.whitebird.parcel.Transporter;

import com.whitebird.parcel.TransPendingList;

import java.util.ArrayList;

/**
 * Created by girish on 28/3/17.
 */

class SlgnTransPendingParcelList {
    public ArrayList<TransPendingList> transPendingLists = new ArrayList<>();
    private static final SlgnTransPendingParcelList ourInstance = new SlgnTransPendingParcelList();

    static SlgnTransPendingParcelList getInstance() {
        return ourInstance;
    }

    private SlgnTransPendingParcelList() {
    }
}
