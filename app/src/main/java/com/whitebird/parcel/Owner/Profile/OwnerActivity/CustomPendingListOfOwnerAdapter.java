package com.whitebird.parcel.Owner.Profile.OwnerActivity;

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
 * Created by girish on 18/3/17.
 */

class CustomPendingListOfOwnerAdapter extends BaseAdapter {

    //private ArrayList<PendingListItem> arrayList;
    private Activity activity;
    CustomPendingListOfOwnerAdapter(Activity activity){
        /*this.arrayList = new ArrayList<>();
        this.arrayList = arrayList;*/
        this.activity = activity;
    }
    @Override
    public int getCount() {
        return GetPendingListOwner.getInstance().pendingListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return GetPendingListOwner.getInstance().pendingListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetPendingListOwner.getInstance().pendingListItems.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_pending_list_model,null);
        }

        TextView textViewAddress = (TextView)convertView.findViewById(R.id.pending_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.pending_model_edit_order_no);
        TextView textViewTime = (TextView)convertView.findViewById(R.id.pending_model_edit_time);
        CardView cardView = (CardView)convertView.findViewById(R.id.owner_card_view_pending_list);
        PendingListItem pendingListItem = GetPendingListOwner.getInstance().pendingListItems.get(position);
        String address = pendingListItem.getAddress()+","+
                pendingListItem.getCityName()+","+
                pendingListItem.getStateName()+","+
                pendingListItem.getLandmark()+","+
                pendingListItem.getPincode();
        String orderNo = pendingListItem.getOrderNumber();
        String status = pendingListItem.getTime();
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewTime.setText(status);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity,ActPendingOwnerFullView.class);
                intent.putExtra("position",position);
                activity.startActivity(intent);
            }
        });
        return convertView;
    }
}
