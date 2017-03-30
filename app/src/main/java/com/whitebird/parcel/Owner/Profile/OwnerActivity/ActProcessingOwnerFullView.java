package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Environment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whitebird.parcel.R;

import java.io.File;

public class ActProcessingOwnerFullView extends FragmentActivity {

    ProcessingListItem processingListItem;
    String address,pickUpTime,orderNo,image,transUid,transName,type,timeline,transMobNo;
    File mediaFile,mediaStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_processing_owner_full_view);


        Button buttonShowLoc = (Button)findViewById(R.id.show_loc);
        buttonShowLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ActProcessingOwnerFullView.this,ActMapView.class);
                intent.putExtra("transUid",transUid);
                startActivity(intent);
            }
        });


        Intent intent = getIntent();
        int position=intent.getIntExtra("position",0);
        processingListItem = GetProcessingListOwner.getInstance().processingListItems.get(position);
        address = processingListItem.getAddress()+","+
                processingListItem.getReceiverCity()+","+
                processingListItem.getReceiverState()+","+
                processingListItem.getReceiverLand()+","+
                processingListItem.getPincode();
        pickUpTime = processingListItem.getPickupTime();
        orderNo = processingListItem.getOrderNumber();
        image = processingListItem.getImage();
        transUid = processingListItem.getTransId();
        transName = processingListItem.getTransName();
        type = processingListItem.getType();
        timeline = processingListItem.getTimeline();
        transMobNo = processingListItem.getTransMobile();

        String newImg =image.replace("\\","");
        Log.d("image",newImg);
        TextView textViewAddress = (TextView)findViewById(R.id.processing_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.processing_model_full_edit_order_no);
        TextView textViewPickUp = (TextView)findViewById(R.id.processing_model_full_edit_pick_up);
        TextView textViewTransName = (TextView)findViewById(R.id.processing_model_full_edit_trans_name);
        TextView textViewTypeV = (TextView)findViewById(R.id.processing_model_full_edit_type);
        TextView textViewTimeline = (TextView)findViewById(R.id.processing_model_full_edit_timeline);
        TextView textViewMobileNo = (TextView)findViewById(R.id.processing_model_full_edit_trans_mob);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewPickUp.setText(pickUpTime);
        textViewTransName.setText(transName);
        textViewTypeV.setText(type);
        textViewTimeline.setText(timeline);
        textViewMobileNo.setText(transMobNo);
        ImageView imageView = (ImageView)findViewById(R.id.image_full_owner_processing_list);
        String imageUrl = getResources().getString(R.string.url)+newImg;

        mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getPackageName()
                + "/Files");

        deleteDirectory(mediaStorageDir);

        mediaStorageDir = new File(Environment.getExternalStorageDirectory()
                + "/Android/data/"
                + getPackageName()
                + "/Files");

        mediaFile = new File(mediaStorageDir.getPath(),newImg);
        new ClsBackgroundImageLoader(this,imageUrl,imageView,mediaFile).execute();

        /*if (isGooglePlayServicesAvailable()) {
            SupportMapFragment supportMapFragment =
                    (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map_view);
            googleMap = supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                @Override
                public void onMapReady(GoogleMap googleMap) {
                    googleMap.setMyLocationEnabled(true);
                    LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
                    Criteria criteria = new Criteria();
                    String bestProvider = locationManager.getBestProvider(criteria, true);
                    Location location = locationManager.getLastKnownLocation(bestProvider);
                    if (location != null) {
                        onLocationChanged(location);
                    }
                    locationManager.requestLocationUpdates(bestProvider,20000,0,this);
                }
            });

        }*/


    }




    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }


}
