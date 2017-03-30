package com.whitebird.parcel.Owner.Profile.ProfileActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.ClsStoreAllDataOfUser;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditProfileActivity extends AppCompatActivity implements ResultInString {

    String stringName,stringEmail,stringMobNo,stringPsswd,stringConPsswd,stringAddress,stringPinCode,stringState,stringCity;
    EditText editTextName,editTextEmail,editTextMobNo,editTextPsswd,editTextAddress,editTextPinCode,editTextState,editTextCity;
    SharedPreferenceUserData sharedPreferenceUserData;
    ClsStoreAllDataOfUser clsStoreAllDataOfUser;
    String oldPassword,newPassword,conPassword;
    String uid;
    EditText editTextOldPassword,editTextNewPassword,editTextConPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        editTextName = (EditText)findViewById(R.id.profile_o_name);
        editTextEmail = (EditText)findViewById(R.id.profile_o_email);
        editTextMobNo = (EditText)findViewById(R.id.profile_o_mobile_no);
        editTextPsswd = (EditText)findViewById(R.id.profile_o_password);
        editTextAddress = (EditText)findViewById(R.id.profile_o_address);
        editTextPinCode = (EditText)findViewById(R.id.profile_o_pincode);
        editTextState = (EditText)findViewById(R.id.profile_o_state);
        editTextCity = (EditText)findViewById(R.id.profile_o_city);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);

        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        editTextName.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_name)));
        editTextEmail.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_email)));
        editTextMobNo.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_mobileNo)));
        editTextPsswd.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_password)));
        editTextAddress.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_address)));
        editTextPinCode.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_pincode)));
        editTextState.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_stateName)));
        editTextCity.setText(sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_cityName)));

    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
        .setMessage("Do You Want To Close Without Saving Profile")
        .setCancelable(false)
        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                StartProfileViewPage();
            }
        })
                .setNegativeButton("No", null)
                .show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.save_new_profile,menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id==R.id.menu_save_profile){

            new AlertDialog.Builder(this)
                    .setMessage("Do You Want To Save New Profile")
                    .setCancelable(false)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            StartSavingProfile();
                        }
                    })
                    .setNegativeButton("No", null)
                    .show();

        }else if(id==R.id.menu_change_password){

            ChangePasword();

        }
        return true;
    }

    private void ChangePasword() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = getLayoutInflater();
        View view = layoutInflater.inflate(R.layout.change_password_layout,null);
        editTextOldPassword = (EditText)view.findViewById(R.id.menu_change_password_old);
        editTextNewPassword = (EditText)view.findViewById(R.id.menu_change_password_new);
        editTextConPassword = (EditText)view.findViewById(R.id.menu_change_password_con);
        builder.setView(view);
        builder.setTitle("Change Password :");
        builder.setPositiveButton("Change", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                oldPassword = editTextOldPassword.getText().toString();
                newPassword = editTextNewPassword.getText().toString();
                conPassword = editTextConPassword.getText().toString();
                if (ValidatePassword()){
                    String data = uid + "|" + oldPassword + "|" + newPassword;
                    String onlineKey = getResources().getString(R.string.updateProfilePasswordKey);
                    HashMap<String,String> hashMapDataPass = new HashMap<String, String>();
                    hashMapDataPass.put(getResources().getString(R.string.server_key_uid),uid);
                    hashMapDataPass.put(getResources().getString(R.string.server_key_oldPassword),oldPassword);
                    hashMapDataPass.put(getResources().getString(R.string.server_key_newPassword),newPassword);
                    new BackgroundTaskForResult(hashMapDataPass, onlineKey, EditProfileActivity.this).execute();
                }else
                {
                    new AlertDialog.Builder(EditProfileActivity.this)
                            .setMessage("Password Not Set! Check Password Input")
                            .setCancelable(false)
                            .setNegativeButton("Ok", null)
                            .show();
                }
            }
        });
        builder.setNegativeButton("Cancel",null);
        builder.show();
    }



    private void StartSavingProfile(){
        stringName = editTextName.getText().toString();
        stringEmail = editTextEmail.getText().toString();
        stringMobNo = editTextMobNo.getText().toString();
        stringAddress = editTextAddress.getText().toString();
        stringPinCode = editTextPinCode.getText().toString();
        stringState = editTextState.getText().toString();
        stringCity = editTextCity.getText().toString();

        if (!Validate()){
            return;
        }
        String data = uid + "|" + stringName + "|" + stringEmail + "|" + stringMobNo + "|" + stringAddress + "|" + stringPinCode + "|" + stringState + "|" + stringCity;
        String onlineKey = getResources().getString(R.string.updateProfileKey);
        HashMap<String,String> hashMapData = new HashMap<String, String>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_user),"1");
        hashMapData.put(getResources().getString(R.string.server_key_name),stringName);
        hashMapData.put(getResources().getString(R.string.server_key_email),stringEmail);
        hashMapData.put(getResources().getString(R.string.server_key_mobileNo),stringMobNo);
        hashMapData.put(getResources().getString(R.string.server_key_address),stringAddress);
        hashMapData.put(getResources().getString(R.string.server_key_pincode),stringPinCode);
        hashMapData.put(getResources().getString(R.string.server_key_stateName),stringState);
        hashMapData.put(getResources().getString(R.string.server_key_cityName),stringCity);
        new BackgroundTaskForResult(hashMapData, onlineKey, EditProfileActivity.this).execute();
    }

    private boolean ValidatePassword() {
        boolean validPass = true;

        if (oldPassword.isEmpty() || oldPassword.length() < 4 || oldPassword.length() > 10) {
            editTextOldPassword.setError("between 4 and 10 alphanumeric characters");
            validPass = false;
        }else {
            editTextOldPassword.setError(null);
        }
        if (newPassword.isEmpty() || newPassword.length() < 4 || newPassword.length() > 10) {
            editTextNewPassword.setError("between 4 and 10 alphanumeric characters");
            validPass = false;
        }else {
            editTextNewPassword.setError(null);
        }
        if (!newPassword.equals(conPassword)||conPassword.isEmpty()){
            editTextConPassword.setError("Check Conform Password if it is equal!");
            validPass = false;
        }else {
            editTextConPassword.setError(null);
        }
        return validPass;
    }


    private boolean Validate() {
        boolean valid = true;

        if (stringName.isEmpty() || stringName.length() < 3) {
            editTextName.setError("at least 3 characters");
            valid = false;
        }else {
            editTextName.setError(null);
        }
        if (stringEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(stringEmail).matches()) {
            editTextEmail.setError("enter a valid email address");
            valid = false;
        }else {
            editTextEmail.setError(null);
        }
        if (stringMobNo.isEmpty()||stringMobNo.length()!=10){
            editTextMobNo.setError("Check Mobile No.");
            valid = false;
        }else {
            editTextMobNo.setError(null);
        }
        if (stringAddress.isEmpty() || stringAddress.length() < 4 || stringAddress.length() > 40) {
            editTextAddress.setError("Put Proper Address");
            valid = false;
        }else {
            editTextAddress.setError(null);
        }
        if (stringPinCode.isEmpty()||stringPinCode.length()!=6){
            editTextPinCode.setError("Check Pin No.");
            valid = false;
        }else {
            editTextPinCode.setError(null);
        }
        if (stringState.isEmpty() || stringState.length() < 4 || stringState.length() > 15) {
            editTextState.setError("Put Correct State");
            valid = false;
        }else {
            editTextState.setError(null);
        }
        if (stringCity.isEmpty() || stringCity.length() < 4 || stringCity.length() > 15) {
            editTextCity.setError("Put Correct City");
            valid = false;
        }else {
            editTextCity.setError(null);
        }
        return valid;
    }

    @Override
    public void Result(String result,String keyOnline) {
        String success ="0";
        Log.d("resultOnProfileEdit",result);
        if (keyOnline.equals(getResources().getString(R.string.updateProfileKey))) {
            sharedPreferenceUserData = new SharedPreferenceUserData(this);
            sharedPreferenceUserData.SaveSharedData(getResources().getString(R.string.server_key_result), result);
            clsStoreAllDataOfUser = new ClsStoreAllDataOfUser(this);
            success = clsStoreAllDataOfUser.GetResult();
        }else if (keyOnline.equals(getResources().getString(R.string.updateProfilePasswordKey))){
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(result);
                success=jsonObject.getString(getResources().getString(R.string.server_key_success));
            } catch (JSONException e) {
                success="0";
                e.printStackTrace();
            }
            //JSONObject json_success = jsonObject.getJSONObject("success");

        }
        Log.d("successOnProfileEdit",success);
        switch (success){
            case "1":
                Toast.makeText(getBaseContext(), "Profile Changes Successfully", Toast.LENGTH_SHORT).show();
                StartProfileViewPage();
                break;
            default:
                new AlertDialog.Builder(EditProfileActivity.this)
                        .setMessage("Profile Not Save")
                        .setCancelable(false)
                        .setNegativeButton("Ok", null)
                        .show();
                Toast.makeText(getBaseContext(), "Profile Not Save", Toast.LENGTH_SHORT).show();
        }
    }

    private void StartProfileViewPage(){
        Intent intentProfileView = new Intent(EditProfileActivity.this,ClsProfileDisplay.class);
        startActivity(intentProfileView);
        finish();
    }
}
