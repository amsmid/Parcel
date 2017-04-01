package com.whitebird.parcel.Transporter;

import android.Manifest;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.whitebird.parcel.R;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.SignIn.ClsSignIn;
import com.whitebird.parcel.Transporter.TransHistoryPage.ActTransHistory;
import com.whitebird.parcel.Transporter.TransporterProfile.ActTransProfileDisplay;

import java.util.Calendar;

public class MainActivityParcelTransporter extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    SharedPreferenceUserData sharedPreferenceUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        setContentView(R.layout.activity_main_parcel_transporter);
        isStoragePermissionGranted();
        isLocationPermissionGranted();
        scheduleAlarm();



        /*// check if GPS enabled
        MyGpsTrackerService gpsTracker = new MyGpsTrackerService(this);

        if (gpsTracker.getIsGPSTrackingEnabled())
        {
            String stringLatitude = String.valueOf(gpsTracker.latitude);
            Log.d("Latitude",stringLatitude);

            String stringLongitude = String.valueOf(gpsTracker.longitude);
            Log.d("stringLongitude",stringLongitude);

            String country = gpsTracker.getCountryName(this);
            Log.d("country",country);

            String city = gpsTracker.getLocality(this);
            Log.d("city",city);

            String postalCode = gpsTracker.getPostalCode(this);
            Log.d("postalCode",postalCode);

            String addressLine = gpsTracker.getAddressLine(this);
            Log.d("addressLine",addressLine);
        }
        else
        {
            // can't get location
            // GPS or Network is not enabled
            // Ask user to enable GPS/network in settings
            gpsTracker.showSettingsAlert();
        }
*/


        //
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarTransPort);
        setSupportActionBar(toolbar);

        CardView cardViewPick = (CardView)findViewById(R.id.transport_act_main_card_view_pick_button);
        CardView cardViewDelivery = (CardView)findViewById(R.id.transport_act_main_card_view_delivery_cv);


        /*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {*/

            cardViewPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartPickParcelActivity();
                }
            });
            cardViewDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartParcelDeliveryManageActivity();
                }
            });
            // Call some material design APIs here
        /*} else {
            cardViewPick.setVisibility(View.GONE);
            cardViewDelivery.setVisibility(View.GONE);
            buttonPick.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartPickParcelActivity();
                }
            });
            buttonDelivery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartParcelDeliveryManageActivity();
                }
            });
            // Implement this feature without material design

        }*/

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view_transport);
        navigationView.setNavigationItemSelectedListener(this);

        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        String name =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_name));
        String email =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_email));
        View header = navigationView.getHeaderView(0);
        TextView textViewDrawerName = (TextView)header.findViewById(R.id.nav_trans_profile_name);
        TextView textViewDrawerEmail = (TextView)header.findViewById(R.id.nav_trans_profile_email);
        textViewDrawerName.setText(name);
        textViewDrawerEmail.setText(email);
    }

    public  boolean isStoragePermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED&&checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("yes","Permission is granted");
                return true;
            } else {

                Log.v("may","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("con","Permission is granted");
            return true;
        }
    }

    public  boolean isLocationPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("Loc yes","Permission is granted");
                return true;
            } else {

                Log.v("Loc may","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("con","Permission is granted");
            return true;
        }
    }

    // Setup a recurring alarm every half hour
    public void scheduleAlarm() {
        // Construct an intent that will execute the AlarmReceiver
        Intent intent = new Intent(this, MyAlarmReceiver.class);
        // Create a PendingIntent to be triggered when the alarm goes off
        final PendingIntent pIntent = PendingIntent.getBroadcast(this, 0,
                intent, 0);
        // Setup periodic alarm every 5 seconds
        long firstMillis = System.currentTimeMillis(); // alarm is set right away
        AlarmManager alarm = (AlarmManager) this.getSystemService(Context.ALARM_SERVICE);
        // First parameter is the type: ELAPSED_REALTIME, ELAPSED_REALTIME_WAKEUP, RTC_WAKEUP
        // Interval can be INTERVAL_FIFTEEN_MINUTES, INTERVAL_HALF_HOUR, INTERVAL_HOUR, INTERVAL_DAY
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.add(Calendar.SECOND, 60); // first time
        long frequency= 60 * 1000; // in ms
        alarm.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), 60*1000, pIntent);
    }

    private void StartParcelDeliveryManageActivity() {
        startActivity(new Intent(this,TransActManageParcelDelivery.class));
    }

    private void StartPickParcelActivity() {
        startActivity(new Intent(this,MainActivityPickShipment.class));
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_activity_parcel_transporter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav__trans_profile) {
            // Handle the camera action
            startActivity(new Intent(this, ActTransProfileDisplay.class));
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.history_transporter) {
            startActivity(new Intent(this, ActTransHistory.class));

        } else if (id == R.id.trans_logout) {
            sharedPreferenceUserData.ClearAllSharedPreferenceData();
            Intent intent = new Intent(this, ClsSignIn.class);
            startActivity(intent);
            finish();

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
