package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by girish on 22/3/17.
 */

public class TransManageTabFragmentDelivering extends Fragment implements AbsListView.OnScrollListener,ResultInString {

    ListView listViewDelivered;
    SharedPreferenceUserData sharedPreferenceUserData;
    SlgnTransManageGetAcceptedList slgnTransManageGetAcceptedList;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private int count =0;
    Activity activity;
    String uid,onlineKey;
    ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentTransAcceptedList= inflater.inflate(R.layout.trans_manage_fragment_accepted_list,container,false);

        onlineKey = getResources().getString(R.string.transManageDeliveredList);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<String, String>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new TransManageBackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
        listViewDelivered = new ListView(getActivity());
        listViewDelivered = (ListView)fragmentTransAcceptedList.findViewById(R.id.trans_manage_accepted_list);
        listViewDelivered.setOnScrollListener(this);
        adapter = new TransManageCustomDeliveredListAdapter(getActivity());


        return fragmentTransAcceptedList;
    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

        if (loading) {
            if (totalItemCount > previousTotal) {
                loading = false;
                previousTotal = totalItemCount;
                currentPage++;
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            //Background Execute here
            count++;
            HashMap<String,String> hashMapData = new HashMap<String, String>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new TransManageBackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
            loading = true;
        }
    }

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("resTransMngDeliveredLst",result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListAccepted = jsonObject.getJSONArray(getResources().getString(R.string.server_key_picked));

            int len1 = jsonArrayListAccepted.length();
            for (int i=0;i<len1;i++){
                GtStTransManageDeliveredList gtStTransManageDeliveredList = new GtStTransManageDeliveredList();
                JSONObject object = jsonArrayListAccepted.getJSONObject(i);
                gtStTransManageDeliveredList.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                gtStTransManageDeliveredList.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                gtStTransManageDeliveredList.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                gtStTransManageDeliveredList.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                gtStTransManageDeliveredList.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                gtStTransManageDeliveredList.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                gtStTransManageDeliveredList.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                gtStTransManageDeliveredList.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                gtStTransManageDeliveredList.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                gtStTransManageDeliveredList.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                gtStTransManageDeliveredList.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                gtStTransManageDeliveredList.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                gtStTransManageDeliveredList.setSenderPin(object.getString(getResources().getString(R.string.server_key_senderPin)));
                gtStTransManageDeliveredList.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                gtStTransManageDeliveredList.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                gtStTransManageDeliveredList.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                gtStTransManageDeliveredList.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                gtStTransManageDeliveredList.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                gtStTransManageDeliveredList.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));
                gtStTransManageDeliveredList.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                gtStTransManageDeliveredList.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));
                gtStTransManageDeliveredList.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                gtStTransManageDeliveredList.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                gtStTransManageDeliveredList.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                gtStTransManageDeliveredList.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));
                SlgnTransManageGetDeliveredList.getInstance().gtStTransManageDeliveredLists.add(gtStTransManageDeliveredList);
            }
            listViewDelivered.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
