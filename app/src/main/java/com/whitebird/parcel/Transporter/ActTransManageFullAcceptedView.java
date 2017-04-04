package com.whitebird.parcel.Transporter;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitebird.parcel.R;

import java.io.File;

public class ActTransManageFullAcceptedView extends AppCompatActivity {

    GtStTransManageAcceptedList gtStTransManageAcceptedList;
    String address,dispatchTime,orderNo,image,senderAddress,senderName,senderMo,receiverName,receiverMo,size,weight,timeline,type;
    File mediaFile,mediaStorageDir;
    Button btnShowLocation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_trans_manage_full_accepted_view);
        Intent intent = getIntent();
        int position=intent.getIntExtra("position",0);
        gtStTransManageAcceptedList = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position);
        senderAddress = gtStTransManageAcceptedList.getSenderAd()+","+
                gtStTransManageAcceptedList.getSenderLand()+","+
                gtStTransManageAcceptedList.getSenderCity()+","+
                gtStTransManageAcceptedList.getSenderState()+","+
                gtStTransManageAcceptedList.getSenderPin();
        address = gtStTransManageAcceptedList.getAddress()+","+
                gtStTransManageAcceptedList.getLandmark()+","+
                gtStTransManageAcceptedList.getReceiverCity()+","+
                gtStTransManageAcceptedList.getReceiverState()+","+
                gtStTransManageAcceptedList.getPincode();
        dispatchTime = gtStTransManageAcceptedList.getDispatchTime();
        orderNo = gtStTransManageAcceptedList.getOrderNumber();
        image = gtStTransManageAcceptedList.getImage();
        senderName = gtStTransManageAcceptedList.getSender();
        senderMo = gtStTransManageAcceptedList.getSenderMo();
        receiverName = gtStTransManageAcceptedList.getReceiver();
        receiverMo = gtStTransManageAcceptedList.getReceiverMo();
        size = gtStTransManageAcceptedList.getSize();
        weight = gtStTransManageAcceptedList.getWeight();
        timeline = gtStTransManageAcceptedList.getTimeline();
        type = gtStTransManageAcceptedList.getType();
        String newImg =image.replace("\\","");
        TextView textViewSenderAddress = (TextView)findViewById(R.id.trans_accepted_model_full_edit_sender_address);
        TextView textViewSenderName = (TextView)findViewById(R.id.trans_accepted_model_full_edit_sender_name);
        TextView textViewSenderMo = (TextView)findViewById(R.id.trans_accepted_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.trans_accepted_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.trans_accepted_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.trans_accepted_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.trans_accepted_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.trans_accepted_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.trans_accepted_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.trans_accepted_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.trans_accepted_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.trans_accepted_model_full_edit_disp_time);
        textViewSenderAddress.setText(senderAddress);
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
        ImageView imageView = (ImageView)findViewById(R.id.trans_image_full_owner_accepted_list);
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
        new TransManageClsBackgroundImageLoader(this,imageUrl,imageView,mediaFile).execute();

//        To Show Location of sender button described here
        btnShowLocation = (Button)findViewById(R.id.trans_btn_show_loc_accepted_parcel);
        btnShowLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(ActTransManageFullAcceptedView.this,TransMapView.class);
                intent1.putExtra("address",senderAddress);
                startActivity(intent1);
            }
        });


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
}
