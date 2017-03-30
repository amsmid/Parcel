package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;

public class ActAcceptedOwnerFullView extends AppCompatActivity implements ResultInString{

    AcceptedListItem acceptedListItem;
    String address,dispatchTime,orderNo,image,senderAddress,transName,transMo,receiverName,receiverMo,size,weight,timeline,type;
    String onlineKey;
    File mediaFile,mediaStorageDir;
    Button buttonAcceptPickUp,buttonDenyPickUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_accepted_owner_full_view);

        /*
        * Accept Or Deny PickUp Button Shown Here*/
        buttonAcceptPickUp = (Button)findViewById(R.id.accepted_btn_accept_pick_up);
        buttonDenyPickUp = (Button)findViewById(R.id.accepted_btn_deny_pick_up);

        onlineKey = getResources().getString(R.string.acceptByOwnerPickOrder);
        buttonAcceptPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMapData = new HashMap<>();
                hashMapData.put(getResources().getString(R.string.server_key_item),"1");
                hashMapData.put(getResources().getString(R.string.server_key_orderNumber), orderNo);
                new BackgroundTaskForResult(hashMapData, onlineKey,ActAcceptedOwnerFullView.this).execute();
            }
        });


        buttonDenyPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMapData = new HashMap<>();
                hashMapData.put(getResources().getString(R.string.server_key_item),"2");
                hashMapData.put(getResources().getString(R.string.server_key_orderNumber), orderNo);
                new BackgroundTaskForResult(hashMapData, onlineKey,ActAcceptedOwnerFullView.this).execute();
            }
        });

        Intent intent = getIntent();
        int position=intent.getIntExtra("position",0);
        acceptedListItem = GetAcceptedListOwner.getInstance().acceptedListItems.get(position);
        address = acceptedListItem.getAddress()+","+
                acceptedListItem.getReceiverCity()+","+
                acceptedListItem.getReceiverState()+","+
                acceptedListItem.getReceiverLand()+","+
                acceptedListItem.getPincode();
        dispatchTime = acceptedListItem.getDispatchTime();
        orderNo = acceptedListItem.getOrderNumber();
        image = acceptedListItem.getImage();
        transName = acceptedListItem.getTransName();
        transMo = acceptedListItem.getTransMo();
        receiverName = acceptedListItem.getReceiver();
        receiverMo = acceptedListItem.getReceiverMo();
        size = acceptedListItem.getSize();
        weight = acceptedListItem.getWeight();
        timeline = acceptedListItem.getTimeline();
        type = acceptedListItem.getType();
        String newImg =image.replace("\\","");
        TextView textViewTransName = (TextView)findViewById(R.id.owner_accepted_model_full_edit_sender_name);
        TextView textViewTransMo = (TextView)findViewById(R.id.owner_accepted_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.owner_accepted_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.owner_accepted_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.owner_accepted_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.owner_accepted_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.owner_accepted_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.owner_accepted_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.owner_accepted_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.owner_accepted_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.owner_accepted_model_full_edit_disp_time);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewDispTime.setText(dispatchTime);
        textViewTransName.setText(transName);
        textViewTransMo.setText(transMo);
        textViewReceiverName.setText(receiverName);
        textViewReceiverMo.setText(receiverMo);
        textViewSize.setText(size);
        textViewWeight.setText(weight);
        textViewTimeline.setText(timeline);
        textViewType.setText(type);
        ImageView imageView = (ImageView)findViewById(R.id.owner_image_full_owner_accepted_list);
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

    }

    public static boolean deleteDirectory(File path) {
        if( path.exists() ) {
            File[] files = path.listFiles();
            if (files == null) {
                return true;
            }
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectory(file);
                } else {
                    file.delete();
                }
            }
        }
        return( path.delete() );
    }

    @Override
    public void Result(String result, String keyOnline) {

        /*Program to Read Success Msg Here*/
        Log.d("resAcceptpickUp",result);
        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            setResult(4);
            finish();
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Check Connection");
            dialog.show();
        }
    }
}
