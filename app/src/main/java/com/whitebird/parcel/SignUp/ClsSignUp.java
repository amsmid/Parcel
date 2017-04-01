package com.whitebird.parcel.SignUp;

import android.app.Dialog;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.ArraySet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.ClsStoreAllDataOfUser;
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.SignIn.ClsSignIn;
import com.whitebird.parcel.Transporter.MainActivityParcelTransporter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ClsSignUp extends AppCompatActivity implements ResultInString{

    EditText editTextName,editTextEmail,editTextMobNo,editTextPsswd,editTextConPsswd,editTextAddress,editTextPinCode;
    Button signUp,signInFromSignupPage;
    TextView editTextState;
    RadioGroup userType;
    RadioButton userOwner,userTransport;
    String userTypeSelected;
    String stringName,stringEmail,stringMobNo,stringPsswd,stringConPsswd,stringAddress,stringPinCode,stringState,stringCity,stringLandmark;
    ClsStoreAllDataOfUser clsStoreAllDataOfUser;
    Spinner spinnerVehicle,spinnerCityArea,editTextCity;
    String stringVehicle,stringCityArea;
    SharedPreferenceUserData sharedPreferenceUserData;
    ArrayList<String> stateNames,stateId,cityName,cityId;
    AlertDialog.Builder builder;
    Dialog dialogDis;
    View viewSearch;
    ListView listViewState;
    BaseAdapter setAdapter;
    int go = 1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_sign_up);


        stateNames = new ArrayList<>();
        stateId = new ArrayList<>();
        cityName = new ArrayList<>();
        cityId = new ArrayList<>();
        //All Edit Text Defined
        editTextName = (EditText)findViewById(R.id.signup_name);
        editTextEmail = (EditText)findViewById(R.id.signup_email);
        editTextMobNo = (EditText)findViewById(R.id.signup_mobile_no);
        editTextPsswd = (EditText)findViewById(R.id.signup_password);
        editTextConPsswd = (EditText)findViewById(R.id.signup_conform_password);
        editTextAddress = (EditText)findViewById(R.id.signup_address);
        editTextPinCode = (EditText)findViewById(R.id.signup_pincode);
        editTextState = (TextView)findViewById(R.id.signup_state);
        editTextCity = (Spinner)findViewById(R.id.signup_city);
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



        editTextState.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                go = 1;
                String onlineKey = getResources().getString(R.string.getStateName);
                HashMap<String,String> hashMapData = new HashMap<String, String>();
                hashMapData.put(getResources().getString(R.string.server_key_stateId),"0");
                new BackgroundTaskForResult(hashMapData, onlineKey, ClsSignUp.this).execute();
                editTextState.setError(null);
                ListBuilderOnPopUp();
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
                stringCity = editTextCity.getSelectedItem().toString();

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

    private void ListBuilderOnPopUp() {
        builder = new AlertDialog.Builder(this);
        LayoutInflater layout = getLayoutInflater();
        viewSearch = layout.inflate(R.layout.search_list_layout, null);
        listViewState = (ListView) viewSearch.findViewById(R.id.list_of_hub_item_in_popup);

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
        }/*
        if (stringCity.isEmpty() || stringCity.length() < 4 || stringCity.length() > 15) {
            editTextCity.setError("Put Correct City");
            valid = false;
        }else {
            editTextCity.setError(null);
        }*/
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
        if (keyOnline.equals(getResources().getString(R.string.signupKey))) {
            clsStoreAllDataOfUser = new ClsStoreAllDataOfUser(this);
            clsStoreAllDataOfUser.SetUserType(userTypeSelected);
            success = clsStoreAllDataOfUser.GetResult();

            switch (success) {
                case "1":
                    onSignUpSuccess();
                    break;
                default:
                    onSignUpFailed();
            }
        }else if (keyOnline.equals(getResources().getString(R.string.getStateName))){
            Log.d("stateNameRes",result);
            try {
                stateNames = new ArrayList<>();
                stateId = new ArrayList<>();
                cityName = new ArrayList<>();
                cityId = new ArrayList<>();
                JSONObject jsonObject = new JSONObject(result);
                if (go == 2){
                    JSONArray city = jsonObject.getJSONArray("City");
                    int len2 = city.length();
                    for (int i=0;i<len2;i++){
                        JSONObject object = city.getJSONObject(i);
                        cityName.add(object.getString("cityName"));
                        cityId.add(object.getString("cityId"));
                    }
                    ArrayAdapter<String> arrayAdapterCityName = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, cityName);
                    editTextCity.setAdapter(arrayAdapterCityName);
                }else if (go==1){
                    JSONArray state = jsonObject.getJSONArray(getResources().getString(R.string.server_key_State));
                    int len1 = state.length();
                    for (int i = 0; i < len1; i++) {
                        JSONObject object = state.getJSONObject(i);
                        stateNames.add(object.getString("stateName"));
                        stateId.add(object.getString("stateId"));
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1, android.R.id.text1, stateNames);
                    listViewState.setAdapter(adapter);
                    listViewState.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            go = 2;
                            editTextState.setText(stateNames.get(position));
                            String onlineKey = getResources().getString(R.string.getStateName);
                            HashMap<String,String> hashMapData = new HashMap<String, String>();
                            hashMapData.put(getResources().getString(R.string.server_key_stateId),stateId.get(position));
                            new BackgroundTaskForResult(hashMapData, onlineKey, ClsSignUp.this).execute();
                            dialogDis.dismiss();
                        }
                    });
                    //Set All List Here And Search it
                    builder.setView(viewSearch);
                    builder.setNegativeButton("Cancel", null);
                    dialogDis = builder.create();
                    dialogDis.show();
                }


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }
}
