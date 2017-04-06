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


/**
 * Created by girish on 19/3/17.
 */

class CustomProcessingListOfOwnerAdapter extends BaseAdapter {
    private Activity activity;
    public CustomProcessingListOfOwnerAdapter(FragmentActivity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return GetProcessingListOwner.getInstance().processingListItems.size();
    }

    @Override
    public Object getItem(int position) {
        return GetProcessingListOwner.getInstance().processingListItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return GetProcessingListOwner.getInstance().processingListItems.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_processing_list_model,null);
        }
        if (GetProcessingListOwner.getInstance().processingListItems.isEmpty()){
            convertView.setBackgroundResource(R.drawable.no_content_image);
        }else {
            TextView textViewAddress = (TextView) convertView.findViewById(R.id.processing_model_edit_address);
            TextView textViewOrderNo = (TextView) convertView.findViewById(R.id.processing_model_edit_order_no);
            TextView textViewTransName = (TextView) convertView.findViewById(R.id.processing_model_edit_status);
            CardView cardView = (CardView) convertView.findViewById(R.id.card_view_processing_list);
            String address = GetProcessingListOwner.getInstance().processingListItems.get(position).getAddress()+"\n"+
                    GetProcessingListOwner.getInstance().processingListItems.get(position).getLandmark()+"\n"+
                    GetProcessingListOwner.getInstance().processingListItems.get(position).getCityName()+","+
                    GetProcessingListOwner.getInstance().processingListItems.get(position).getStateName()+"\n"+
                    GetProcessingListOwner.getInstance().processingListItems.get(position).getPincode();
            String orderNo = GetProcessingListOwner.getInstance().processingListItems.get(position).getOrderNumber();
            String status = GetProcessingListOwner.getInstance().processingListItems.get(position).getTransName();
            textViewAddress.setText(address);
            textViewOrderNo.setText(orderNo);
            textViewTransName.setText(status);
            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(activity, ActProcessingOwnerFullView.class);
                    intent.putExtra("position", position);
                    activity.startActivity(intent);
                }
            });
        }
        return convertView;
    }
}
