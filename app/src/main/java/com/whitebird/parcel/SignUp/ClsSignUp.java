package com.whitebird.parcel.SignUp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.ClsStoreAllDataOfUser;
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.SignIn.ClsSignIn;
import com.whitebird.parcel.Transporter.MainActivityParcelTransporter;

import java.util.HashMap;

public class ClsSignUp extends AppCompatActivity implements ResultInString{

    EditText editTextName,editTextEmail,editTextMobNo,editTextPsswd,editTextConPsswd,editTextAddress,editTextPinCode,editTextState,editTextCity;
    Button signUp,signInFromSignupPage;
    RadioGroup userType;
    RadioButton userOwner,userTransport;
    String userTypeSelected;
    String stringName,stringEmail,stringMobNo,stringPsswd,stringConPsswd,stringAddress,stringPinCode,stringState,stringCity,stringLandmark;
    ClsStoreAllDataOfUser clsStoreAllDataOfUser;
    Spinner spinnerVehicle,spinnerCityArea;
    String stringVehicle,stringCityArea;
    SharedPreferenceUserData sharedPreferenceUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_sign_up);


        //All Edit Text Defined
        editTextName = (EditText)findViewById(R.id.signup_name);
        editTextEmail = (EditText)findViewById(R.id.signup_email);
        editTextMobNo = (EditText)findViewById(R.id.signup_mobile_no);
        editTextPsswd = (EditText)findViewById(R.id.signup_password);
        editTextConPsswd = (EditText)findViewById(R.id.signup_conform_password);
        editTextAddress = (EditText)findViewById(R.id.signup_address);
        editTextPinCode = (EditText)findViewById(R.id.signup_pincode);
        editTextState = (EditText)findViewById(R.id.signup_state);
        editTextCity = (EditText)findViewById(R.id.signup_city);
        userType = (RadioGroup)findViewById(R.id.user_type);
        userOwner = (RadioButton)findViewById(R.id.user_owner);
        userTransport = (RadioButton)findViewById(R.id.user_transport);
        spinnerCityArea = (Spinner)findViewById(R.id.select_city_area);
        spinnerVehicle = (Spinner)findViewById(R.id.select_vehicle);



        //Buttons On SignUp Page

        signUp = (Button)findViewById(R.id.main_sign_up);
        signInFromSignupPage = (Button)findViewById(R.id.signin_from_signup_page);

        signInFromSignupPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignIn = new Intent(ClsSignUp.this,ClsSignIn.class);
                startActivity(intentSignIn);
                finish();
            }
        });

        userTypeSelected ="1";
        SpinnerVisibilityGone();

        userOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userOwner.isChecked()){
                    userOwner.setChecked(true);
                    userTransport.setChecked(false);
                    userTypeSelected ="1";
                    SpinnerVisibilityGone();
                }else if (userTransport.isChecked()){
                    userTransport.setChecked(false);
                    userOwner.setChecked(true);
                    userTypeSelected ="1";
                    SpinnerVisibilityGone();
                }
            }
        });
        userTransport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userTransport.isChecked()){
                    userTransport.setChecked(true);
                    userOwner.setChecked(false);
                    userTypeSelected ="2";
                    SpinnerVisibilityVisible();
                }else if (userOwner.isChecked()){
                    userOwner.setChecked(false);
                    userTransport.setChecked(true);
                    userTypeSelected ="2";
                    SpinnerVisibilityVisible();
                }
            }
        });

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterVehicle = ArrayAdapter.createFromResource(this,
                R.array.vehicletype, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterVehicle.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerVehicle.setAdapter(adapterVehicle);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(this,
                R.array.city_area, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerCityArea.setAdapter(adapterCity);

        spinnerVehicle.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item = spinnerVehicle.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        spinnerCityArea.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String item2 = spinnerCityArea.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });




        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stringName = editTextName.getText().toString();
                stringEmail = editTextEmail.getText().toString();
                stringMobNo = editTextMobNo.getText().toString();
                stringPsswd = editTextPsswd.getText().toString();
                stringConPsswd = editTextConPsswd.getText().toString();
                stringAddress = editTextAddress.getText().toString();
                stringPinCode = editTextPinCode.getText().toString();
                stringState = editTextState.getText().toString();
                stringCity = editTextCity.getText().toString();

                Log.d("strPrf_uid",userTypeSelected);
                if (Validate()) {
                switch (userTypeSelected){
                    case "1":
                        stringCityArea ="empty";
                        stringVehicle = "empty";
                        break;
                    case "2":
                        stringCityArea = spinnerCityArea.getSelectedItem().toString();
                        stringVehicle = spinnerVehicle.getSelectedItem().toString();
                        if (stringCityArea.equals("Inter-City")){
                            stringCityArea = "0";
                        }else if (stringCityArea.equals("Intra-City")){
                            stringCityArea = "1";
                        }
                        if (stringCityArea.isEmpty()){
                            Toast.makeText(getBaseContext(),"Select Spinner City Area", Toast.LENGTH_SHORT).show();
                            return;
                        }else
                            if(stringVehicle.isEmpty()){
                                Toast.makeText(getBaseContext(),"Select Spinner Vehicle type", Toast.LENGTH_SHORT).show();
                                return;
                        }
                        break;

                }


                    String data = stringName + "|" + stringEmail + "|" + stringMobNo + "|" + stringPsswd + "|" + stringAddress + "|" + stringPinCode + "|" + stringState + "|" + stringCity + "|" + userTypeSelected + "|" + stringCityArea + "|" + stringVehicle;
                    String onlineKey = getResources().getString(R.string.signupKey);
                    HashMap<String,String> hashMapData = new HashMap<String, String>();
                    hashMapData.put(getResources().getString(R.string.server_key_name),stringName);
                    hashMapData.put(getResources().getString(R.string.server_key_email),stringEmail);
                    hashMapData.put(getResources().getString(R.string.server_key_mobileNo),stringMobNo);
                    hashMapData.put(getResources().getString(R.string.server_key_password),stringPsswd);
                    hashMapData.put(getResources().getString(R.string.server_key_address),stringAddress);
                    hashMapData.put(getResources().getString(R.string.server_key_pincode),stringPinCode);
                    hashMapData.put(getResources().getString(R.string.server_key_stateName),stringState);
                    hashMapData.put(getResources().getString(R.string.server_key_cityName),stringCity);
                    hashMapData.put(getResources().getString(R.string.server_key_userType),userTypeSelected);
                    hashMapData.put(getResources().getString(R.string.server_key_interCity),stringCityArea);
                    hashMapData.put(getResources().getString(R.string.server_key_vehicle),stringVehicle);
                    new BackgroundTaskForResult(hashMapData, onlineKey, ClsSignUp.this).execute();
                }else {
                    onSignUpFailed();
                }
            }
        });


    }

    private void SpinnerVisibilityGone(){
        spinnerVehicle.setVisibility(View.GONE);
        spinnerCityArea.setVisibility(View.GONE);
    }
    private void SpinnerVisibilityVisible(){
        spinnerVehicle.setVisibility(View.VISIBLE);
        spinnerCityArea.setVisibility(View.VISIBLE);
    }
    private void onSignUpFailed() {
        Toast.makeText(getBaseContext(), "Signup failed", Toast.LENGTH_LONG).show();
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
        if (stringPsswd.isEmpty() || stringPsswd.length() < 4 || stringPsswd.length() > 10) {
            editTextPsswd.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        }else {
            editTextPsswd.setError(null);
        }
        if (!stringPsswd.equals(stringConPsswd)||stringConPsswd.isEmpty()){
            editTextConPsswd.setError("Check Conform Password if it is equal!");
        }else {
            editTextConPsswd.setError(null);
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
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onSignUpSuccess() {
        Toast.makeText(getBaseContext(), "Signup Successfully", Toast.LENGTH_LONG).show();
        String userType =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_userType));
        switch (userType){
            case "1":
                startActivity(new Intent(this, ActMainActivityParcelOwner.class));
                finish();
                break;
            case "2":
                startActivity(new Intent(this, MainActivityParcelTransporter.class));
                finish();
                break;
        }
    }

    @Override
    public void Result(String result,String keyOnline) {
        String success ="0";
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        sharedPreferenceUserData.SaveSharedData("result",result);
        clsStoreAllDataOfUser = new ClsStoreAllDataOfUser(this);
        clsStoreAllDataOfUser.SetUserType(userTypeSelected);
        success=clsStoreAllDataOfUser.GetResult();

        switch (success){
            case "1":
                onSignUpSuccess();
                break;
            default:
                onSignUpFailed();
        }
    }
}
