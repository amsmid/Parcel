package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitebird.parcel.Owner.Profile.OwnerActivity.ClsBackgroundImageLoader;
import com.whitebird.parcel.R;

import java.io.File;
import java.util.ArrayList;

public class ClsFullReceiveHistoryListView extends AppCompatActivity {

    ArrayList<ItemsInListReceive> arrayList;
    int position;
    ItemsInListReceive itemsInList;
    String address,dispatchTime,orderNo,image,senderAddress,senderName,senderMo,receiverName,receiverMo,size,weight,timeline,type;
    File mediaFile,mediaStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cls_full_receive_history_list_view);

        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        itemsInList = GetReceiveHistoryList.getInstance().itemsInListReceives.get(position);
        senderAddress = itemsInList.getSenderAd()+","+
                itemsInList.getSenderCity()+","+
                itemsInList.getSenderState()+","+
                itemsInList.getSenderLand()+","+
                itemsInList.getSenderPin();
        address = itemsInList.getAddress()+","+
                itemsInList.getReceiverCity()+","+
                itemsInList.getReceiverState()+","+
                itemsInList.getAddress()+","+
                itemsInList.getPincode();
        dispatchTime = itemsInList.getDispatchTime();
        orderNo = itemsInList.getOrderNumber();
        image = itemsInList.getImage();
        senderName = itemsInList.getSender();
        senderMo = itemsInList.getSenderMo();
        receiverName = itemsInList.getReceiver();
        receiverMo = itemsInList.getReceiverMo();
        size = itemsInList.getSize();
        weight = itemsInList.getWeight();
        timeline = itemsInList.getTimeline();
        type = itemsInList.getType();
        String newImg =image.replace("\\","");
        TextView textViewSenderAddress = (TextView)findViewById(R.id.owner_history_received_model_full_edit_sender_address);
        TextView textViewSenderName = (TextView)findViewById(R.id.owner_history_received_model_full_edit_sender_name);
        TextView textViewSenderMo = (TextView)findViewById(R.id.owner_history_received_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.owner_history_received_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.owner_history_received_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.owner_history_received_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.owner_history_received_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.owner_history_received_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.owner_history_received_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.owner_history_received_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.owner_history_received_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.owner_history_received_model_full_edit_disp_time);
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
        ImageView imageView = (ImageView)findViewById(R.id.owner_image_full_owner_history_received_list);

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
}
