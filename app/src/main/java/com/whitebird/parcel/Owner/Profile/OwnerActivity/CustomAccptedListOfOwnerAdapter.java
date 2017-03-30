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
 * Created by girish on 22/3/17.
 */

class CustomAccptedListOfOwnerAdapter extends BaseAdapter {
    private ArrayList<AcceptedListItem> arrayList;
    private Activity activity;
    public CustomAccptedListOfOwnerAdapter(FragmentActivity activity, ArrayList<AcceptedListItem> arrayList) {
        this.arrayList = new ArrayList<>();
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GetAcceptedListOwner.getInstance().acceptedListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return GetAcceptedListOwner.getInstance().acceptedListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetAcceptedListOwner.getInstance().acceptedListItems.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_accepted_list_model,null);
        }



        TextView textViewAddress = (TextView)convertView.findViewById(R.id.accepted_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.accepted_model_edit_order_no);
        TextView textViewTransName = (TextView)convertView.findViewById(R.id.accepted_model_edit_trans_name);
        CardView cardView = (CardView)convertView.findViewById(R.id.card_view_accepted_list);
        String address = GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getAddress()+","+
                GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getReceiverCity()+","+
                GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getReceiverState()+","+
                GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getReceiverLand()+","+
                GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getPincode();
        String orderNo = GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getOrderNumber();
        String transName = GetAcceptedListOwner.getInstance().acceptedListItems.get(position).getTransName();
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewTransName.setText(transName);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActAcceptedOwnerFullView.class);
                intent.putExtra("position",position);
                activity.startActivityForResult(intent,0);
            }
        });
        return convertView;
    }
}
