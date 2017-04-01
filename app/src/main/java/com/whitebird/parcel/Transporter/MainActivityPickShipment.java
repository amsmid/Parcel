package com.whitebird.parcel.Transporter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.TextView;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.GetHubListData;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.TransPendingList;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivityPickShipment extends AppCompatActivity implements View.OnClickListener, SearchView.OnQueryTextListener,ResultInString {

    SearchView svTransHubList;
    ListView listViewCombine;
    TextView searchTextView;
    HubListAdapter hubListAdapter;
    ClsStoreHubListTrans clsStoreHubListTrans;
    BaseAdapter adapter;
    ArrayList<String> getHubNameListStored;
    ArrayList<GetHubListData> getHubFullListStored;
    ClsTransStorePendingListOfHub clsTransStorePendingListOfHub;
    ArrayList<TransPendingList> transPendingListArrayList;
    View view;
    int positionNew;
    String passingUid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String onlineKey = getResources().getString(R.string.fetchHubListKey);
        HashMap<String,String> hashMapData = new HashMap<String, String>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),"");
        hashMapData.put(getResources().getString(R.string.server_key_flag),"2");
        new BackgroundTaskForResult(hashMapData, onlineKey, MainActivityPickShipment.this).execute();
        setContentView(R.layout.activity_main_pick_shipment);
        getHubNameListStored = new ArrayList<>();
        clsStoreHubListTrans = new ClsStoreHubListTrans(this);
        getHubFullListStored = new ArrayList<>();
        listViewCombine = (ListView)findViewById(R.id.transport_pick_s_list_view_pending_parcel);
        searchTextView = (TextView)findViewById(R.id.search_text_notation);
        svTransHubList = (SearchView)findViewById(R.id.transport_pick_s_search_hub_list);
        svTransHubList.setOnClickListener(this);
        svTransHubList.setOnQueryTextListener(this);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==2){
            searchTextView.setText("Search Hub List Here!");
            svTransHubList.onActionViewCollapsed();
            String uid = passingUid;
            String onlineKeyPending = getResources().getString(R.string.pendingParcelListKey);
            HashMap<String,String> hashMapData = new HashMap<String, String>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
            new BackgroundTaskForResult(hashMapData, onlineKeyPending, MainActivityPickShipment.this).execute();
        }
    }


    @Override
    public void onClick(View v) {
        if (v==svTransHubList){
            searchTextView.setText("");
            svTransHubList.onActionViewExpanded();
            listViewCombine = new ListView(this);
            listViewCombine = (ListView)findViewById(R.id.transport_pick_s_list_view_pending_parcel);
            String onlineKey = getResources().getString(R.string.fetchHubListKey);
            HashMap<String,String> hashMapData = new HashMap<String, String>();
            hashMapData.put(getResources().getString(R.string.server_key_uid),"");
            hashMapData.put(getResources().getString(R.string.server_key_flag),"2");
            new BackgroundTaskForResult(hashMapData, onlineKey, MainActivityPickShipment.this).execute();
        }
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        if (TextUtils.isEmpty(newText)) {
            hubListAdapter = new HubListAdapter(MainActivityPickShipment.this, getHubNameListStored, getHubFullListStored);
            adapter = hubListAdapter;
            listViewCombine.setAdapter(adapter);
        } else {
            hubListAdapter.getFilter().filter(newText);
        }
        return true;
    }

    @Override
    public void Result(String result, String keyOnline) {
        Log.d("resultOfTPickHub",result);
        getHubNameListStored = new ArrayList<>();
        clsStoreHubListTrans = new ClsStoreHubListTrans(this);
        getHubFullListStored = new ArrayList<>();

        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            if (keyOnline.equals(getResources().getString(R.string.fetchHubListKey))){
                clsStoreHubListTrans.SaveHubList(result);
                getHubFullListStored = clsStoreHubListTrans.getGetHubListDatas();

                for (int i = 0; i < getHubFullListStored.size(); i++) {
                    getHubNameListStored.add(getHubFullListStored.get(i).getName());
                }
                if (getHubNameListStored.isEmpty()){
                    getHubNameListStored= new ArrayList<>();
                }
                if (getHubFullListStored.isEmpty()){
                    getHubFullListStored= new ArrayList<>();
                }

                hubListAdapter = new HubListAdapter(MainActivityPickShipment.this, getHubNameListStored,getHubFullListStored);
                adapter = hubListAdapter;
                listViewCombine.setAdapter(adapter);
            }else if (keyOnline.equals(getResources().getString(R.string.pendingParcelListKey))){
                Log.d("pendingKey",result);
                SlgnTransPendingParcelList.getInstance().transPendingLists.clear();
                clsTransStorePendingListOfHub = new ClsTransStorePendingListOfHub(this);
                clsTransStorePendingListOfHub.SavePendingList(result);
                transPendingListArrayList = new ArrayList<>();
                transPendingListArrayList = SlgnTransPendingParcelList.getInstance().transPendingLists;
                listViewCombine = new ListView(this);
                listViewCombine = (ListView)findViewById(R.id.transport_pick_s_list_view_pending_parcel);
                listViewCombine.setAdapter(new TransPendingListOfHubAdapter(this,transPendingListArrayList));
            }
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Empty Pending Data");
            dialog.show();
        }



    }

    private class HubListAdapter extends BaseAdapter implements Filterable {
        ArrayList<String> getHubListStoredAdapter;
        ArrayList<GetHubListData> getHubFullList;
        Activity activity;
        ArrayList<GetHubListData> getFilterlist;
        HubListAdapter(Activity activity, ArrayList<String> getHubListStored, ArrayList<GetHubListData> getHubFullListStored) {
            getHubListStoredAdapter=new ArrayList<>();
            this.getHubListStoredAdapter=getHubListStored;
            this.activity=activity;
            getFilterlist = new ArrayList<>();
            getHubFullList=new ArrayList<>();
            getHubFullList=getHubFullListStored;
        }

        @Override
        public int getCount() {
            return getHubFullList.size();
        }

        @Override
        public Object getItem(int position) {
            return getHubFullList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return getHubFullList.indexOf(position);
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.list_view_of_hub_model_trans,null);
            }

            view=convertView;

            final TextView textViewName = (TextView)view.findViewById(R.id.trans_tv_history_list_send_name_hub);
            textViewName.setText(getHubFullList.get(position).getName());

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    searchTextView.setText("Search Hub List Here!");
                    svTransHubList.onActionViewCollapsed();
                    String uid = getHubFullList.get(position).getUid();
                    passingUid = uid;
                    String onlineKeyPending = getResources().getString(R.string.pendingParcelListKey);
                    HashMap<String,String> hashMapData = new HashMap<String, String>();
                    hashMapData.put(getResources().getString(R.string.server_key_uid),uid);
                    new BackgroundTaskForResult(hashMapData, onlineKeyPending, MainActivityPickShipment.this).execute();
                }
            });
            return view;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<GetHubListData> results = new ArrayList<>();
                    if (getFilterlist == null)
                        getFilterlist = getHubFullList;

                    if (constraint != null) {
                        //if (list != null && list.size() > 0) {
                        for (final GetHubListData g : getHubFullListStored) {
                            if (g.getName().toUpperCase()
                                    .contains(constraint.toString()) || g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                        // }
                        oReturn.values = results;
                    } else {
                        oReturn.values = getHubFullListStored;
                    }
                    return oReturn;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    getHubFullList = new ArrayList<>();
                    getHubFullList = (ArrayList<GetHubListData>) results.values;
                    HubListAdapter.this.notifyDataSetChanged();
                }
            };
        }
    }
}
