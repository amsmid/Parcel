package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;


import java.util.HashMap;

/**
 * Created by girish on 25/3/17.
 */

class BackgroundTaskForHistoryFragmentResult extends AsyncTask<Void,Void,Void> {
    private String onlineKey;
    private HashMap<String,String> getData;
    private ConnectToVolleyServerHistoryFragment connectToVolleyServerFragment;
    BackgroundTaskForHistoryFragmentResult(HashMap<String, String> hashMapData, String onlineKey, Fragment fragment) {
        this.getData = hashMapData;
        this.onlineKey = onlineKey;
        //connectToServer = new ConnectToServer(activity);
        connectToVolleyServerFragment = new ConnectToVolleyServerHistoryFragment(fragment);
    }

    @Override
    protected Void doInBackground(Void... params) {
        connectToVolleyServerFragment.GetResult(getData,onlineKey);
        /*try {
            result = connectToServer.GetResult(getData, onlineKey);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
