package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.whitebird.parcel.R;
import com.whitebird.parcel.TransPendingList;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by girish on 21/3/17.
 */

class TransPendingListOfHubAdapter extends BaseAdapter {

    private Activity activity;

    TransPendingListOfHubAdapter(Activity activity) {
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return SlgnTransPendingParcelList.getInstance().transPendingLists.size();
    }

    @Override
    public Object getItem(int position) {
        return SlgnTransPendingParcelList.getInstance().transPendingLists.get(position);
    }

    @Override
    public long getItemId(int position) {
        return SlgnTransPendingParcelList.getInstance().transPendingLists.indexOf(position);
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflator = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView==null){
            convertView = layoutInflator.inflate(R.layout.trans_pending_list_of_hub_model,null);
        }

        TextView textViewSenderAddress = (TextView)convertView.findViewById(R.id.trans_pending_model_edit_sender_address);
        TextView textViewAddress = (TextView)convertView.findViewById(R.id.trans_pending_model_edit_address);
        TextView textViewOrderNo = (TextView)convertView.findViewById(R.id.trans_pending_model_edit_order_no);
        TextView textViewDispTime = (TextView)convertView.findViewById(R.id.trans_pending_model_edit_dispatch_time);
        final String senderAddress = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderAd()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderCity()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderState()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderLand()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getSenderPin();
        final String address = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getAddress()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiverCity()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getReceiverState()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getAddress()+","+
                SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getPincode();
        final String orderNo = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getOrderNumber();
        final String dispTime = SlgnTransPendingParcelList.getInstance().transPendingLists.get(position).getDispatchTime();
        textViewSenderAddress.setText(senderAddress);
        textViewAddress.setText(address);
        textViewOrderNo.setText(orderNo);
        textViewDispTime.setText(dispTime);
        //final List<TransPendingList> transPendingLists = SlgnTransPendingParcelList.getInstance().transPendingLists;
        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentAcceptPendingParcelView = new Intent(activity,ActFullAcceptPendingParcelView.class);
                intentAcceptPendingParcelView.putExtra("positionPendingList",position);
                activity.startActivityForResult(intentAcceptPendingParcelView,0);
            }
        });

        return convertView;
    }
}
