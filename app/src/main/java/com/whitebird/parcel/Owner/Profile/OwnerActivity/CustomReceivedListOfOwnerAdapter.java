package com.whitebird.parcel.Owner.Profile.OwnerActivity;

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

class CustomReceivedListOfOwnerAdapter extends BaseAdapter {
    private ArrayList<ReceivedListItem> arrayList;
    private Activity activity;
    public CustomReceivedListOfOwnerAdapter(FragmentActivity activity, ArrayList<ReceivedListItem> arrayList) {
        this.arrayList = new ArrayList<>();
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GetReceivedListOwner.getInstance().receivedListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return GetReceivedListOwner.getInstance().receivedListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetReceivedListOwner.getInstance().receivedListItems.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_received_list_model,null);
        }

        TextView textViewAddress = (TextView)convertView.findViewById(R.id.received_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.received_model_edit_order_no);
        TextView textViewStatus = (TextView)convertView.findViewById(R.id.received_model_edit_status);
        CardView cardView = (CardView)convertView.findViewById(R.id.card_view_received_list);
        String address = GetReceivedListOwner.getInstance().receivedListItems.get(position).getSenderAd()+","+
                GetReceivedListOwner.getInstance().receivedListItems.get(position).getSenderLand()+","+
                GetReceivedListOwner.getInstance().receivedListItems.get(position).getSenderCity()+","+
                GetReceivedListOwner.getInstance().receivedListItems.get(position).getSenderState()+","+
                GetReceivedListOwner.getInstance().receivedListItems.get(position).getPincode();
        String orderNo = GetReceivedListOwner.getInstance().receivedListItems.get(position).getOrderNumber();
        String status = GetReceivedListOwner.getInstance().receivedListItems.get(position).getStatus();
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewStatus.setText(status);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActReceivedOwnerFullView.class);
                intent.putExtra("position",position);
                activity.startActivityForResult(intent,0);
            }
        });
        return convertView;
    }

}

