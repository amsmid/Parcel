package com.whitebird.parcel.Transporter;

import android.app.Activity;

import com.whitebird.parcel.GetHubListData;
import com.whitebird.parcel.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by girish on 21/3/17.
 */

public class ClsStoreHubListTrans {
    ArrayList<GetHubListData> getHubListDatas;
    Activity activity;
    ClsStoreHubListTrans(Activity activity){
        this.activity=activity;
    }

    public void SaveHubList(String result){
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
                getHubListData.setMobileNo(jsonObject1.getString(activity.getResources().getString(R.string.server_key_name)));
                getHubListData.setStateName(jsonObject1.getString(activity.getResources().getString(R.string.server_key_stateName)));
                getHubListData.setCityName(jsonObject1.getString(activity.getResources().getString(R.string.server_key_cityName)));
                getHubListData.setPincode(jsonObject1.getString(activity.getResources().getString(R.string.server_key_pincode)));
                getHubListData.setAddress(jsonObject1.getString(activity.getResources().getString(R.string.server_key_address)));
                getHubListData.setLastLogin(jsonObject1.getString(activity.getResources().getString(R.string.server_key_lastLogin)));
                getHubListData.setVehicle(jsonObject1.getString(activity.getResources().getString(R.string.server_key_vehicle)));
                getHubListData.setInterCity(jsonObject1.getString(activity.getResources().getString(R.string.server_key_interCity)));
                getHubListData.setLandmark(jsonObject1.getString(activity.getResources().getString(R.string.server_key_landmark)));
                getHubListDatas.add(getHubListData);
            }
        } catch (JSONException e) {
            getHubListDatas = new ArrayList<>();
            e.printStackTrace();
        }

    }

    public ArrayList<GetHubListData> getGetHubListDatas() {
        return getHubListDatas;
    }
}
