package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
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

import com.whitebird.parcel.AddParcelActivity.MainActivityAddNewParcel;
import com.whitebird.parcel.Owner.Profile.HistoryPageForOwner.MainActivityHistoryList;
import com.whitebird.parcel.Owner.Profile.ProfileActivity.ClsProfileDisplay;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.SignIn.ClsSignIn;


public class ActMainActivityParcelOwner extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener,ResultInString {

    SharedPreferenceUserData sharedPreferenceUserData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Taking User Data
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        String name =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_name));
        String email =sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_email));
        setContentView(R.layout.activity_main_parcel);
        isStoragePermissionGranted();
        CardView cardViewCreate = (CardView)findViewById(R.id.card_view_create_button);
        CardView cardViewManage = (CardView)findViewById(R.id.card_view);
        CardView cardViewTrack = (CardView)findViewById(R.id.card_view_track_button);



            // Call some material design APIs here

            cardViewCreate.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartCreateActivity();
                }
            });
            cardViewManage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartMainManageActivity();
                }
            });
            cardViewTrack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    StartTrackParcelActivity();
                }
            });
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.LOLLIPOP) {

        }
            // Implement this feature without material design




        //Display Data On Drawer Info View


        //All App Drawer Data
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Header View Is Add From Here To Navigation Drawer
        //View inflater =navigationView.inflateHeaderView(R.layout.nav_header_main_activity_parcel);
        View header = navigationView.getHeaderView(0);
        TextView textViewDrawerName = (TextView)header.findViewById(R.id.text_signin_user_name);
        TextView textViewDrawerEmail = (TextView)header.findViewById(R.id.text_signin_user_email);
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
    private void StartCreateActivity() {
        startActivity(new Intent(ActMainActivityParcelOwner.this, MainActivityAddNewParcel.class));
        finish();
    }

    private void StartMainManageActivity() {
        startActivity(new Intent(ActMainActivityParcelOwner.this,ActMainManage.class));
    }

    private void StartTrackParcelActivity() {
        startActivity(new Intent(ActMainActivityParcelOwner.this,ActOwnerTrackParcel.class));

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
        getMenuInflater().inflate(R.menu.main_activity_parcel, menu);
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

        if (id == R.id.profile_owner) {
            //Go To Profile Page For owner
            Intent intentProfile = new Intent(this, ClsProfileDisplay.class);
            startActivity(intentProfile);
            // Handle the camera action

        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.history_owner) {
            //Go To History list from here
            startActivity(new Intent(this, MainActivityHistoryList.class));
        } else if (id == R.id.logout_owner) {
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

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("tryResult",result);
    }
}
