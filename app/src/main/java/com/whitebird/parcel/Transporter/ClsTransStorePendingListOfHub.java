package com.whitebird.parcel.Transporter;



import android.app.Activity;

import com.whitebird.parcel.R;
import com.whitebird.parcel.TransPendingList;
import com.whitebird.parcel.Transporter.MainActivityPickShipment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by girish on 21/3/17.
 */

public class ClsTransStorePendingListOfHub {
    private ArrayList<TransPendingList> transPendingLists;
    private Activity activity;
    public ClsTransStorePendingListOfHub(Activity activity){
        transPendingLists = new ArrayList<>();
        this.activity=activity;
    }

    public void SavePendingList(String listArray){

        try {
            JSONObject jsonObject = new JSONObject(listArray);
            JSONArray jsonArrayListPending = jsonObject.getJSONArray(activity.getResources().getString(R.string.server_key_pending));

            int len1 = jsonArrayListPending.length();
            for (int i=0;i<len1;i++){
                TransPendingList pendingListItem = new TransPendingList();
                JSONObject object = jsonArrayListPending.getJSONObject(i);
                pendingListItem.setSenderId(object.getString(activity.getResources().getString(R.string.server_key_senderId)));
                pendingListItem.setReceiverId(object.getString(activity.getResources().getString(R.string.server_key_receiverId)));
                pendingListItem.setOrderNumber(object.getString(activity.getResources().getString(R.string.server_key_orderNumber)));
                pendingListItem.setAddress(object.getString(activity.getResources().getString(R.string.server_key_address)));
                pendingListItem.setPincode(object.getString(activity.getResources().getString(R.string.server_key_pincode)));
                pendingListItem.setTime(object.getString(activity.getResources().getString(R.string.server_key_time)));
                pendingListItem.setLandmark(object.getString(activity.getResources().getString(R.string.server_key_landmark)));
                pendingListItem.setSize(object.getString(activity.getResources().getString(R.string.server_key_size)));
                pendingListItem.setWeight(object.getString(activity.getResources().getString(R.string.server_key_weight)));
                pendingListItem.setImage(object.getString(activity.getResources().getString(R.string.server_key_image)));
                pendingListItem.setSenderAd(object.getString(activity.getResources().getString(R.string.server_key_senderAd)));
                pendingListItem.setSender(object.getString(activity.getResources().getString(R.string.server_key_sender)));
                pendingListItem.setSenderPin(object.getString(activity.getResources().getString(R.string.server_key_senderPin)));
                pendingListItem.setReceiver(object.getString(activity.getResources().getString(R.string.server_key_receiver)));
                pendingListItem.setType(object.getString(activity.getResources().getString(R.string.server_key_type)));
                pendingListItem.setTimeline(object.getString(activity.getResources().getString(R.string.server_key_timeline)));
                pendingListItem.setLandmark(object.getString(activity.getResources().getString(R.string.server_key_landmark)));
                pendingListItem.setDispatchTime(object.getString(activity.getResources().getString(R.string.server_key_dispatchTime)));
                pendingListItem.setSenderCity(object.getString(activity.getResources().getString(R.string.server_key_senderCity)));
                pendingListItem.setSenderState(object.getString(activity.getResources().getString(R.string.server_key_senderState)));
                pendingListItem.setSenderMo(object.getString(activity.getResources().getString(R.string.server_key_senderMo)));
                pendingListItem.setSenderLand(object.getString(activity.getResources().getString(R.string.server_key_senderLand)));
                pendingListItem.setReceiverCity(object.getString(activity.getResources().getString(R.string.server_key_receiverCity)));
                pendingListItem.setReceiverState(object.getString(activity.getResources().getString(R.string.server_key_receiverState)));
                pendingListItem.setReceiverMo(object.getString(activity.getResources().getString(R.string.server_key_receiverMo)));
                pendingListItem.setReceiverLand(object.getString(activity.getResources().getString(R.string.server_key_receiverLand)));
                SlgnTransPendingParcelList.getInstance().transPendingLists.add(pendingListItem);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public ArrayList<TransPendingList> getTransPendingLists() {
        return transPendingLists;
    }
}
