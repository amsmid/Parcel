package com.whitebird.parcel;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.view.View;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;

/**
 * Created by girish on 8/3/17.
 */

public class BackgroundTaskForResult extends AsyncTask<Void,Void,Void> {

    private String onlineKey, result;
    private HashMap<String,String> getData;
    private ConnectToServer connectToServer;
    private ConnectToVolleyServer connectToVolleyServer;
    private Activity activity;
    private ProgressDialog pd;

    public BackgroundTaskForResult(HashMap<String,String> data, String onlineKey, Activity activity) {
        this.getData = data;
        this.onlineKey = onlineKey;
        this.activity=activity;
        //connectToServer = new ConnectToServer(activity);
        pd = new ProgressDialog(activity);
        connectToVolleyServer = new ConnectToVolleyServer(activity,pd);

    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
        pd.setMessage("Processing...");
        pd.setCancelable(false);
        pd.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        connectToVolleyServer.GetResult(getData,onlineKey);
        /*try {
            result = connectToServer.GetResult(getData, onlineKey);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
