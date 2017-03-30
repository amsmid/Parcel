package com.whitebird.parcel.Transporter;

import android.os.AsyncTask;
import android.support.v4.app.Fragment;


import java.util.HashMap;

/**
 * Created by girish on 22/3/17.
 */

class TransManageBackgroundTaskForFragmentResult extends AsyncTask<Void,Void,Void> {
    private String onlineKey, result;
    private HashMap<String,String> getData;
    private TransManageConnectToVolleyServerFragment transManageConnectToVolleyServerFragment;
    public TransManageBackgroundTaskForFragmentResult(HashMap<String, String> hashMapData, String onlineKey, Fragment fragment) {
        this.getData = hashMapData;
        this.onlineKey = onlineKey;
        //connectToServer = new ConnectToServer(activity);
        transManageConnectToVolleyServerFragment = new TransManageConnectToVolleyServerFragment(fragment);
    }

    @Override
    protected Void doInBackground(Void... params) {
        transManageConnectToVolleyServerFragment.GetResult(getData,onlineKey);
        /*try {
            result = connectToServer.GetResult(getData, onlineKey);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return null;
    }
}
