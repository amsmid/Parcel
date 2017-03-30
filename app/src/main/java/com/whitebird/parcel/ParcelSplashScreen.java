package com.whitebird.parcel;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.SignIn.ClsSignIn;
import com.whitebird.parcel.Transporter.MainActivityParcelTransporter;

public class ParcelSplashScreen extends AppCompatActivity {

    SharedPreferenceUserData sharedPreferenceUserData;
    private static boolean splashLoaded = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!splashLoaded) {
            //Code To On Full Screen Mode
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
            setContentView(R.layout.activity_parcel_splash_screen);
            int secondsDelayed = 1;
            new Handler().postDelayed(new Runnable() {
                public void run() {
                    sharedPreferenceUserData = new SharedPreferenceUserData(ParcelSplashScreen.this);
                    String uid =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
                    try {
                        if (uid == null) {
                            StartSignin();
                        } else if (uid.equals("0") || uid.isEmpty()||uid.equals("")) {
                            StartSignin();
                        }else {
                            StartMainActivity();
                        }
                    }catch (Exception e){
                        assert uid != null;
                        if (uid.equals("0") || uid.isEmpty()||uid.equals("")) {
                            StartSignin();
                        }else {
                            StartMainActivity();
                        }
                    }

                    //StartMainActivity();
                }
            }, secondsDelayed * 500);

            splashLoaded = true;
        }
        else {
            sharedPreferenceUserData = new SharedPreferenceUserData(this);
            String uid =sharedPreferenceUserData.getMyLoginUserData("uid");
            try {
                if (uid == null) {
                    StartSignin();
                } else if (uid.equals("0") || uid.isEmpty()||uid.equals("")) {
                    StartSignin();
                }else {
                    StartMainActivity();
                }
            }catch (Exception e){
                assert uid != null;
                if (uid.equals("0") || uid.isEmpty()||uid.equals("")) {
                    StartSignin();
                }else {
                    StartMainActivity();
                }
            }

        }
    }

    private void StartMainActivity() {
        new ClsStoreAllDataOfUser(this);
        String userType =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_userType));
        switch (userType){
            case "1":
                Intent goToMainActivity = new Intent(ParcelSplashScreen.this, ActMainActivityParcelOwner.class);
                goToMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(goToMainActivity);
                finish();
                break;
            case "2":
                Intent goToMainActivityTrans = new Intent(ParcelSplashScreen.this, MainActivityParcelTransporter.class);
                goToMainActivityTrans.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                startActivity(goToMainActivityTrans);
                finish();
                break;
        }
    }

    public void StartSignin(){
        Intent intent = new Intent(ParcelSplashScreen.this, ClsSignIn.class);
        startActivity(intent);
        finish();
    }
}
