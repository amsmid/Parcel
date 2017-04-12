package com.whitebird.parcel.AddParcelActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.whitebird.parcel.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class CreateParcelTakeImageAndData extends AppCompatActivity {

    ImageView imageViewItemSelected;
    Spinner spinnerTypeOfItem;
    ImageButton ibSubmitButton;
    String receiverUid,receiverPincode,receiverLandmark,receiverAddress,receiverName,type,itemImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_parcel_take_image_and_data);

        Bundle getBundle = getIntent().getBundleExtra("firstBundle");
        receiverAddress = getBundle.getString("rAddress");
        receiverName = getBundle.getString("rName");
        receiverUid = getBundle.getString("rUid");
        receiverPincode = getBundle.getString("rPincode");
        receiverLandmark = getBundle.getString("rLandmark");
        imageViewItemSelected = (ImageView)findViewById(R.id.image_of_item_selected);
        imageViewItemSelected.setImageDrawable(getResources().getDrawable(R.drawable.parcel_image_camera));

        imageViewItemSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        spinnerTypeOfItem = (Spinner)findViewById(R.id.get_type);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapterTypeOfItem = ArrayAdapter.createFromResource(this,
                R.array.new_item_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapterTypeOfItem.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerTypeOfItem.setAdapter(adapterTypeOfItem);

        ibSubmitButton = (ImageButton)findViewById(R.id.submit_image_data_page_button);
        ibSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToNextPage();
            }
        });

    }

    private void submitDataToNextPage() {

        type = spinnerTypeOfItem.getSelectedItem().toString();
        BitmapDrawable drawable = (BitmapDrawable) imageViewItemSelected.getDrawable();
        Bitmap bitmap = drawable.getBitmap();
        //Bitmap bm=imageViewItemSelected.getDrawable().get
        itemImage = getStringImage(bitmap);
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ActCreateParcelTakeWeightSize.class);
        bundle.putString("rAddress",receiverAddress);
        bundle.putString("rName",receiverName);
        bundle.putString("rUid",receiverUid);
        bundle.putString("rPincode",receiverPincode);
        bundle.putString("rLandmark",receiverLandmark);
        bundle.putString("type",type);
        bundle.putString("itemImage",itemImage);
        intent.putExtra("secondBundle",bundle);
        Bundle bndlanimation;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.first_animation, R.anim.second_animation).toBundle();
            startActivity(intent, bndlanimation);
        }else
            startActivity(intent);

    }

    @SuppressLint("SetTextI18n")
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
                CreateParcelTakeImageAndData.this);



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

    public String getStringImage(Bitmap bmp)
    {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG,70,baos);
        byte[] imageBytes = baos.toByteArray();
        String input = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        Log.d("tempImageView",input);
        return input;
    }
}
