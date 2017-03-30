package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by girish on 18/3/17.
 */

class ConnectToVolleyServerFragment {
    Fragment fragment;
    Activity activity;
    public ConnectToVolleyServerFragment(Fragment fragment) {
        this.fragment=fragment;
    }

    public void GetResult(final HashMap<String, String> hashMapData, final String onlineKey) {
        activity = fragment.getActivity();
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(activity);

        String url =activity.getResources().getString(R.string.url)+onlineKey;

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        if (response.isEmpty()||response.equals(null)||response.equals("")){
                            return;
                        }
                        // Display the first 500 characters of the response string.
                        ResultInString resultInString= null;
                        resultInString = (ResultInString)fragment;
                        resultInString.Result(response,onlineKey);
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