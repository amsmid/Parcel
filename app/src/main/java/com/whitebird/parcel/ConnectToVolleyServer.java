package com.whitebird.parcel;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by girish on 18/3/17.
 */

public class ConnectToVolleyServer {
    private Activity activity;
    private ProgressDialog pd;
    private RequestQueue queue;
    ConnectToVolleyServer(Activity activity, ProgressDialog pd){
        this.pd=pd;
        this.activity=activity;
    }

    void GetResult(final HashMap<String,String> hashMapData, final String onlineKey){
        // Instantiate the RequestQueue.
        if (activity==null) {
            return;
        }
        if (queue==null)
            queue = Volley.newRequestQueue(activity);

        String url =activity.getResources().getString(R.string.url)+onlineKey;


// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        pd.dismiss();
                        if (response.isEmpty()||response.equals(null)||response.equals("")){
                            return;
                        }
                        // Display the first 500 characters of the response string.
                        if(activity != null){
                            ResultInString resultInString= null;
                            resultInString = (ResultInString)activity;
                            resultInString.Result(response,onlineKey);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                pd.dismiss();
                AlertDialog.Builder dialog = new AlertDialog.Builder(activity);
                dialog.setTitle("Check Connection");
                dialog.setPositiveButton("Ok",null);
                dialog.show();
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
