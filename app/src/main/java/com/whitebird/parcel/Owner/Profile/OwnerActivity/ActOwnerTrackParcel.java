package com.whitebird.parcel.Owner.Profile.OwnerActivity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.AbsListView;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class ActOwnerTrackParcel extends AppCompatActivity implements AbsListView.OnScrollListener,ResultInString {

    ListView listViewProcessing;
    SharedPreferenceUserData sharedPreferenceUserData;
    GetPendingListOwner getPendingListOwner;
    private int visibleThreshold = 5;
    private int currentPage = 0;
    private int previousTotal = 0;
    private boolean loading = true;
    private int count =0;
    Activity activity;
    String uid,onlineKey;
    ListAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_owner_track_parcel);
        GetProcessingListOwner.getInstance().processingListItems.clear();
            onlineKey = getResources().getString(R.string.mainListofOwner);
            sharedPreferenceUserData = new SharedPreferenceUserData(this);
            uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
            HashMap<String,String> hashMapData = new HashMap<String, String>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new BackgroundTaskForResult(hashMapData, onlineKey,this).execute();
            listViewProcessing = new ListView(this);
            listViewProcessing = (ListView)findViewById(R.id.list_view_processing_owner_list);
            listViewProcessing.setOnScrollListener(this);
            adapter = new CustomProcessingListOfOwnerAdapter(this);
        }

        @Override
        public void Result(String result, String keyOnline) {
            Log.d("ResultFragmePrc",result);

            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArrayListProcessing = jsonObject.getJSONArray(getResources().getString(R.string.server_key_outgoing));

                int len1 = jsonArrayListProcessing.length();
                for (int i=0;i<len1;i++){
                    ProcessingListItem processingListItem = new ProcessingListItem();
                    JSONObject object = jsonArrayListProcessing.getJSONObject(i);
                    processingListItem.setTransId(object.getString(getResources().getString(R.string.server_key_transId)));
                    processingListItem.setTransName(object.getString(getResources().getString(R.string.server_key_transName)));
                    processingListItem.setTransMobile(object.getString(getResources().getString(R.string.server_key_transMobile)));
                    processingListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                    processingListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                    processingListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                    processingListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                    processingListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                    processingListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                    processingListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                    processingListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                    processingListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                    processingListItem.setPickupTime(object.getString(getResources().getString(R.string.server_key_pickupTime)));
                    processingListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                    processingListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                    processingListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                    processingListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                    processingListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                    processingListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                    processingListItem.setCityName(object.getString(getResources().getString(R.string.server_key_cityName)));
                    processingListItem.setStateName(object.getString(getResources().getString(R.string.server_key_stateName)));
                    /*processingListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));*/
                    GetProcessingListOwner.getInstance().processingListItems.add(processingListItem);
                }
                listViewProcessing.setAdapter(adapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

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
                new BackgroundTaskForResult(hashMapData, onlineKey,this).execute();
                loading = true;
            }
        }
}
