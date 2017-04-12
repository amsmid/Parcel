package com.whitebird.parcel.AddParcelActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

public class ActCreateParcelTakeDates extends AppCompatActivity implements ResultInString{

    TextView textViewDate,textViewTime,textViewDateTimeline;
    ImageButton imageButtonDate,imageButtonTime,imageButtonDateTimeline,submitAllData;
    int yearD,dayD,monthD,yearT,dayT,monthT;
    String unique_id_device,currentDateTimeString,uniqueId;
    String receiverUid,receiverPincode,receiverLandmark,receiverAddress,receiverName,type,size,weight,itemImage;
    SharedPreferenceUserData sharedPreferenceUserData;
    String senderUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_create_parcel_take_dates);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        senderUid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
        getIntentData();
        initViews();
        submitAllData = (ImageButton)findViewById(R.id.submit_dates_page_button);
        submitAllData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAllData();
            }
        });

    }

    private void getIntentData() {
        Bundle getBundle = getIntent().getBundleExtra("thirdBundle");
        receiverAddress = getBundle.getString("rAddress");
        receiverName = getBundle.getString("rName");
        receiverUid = getBundle.getString("rUid");
        receiverPincode = getBundle.getString("rPincode");
        receiverLandmark = getBundle.getString("rLandmark");
        type = getBundle.getString("type");
        itemImage = getBundle.getString("itemImage");
        size = getBundle.getString("size");
        weight = getBundle.getString("weight");
    }

    private void submitAllData() {
        String dateNTime = textViewDate.getText().toString()+" "+textViewTime.getText().toString();
        String dateTimeline = textViewDateTimeline.getText().toString();
        if (!validate()){
            return;
        }
        String onlineKey = getResources().getString(R.string.sendListHubSelectedKey);
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_sender),senderUid);
        hashMapData.put(getResources().getString(R.string.server_key_receiver),receiverUid);
        hashMapData.put(getResources().getString(R.string.server_key_address),receiverAddress);
        hashMapData.put(getResources().getString(R.string.server_key_type),type);
        hashMapData.put(getResources().getString(R.string.server_key_pincode),receiverPincode);
        hashMapData.put(getResources().getString(R.string.server_key_landmark),receiverLandmark);
        hashMapData.put(getResources().getString(R.string.server_key_size),size);
        hashMapData.put(getResources().getString(R.string.server_key_weight),weight);
        hashMapData.put(getResources().getString(R.string.server_key_orderNumber),uniqueId);
        hashMapData.put(getResources().getString(R.string.server_key_timeline),dateTimeline);
        hashMapData.put(getResources().getString(R.string.server_key_dispatchTime),dateNTime);
        hashMapData.put(getResources().getString(R.string.server_key_image),itemImage);
        new BackgroundTaskForResult(hashMapData, onlineKey, ActCreateParcelTakeDates.this).execute();
    }

    @SuppressLint("HardwareIds")
    private void initViews() {
        /*
        * Time And Date Pick And Show on dialog
        */
        textViewDate = (TextView)findViewById(R.id.date_picker_view);
        textViewDateTimeline = (TextView)findViewById(R.id.date_picker_timeline_view);
        textViewTime = (TextView)findViewById(R.id.time_picker_view);
        imageButtonDate = (ImageButton) findViewById(R.id.date_picker_icon);
        imageButtonDateTimeline = (ImageButton) findViewById(R.id.date_picker_timeline_icon);
        imageButtonTime = (ImageButton) findViewById(R.id.time_picker_icon);

        textViewDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDateDialog(1);
            }
        });
        imageButtonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDateDialog(1);
            }
        });

        textViewDateTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDateDialog(0);
            }
        });
        imageButtonDateTimeline.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickDateDialog(0);
            }
        });

        textViewTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickTimeDialog();
            }
        });

        imageButtonTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PickTimeDialog();
            }
        });
        /*
        * Take time And Device Id To generate Unique Code
        */
        unique_id_device = Settings.Secure.getString(getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        Calendar c = Calendar.getInstance();


        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        currentDateTimeString = df.format(c.getTime());
        uniqueId = unique_id_device+currentDateTimeString;
        Log.d("uniqueId",uniqueId);
    }

    private void PickTimeDialog() {
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        int mHour = c.get(Calendar.HOUR_OF_DAY);
        int mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {
                        textViewTime.setError(null);
                        if (hourOfDay<10&&minute>10){
                            textViewTime.setText(0+hourOfDay + ":" + minute + ":" + 0+0);
                        }else if (hourOfDay>10&&minute<10){
                            textViewTime.setText(hourOfDay + ":" + 0+minute + ":" + 0+0);
                        }else if (hourOfDay<10&&minute<10)
                            textViewTime.setText(0+hourOfDay + ":" + 0+minute + ":" + 0+0);
                        else
                            textViewTime.setText(hourOfDay + ":" + minute + ":" + 0+0);
                    }
                }, mHour, mMinute,false);
        timePickerDialog.show();
    }

    private void PickDateDialog(final int type) {
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        final int mYear = c.get(Calendar.YEAR);
        final int mMonth = c.get(Calendar.MONTH);
        final int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        switch (type){
                            case 0:
                                if (mYear>year||mMonth>monthOfYear||(mYear==year&&mMonth==monthOfYear)&&mDay>dayOfMonth){
                                    textViewDateTimeline.setError("Enter Valid Date Greater Than Dispatch Date");
                                    textViewDateTimeline.setText("");
                                }else {
                                    textViewDateTimeline.setError(null);
                                    yearT=year;
                                    monthT=monthOfYear+1;
                                    dayT=dayOfMonth;
                                    /*if (monthOfYear+1<10&&dayOfMonth<10){
                                        textViewDateTimeline.setText( year + "-0" + (monthOfYear + 1) + "-0" +dayOfMonth );
                                    }else if (monthOfYear+1<10){
                                        textViewDateTimeline.setText( year + "-0" +(monthOfYear + 1) + "-" +dayOfMonth );
                                    }else if (dayOfMonth<10){
                                        textViewDateTimeline.setText( year + "-" + (monthOfYear + 1) + "-0" +dayOfMonth );
                                    }else
                                        textViewDateTimeline.setText( year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );*/
                                    if (monthOfYear+1<10&&dayOfMonth<10){
                                        textViewDateTimeline.setText( year + "-" + 0+(monthOfYear + 1) + "-" +0+dayOfMonth );
                                    }else if (monthOfYear+1<10){
                                        textViewDateTimeline.setText( year + "-" + 0+(monthOfYear + 1) + "-" +dayOfMonth );
                                    }else if (dayOfMonth<10){
                                        textViewDateTimeline.setText( year + "-" + (monthOfYear + 1) + "-" +0+dayOfMonth );
                                    }else
                                        textViewDateTimeline.setText( year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                }
                                break;
                            case 1:
                                if (mYear>year||mMonth>monthOfYear||(mYear==year&&mMonth==monthOfYear)&&mDay>dayOfMonth){
                                    textViewDate.setError("Enter Date Proper Date");
                                    textViewDate.setText("");
                                }else {
                                    textViewDate.setError(null);
                                    yearD=year;
                                    monthD=monthOfYear+1;
                                    dayD=dayOfMonth;
                                    if (monthOfYear+1<10&&dayOfMonth<10){
                                        textViewDate.setText( year + "-" + 0+(monthOfYear + 1) + "-" +0+dayOfMonth );
                                    }else if (monthOfYear+1<10){
                                        textViewDate.setText( year + "-" + 0+(monthOfYear + 1) + "-" +dayOfMonth );
                                    }else if (dayOfMonth<10){
                                        textViewDate.setText( year + "-" + (monthOfYear + 1) + "-" +0+dayOfMonth );
                                    }else
                                        textViewDate.setText( year + "-" + (monthOfYear + 1) + "-" +dayOfMonth );
                                }
                                break;
                        }

                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.show();
    }

    public boolean validate() {
        boolean valid = true;

        if (textViewDateTimeline.getText().toString().isEmpty()) {
            textViewDateTimeline.setError("Enter Valid Date");
            valid = false;
        } else {
            textViewDateTimeline.setError(null);
        }
        if (textViewDate.getText().toString().isEmpty()) {
            textViewDate.setError("Enter Valid Date");
            valid = false;
        } else {
            textViewDate.setError(null);
        }
        if (textViewTime.getText().toString().isEmpty()) {
            textViewTime.setError("Enter Valid Time");
            valid = false;
        } else {
            textViewTime.setError(null);
        }

        if (!textViewDateTimeline.getText().toString().isEmpty()&&!textViewDate.getText().toString().isEmpty()){
            if (yearD>yearT||monthD>monthT||(yearD==yearT&&monthD==monthT)&&dayD>dayT){
                textViewDateTimeline.setError("Timeline Should Be More than Dispatch");
                valid = false;
            }else
                textViewDateTimeline.setError(null);
        }
        return valid;
    }

    @Override
    public void Result(String result, String keyOnline) {
        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            if (keyOnline.equals(getResources().getString(R.string.sendListHubSelectedKey))){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = getLayoutInflater();
                @SuppressLint("InflateParams") View view = layoutInflater.inflate(R.layout.submit_parcel_data_view_dialog,null);
                TextView textViewSourceAddress = (TextView)view.findViewById(R.id.add_parcel_submit_data_source_address_name_view);
                TextView textViewDestinationAddress = (TextView)view.findViewById(R.id.add_parcel_submit_data_destination_address_name_view);
                TextView textViewOrderNumber = (TextView)view.findViewById(R.id.add_parcel_submit_data_order_number_name_view);
                TextView textViewFare = (TextView)view.findViewById(R.id.add_parcel_submit_data_fare_name_view);
                try {
                    JSONObject jsonObjectSubmit = new JSONObject(result);
                    JSONObject jsonObjectParcel = jsonObjectSubmit.getJSONObject(getResources().getString(R.string.server_key_parcel));
                    textViewDestinationAddress.setText(jsonObjectParcel.getString(getResources().getString(R.string.server_key_address)));
                    textViewOrderNumber.setText(jsonObjectParcel.getString(getResources().getString(R.string.server_key_orderNumber)));
                    textViewFare.setText(jsonObjectParcel.getString(getResources().getString(R.string.server_key_fare)));

                } catch (JSONException e) {
                    textViewDestinationAddress.setText(e.getMessage());
                    e.printStackTrace();
                }


                sharedPreferenceUserData = new SharedPreferenceUserData(this);
                String sourceAddress = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_address));
                textViewSourceAddress.setText(sourceAddress);
                builder.setView(view);
                builder.setCancelable(false);
                builder.setTitle("Submit data :");
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(ActCreateParcelTakeDates.this,ActMainActivityParcelOwner.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                });
                builder.show();

            }
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Try Again");
            dialog.show();
        }
    }
}
