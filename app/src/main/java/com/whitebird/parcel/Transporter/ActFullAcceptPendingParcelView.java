package com.whitebird.parcel.Transporter;

import android.content.DialogInterface;
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
import com.whitebird.parcel.Owner.Profile.OwnerActivity.ClsBackgroundImageLoader;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;
import com.whitebird.parcel.TransPendingList;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ActFullAcceptPendingParcelView extends AppCompatActivity implements ResultInString{
    int position;
    String address,dispatchTime,orderNo,image,senderAddress,senderName,senderMo,receiverName,receiverMo,size,weight,timeline,type;
    File mediaFile,mediaStorageDir;
    SharedPreferenceUserData sharedPreferenceUserData;
    String uid,onlineKey;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onlineKey = getResources().getString(R.string.acceptPendingOrder);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));

        setContentView(R.layout.activity_act_full_accept_pending_parcel_view);

        position = getIntent().getIntExtra("positionPendingList",0);

        senderAddress = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderAd()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderCity()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderState()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderLand()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderPin();
        address = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getAddress()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiverCity()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiverState()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getAddress()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getPincode();
        dispatchTime = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getDispatchTime();
        orderNo = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getOrderNumber();
        image = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getImage();
        senderName = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSender();
        senderMo = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderMo();
        receiverName = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiver();
        receiverMo = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiverMo();
        size = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSize();
        weight = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getWeight();
        timeline = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getTimeline();
        type = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getType();
        String newImg =image.replace("\\","");
        Button buttonAcceptDelivery = (Button)findViewById(R.id.btn_accept_delivery_in_pending);
        TextView textViewSenderAddress = (TextView)findViewById(R.id.trans_pending_model_full_edit_sender_address);
        TextView textViewSenderName = (TextView)findViewById(R.id.trans_pending_model_full_edit_sender_name);
        TextView textViewSenderMo = (TextView)findViewById(R.id.trans_pending_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.trans_pending_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.trans_pending_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.trans_pending_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.trans_pending_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.trans_pending_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.trans_pending_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.trans_pending_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.trans_pending_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.trans_pending_model_full_edit_disp_time);
        textViewSenderAddress.setText(address);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewDispTime.setText(dispatchTime);
        textViewSenderName.setText(senderName);
        textViewSenderMo.setText(senderMo);
        textViewReceiverName.setText(receiverName);
        textViewReceiverMo.setText(receiverMo);
        textViewSize.setText(size);
        textViewWeight.setText(weight);
        textViewTimeline.setText(timeline);
        textViewType.setText(type);
        ImageView imageView = (ImageView)findViewById(R.id.trans_image_full_owner_pending_list);
        String imageUrl = getResources().getString(R.string.url)+newImg;

        buttonAcceptDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,String> hashMapData = new HashMap<>();
                hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
                hashMapData.put(getResources().getString(R.string.server_key_orderNumber), orderNo);
                new BackgroundTaskForResult(hashMapData, onlineKey,ActFullAcceptPendingParcelView.this).execute();
            }
        });
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
        Log.d("resultInAcceptOrder",result);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Pick Parcel From Hub For Further Shipment");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                setResult(2);
                //startActivity(new Intent(ActFullAcceptPendingParcelView.this,MainActivityPickShipment.class));
                finish();

            }
        });
        builder.show();
    }
}
