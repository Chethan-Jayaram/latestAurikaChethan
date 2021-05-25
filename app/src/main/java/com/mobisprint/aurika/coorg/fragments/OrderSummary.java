package com.mobisprint.aurika.coorg.fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.ReviewOrderAdapter;
import com.mobisprint.aurika.coorg.adapter.ReviewOrderExpandableListAdapter;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;


public class OrderSummary extends Fragment {

    private RecyclerView order_summary_recyclerview;
    private TextView tv_item_total_price, tv_total_price, tv_service_tax, toolbar_title;
    private EditText order_summary_note;
    private Button btn_place_your_order;
    private List<Data> amenitiesList,houskeepingList;
    private List<K9Data> k9Amenities,k9Menu;
    private List<Data> laundryList;
    private Context mContext;
    private ImageView img_back;
    private String order_category;
    private double total_price = 0,service_tax=0;
    private ExpandableListView expandableListView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);


        tv_item_total_price = view.findViewById(R.id.tv_item_total_price);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        tv_service_tax = view.findViewById(R.id.tv_service_tax);
        order_summary_note = view.findViewById(R.id.order_summary_note);
        btn_place_your_order = view.findViewById(R.id.btn_place_your_order);
        order_summary_recyclerview = view.findViewById(R.id.order_summary_recyclerview);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Review Your Order");
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        expandableListView = view.findViewById(R.id.order_summary_expandable_listview);

        service_tax = 320.04;
        tv_service_tax.setText("₹" +" "+ service_tax);
/*
        Gson gson = new Gson();
        String json = GlobalClass.sharedPreferences.getString("selected_list", "");*/
      /*  Log.d("ReceviedJson", json);
        selectedList = (List<Data>) gson.fromJson(json, Data.class);
        Log.d("selList", selectedList.size() + "This is my size");
*/

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }

        };

            Bundle bundle = getArguments();
            order_category = bundle.getString("category");

            total_price = 0;

            switch (order_category){
                case "amenities":
                    amenitiesList = bundle.getParcelableArrayList("list"); ;
                    for (int i = 0; i<amenitiesList.size(); i++){
                        if (amenitiesList.get(i).getCount() >= 0 ){
                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice()) ;
                            tv_item_total_price.setText("₹ "+ " "+total_price);
                            tv_total_price.setText("₹"+" "+(double) (total_price + service_tax));

                        }
                    }
                    ReviewOrderAdapter adapter = new ReviewOrderAdapter(amenitiesList,"amenities");
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter);
                    break;
                case "k9amenities":
                  k9Amenities =  bundle.getParcelableArrayList("list");


                    for (int i = 0; i<k9Amenities.size() ; i++){
                        if (k9Amenities.get(i).getCount() >= 0 ){
                            total_price += k9Amenities.get(i).getCount() * Double.parseDouble(k9Amenities.get(i).getPrice()) ;
                            tv_item_total_price.setText("₹ "+ " "+total_price);
                            tv_total_price.setText("₹"+" "+(double) (total_price + service_tax));

                        }
                    }
                    ReviewOrderAdapter adapter1 = new ReviewOrderAdapter(k9Amenities,1);
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter1);
                    break;


                case "house_keeping":
                    houskeepingList =  bundle.getParcelableArrayList("list");


                    for (int i = 0; i<houskeepingList.size() ; i++){
                        if (houskeepingList.get(i).getCount() >= 0 ){
                            total_price += houskeepingList.get(i).getCount() * Double.parseDouble(houskeepingList.get(i).getPrice()) ;
                            tv_item_total_price.setText("₹ "+ " "+total_price);
                            tv_total_price.setText("₹"+" "+(double) (total_price + service_tax));

                        }
                    }
                    ReviewOrderAdapter adapter2 = new ReviewOrderAdapter(houskeepingList);
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter2);
                    break;



                case "k9menu":
                    k9Menu =  bundle.getParcelableArrayList("list");


                    for (int i = 0; i<k9Menu.size() ; i++){
                        if (k9Menu.get(i).getCount() >= 0 ){
                            total_price += k9Menu.get(i).getCount() * Double.parseDouble(k9Menu.get(i).getPrice()) ;
                            tv_item_total_price.setText("₹ "+ " "+total_price);
                            tv_total_price.setText("₹"+" "+(double) (total_price + service_tax));

                        }
                    }
                    ReviewOrderAdapter adapter3 = new ReviewOrderAdapter(k9Menu,"k9Menu",1);
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter3);
                    break;


                case "laundry":
                    expandableListView.setVisibility(View.VISIBLE);
                    order_summary_recyclerview.setVisibility(View.GONE);
                    laundryList =  bundle.getParcelableArrayList("list");
                    Log.d("received list", String.valueOf(laundryList.size()));
                    for(int i=0;i<laundryList.get(1).getCategory_item().size();i++) {
                        Log.d("categoiry list "+ i, String.valueOf(laundryList.get(1).getCategory_item().get(i).getCount()));
                    }

                    /*for (int i = 0; i<laundryList.size() ; i++){
                        if (laundryList.get(i).getCount() >= 0 ){
                            total_price += laundryList.get(i).getCount() * Double.parseDouble(laundryList.get(i).getPrice()) ;
                            tv_item_total_price.setText("₹ "+ " "+total_price);
                            tv_total_price.setText("₹"+" "+(double) (total_price + service_tax));

                        }
                    }*/
                    ReviewOrderExpandableListAdapter adapter4 = new ReviewOrderExpandableListAdapter(laundryList,mContext);
                    expandableListView.setAdapter(adapter4);
                    break;

            }

        return view;
    }
}