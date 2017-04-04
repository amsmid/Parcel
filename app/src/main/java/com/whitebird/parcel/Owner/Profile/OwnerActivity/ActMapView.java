package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ActMapView extends AppCompatActivity implements OnMapReadyCallback,ResultInString{

    GoogleMap mMap;
    MapView mapView;
    int geocoderMaxResults = 1;
    TextView textView;
    String transUid;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_map_view);

        textView = (TextView)findViewById(R.id.address_lat_lan);

        transUid = getIntent().getStringExtra("transUid");

        String onlineKey = getResources().getString(R.string.getLanLonKey);
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),transUid);
        new BackgroundTaskForResult(hashMapData, onlineKey, ActMapView.this).execute();
         /*SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);*/
        mapView = (MapView)findViewById(R.id.map_view);
        mapView.onCreate(savedInstanceState);

    }

    @Override
    protected void onResume() {
        mapView.onResume();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mapView.onPause();
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mapView.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onLowMemory() {
        mapView.onLowMemory();
        super.onLowMemory();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;


            // Add a marker in Sydney, Australia, and move the camera.
//            LatLng myPosition = new LatLng(latitude, longitude);


            /*CameraPosition position = new CameraPosition.Builder().
                    target(myPosition).zoom(17).bearing(19).tilt(30).build();*/
        /*mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        mMap.addMarker(new
                MarkerOptions().position(myPosition).title("start"));*/
            LatLng sydney = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(sydney).title(transUid).icon(BitmapDescriptorFactory
                    .fromResource(R.drawable.map_car_image)));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(latitude,
                            longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);

        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            String locality = address.getLocality();
            String postalCode = address.getPostalCode();
            String countryName = address.getCountryName();
            textView.setText(addressLine+"\n"+locality+"\n"+postalCode+"\n"+countryName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /*Map Program
    * 19.100981
    * 72.9984171*/
    /*private boolean isGooglePlayServicesAvailable() {
        int status = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this);
        if (ConnectionResult.SUCCESS == status) {
            return true;
        } else {
            GooglePlayServicesUtil.getErrorDialog(status, this, 0).show();
            return false;
        }
    }*/

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("LatLngResult",result);
        String success;
        String Lat,Lang;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString("success");
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            try {
                JSONObject jsonObjectSuccess = new JSONObject(result);
                JSONObject jsonObjectLoc = jsonObjectSuccess.getJSONObject("location");

                Lat = jsonObjectLoc.getString("lat");
                Lang = jsonObjectLoc.getString("lon");
                latitude = Double.parseDouble(Lat);
                longitude = Double.parseDouble(Lang);
                //latitude = Long.parseLong(Lat);
                //longitude = Long.parseLong(Lang);
                mapView.getMapAsync(this);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {

        }
    }
}
