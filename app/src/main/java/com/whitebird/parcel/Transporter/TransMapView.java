package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

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
import java.util.Locale;

public class TransMapView extends AppCompatActivity implements OnMapReadyCallback {

    String address;
    GoogleMap mMap;
    MapView mapView;
    double latitude,longitude;
    int geocoderMaxResults = 1;

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
            latitude =19.100981;
            longitude = 72.9984171;
        }
        return null;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        String Addressfull;


        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            String locality = address.getLocality();
            String postalCode = address.getPostalCode();
            String countryName = address.getCountryName();
            Addressfull = addressLine+"\n"+locality+"\n"+postalCode+"\n"+countryName;
        } catch (IOException e) {
            Addressfull = "";
            e.printStackTrace();
        }
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        numTxt.setText(Addressfull);

        LatLng sydney = new LatLng(latitude, longitude);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Transporter").icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this,marker))));

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

    // Convert a view to bitmap
    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;
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
