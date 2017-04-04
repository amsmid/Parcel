package com.whitebird.parcel.Transporter;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.whitebird.parcel.R;

import java.io.IOException;
import java.util.List;

public class TransMapView extends AppCompatActivity implements OnMapReadyCallback {

    String address;
    GoogleMap mMap;
    MapView mapView;
    double latitude,longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trans_map_view);
        Intent intent = getIntent();
        address = intent.getStringExtra("address");
        getLocationFromAddress(address);
        mapView = (MapView)findViewById(R.id.trans_map_view);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(this);
    }

    public Void getLocationFromAddress(String strAddress){

        Geocoder coder = new Geocoder(this);
        List<Address> address = null;

        try {
            try {
                address = coder.getFromLocationName(strAddress,5);
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (address==null) {
                return null;
            }
            Address location=address.get(0);
            latitude=location.getLatitude();
            longitude=location.getLongitude();
            Log.d("lat", String.valueOf(latitude));
            Log.d("lan", String.valueOf(longitude));
            /*p1 = new LatLng((double) (location.getLatitude() * 1E6),
                    (double) (location.getLongitude() * 1E6));
*/

        }catch (Exception e){
            latitude=0;
            longitude=0;
        }
        return null;
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
        mMap.addMarker(new MarkerOptions().position(sydney).title("Hub").icon(BitmapDescriptorFactory
                .fromResource(R.drawable.map_car_image)));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        CameraUpdate center =
                CameraUpdateFactory.newLatLng(new LatLng(latitude,
                        longitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
        mMap.moveCamera(center);
        mMap.animateCamera(zoom);

        /*Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            String locality = address.getLocality();
            String postalCode = address.getPostalCode();
            String countryName = address.getCountryName();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
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
}
