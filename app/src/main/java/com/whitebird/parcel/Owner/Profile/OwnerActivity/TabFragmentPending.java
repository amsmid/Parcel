package com.whitebird.parcel.Owner.Profile.OwnerActivity;

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
public class TabFragmentPending extends Fragment implements AbsListView.OnScrollListener,ResultInString{

    ListView listViewPending;
    SharedPreferenceUserData sharedPreferenceUserData;
    GetPendingListOwner getPendingListOwner;
    private int visibleThreshold = 2;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private int count =0;
    Activity activity;
    String uid,onlineKey;
    ListAdapter adapter;
    HashMap<String,String> hashMapData;
    //ArrayList<PendingListItem> arrayList;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentPendingList= inflater.inflate(R.layout.fragment_pending_main_list_owner,container,false);

        if (!isAdded()){
            return fragmentPendingList;
        }
        /*arrayList = new ArrayList<>();
        arrayList.clear();
        arrayList = GetPendingListOwner.getInstance().pendingListItems;*/
        onlineKey = getResources().getString(R.string.mainListofOwner);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
        hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
        //listViewPending = new ListView(getActivity());
        listViewPending = (ListView)fragmentPendingList.findViewById(R.id.list_view_pending_owner_list);
        listViewPending.setOnScrollListener(this);
        adapter = new CustomPendingListOfOwnerAdapter(getActivity());


        return fragmentPendingList;
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
            hashMapData = new HashMap<>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new BackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
            loading = true;
        }
    }

    @Override
    public void Result(String result, String keyOnline) {
        hashMapData.clear();

        if (!isAdded()){
            return;
        }

        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArrayListPending = jsonObject.getJSONArray(getString(R.string.server_key_pending));

                int len1 = jsonArrayListPending.length();
                for (int i=0;i<len1;i++){
                    PendingListItem pendingListItem = new PendingListItem();
                    JSONObject object = jsonArrayListPending.getJSONObject(i);
                    pendingListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                    pendingListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                    pendingListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                    pendingListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                    pendingListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                    pendingListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                    pendingListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                    pendingListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                    pendingListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                    pendingListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                    pendingListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                    pendingListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                    pendingListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                    pendingListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                    pendingListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                    pendingListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                    pendingListItem.setCityName(object.getString(getResources().getString(R.string.server_key_cityName)));
                    pendingListItem.setStateName(object.getString(getResources().getString(R.string.server_key_stateName)));
                    GetPendingListOwner.getInstance().pendingListItems.add(pendingListItem);
                }
                listViewPending.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else {

        }
        result = "";
    }
}
