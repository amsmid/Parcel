package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whitebird.parcel.R;


import java.util.ArrayList;


/**
 * Created by girish on 14/3/17.
 */

class CustomHistoryReceiveListAdapter extends BaseAdapter {
    private ArrayList<ItemsInListReceive> arrayList;
    private Activity activity;
    CustomHistoryReceiveListAdapter(Activity activity,ArrayList<ItemsInListReceive> arrayList){
        this.arrayList = new ArrayList<>();
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GetReceiveHistoryList.getInstance().itemsInListReceives.size();
    }

    @Override
    public Object getItem(int position) {
        return GetReceiveHistoryList.getInstance().itemsInListReceives.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetReceiveHistoryList.getInstance().itemsInListReceives.indexOf(position);
    }

    @SuppressLint("InflateParams")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_history_model_receive,null);
        }

        final ItemsInListReceive itemsInList;
        itemsInList = GetReceiveHistoryList.getInstance().itemsInListReceives.get(position);
        

        TextView textViewSenderAddress = (TextView)convertView.findViewById(R.id.owner_history_receive_ad_model_edit_sender_address);
        TextView textViewAddress = (TextView)convertView.findViewById(R.id.owner_history_receive_ad_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.owner_history_receive_ad_model_edit_order_no);
        CardView cardView = (CardView)convertView.findViewById(R.id.owner_card_view_history_receive_ad_list);
        String senderAddress = itemsInList.getSenderAd()+","+
                itemsInList.getSenderCity()+","+
                itemsInList.getSenderState()+","+
                itemsInList.getSenderLand()+","+
                itemsInList.getSenderPin();
        String address = itemsInList.getAddress()+","+
                itemsInList.getReceiverCity()+","+
                itemsInList.getReceiverState()+","+
                itemsInList.getAddress()+","+
                itemsInList.getPincode();
        String orderNo = itemsInList.getOrderNumber();
        textViewSenderAddress.setText(senderAddress);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);

        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentFullView = new Intent(activity,ClsFullReceiveHistoryListView.class);
                intentFullView.putExtra("position", position);
                activity.startActivity(intentFullView);
            }
        });
        return convertView;
    }
}
