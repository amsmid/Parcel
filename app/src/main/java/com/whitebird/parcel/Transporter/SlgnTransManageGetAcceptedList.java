package com.whitebird.parcel.Transporter;

import java.util.ArrayList;

/**
 * Created by girish on 22/3/17.
 */

class SlgnTransManageGetAcceptedList {
    public ArrayList<GtStTransManageAcceptedList> gtStTransManageAcceptedLists = new ArrayList<>();
    private static final SlgnTransManageGetAcceptedList ourInstance = new SlgnTransManageGetAcceptedList();

    static SlgnTransManageGetAcceptedList getInstance() {
        return ourInstance;
    }

    private SlgnTransManageGetAcceptedList() {
    }
}
