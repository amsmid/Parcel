package com.whitebird.parcel.AddParcelActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.whitebird.parcel.BackgroundTaskForResult;
import com.whitebird.parcel.GetHubListData;
import com.whitebird.parcel.R;
import com.whitebird.parcel.ResultInString;
import com.whitebird.parcel.SharedPreferenceUserData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivityCreateNewParcel extends AppCompatActivity implements ResultInString{

    ListView listHub;
    SearchView searchView;
    SharedPreferenceUserData sharedPreferenceUserData;
    BaseAdapter adapter;
    CustomListViewOfHubAdapter customListViewOfHubAdapter;
    ArrayList<GetHubListData> arrayListHubData;
    ArrayList<String> getName;
    ClsStoreListofHub clsStoreListOfHub;
    String receiverUid,receiverPincode,receiverLandmark,receiverAddress,receiverName;
    ImageButton ibSubmitHubList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_create_new_parcel);
        sharedPreferenceUserData = new SharedPreferenceUserData(this);
        String data = sharedPreferenceUserData.getMyLoginUserData(getResources().getString(R.string.server_key_uid));
        String onlineKey = getResources().getString(R.string.fetchHubListKey);
        HashMap<String,String> hashMapData = new HashMap<>();
        hashMapData.put(getResources().getString(R.string.server_key_uid),data);
        hashMapData.put(getResources().getString(R.string.server_key_flag),"1");
        new BackgroundTaskForResult(hashMapData, onlineKey, MainActivityCreateNewParcel.this).execute();
        receiverAddress="";
        listHub = (ListView) findViewById(R.id.list_of_hub_item_in_popup);

        searchView = (SearchView) findViewById(R.id.search_view_for_hub_list);
        searchView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchView.onActionViewExpanded();
            }
        });

        ibSubmitHubList = (ImageButton)findViewById(R.id.submit_hub_list_button);
        ibSubmitHubList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitDataToNextAct();
            }
        });
        arrayListHubData = new ArrayList<>();
        customListViewOfHubAdapter = new CustomListViewOfHubAdapter(MainActivityCreateNewParcel.this, arrayListHubData,getName);
        adapter = customListViewOfHubAdapter;
        listHub.setEmptyView(findViewById(R.id.tv_empty_list));
        /*listHub.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (int j = 0; j < parent.getChildCount(); j++)
                    parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                view.setSelected(true);
                view.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
            }
        });*/
        listHub.setAdapter(adapter);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if (TextUtils.isEmpty(newText)) {
                    customListViewOfHubAdapter = new CustomListViewOfHubAdapter(MainActivityCreateNewParcel.this, arrayListHubData, getName);
                    adapter = customListViewOfHubAdapter;
                    listHub.setEmptyView(findViewById(R.id.tv_empty_list));
                    listHub.setAdapter(adapter);
                } else {
                    //  listView.setTextFilterEnabled(false);
                    //  listView.setAdapter(new baseCombinationAdepter(MainPlaceOrderActivity.this, listCombinations));
                    customListViewOfHubAdapter.getFilter().filter(newText);
                }
                return true;
            }
        });

    }

    private void submitDataToNextAct() {
        if (receiverAddress.equals("")){
            Toast.makeText(this,"Select Hub!",Toast.LENGTH_SHORT).show();
            return;
        }
        Bundle bundle = new Bundle();
        Intent intent = new Intent(this, CreateParcelTakeImageAndData.class);
        bundle.putString("rAddress",receiverAddress);
        bundle.putString("rName",receiverName);
        bundle.putString("rUid",receiverUid);
        bundle.putString("rPincode",receiverPincode);
        bundle.putString("rLandmark",receiverLandmark);
        intent.putExtra("firstBundle",bundle);
        Bundle bndlanimation;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
            bndlanimation = ActivityOptions.makeCustomAnimation(getApplicationContext(), R.anim.first_animation, R.anim.second_animation).toBundle();
            startActivity(intent, bndlanimation);
        }else
            startActivity(intent);
    }

    private class CustomListViewOfHubAdapter extends BaseAdapter implements Filterable {
        ArrayList<GetHubListData> arrayList;
        Activity activity;
        ArrayList<GetHubListData> getFilterlist;
        ArrayList<String> getNameAdapter;
        CustomListViewOfHubAdapter(Activity activity,ArrayList<GetHubListData> arrayList,ArrayList<String> name){
            this.arrayList = new ArrayList<>();
            this.arrayList = arrayList;
            this.activity = activity;
            getFilterlist = new ArrayList<>();
            getNameAdapter = new ArrayList<>();
            this.getNameAdapter=name;
        }

        @Override
        public int getCount() {
            // To Check Empty List
            return arrayList.size();
        }

        @Override
        public Object getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return arrayList.indexOf(position);
        }

        @SuppressLint("InflateParams")
        @Override
        public View getView(final int position, View convertView, final ViewGroup parent) {
            LayoutInflater layoutInflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            if (convertView==null){
                convertView = layoutInflater.inflate(R.layout.list_view_of_hub_model,null);
            }

            final TextView textViewName = (TextView)convertView.findViewById(R.id.tv_history_list_send_name_hub);

            textViewName.setText(arrayList.get(position).getName());

            final View finalConvertView = convertView;
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j = 0; j < parent.getChildCount(); j++)
                        parent.getChildAt(j).setBackgroundColor(Color.TRANSPARENT);

                    finalConvertView.setSelected(true);
                    finalConvertView.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                    receiverName = arrayList.get(position).getName();
                    receiverAddress = arrayList.get(position).getAddress();
                    receiverUid = arrayList.get(position).getUid();
                    receiverPincode = arrayList.get(position).getPincode();
                    receiverLandmark = arrayList.get(position).getLandmark();
                }
            });

            return convertView;
        }

        @Override
        public Filter getFilter() {
            return new Filter() {

                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    final FilterResults oReturn = new FilterResults();
                    final ArrayList<GetHubListData> results = new ArrayList<>();
                    if (getFilterlist == null)
                        getFilterlist = arrayList;

                    if (constraint != null) {
                        //if (list != null && list.size() > 0) {
                        for (final GetHubListData g : arrayListHubData) {
                            if (g.getName().toUpperCase()
                                    .contains(constraint.toString()) || g.getName().toLowerCase()
                                    .contains(constraint.toString()))
                                results.add(g);
                        }
                        // }
                        oReturn.values = results;
                    } else {
                        oReturn.values = arrayListHubData;
                    }
                    return oReturn;
                }

                @SuppressWarnings("unchecked")
                @Override
                protected void publishResults(CharSequence constraint,
                                              FilterResults results) {
                    arrayList = new ArrayList<>();
                    arrayList = (ArrayList<GetHubListData>) results.values;
                    CustomListViewOfHubAdapter.this.notifyDataSetChanged();
                }
            };
        }

    }

    @Override
    public void Result(String result,String keyOnline) {
        Log.d("resultOfHubList",result);

        getName = new ArrayList<>();
        clsStoreListOfHub = new ClsStoreListofHub(this);
        arrayListHubData = new ArrayList<>();

        String success;
        try {
            JSONObject jsonObjectSuccess = new JSONObject(result);
            success = jsonObjectSuccess.getString(getResources().getString(R.string.server_key_success));
        } catch (JSONException e) {
            success ="0";
            e.printStackTrace();
        }

        if (success.equals("1")){
            if (keyOnline.equals(getResources().getString(R.string.fetchHubListKey))) {
                clsStoreListOfHub.SaveHubList(result);
                arrayListHubData = clsStoreListOfHub.getGetHubListDatas();
                for (int i = 0; i < arrayListHubData.size(); i++) {
                    getName.add(arrayListHubData.get(i).getName());
                }
                if (getName.isEmpty()) {
                    getName = new ArrayList<>();
                }
                customListViewOfHubAdapter = new CustomListViewOfHubAdapter(MainActivityCreateNewParcel.this, arrayListHubData, getName);
                adapter = customListViewOfHubAdapter;
                listHub.setEmptyView(findViewById(R.id.tv_empty_list));
                listHub.setAdapter(adapter);
            }

        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Try Again");
            dialog.show();
        }
    }
}
