package com.whitebird.parcel.Owner.Profile.HistoryPageForOwner;

import android.annotation.SuppressLint;
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

import com.whitebird.parcel.ItemsInListSend;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by girish on 14/3/17.
 */

@SuppressLint("ValidFragment")
class FragmentSendList extends Fragment implements AbsListView.OnScrollListener,ResultInString{
    ListView listViewHistory;
    Activity activity;
    SharedPreferenceUserData sharedPreferenceUserData;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private boolean loading = true;
    private int count =0;
    String uid,onlineKey;
    ListAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentSend= inflater.inflate(R.layout.fragment_send_history_owner,container,false);
        //Set Data For Showing List
        onlineKey = getResources().getString(R.string.historyListOwnerKey);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_user),"1");
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForHistoryFragmentResult(hashMapData, onlineKey,this).execute();
        listViewHistory = new ListView(getActivity());
        listViewHistory = (ListView)fragmentSend.findViewById(R.id.history_owner_send_list_view);
        listViewHistory.setOnScrollListener(this);
        adapter = new CustomHistorySendListAdapter(getActivity());
        return fragmentSend;
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
            }
        }
        if (!loading && (totalItemCount - visibleItemCount) <= (firstVisibleItem + visibleThreshold)) {
            // I load the next page of gigs using a background task,
            // but you can call any function here.
            //Background Execute here
            count++;
            HashMap<String,String> hashMapData = new HashMap<>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
            hashMapData.put(getResources().getString(R.string.server_key_user),"1");
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new BackgroundTaskForHistoryFragmentResult(hashMapData, onlineKey,this).execute();
            loading = true;
        }
    }

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("ResultInFragSendHis",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListSend = jsonObject.getJSONArray(getResources().getString(R.string.server_key_outgoing));

            int len1 = jsonArrayListSend.length();
            for (int i=0;i<len1;i++){
                ItemsInListSend sendHistoryListItem = new ItemsInListSend();
                JSONObject object = jsonArrayListSend.getJSONObject(i);
                sendHistoryListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                sendHistoryListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                sendHistoryListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                sendHistoryListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                sendHistoryListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                sendHistoryListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                sendHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                sendHistoryListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                sendHistoryListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                sendHistoryListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                sendHistoryListItem.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                sendHistoryListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                /*sendHistoryListItem.setSenderPin(object.getString(getResources().getString(R.string.server_key_senderPin)));*/
                sendHistoryListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                sendHistoryListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                sendHistoryListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                sendHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                sendHistoryListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                sendHistoryListItem.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                sendHistoryListItem.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));
                sendHistoryListItem.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                sendHistoryListItem.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));
                sendHistoryListItem.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                sendHistoryListItem.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                sendHistoryListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                sendHistoryListItem.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));
                GetSendHistoryList.getInstance().itemsInListSends.add(sendHistoryListItem);
            }
            listViewHistory.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
