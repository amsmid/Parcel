package com.whitebird.parcel.AddParcelActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.whitebird.parcel.R;

public class ActCreateParcelTakeWeightSize extends AppCompatActivity {

    EditText getSizeOne,getSizeTwo,getWeight;
    ImageButton ibSubmitButton;
    String receiverUid,receiverPincode,receiverLandmark,receiverAddress,receiverName,type,size,weight,itemImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_create_parcel_take_weight_size);
        initViews();
        getIntentData();
    }

    private void getIntentData() {
        Bundle getBundle = getIntent().getBundleExtra("secondBundle");
        receiverAddress = getBundle.getString("rAddress");
        receiverName = getBundle.getString("rName");
        receiverUid = getBundle.getString("rUid");
        receiverPincode = getBundle.getString("rPincode");
        receiverLandmark = getBundle.getString("rLandmark");
        type = getBundle.getString("type");
        itemImage = getBundle.getString("itemImage");
    }

    private void initViews() {
        getSizeOne = (EditText)findViewById(R.id.get_size_one);
        getSizeTwo = (EditText)findViewById(R.id.get_size_two);
        getWeight = (EditText)findViewById(R.id.get_weight);

        ibSubmitButton = (ImageButton)findViewById(R.id.submit_parcel_size_page_button);
        ibSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToNextPage();
            }
        });
    }

    private void submitDataToNextPage() {

        if (!validate()){
            return;
        }
        weight = getWeight.getText().toString();
        size = getSizeOne.getText().toString()+"X"+getSizeTwo.getText().toString();

        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, ActCreateParcelTakeDates.class);
        bundle.putString("rAddress",receiverAddress);
        bundle.putString("rName",receiverName);
        bundle.putString("rUid",receiverUid);
        bundle.putString("rPincode",receiverPincode);
        bundle.putString("rLandmark",receiverLandmark);
        bundle.putString("type",type);
        bundle.putString("size",size);
        bundle.putString("weight",weight);
        bundle.putString("itemImage",itemImage);
        intent.putExtra("thirdBundle",bundle);
        Bundle bndlanimation;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.first_animation, R.anim.second_animation).toBundle();
            startActivity(intent, bndlanimation);
        }else
            startActivity(intent);

    }

    private boolean validate() {
        boolean valid = true;
        if (getWeight.getText().toString().isEmpty()){
            getWeight.setError("Add Weight");
            valid=false;
        }else if (getSizeOne.getText().toString().isEmpty()){
            getSizeOne.setError("Add Size");
            valid=false;
        }else if (getSizeTwo.getText().toString().isEmpty()){
            getSizeTwo.setError("Add Size");
            valid=false;
        }
        return valid;
    }
}
