package com.whitebird.parcel.Owner.Profile.OwnerActivity;

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

public class TabFragmentAccepted extends Fragment implements AbsListView.OnScrollListener,ResultInString {
    ListView listViewAccepted;
    SharedPreferenceUserData sharedPreferenceUserData;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private boolean loading = true;
    private int count =0;
    String uid,onlineKey;
    ListAdapter adapter;
    ArrayList<AcceptedListItem> arrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentAcceptedList= inflater.inflate(R.layout.fragment_accepted_main_list_owner,container,false);

        arrayList = new ArrayList<>();
        onlineKey = getResources().getString(R.string.mainListofOwner);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
        listViewAccepted = new ListView(getActivity());
        listViewAccepted = (ListView)fragmentAcceptedList.findViewById(R.id.list_view_accepted_owner_list);
        listViewAccepted.setOnScrollListener(this);
        adapter = new CustomAccptedListOfOwnerAdapter(getActivity(),arrayList);


        return fragmentAcceptedList;
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
        Log.d("ResultInFragAccepted",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListAccepted = jsonObject.getJSONArray(getResources().getString(R.string.server_key_accepted));

            int len1 = jsonArrayListAccepted.length();
            for (int i=0;i<len1;i++){
                AcceptedListItem acceptedListItem = new AcceptedListItem();
                JSONObject object = jsonArrayListAccepted.getJSONObject(i);
                acceptedListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                acceptedListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                acceptedListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                acceptedListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                acceptedListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                acceptedListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                acceptedListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                acceptedListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                acceptedListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                acceptedListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                acceptedListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                acceptedListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                acceptedListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                acceptedListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                acceptedListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                acceptedListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                acceptedListItem.setTransName(object.getString(getResources().getString(R.string.server_key_transName)));
                acceptedListItem.setTransMo(object.getString(getResources().getString(R.string.server_key_transMobile)));
                acceptedListItem.setCityName(object.getString(getResources().getString(R.string.server_key_cityName)));
                acceptedListItem.setStateName(object.getString(getResources().getString(R.string.server_key_stateName)));
                GetAcceptedListOwner.getInstance().acceptedListItems.add(acceptedListItem);
            }
            arrayList = new ArrayList<>();
            arrayList = GetAcceptedListOwner.getInstance().acceptedListItems;
            listViewAccepted.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
