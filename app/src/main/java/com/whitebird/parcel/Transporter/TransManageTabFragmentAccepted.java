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

public class TransManageTabFragmentAccepted extends Fragment implements AbsListView.OnScrollListener,ResultInString {

    ListView listViewAccepted;
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
    ArrayList<GtStTransManageAcceptedList> gtStTransManageAcceptedLists;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View fragmentTransAcceptedList= inflater.inflate(R.layout.trans_manage_fragment_accepted_list,container,false);

        gtStTransManageAcceptedLists = new ArrayList<>();
        gtStTransManageAcceptedLists = SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists;
        onlineKey = getResources().getString(R.string.transManageAcceptList);
        sharedPreferenceUserData = new SharedPreferenceUserData(getActivity());
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<String, String>();
        hashMapData.put(getResources().getString(R.string.key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new TransManageBackgroundTaskForFragmentResult(hashMapData, onlineKey,this).execute();
        listViewAccepted = new ListView(getActivity());
        listViewAccepted = (ListView)fragmentTransAcceptedList.findViewById(R.id.trans_manage_accepted_list);
        listViewAccepted.setOnScrollListener(this);
        adapter = new TransManageCustomAcceptListAdapter(getActivity(),gtStTransManageAcceptedLists);


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
        Log.d("resTransMngAcceptedLst",result);

        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListAccepted = jsonObject.getJSONArray(getResources().getString(R.string.server_key_pending));

            int len1 = jsonArrayListAccepted.length();
            for (int i=0;i<len1;i++){
                GtStTransManageAcceptedList gtStTransManageAcceptedList = new GtStTransManageAcceptedList();
                JSONObject object = jsonArrayListAccepted.getJSONObject(i);
                gtStTransManageAcceptedList.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                gtStTransManageAcceptedList.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                gtStTransManageAcceptedList.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                gtStTransManageAcceptedList.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                gtStTransManageAcceptedList.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                gtStTransManageAcceptedList.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                gtStTransManageAcceptedList.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                gtStTransManageAcceptedList.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                gtStTransManageAcceptedList.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                gtStTransManageAcceptedList.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                gtStTransManageAcceptedList.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                gtStTransManageAcceptedList.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                gtStTransManageAcceptedList.setSenderPin(object.getString(getResources().getString(R.string.server_key_senderPin)));
                gtStTransManageAcceptedList.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                gtStTransManageAcceptedList.setType(object.getString(getResources().getString(R.string.server_key_type)));
                gtStTransManageAcceptedList.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                gtStTransManageAcceptedList.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                gtStTransManageAcceptedList.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                gtStTransManageAcceptedList.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                gtStTransManageAcceptedList.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));
                gtStTransManageAcceptedList.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                gtStTransManageAcceptedList.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));
                gtStTransManageAcceptedList.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                gtStTransManageAcceptedList.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                gtStTransManageAcceptedList.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                gtStTransManageAcceptedList.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));
                SlgnTransManageGetAcceptedList.getInstance().gtStTransManageAcceptedLists.add(gtStTransManageAcceptedList);
            }
            listViewAccepted.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
