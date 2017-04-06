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

public class ActReceivedOwnerFullView extends AppCompatActivity implements ResultInString {

    ReceivedListItem receivedListItem;
    String receiverId,senderId;
    String address,dispatchTime,orderNo,image,senderAddress,transName,transMo,receiverName,receiverMo,size,weight,timeline,type;

    String onlineKey;
    File mediaFile,mediaStorageDir;
    Button buttonReceivedPickUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_received_owner_full_view);


        /*
        * Accept Or Deny PickUp Button Shown Here*/
        buttonReceivedPickUp = (Button) findViewById(R.id.btn_act_received_parcel_arrived);
        onlineKey = getResources().getString(R.string.receivedParcelBtnOrder);
        buttonReceivedPickUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String, String> hashMapData = new HashMap<>();
                hashMapData.clear();
                hashMapData.put(getResources().getString(R.string.server_key_orderNumber), orderNo);
                hashMapData.put(getResources().getString(R.string.server_key_receiver), receiverId);
                hashMapData.put(getResources().getString(R.string.server_key_sender), senderId);
                new BackgroundTaskForResult(hashMapData, onlineKey, ActReceivedOwnerFullView.this).execute();
            }
        });


        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 0);
        intent.removeExtra("position");
        receivedListItem = GetReceivedListOwner.getInstance().receivedListItems.get(position);
        receiverId = receivedListItem.getReceiverId();
        senderId = receivedListItem.getSenderId();
        address = receivedListItem.getAddress()+","+
                receivedListItem.getLandmark()+","+
                receivedListItem.getCityName()+","+
                receivedListItem.getStateName()+","+
                receivedListItem.getPincode();
        dispatchTime = receivedListItem.getDispatchTime();
        orderNo = receivedListItem.getOrderNumber();
        image = receivedListItem.getImage();
        transName = receivedListItem.getTransName();
        transMo = receivedListItem.getTransMo();
        receiverName = receivedListItem.getSender();
        receiverMo = receivedListItem.getSenderMo();
        size = receivedListItem.getSize();
        weight = receivedListItem.getWeight();
        timeline = receivedListItem.getTimeline();
        type = receivedListItem.getType();
        String newImg =image.replace("\\","");
        TextView textViewTransName = (TextView)findViewById(R.id.owner_received_model_full_edit_sender_name);
        TextView textViewTransMo = (TextView)findViewById(R.id.owner_received_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.owner_received_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.owner_received_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.owner_received_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.owner_received_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.owner_received_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.owner_received_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.owner_received_model_full_edit_sender_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.owner_received_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.owner_received_model_full_edit_disp_time);
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
        ImageView imageView = (ImageView)findViewById(R.id.owner_image_full_owner_received_list);
        String imageUrl = getResources().getString(R.string.url) + newImg;

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
        Log.d("resReceivePickUp",result);
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
            result.equals("");
            finish();
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Check Connection");
            dialog.setPositiveButton("Ok",null);
            dialog.show();
        }
    }
}
