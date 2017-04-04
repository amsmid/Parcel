package com.whitebird.parcel.Transporter.TransHistoryPage;

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
 * Created by girish on 25/3/17.
 */


class CustomTransHistoryListAdapter extends BaseAdapter {
    private Activity activity;
    public CustomTransHistoryListAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GetTransHistoryList.getInstance().transHistoryListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return GetTransHistoryList.getInstance().transHistoryListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetTransHistoryList.getInstance().transHistoryListItems.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_trans_history_list_model,null);
        }



        TextView textViewSenderAddress = (TextView)convertView.findViewById(R.id.trans_history_model_edit_sender_address);
        TextView textViewAddress = (TextView)convertView.findViewById(R.id.trans_history_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.trans_history_model_edit_order_no);
        CardView cardView = (CardView)convertView.findViewById(R.id.trans_history_card_view_list);
        String senderAddress = GetTransHistoryList.getInstance().transHistoryListItems.get(position).getSenderAd()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getSenderCity()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getSenderState()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getSenderLand()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getSenderPin();
        String address = GetTransHistoryList.getInstance().transHistoryListItems.get(position).getAddress()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getReceiverCity()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getReceiverState()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getAddress()+","+
                GetTransHistoryList.getInstance().transHistoryListItems.get(position).getPincode();
        String orderNo = GetTransHistoryList.getInstance().transHistoryListItems.get(position).getOrderNumber();
        textViewSenderAddress.setText(senderAddress);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
            cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActTransHistoryFullView.class);
                intent.putExtra("position",position);
                activity.startActivityForResult(intent,0);
            }
        });
        return convertView;
    }
}

