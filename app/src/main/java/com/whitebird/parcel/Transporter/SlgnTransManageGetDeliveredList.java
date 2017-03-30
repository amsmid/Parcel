package com.whitebird.parcel.Transporter;

import java.util.ArrayList;

/**
 * Created by girish on 22/3/17.
 */

class SlgnTransManageGetDeliveredList {
    public ArrayList<GtStTransManageDeliveredList> gtStTransManageDeliveredLists = new ArrayList<>();
    private static final SlgnTransManageGetDeliveredList ourInstance = new SlgnTransManageGetDeliveredList();

    static SlgnTransManageGetDeliveredList getInstance() {
        return ourInstance;
    }

    private SlgnTransManageGetDeliveredList() {
    }
}
