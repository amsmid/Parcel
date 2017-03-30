package com.whitebird.parcel;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by girish on 11/3/17.
 */

public class ClsStoreAllDataOfUser {
    private Activity activity;
    private String userTypeSelected;
    private String strPrf_interCity,strPrf_vehicleType;
    public ClsStoreAllDataOfUser(Activity activity){
        this.activity=activity;
    }

    public void SetUserType(String userTypeSelected){
        this.userTypeSelected=userTypeSelected;
    }

    public String GetResult(){
        String str_success;
        SharedPreferenceUserData sharedPreferenceUserData = new SharedPreferenceUserData(activity);
        String result = sharedPreferenceUserData.getMyLoginUserData(activity.getResources().getString(R.string.server_key_result));

        try {
            JSONObject jsonObject = new JSONObject(result);
            //JSONObject json_success = jsonObject.getJSONObject("success");
            str_success=jsonObject.getString(activity.getResources().getString(R.string.server_key_success));
            String str_message=jsonObject.getString(activity.getResources().getString(R.string.server_key_message));
            Toast.makeText(activity.getBaseContext(), str_message, Toast.LENGTH_LONG).show();

            Log.d("MessageJson",str_message);
            JSONObject jsonObjectProfile = jsonObject.getJSONObject(activity.getResources().getString(R.string.server_key_Profile));
            String strPrf_uid = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_uid));
            String strPrf_name = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_name));
            String strPrf_email = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_email));
            String strPrf_mobileNo = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_mobileNo));
            String strPrf_password = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_password));
            String strPrf_address = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_address));
            String strPrf_pincode = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_pincode));
            String strPrf_stateName = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_stateName));
            String strPrf_cityName = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_cityName));
            String strPrf_type = userTypeSelected;
            if (userTypeSelected.equals("2")) {
                strPrf_interCity = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_interCity));
                strPrf_vehicleType = jsonObjectProfile.getString(activity.getResources().getString(R.string.server_key_vehicle));
            }
            Log.d("strPrf_uid",strPrf_uid);


            //Save All Data To sharedPreference


            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_success),str_message);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_uid),strPrf_uid);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_name),strPrf_name);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_email),strPrf_email);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_mobileNo),strPrf_mobileNo);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_password),strPrf_password);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_address),strPrf_address);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_pincode),strPrf_pincode);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_stateName),strPrf_stateName);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_cityName),strPrf_cityName);
            sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_userType),strPrf_type);
            if (userTypeSelected.equals("2")) {
                sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_interCity), strPrf_interCity);
                sharedPreferenceUserData.SaveSharedData(activity.getResources().getString(R.string.server_key_vehicle), strPrf_vehicleType);
            }

        } catch (JSONException e) {
            e.printStackTrace();
            str_success="0";
            Log.d("ResultJson",e.getMessage());
        }
        return str_success;
    }
}
