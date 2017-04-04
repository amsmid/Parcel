package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import com.whitebird.parcel.R;

import java.util.ArrayList;

/**
 * Created by girish on 22/3/17.
 */


class TransManageCustomDeliveredListAdapter extends BaseAdapter {

    private Activity activity;
    TransManageCustomDeliveredListAdapter(Activity activity){
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.size();
    }

    @Override
    public Object getItem(int position) {
        return SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.trans_manage_list_view_model_delivered_list,null);
        }
//        SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position)

        TextView textViewSenderAddress = (TextView)convertView.findViewById(R.id.trans_manage_delivered_model_edit_sender_address);
        TextView textViewAddress = (TextView)convertView.findViewById(R.id.trans_manage_delivered_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.trans_manage_delivered_model_edit_order_no);
        TextView textViewStatus = (TextView)convertView.findViewById(R.id.trans_manage_delivered_model_edit_dispatch_time);
        CardView cardView = (CardView)convertView.findViewById(R.id.trans_manage_card_view_delivered_list);
        String senderAddress = SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getSenderAd()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getSenderCity()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getSenderState()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getSenderLand()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getSenderPin();
        String address = SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getAddress()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getReceiverCity()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getReceiverState()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getAddress()+","+
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getPincode();
        String orderNo = SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getOrderNumber();
        String status = SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.get(position).getDispatchTime();
        textViewSenderAddress.setText(senderAddress);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewStatus.setText(status);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActTransManageFullDeliveredView.class);
                intent.putExtra("position",position);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}


