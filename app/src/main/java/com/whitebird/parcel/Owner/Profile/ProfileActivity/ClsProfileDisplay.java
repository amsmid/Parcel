package com.whitebird.parcel.Owner.Profile.ProfileActivity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.whitebird.parcel.R;
import com.whitebird.parcel.SharedPreferenceUserData;

public class ClsProfileDisplay extends AppCompatActivity {

    TextView editTextName,editTextEmail,editTextMobNo,editTextPsswd,editTextAddress,editTextPinCode,editTextState,editTextCity;
    SharedPreferenceUserData sharedPreferenceUserData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_profile_display);
        editTextName = (TextView) findViewById(R.id.view_profile_o_name);
        editTextEmail = (TextView)findViewById(R.id.view_profile_o_email);
        editTextMobNo = (TextView)findViewById(R.id.view_profile_o_mobile_no);
        editTextAddress = (TextView)findViewById(R.id.view_profile_o_address);
        editTextPinCode = (TextView)findViewById(R.id.view_profile_o_pincode);
        editTextState = (TextView)findViewById(R.id.view_profile_o_state);
        editTextCity = (TextView)findViewById(R.id.view_profile_o_city);



        //If Result Needs To Be Recheck Before Profile Display Put Store All Data Cls Here

        //Get All Need Data From Share Preference
        sharedPreferenceUserData = new SharedPreferenceUserData(this);

        editTextName.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_name)));
        editTextEmail.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_email)));
        editTextMobNo.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_mobileNo)));
        editTextAddress.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_address)));
        editTextPinCode.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_pincode)));
        editTextState.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_stateName)));
        editTextCity.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_cityName)));

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.profile_menu_for_edit,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_edit_profile){
            Intent intentEditProfile = new Intent(this,EditProfileActivity.class);
            startActivity(intentEditProfile);
            finish();
        }
        return true;
    }
}
