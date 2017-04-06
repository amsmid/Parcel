package com.whitebird.parcel.AddParcelActivity;

import android.app.Activity;

import com.whitebird.parcel.GetHubListData;
import com.whitebird.parcel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by girish on 15/3/17.
 */

class ClsStoreListofHub {
    private Activity activity;
    private ArrayList<GetHubListData> getHubListDatas;
    ClsStoreListofHub(Activity activity){
        this.activity=activity;
    }

    void SaveHubList(String result){
        getHubListDatas= new ArrayList<>();

        try {
            JSONObject jsonObject = new JSONObject(result);

            JSONArray jsonArrayListHub = jsonObject.getJSONArray(activity.getResources().getString(R.string.server_key_hub));
            int len1 = jsonArrayListHub.length();

            for (int i=0;i<len1;i++){
                JSONObject jsonObject1 = jsonArrayListHub.getJSONObject(i);
                GetHubListData getHubListData = new GetHubListData();
                getHubListData.setUid(jsonObject1.getString(activity.getResources().getString(R.string.server_key_uid)));
                getHubListData.setEmail(jsonObject1.getString(activity.getResources().getString(R.string.server_key_email)));
                getHubListData.setPassword(jsonObject1.getString(activity.getResources().getString(R.string.server_key_password)));
                getHubListData.setName(jsonObject1.getString(activity.getResources().getString(R.string.server_key_name)));
                getHubListData.setMobileNo(jsonObject1.getString(activity.getResources().getString(R.string.server_key_mobileNo)));
                getHubListData.setStateName(jsonObject1.getString(activity.getResources().getString(R.string.server_key_stateName)));
                getHubListData.setCityName(jsonObject1.getString(activity.getResources().getString(R.string.server_key_cityName)));
                getHubListData.setPincode(jsonObject1.getString(activity.getResources().getString(R.string.server_key_pincode)));
                getHubListData.setAddress(jsonObject1.getString(activity.getResources().getString(R.string.server_key_address)));
                getHubListData.setLastLogin(jsonObject1.getString(activity.getResources().getString(R.string.server_key_lastLogin)));
                getHubListData.setLandmark(jsonObject1.getString(activity.getResources().getString(R.string.server_key_landmark)));
                getHubListDatas.add(getHubListData);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        result = "";

    }

    ArrayList<GetHubListData> getGetHubListDatas() {
        return getHubListDatas;
    }
}
