package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.Transporter.MyGpsTrackerService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by girish on 23/3/17.
 */

public class ConnectToVolleyServerGpsLocSend {
    MyGpsTrackerService myGpsTrackerService;
    RequestQueue queue;
    public ConnectToVolleyServerGpsLocSend(MyGpsTrackerService myGpsTrackerService){
        this.myGpsTrackerService = myGpsTrackerService;
    }

    void GetResult(final HashMap<String,String> hashMapData, final String onlineKey){
        if (myGpsTrackerService==null) {
            return;
        }
        // Instantiate the RequestQueue.
        if (queue==null)
            queue = Volley.newRequestQueue(myGpsTrackerService);

        String url =myGpsTrackerService.getResources().getString(R.string.url)+onlineKey;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()||response.equals(null)||response.equals("")){
                            return;
                        }
                        if (myGpsTrackerService!=null){
                            // Display the first 500 characters of the response string.
                            ResultInString resultInString= null;
                            resultInString = (ResultInString)myGpsTrackerService;
                            resultInString.Result(response,onlineKey);
                            queue.getCache().clear();
                            hashMapData.clear();
                            new DiskBasedCache(myGpsTrackerService.getCacheDir()).clear();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                return hashMapData;
            }
        };
// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }
}
