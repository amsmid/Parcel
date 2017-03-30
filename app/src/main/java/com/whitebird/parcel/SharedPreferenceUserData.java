package com.whitebird.parcel;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;



/**
 * Created by girish on 9/3/17.
 */

public class SharedPreferenceUserData {
    public static final String MY_LOGIN_USER_DATA = "myloginuserdata";
    Activity activity;
    SharedPreferences sharedPref;
    public SharedPreferenceUserData(Activity activity){
        this.activity= activity;
        sharedPref = activity.getSharedPreferences(MY_LOGIN_USER_DATA,Context.MODE_PRIVATE);

    }

    public void SaveSharedData(String KEY,String VALUE){
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(KEY, VALUE);
        editor.commit();
    }

    public String getMyLoginUserData(String KEY){
        String data = sharedPref.getString(KEY,"");
        return data;
    }

    public void ClearAllSharedPreferenceData(){
        sharedPref.edit().clear().commit();
    }
}
