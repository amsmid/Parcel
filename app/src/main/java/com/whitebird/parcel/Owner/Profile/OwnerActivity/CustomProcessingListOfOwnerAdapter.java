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
 * Created by girish on 19/3/17.
 */

class CustomProcessingListOfOwnerAdapter extends BaseAdapter {
    private ArrayList<ProcessingListItem> arrayList;
    private Activity activity;
    public CustomProcessingListOfOwnerAdapter(FragmentActivity activity, ArrayList<ProcessingListItem> arrayList) {
        this.arrayList = new ArrayList<>();
        this.arrayList = arrayList;
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflater.inflate(R.layout.list_view_of_processing_list_model,null);
        }
        if (arrayList.isEmpty()){
            convertView.setBackgroundResource(R.drawable.no_content_image);
        }else {
            TextView textViewAddress = (TextView) convertView.findViewById(R.id.processing_model_edit_address);
            TextView textViewOrderNo = (TextView) convertView.findViewById(R.id.processing_model_edit_order_no);
            TextView textViewTransName = (TextView) convertView.findViewById(R.id.processing_model_edit_status);
            CardView cardView = (CardView) convertView.findViewById(R.id.card_view_processing_list);
            String address = arrayList.get(position).getAddress()+","+
                    arrayList.get(position).getCityName()+","+
                    arrayList.get(position).getStateName()+","+
                    arrayList.get(position).getLandmark()+","+
                    arrayList.get(position).getPincode();
            String orderNo = arrayList.get(position).getOrderNumber();
            String status = arrayList.get(position).getTransName();
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
