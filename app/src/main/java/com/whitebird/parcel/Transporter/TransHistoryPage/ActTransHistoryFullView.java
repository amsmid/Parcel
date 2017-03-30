package com.whitebird.parcel.Transporter.TransHistoryPage;

import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.whitebird.parcel.R;
import com.whitebird.parcel.Transporter.TransManageClsBackgroundImageLoader;

import java.io.File;
import java.util.ArrayList;

public class ActTransHistoryFullView extends AppCompatActivity {

    ArrayList<TransHistoryListItem> arrayList;
    int position;
    TransHistoryListItem transHistoryListItem;
    String address,dispatchTime,orderNo,image,senderAddress,senderName,senderMo,receiverName,receiverMo,size,weight,timeline,type;
    File mediaFile,mediaStorageDir;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_trans_history_full_view);

        arrayList = new ArrayList<>();
        Intent intent = getIntent();
        position = intent.getIntExtra("position",0);
        transHistoryListItem = GetTransHistoryList.getInstance().transHistoryListItems.get(position);

        senderAddress = transHistoryListItem.getSenderAd()+","+
                transHistoryListItem.getSenderCity()+","+
                transHistoryListItem.getSenderState()+","+
                transHistoryListItem.getSenderLand()+","+
                transHistoryListItem.getSenderPin();
        address = transHistoryListItem.getAddress()+","+
                transHistoryListItem.getReceiverCity()+","+
                transHistoryListItem.getReceiverState()+","+
                transHistoryListItem.getAddress()+","+
                transHistoryListItem.getPincode();
        dispatchTime = transHistoryListItem.getDispatchTime();
        orderNo = transHistoryListItem.getOrderNumber();
        image = transHistoryListItem.getImage();
        senderName = transHistoryListItem.getSender();
        senderMo = transHistoryListItem.getSenderMo();
        receiverName = transHistoryListItem.getReceiver();
        receiverMo = transHistoryListItem.getReceiverMo();
        size = transHistoryListItem.getSize();
        weight = transHistoryListItem.getWeight();
        timeline = transHistoryListItem.getTimeline();
        type = transHistoryListItem.getType();
        String newImg =image.replace("\\","");
        TextView textViewSenderAddress = (TextView)findViewById(R.id.trans_history_model_full_edit_sender_address);
        TextView textViewSenderName = (TextView)findViewById(R.id.trans_history_model_full_edit_sender_name);
        TextView textViewSenderMo = (TextView)findViewById(R.id.trans_history_model_full_edit_sender_mob);
        TextView textViewReceiverName = (TextView)findViewById(R.id.trans_history_model_full_edit_receiver_name);
        TextView textViewReceiverMo = (TextView)findViewById(R.id.trans_history_model_full_edit_receiver_mob);
        TextView textViewSize = (TextView)findViewById(R.id.trans_history_model_full_edit_size);
        TextView textViewWeight = (TextView)findViewById(R.id.trans_history_model_full_edit_weight);
        TextView textViewTimeline = (TextView)findViewById(R.id.trans_history_model_full_edit_timeline);
        TextView textViewType = (TextView)findViewById(R.id.trans_history_model_full_edit_type);
        TextView textViewAddress = (TextView)findViewById(R.id.trans_history_model_full_edit_address);
        TextView textViewOrderNo = (TextView)findViewById(R.id.trans_history_model_full_edit_order_no);
        TextView textViewDispTime = (TextView)findViewById(R.id.trans_history_model_full_edit_disp_time);
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
        ImageView imageView = (ImageView)findViewById(R.id.trans_image_full_owner_history_list);

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
