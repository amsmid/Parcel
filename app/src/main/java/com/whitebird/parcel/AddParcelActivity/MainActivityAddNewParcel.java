package com.whitebird.parcel.AddParcelActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.GetHubListData;
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ActMainActivityParcelOwner;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class MainActivityAddNewParcel extends AppCompatActivity implements ResultInString {

    SharedPreferenceUserData sharedPreferenceUserData;
    TextView textViewHubSelectionAddress;
    Button textViewHubSelection;
    View viewSearch;
    ListView listHub;
    SearchView searchView;
    ClsStoreListofHub clsStoreListOfHub;
    ArrayList<GetHubListData> arrayListHubData;
    ArrayList<String> getName;
    EditText getSize,getWeight;
    Button submit;
    String receiverUid,receiverPincode,receiverLandmark;
    String unique_id_device,currentDateTimeString,uniqueId;
    AlertDialog.Builder builder;
    BaseAdapter adapter;
    Dialog dialogDis;
    CustomListViewOfHubAdapter customListViewOfHubAdapter;
    Spinner spinnerTypeOfItem;
    ImageView imageViewItemSelected;
    TextView textViewDate,textViewTime,textViewDateTimeline;
    ImageButton imageButtonDate,imageButtonTime,imageButtonDateTimeline;
    int yearD,dayD,monthD,yearT,dayT,monthT;

    @SuppressLint("HardwareIds")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        String data = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
        String onlineKey = getResources().getString(R.string.fetchHubListKey);
        HashMap<String,String> hashMapData = new HashMap<String, String>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),data);
        hashMapData.put(getResources().getString(R.string.server_key_flag),"1");
        new BackgroundTaskForResult(hashMapData, onlineKey, MainActivityAddNewParcel.this).execute();
        getName = new ArrayList<>();
        clsStoreListOfHub = new ClsStoreListofHub(this);
        arrayListHubData = new ArrayList<>();

        setContentView(R.layout.activity_main_add_new_parcel);


        textViewHubSelection = (Button) findViewById(R.id.list_item_of_hub_select);
        textViewHubSelectionAddress = (TextView) findViewById(R.id.list_item_of_hub_select_address);
        imageViewItemSelected = (ImageView)findViewById(R.id.image_of_item_selected);
        imageViewItemSelected.setImageDrawable(getResources().getDrawable(R.drawable.blank_camera_image));



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


        imageViewItemSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });

        spinnerTypeOfItem = (Spinner)findViewById(R.id.get_type);
        getSize = (EditText)findViewById(R.id.get_size);
        getWeight = (EditText)findViewById(R.id.get_weight);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTypeOfItem = ArrayAdapter.createFromResource(this,
                R.array.new_item_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterTypeOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypeOfItem.setAdapter(adapterTypeOfItem);



        textViewHubSelection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textViewHubSelection.setError(null);
                ListBuilderOnPopUp();
            }

        });

        submit = (Button)findViewById(R.id.submit_button_hub);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String type = spinnerTypeOfItem.getSelectedItem().toString();
                String weight = getWeight.getText().toString();
                String size = getSize.getText().toString();
                String senderUid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
                String senderaddress = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_address));
                String dateNTime = textViewDate.getText().toString()+" "+textViewTime.getText().toString();
                String dateTimeline = textViewDateTimeline.getText().toString();
                imageViewItemSelected.setDrawingCacheEnabled(true);
                imageViewItemSelected.buildDrawingCache();
                String receiverAd =textViewHubSelectionAddress.getText().toString();
                BitmapDrawable drawable = (BitmapDrawable) imageViewItemSelected.getDrawable();
                Bitmap bitmap = drawable.getBitmap();
                //Bitmap bm=imageViewItemSelected.getDrawable().get
                String itemImage = getStringImage(bitmap);
                Log.d("imageString",itemImage);
                if (!validate()){
                    return;
                }
                String data = senderUid +"|"+ receiverUid +"|"+ senderaddress +"|"+  type  +"|"+ receiverPincode +"|"+ receiverLandmark +"|"+ size +"|"+ weight +"|"+itemImage;
                String onlineKey = getResources().getString(R.string.sendListHubSelectedKey);
                HashMap<String,String> hashMapData = new HashMap<String, String>();
                hashMapData.put(getResources().getString(R.string.server_key_sender),senderUid);
                hashMapData.put(getResources().getString(R.string.server_key_receiver),receiverUid);
                hashMapData.put(getResources().getString(R.string.server_key_address),receiverAd);
                hashMapData.put(getResources().getString(R.string.server_key_type),type);
                hashMapData.put(getResources().getString(R.string.server_key_pincode),receiverPincode);
                hashMapData.put(getResources().getString(R.string.server_key_landmark),receiverLandmark);
                hashMapData.put(getResources().getString(R.string.server_key_size),size);
                hashMapData.put(getResources().getString(R.string.server_key_weight),weight);
                hashMapData.put(getResources().getString(R.string.server_key_orderNumber),uniqueId);
                hashMapData.put(getResources().getString(R.string.server_key_timeline),dateTimeline);
                hashMapData.put(getResources().getString(R.string.server_key_dispatchTime),dateNTime);
                hashMapData.put(getResources().getString(R.string.server_key_image),itemImage);
                new BackgroundTaskForResult(hashMapData, onlineKey, MainActivityAddNewParcel.this).execute();


            }
        });
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

        String weight = getWeight.getText().toString();
        String size = getSize.getText().toString();
        if (weight.isEmpty()) {
            getWeight.setError("Enter Valid Data");
            valid = false;
        } else {
            getWeight.setError(null);
        }

        if (size.isEmpty()) {
            getSize.setError("Enter Valid Data");
            valid = false;
        } else {
            getSize.setError(null);
        }

        if (textViewHubSelection.getText().toString().isEmpty()) {
            textViewHubSelection.setError("Enter Valid Data");
            valid = false;
        } else {
            textViewHubSelection.setError(null);
        }

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

    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,70,baos);
        byte[] imageBytes = baos.toByteArray();
        String input = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("tempImageView",input);
        return input;
    }

    public Bitmap StringToBitMap(String encodedString){
        try {
            byte [] encodeByte=Base64.decode(encodedString,Base64.DEFAULT);
            Bitmap bitmap= BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    private void selectImage() {
        final CharSequence[] items = { "Take Photo", "Choose from Library",
                "Cancel" };

        TextView title = new TextView(this);
        title.setText("Add Photo!");
        title.setBackgroundColor(Color.RED);
        title.setPadding(10, 15, 15, 10);
        title.setGravity(Gravity.CENTER);
        title.setTextColor(Color.WHITE);
        title.setTextSize(22);


        AlertDialog.Builder builder = new AlertDialog.Builder(
                MainActivityAddNewParcel.this);



        builder.setCustomTitle(title);

        // builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int item) {
                if (items[item].equals("Take Photo")) {
                    // Intent intent = new
                    // Intent(MediaStore.ACTION_IMAGE_CAPTURE);

                /*
                 * File photo = new
                 * File(Environment.getExternalStorageDirectory(),
                 * "Pic.jpg"); intent.putExtra(MediaStore.EXTRA_OUTPUT,
                 * Uri.fromFile(photo)); imageUri = Uri.fromFile(photo);
                 */
                    // startActivityForResult(intent,TAKE_PICTURE);
                    if (isCameraPermissionGranted()) {
                        Intent takePicture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(takePicture, 0);//zero can be replaced with any action code
                    }else {
                        dialog.dismiss();
                    }

                } else if (items[item].equals("Choose from Library")) {
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                            android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(pickPhoto , 1);//one can be replaced with any action code
                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }


    public  boolean isCameraPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_GRANTED) {
                Log.v("yes","Permission is granted");
                return true;
            } else {

                Log.v("may","Permission is revoked");
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                return false;
            }
        }
        else { //permission is automatically granted on sdk<23 upon installation
            Log.v("con","Permission is granted");
            return true;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        switch(requestCode) {
            case 0:
                if(resultCode == RESULT_OK){
                    Bitmap photo = (Bitmap) imageReturnedIntent.getExtras().get("data");
                    imageViewItemSelected.setImageBitmap(photo);
                }

                break;
            case 1:
                if(resultCode == RESULT_OK){
                    Uri selectedImage = imageReturnedIntent.getData();
                    imageViewItemSelected.setImageURI(selectedImage);
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                        getStringImage(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void ListBuilderOnPopUp() {
        builder = new AlertDialog.Builder(this);
        LayoutInflater layout = getLayoutInflater();
        viewSearch = layout.inflate(R.layout.search_list_layout, null);
        listHub = (ListView) viewSearch.findViewById(R.id.list_of_hub_item_in_popup);
        searchView = (SearchView) viewSearch.findViewById(R.id.search_view_for_hub_list);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });


        customListViewOfHubAdapter = new CustomListViewOfHubAdapter(MainActivityAddNewParcel.this, arrayListHubData,getName);
        adapter = customListViewOfHubAdapter;
        listHub.setAdapter(adapter);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    customListViewOfHubAdapter = new CustomListViewOfHubAdapter(MainActivityAddNewParcel.this, arrayListHubData,getName);
                    adapter = customListViewOfHubAdapter;
                    listHub.setAdapter(adapter);
                } else {
                    //  listView.setTextFilterEnabled(false);
                    //  listView.setAdapter(new baseCombinationAdepter(MainPlaceOrderActivity.this, listCombinations));
                    customListViewOfHubAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });


        //Set All List Here And Search it
        builder.setView(viewSearch);
        builder.setNegativeButton("Cancel", null);
        builder.setPositiveButton("Ok",null);
        dialogDis = builder.create();
        dialogDis.show();
    }

    @Override
    public void Result(String result,String keyOnline) {
        Log.d("resultOfHubList",result);

        getName = new ArrayList<>();
        clsStoreListOfHub = new ClsStoreListofHub(this);
        arrayListHubData = new ArrayList<>();

        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            if (keyOnline.equals(getResources().getString(R.string.fetchHubListKey))){
                clsStoreListOfHub.SaveHubList(result);
                arrayListHubData = clsStoreListOfHub.getGetHubListDatas();
                for (int i = 0; i < arrayListHubData.size(); i++) {
                    getName.add(arrayListHubData.get(i).getName());
                }
                if (getName.isEmpty()){
                    getName= new ArrayList<>();
                }
            }else if (keyOnline.equals(getResources().getString(R.string.sendListHubSelectedKey))){
                final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater layoutInflater = getLayoutInflater();
                View view = layoutInflater.inflate(R.layout.submit_parcel_data_view_dialog,null);
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
                        startActivity(new Intent(MainActivityAddNewParcel.this,ActMainActivityParcelOwner.class));
                        finish();
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

    public class CustomListViewOfHubAdapter extends BaseAdapter implements Filterable{
        ArrayList<GetHubListData> arrayList;
        Activity activity;
        ArrayList<GetHubListData> getFilterlist;
        ArrayList<String> getNameAdapter;
        CustomListViewOfHubAdapter(Activity activity,ArrayList<GetHubListData> arrayList,ArrayList<String> name){
            this.arrayList = new ArrayList<>();
            this.arrayList = arrayList;
            this.activity = activity;
            getFilterlist = new ArrayList<>();
            getNameAdapter = new ArrayList<>();
            this.getNameAdapter=name;
        }

        @Override
        public int getCount() {
            // To Check Empty List
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.indexOf(position);
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.list_view_of_hub_model,null);
            }


            final TextView textViewName = (TextView)convertView.findViewById(R.id.tv_history_list_send_name_hub);
            textViewName.setText(arrayList.get(position).getName());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    textViewHubSelection.setText(arrayList.get(position).getName());
                    textViewHubSelectionAddress.setText(arrayList.get(position).getAddress());
                    receiverUid = arrayList.get(position).getUid();
                    receiverPincode = arrayList.get(position).getPincode();
                    receiverLandmark = arrayList.get(position).getLandmark();
                    dialogDis.dismiss();
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<GetHubListData> results = new ArrayList<GetHubListData>();
                    if (getFilterlist == null)
                        getFilterlist = arrayList;

                    if (constraint != null) {
                        //if (list != null && list.size() > 0) {
                        for (final GetHubListData g : arrayListHubData) {
                            if (g.getName().toUpperCase()
                                    .contains(constraint.toString()) || g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                        // }
                        oReturn.values = results;
                    } else {
                        oReturn.values = arrayListHubData;
                    }
                    return oReturn;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    arrayList = new ArrayList<>();
                    arrayList = (ArrayList<GetHubListData>) results.values;
                    CustomListViewOfHubAdapter.this.notifyDataSetChanged();
                }
            };
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,ActMainActivityParcelOwner.class));

    }
}
