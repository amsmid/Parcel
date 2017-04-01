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
class FragmentReceiveList extends Fragment implements AbsListView.OnScrollListener,ResultInString {
    ListView listViewReceiveHistory;
    Activity activity;
    SharedPreferenceUserData sharedPreferenceUserData;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private boolean loading = true;
    private int count =0;
    String uid,onlineKey;
    ListAdapter adapter;
    ArrayList<ItemsInListReceive> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentReceive= inflater.inflate(R.layout.fragment_receive_history_owner,container,false);
        //Set Data For Showing List

        //Set Data For Showing List
        arrayList = new ArrayList<>();
        onlineKey = getResources().getString(R.string.historyListOwnerKey);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_user),"1");
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForHistoryFragmentResult(hashMapData, onlineKey,this).execute();
        listViewReceiveHistory = new ListView(getActivity());
        listViewReceiveHistory = (ListView)fragmentReceive.findViewById(R.id.history_owner_receive_list_view);
        listViewReceiveHistory.setOnScrollListener(this);
        adapter = new CustomHistoryReceiveListAdapter(getActivity(),arrayList);
        return fragmentReceive;
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
        Log.d("ResultInFragReceiveHis",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListReceive = jsonObject.getJSONArray(getResources().getString(R.string.server_key_incoming));

            int len1 = jsonArrayListReceive.length();
            for (int i=0;i<len1;i++){
                ItemsInListReceive receiveHistoryListItem = new ItemsInListReceive();
                JSONObject object = jsonArrayListReceive.getJSONObject(i);
                receiveHistoryListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                receiveHistoryListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                receiveHistoryListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                receiveHistoryListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                receiveHistoryListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                receiveHistoryListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                receiveHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                receiveHistoryListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                receiveHistoryListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                receiveHistoryListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                receiveHistoryListItem.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                receiveHistoryListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                /*receiveHistoryListItem.setSenderPin(object.getString(getResources().getString(R.string.server_key_senderPin)));*/
                receiveHistoryListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                receiveHistoryListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                receiveHistoryListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                receiveHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                receiveHistoryListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                receiveHistoryListItem.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                receiveHistoryListItem.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));
                receiveHistoryListItem.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                receiveHistoryListItem.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));
                receiveHistoryListItem.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                receiveHistoryListItem.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                receiveHistoryListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                receiveHistoryListItem.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));
                GetReceiveHistoryList.getInstance().itemsInListReceives.add(receiveHistoryListItem);
            }
            arrayList = new ArrayList<>();
            arrayList = GetReceiveHistoryList.getInstance().itemsInListReceives;
            listViewReceiveHistory.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
