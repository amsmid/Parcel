package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitebird.parcel.R;

import java.io.File;

public class ActPendingOwnerFullView extends AppCompatActivity {

    PendingListItem pendingListItem;
    String address,dispatchTime,orderNo,image,senderAddress,senderName,senderMo,receiverName,receiverMo,size,weight,timeline,type;
    File mediaFile,mediaStorageDir;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pending_owner_full_view);
        Intent intent = getIntent();
        int position=intent.getIntExtra("position",0);
        pendingListItem = GetPendingListOwner.getInstance().pendingListItems.get(position);
        address = pendingListItem.getAddress()+","+
                pendingListItem.getReceiverCity()+","+
                pendingListItem.getReceiverState()+","+
                pendingListItem.getReceiverLand()+","+
                pendingListItem.getPincode();
        dispatchTime = pendingListItem.getDispatchTime();
        orderNo = pendingListItem.getOrderNumber();
        image = pendingListItem.getImage();
        senderName = pendingListItem.getSender();
        senderMo = pendingListItem.getSenderMo();
        receiverName = pendingListItem.getReceiver();
        receiverMo = pendingListItem.getReceiverMo();
        size = pendingListItem.getSize();
        weight = pendingListItem.getWeight();
        timeline = pendingListItem.getTimeline();
        type = pendingListItem.getType();
        String newImg =image.replace("\\","");
        TextView textViewReceiverName = (TextView)findViewById(R.id.owner_pending_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.owner_pending_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.owner_pending_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.owner_pending_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.owner_pending_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.owner_pending_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.owner_pending_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.owner_pending_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.owner_pending_model_full_edit_disp_time);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewDispTime.setText(dispatchTime);
        textViewReceiverName.setText(receiverName);
        textViewReceiverMo.setText(receiverMo);
        textViewSize.setText(size);
        textViewWeight.setText(weight);
        textViewTimeline.setText(timeline);
        textViewType.setText(type);
        ImageView imageView = (ImageView)findViewById(R.id.owner_image_full_owner_pending_list);
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
            for(int i=0; i<files.length; i++) {
                if(files[i].isDirectory()) {
                    deleteDirectory(files[i]);
                }
                else {
                    files[i].delete();
                }
            }
        }
        return( path.delete() );
    }
}
