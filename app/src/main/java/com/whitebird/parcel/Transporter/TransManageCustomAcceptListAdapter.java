package com.whitebird.parcel.Transporter;

import android.annotation.SuppressLint;
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


class TransManageCustomAcceptListAdapter extends BaseAdapter {

    private Activity activity;
    TransManageCustomAcceptListAdapter(Activity activity){
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.size();
    }

    @Override
    public Object getItem(int position) {
        return SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.indexOf(position);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.trans_manage_list_view_model_accepted_list,null);
        }

        TextView textViewSenderAddress = (TextView)convertView.findViewById(R.id.trans_manage_accepted_model_edit_sender_address);
        TextView textViewAddress = (TextView)convertView.findViewById(R.id.trans_manage_accepted_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.trans_manage_accepted_model_edit_order_no);
        TextView textViewStatus = (TextView)convertView.findViewById(R.id.trans_manage_accepted_model_edit_dispatch_time);
        CardView cardView = (CardView)convertView.findViewById(R.id.trans_manage_card_view_accepted_list);
        String senderAddress = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getSenderAd()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getSenderCity()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getSenderState()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getSenderLand()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getSenderPin();
        String address = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getAddress()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getReceiverCity()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getReceiverState()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getAddress()+","+
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getPincode();
        String orderNo = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getOrderNumber();
        String status = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.get(position).getDispatchTime();
        textViewSenderAddress.setText(senderAddress);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewStatus.setText(status);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActTransManageFullAcceptedView.class);
                intent.putExtra("position",position);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}

