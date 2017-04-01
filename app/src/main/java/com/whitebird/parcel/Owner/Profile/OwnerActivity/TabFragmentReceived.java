package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.annotation.SuppressLint;
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
 * Created by girish on 18/3/17.
 */

@SuppressLint("ValidFragment")
public class TabFragmentReceived extends Fragment implements AbsListView.OnScrollListener,ResultInString {
    ListView listViewReceived;
    SharedPreferenceUserData sharedPreferenceUserData;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private boolean loading = true;
    private int count =0;
    String uid,onlineKey;
    ListAdapter adapter;
    ArrayList<ReceivedListItem> arrayList;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentReceivedList= inflater.inflate(R.layout.fragment_received_main_list_owner,container,false);
        arrayList = new ArrayList<>();
        onlineKey = getResources().getString(R.string.mainListofOwner);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
        listViewReceived = new ListView(getActivity());
        listViewReceived = (ListView)fragmentReceivedList.findViewById(R.id.list_view_received_owner_list);
        listViewReceived.setOnScrollListener(this);
        adapter = new CustomReceivedListOfOwnerAdapter(getActivity(),arrayList);
        return fragmentReceivedList;
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
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new BackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
            loading = true;
        }
    }

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("ResultInFragReceived",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListReceived = jsonObject.getJSONArray(getResources().getString(R.string.server_key_incoming));

            int len1 = jsonArrayListReceived.length();
            for (int i=0;i<len1;i++){
                ReceivedListItem receivedListItem = new ReceivedListItem();
                JSONObject object = jsonArrayListReceived.getJSONObject(i);
                receivedListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                receivedListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                receivedListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                receivedListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                receivedListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                receivedListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                receivedListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                receivedListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                receivedListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                receivedListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                receivedListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                receivedListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                receivedListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                receivedListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                receivedListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                receivedListItem.setTransName(object.getString(getResources().getString(R.string.server_key_transName)));
                receivedListItem.setTransMo(object.getString(getResources().getString(R.string.server_key_transMobile)));
                receivedListItem.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                /*receivedListItem.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                receivedListItem.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                receivedListItem.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));

                receivedListItem.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));*/
                /*receivedListItem.setReceiverAd(object.getString(getResources().getString(R.string.server_key_receiverAd)));
                receivedListItem.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                receivedListItem.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                receivedListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                receivedListItem.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));*/
                receivedListItem.setCityName(object.getString(getResources().getString(R.string.server_key_cityName)));
                receivedListItem.setStateName(object.getString(getResources().getString(R.string.server_key_stateName)));
                GetReceivedListOwner.getInstance().receivedListItems.add(receivedListItem);
            }
            arrayList = new ArrayList<>();
            arrayList = GetReceivedListOwner.getInstance().receivedListItems;
            listViewReceived.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
