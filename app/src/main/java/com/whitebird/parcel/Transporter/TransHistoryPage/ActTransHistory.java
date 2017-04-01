package com.whitebird.parcel.Transporter.TransHistoryPage;

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

public class ActTransHistory extends AppCompatActivity implements AbsListView.OnScrollListener,ResultInString {

    ListView lvTransListory;
    SharedPreferenceUserData sharedPreferenceUserData;
    private int previousTotal = 0;
    private int visibleThreshold = 5;
    private boolean loading = true;
    private int count =0;
    String uid,onlineKey;
    ListAdapter adapter;
    ArrayList<TransHistoryListItem> arrayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act_trans_history);
        GetTransHistoryList.getInstance().transHistoryListItems.clear();
        lvTransListory = (ListView)findViewById(R.id.lv_trans_history);
        arrayList = new ArrayList<>();
        onlineKey = getResources().getString(R.string.historyListOwnerKey);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        uid = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.key_uid));
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
        hashMapData.put(getResources().getString(R.string.server_key_user),"2");
        hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
        new BackgroundTaskForResult(hashMapData, onlineKey,this).execute();
        lvTransListory.setOnScrollListener(this);
        adapter = new CustomTransHistoryListAdapter(this,arrayList);

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
            hashMapData.put(getResources().getString(R.string.server_key_user),"2");
            hashMapData.put(getResources().getString(R.string.server_key_count), String.valueOf(count));
            new BackgroundTaskForResult(hashMapData, onlineKey,this).execute();
            loading = true;
        }
    }

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("ResultInTransHistory",result);
        try {
            JSONObject jsonObject = new JSONObject(result);
            JSONArray jsonArrayListHistory = jsonObject.getJSONArray(getResources().getString(R.string.server_key_history));

            int len1 = jsonArrayListHistory.length();
            for (int i=0;i<len1;i++){
                TransHistoryListItem transHistoryListItem = new TransHistoryListItem();
                JSONObject object = jsonArrayListHistory.getJSONObject(i);
                transHistoryListItem.setSenderId(object.getString(getResources().getString(R.string.server_key_senderId)));
                transHistoryListItem.setReceiverId(object.getString(getResources().getString(R.string.server_key_receiverId)));
                transHistoryListItem.setOrderNumber(object.getString(getResources().getString(R.string.server_key_orderNumber)));
                transHistoryListItem.setAddress(object.getString(getResources().getString(R.string.server_key_address)));
                transHistoryListItem.setPincode(object.getString(getResources().getString(R.string.server_key_pincode)));
                transHistoryListItem.setTime(object.getString(getResources().getString(R.string.server_key_time)));
                transHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                transHistoryListItem.setSize(object.getString(getResources().getString(R.string.server_key_size)));
                transHistoryListItem.setWeight(object.getString(getResources().getString(R.string.server_key_weight)));
                transHistoryListItem.setImage(object.getString(getResources().getString(R.string.server_key_image)));
                transHistoryListItem.setSenderAd(object.getString(getResources().getString(R.string.server_key_senderAd)));
                transHistoryListItem.setSender(object.getString(getResources().getString(R.string.server_key_sender)));
                /*transHistoryListItem.setSenderPin(object.getString(getResources().getString(R.string.server_key_senderPin)));*/
                transHistoryListItem.setReceiver(object.getString(getResources().getString(R.string.server_key_receiver)));
                transHistoryListItem.setType(object.getString(getResources().getString(R.string.server_key_type)));
                transHistoryListItem.setTimeline(object.getString(getResources().getString(R.string.server_key_timeline)));
                transHistoryListItem.setLandmark(object.getString(getResources().getString(R.string.server_key_landmark)));
                transHistoryListItem.setDispatchTime(object.getString(getResources().getString(R.string.server_key_dispatchTime)));
                transHistoryListItem.setSenderCity(object.getString(getResources().getString(R.string.server_key_senderCity)));
                transHistoryListItem.setSenderState(object.getString(getResources().getString(R.string.server_key_senderState)));
                transHistoryListItem.setSenderMo(object.getString(getResources().getString(R.string.server_key_senderMo)));
                transHistoryListItem.setSenderLand(object.getString(getResources().getString(R.string.server_key_senderLand)));
                transHistoryListItem.setReceiverCity(object.getString(getResources().getString(R.string.server_key_receiverCity)));
                transHistoryListItem.setReceiverState(object.getString(getResources().getString(R.string.server_key_receiverState)));
                transHistoryListItem.setReceiverMo(object.getString(getResources().getString(R.string.server_key_receiverMo)));
                transHistoryListItem.setReceiverLand(object.getString(getResources().getString(R.string.server_key_receiverLand)));
                GetTransHistoryList.getInstance().transHistoryListItems.add(transHistoryListItem);
            }
            arrayList = new ArrayList<>();
            arrayList = GetTransHistoryList.getInstance().transHistoryListItems;
            lvTransListory.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }
}
