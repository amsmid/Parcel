package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutCompat;
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
    //TextView textView;
    String transUid;
    double latitude,longitude;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_map_view);

        //textView = (TextView)findViewById(R.id.address_lat_lan);

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
        String Addresses;


            // Add a marker in Sydney, Australia, and move the camera.
//            LatLng myPosition = new LatLng(latitude, longitude);


        Geocoder geocoder = new Geocoder(this, Locale.ENGLISH);
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, this.geocoderMaxResults);
            Address address = addresses.get(0);
            String addressLine = address.getAddressLine(0);
            String locality = address.getLocality();
            String postalCode = address.getPostalCode();
            String countryName = address.getCountryName();
            Addresses = addressLine+"\n"+locality+"\n"+postalCode+"\n"+countryName;
        } catch (IOException e) {
            Addresses = transUid;
            e.printStackTrace();
        }
            /*CameraPosition position = new CameraPosition.Builder().
                    target(myPosition).zoom(17).bearing(19).tilt(30).build();*/
        /*mMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
        mMap.addMarker(new
                MarkerOptions().position(myPosition).title("start"));*/
            LatLng sydney = new LatLng(latitude, longitude);
        View marker = ((LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);
        TextView numTxt = (TextView) marker.findViewById(R.id.num_txt);
        numTxt.setText(Addresses);
            mMap.addMarker(new MarkerOptions().position(sydney).title(transUid).icon(BitmapDescriptorFactory.fromBitmap(createDrawableFromView(this,marker))));
            //mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
            CameraUpdate center =
                    CameraUpdateFactory.newLatLng(new LatLng(latitude,
                            longitude));
            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);
            mMap.moveCamera(center);
            mMap.animateCamera(zoom);


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
                latitude =19.100981;
                longitude = 72.9984171;
                e.printStackTrace();
            }

        }else {

        }
    }
}
