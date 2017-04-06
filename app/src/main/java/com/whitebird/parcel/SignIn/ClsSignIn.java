package com.whitebird.parcel.SignIn;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.ClsStoreAllDataOfUser;
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.SignUp.ClsSignUp;
import com.whitebird.parcel.Transporter.MainActivityParcelTransporter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ClsSignIn extends AppCompatActivity implements ResultInString {

    EditText editTextUsername,editTextPassword;
    CardView signIn,signupRegister;
    String userTypeSelected;
    RadioGroup userType;
    RadioButton userOwner,userTransport;
    SharedPreferenceUserData sharedPreferenceUserData;
    ClsStoreAllDataOfUser clsStoreAllDataOfUser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_sign_in);
        editTextUsername = (EditText)findViewById(R.id.get_email);
        editTextPassword = (EditText)findViewById(R.id.get_password);
        //Sign In Button
        signIn = (CardView) findViewById(R.id.sign_in);

        userType = (RadioGroup)findViewById(R.id.user_type_signIn);
        userOwner = (RadioButton)findViewById(R.id.user_owner_signIn);
        userTransport = (RadioButton)findViewById(R.id.user_transport_signIn);

        userTypeSelected ="1";

        userOwner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userOwner.isChecked()){
                    userOwner.setChecked(true);
                    userTransport.setChecked(false);
                    userTypeSelected ="1";
                }else if (userTransport.isChecked()){
                    userTransport.setChecked(false);
                    userOwner.setChecked(true);
                    userTypeSelected ="1";
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
                }else if (userOwner.isChecked()){
                    userOwner.setChecked(false);
                    userTransport.setChecked(true);
                    userTypeSelected ="2";
                }
            }
        });

        signupRegister = (CardView) findViewById(R.id.signup_register);


        signupRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentSignUp = new Intent(ClsSignIn.this,ClsSignUp.class);
                startActivity(intentSignUp);
                finish();
            }
        });
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = editTextUsername.getText().toString();
                String password = editTextPassword.getText().toString();
                if (!validate()) {
                    onLoginFailed();
                    return;
                }
                HashMap<String,String> hashMapData = new HashMap<>();
                hashMapData.put(getResources().getString(R.string.server_key_email),email);
                hashMapData.put(getResources().getString(R.string.server_key_password),password);
                hashMapData.put(getResources().getString(R.string.server_key_userType),userTypeSelected);
                //String data = email + "|" + password;
                String onlineKey = getResources().getString(R.string.loginKey);
                new BackgroundTaskForResult(hashMapData, onlineKey, ClsSignIn.this).execute();
            }
        });
    }

    public void onLoginFailed() {
        Toast.makeText(getBaseContext(), "Login failed", Toast.LENGTH_LONG).show();
    }
    @Override
    public void onBackPressed() {
        // disable going back to the MainActivity
        moveTaskToBack(true);
    }

    public void onLoginSuccess() {
        Toast.makeText(getBaseContext(), "Login Success", Toast.LENGTH_LONG).show();
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
        String successRead="0";
        Log.d("resultSignIn",result);
        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            sharedPreferenceUserData = new SharedPreferenceUserData(this);
            sharedPreferenceUserData.SaveSharedData(getResources().getString(R.string.server_key_result),result);
            clsStoreAllDataOfUser = new ClsStoreAllDataOfUser(this);
            clsStoreAllDataOfUser.SetUserType(userTypeSelected);
            successRead=clsStoreAllDataOfUser.GetResult();
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Check Connection");
            dialog.setPositiveButton("Ok",null);
            dialog.show();
        }



        switch (successRead){
            case "1":
                onLoginSuccess();
                break;
            default:
                onLoginFailed();
        }
    }

    public boolean validate() {
        boolean valid = true;
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextUsername.setError("enter a valid email address");
            valid = false;
        } else {
            editTextUsername.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            editTextPassword.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }
        return valid;
    }
}
