package com.whitebird.parcel.Owner.Profile.OwnerActivity;


import android.os.AsyncTask;
import android.support.v4.app.Fragment;


import java.util.HashMap;

/**
 * Created by girish on 18/3/17.
 */

class BackgroundTaskForFragmentResult extends AsyncTask<Void,Void,Void> {
    private String onlineKey, result;
    private HashMap<String,String> getData;
    private ConnectToVolleyServerFragment connectToVolleyServerFragment;
    public BackgroundTaskForFragmentResult(HashMap<String, String> hashMapData, String onlineKey, Fragment fragment) {
        this.getData = hashMapData;
        this.onlineKey = onlineKey;
        //connectToServer = new ConnectToServer(activity);
        connectToVolleyServerFragment = new ConnectToVolleyServerFragment(fragment);
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
