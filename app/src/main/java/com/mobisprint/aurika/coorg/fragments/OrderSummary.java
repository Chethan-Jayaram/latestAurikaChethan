package com.mobisprint.aurika.coorg.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.ReviewOrderAdapter;
import com.mobisprint.aurika.coorg.adapter.ReviewOrderExpandableListAdapter;
import com.mobisprint.aurika.coorg.adapter.ReviewOrderSleeWellAdapter;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


public class OrderSummary extends Fragment {

    private RecyclerView order_summary_recyclerview;
    private TextView tv_item_total_price, tv_total_price, tv_service_tax, toolbar_title;
    private EditText order_summary_note;
    private Button btn_place_your_order;
    private List<Data> amenitiesList, houskeepingList;
    private List<K9Data> k9Amenities, k9Menu;
    private List<Data> laundryList,sleepWellList;
    private List<com.mobisprint.aurika.coorg.pojo.dining.Data> diningList;
    private Context mContext;
    private ImageView img_back;
    private String order_category;
    private double total_price = 0, service_tax = 0;
    private Integer items_count = 0;
    private ExpandableListView expandableListView;
    private String title;

    private List<com.mobisprint.aurika.coorg.pojo.dining.Data> diningArrPackageData;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_order_summary, container, false);

        try {


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
            tv_service_tax.setText("₹" + " " + service_tax);
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
            title = bundle.getString("title");

            total_price = 0;

            switch (order_category) {
                case "amenities":
                    order_summary_recyclerview.setVisibility(View.VISIBLE);

                    Gson amenitiesGson = new Gson();
                    String amenitiesJson = GlobalClass.sharedPreferences.getString("Amenities", "");
                    if (amenitiesJson.isEmpty()) {
                        // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<Data>>() {
                        }.getType();
                        amenitiesList = new ArrayList(amenitiesGson.fromJson(amenitiesJson,type));
                    }

                    for (int i = 0; i < amenitiesList.size(); i++) {
                        if (amenitiesList.get(i).getCount() >= 0) {
                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                            tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                            tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                        }
                    }

                    items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.Amenities_count,0);
                    ReviewOrderAdapter adapter = new ReviewOrderAdapter(amenitiesList, "amenities",data ->{

                        total_price =0;
                        items_count =0;

                        for (int i = 0; i < amenitiesList.size(); i++) {
                            items_count += amenitiesList.get(i).getCount();

                            if (amenitiesList.get(i).getCount() >= 0) {
                                total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                            }
                        }

                        GlobalClass.editor.putInt(GlobalClass.Amenities_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.Amenities_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                    });
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter);
                    break;


                case "k9amenities":
                    order_summary_recyclerview.setVisibility(View.VISIBLE);
                    Gson k9AmenitiesGson = new Gson();
                    String k9AmenitiesJson = GlobalClass.sharedPreferences.getString("K9Amenities", "");
                    if (k9AmenitiesJson.isEmpty()) {
                        // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<K9Data>>() {
                        }.getType();
                        k9Amenities = new ArrayList(k9AmenitiesGson.fromJson(k9AmenitiesJson,type));
                    }


                    for (int i = 0; i < k9Amenities.size(); i++) {
                        if (k9Amenities.get(i).getCount() >= 0) {
                            total_price += k9Amenities.get(i).getCount() * Double.parseDouble(k9Amenities.get(i).getPrice());
                            tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                            tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                        }
                    }

                    items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.K9Amenities_count,0);
                    ReviewOrderAdapter adapter1 = new ReviewOrderAdapter(k9Amenities, 1,data -> {
                        total_price =0;
                        items_count =0;
                        for (int i = 0; i < k9Amenities.size(); i++) {
                            items_count += k9Amenities.get(i).getCount();
                            if (k9Amenities.get(i).getCount() >= 0) {
                                total_price += k9Amenities.get(i).getCount() * Double.parseDouble(k9Amenities.get(i).getPrice());
                                tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                            }
                        }

                        GlobalClass.editor.putInt(GlobalClass.K9Amenities_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.K9Amenities_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                    });
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter1);
                    break;


                case "house_keeping":
                    order_summary_recyclerview.setVisibility(View.VISIBLE);

                    Gson houseKeepingGson = new Gson();
                    String houseKeepingJson = GlobalClass.sharedPreferences.getString("HouseKeeping", "");
                    if (houseKeepingJson.isEmpty()) {
                        // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<Data>>() {
                        }.getType();
                        houskeepingList = new ArrayList(houseKeepingGson.fromJson(houseKeepingJson,type));
                    }

                    for (int i = 0; i < houskeepingList.size(); i++) {
                        if (houskeepingList.get(i).getCount() >= 0) {
                            total_price += houskeepingList.get(i).getCount() * Double.parseDouble(houskeepingList.get(i).getPrice());
                            tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                            tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                        }
                    }

                    ReviewOrderAdapter adapter2 = new ReviewOrderAdapter(houskeepingList,data -> {
                        total_price =0;
                        items_count =0;
                        for (int i = 0; i < houskeepingList.size(); i++) {
                            items_count += houskeepingList.get(i).getCount();
                            if (houskeepingList.get(i).getCount() >= 0) {
                                total_price += houskeepingList.get(i).getCount() * Double.parseDouble(houskeepingList.get(i).getPrice());
                                tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                            }
                        }

                        GlobalClass.editor.putInt(GlobalClass.HouseKeeping_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.HouseKeeping_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter2);
                    break;


                case "k9menu":
                    order_summary_recyclerview.setVisibility(View.VISIBLE);
                    Gson k9MenuGson = new Gson();
                    String k9MenuJson = GlobalClass.sharedPreferences.getString("K9Menu", "");
                    if (k9MenuJson.isEmpty()) {
                        // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<K9Data>>() {
                        }.getType();
                        k9Menu = new ArrayList(k9MenuGson.fromJson(k9MenuJson,type));
                    }


                    for (int i = 0; i < k9Menu.size(); i++) {
                        if (k9Menu.get(i).getCount() >= 0) {
                            total_price += k9Menu.get(i).getCount() * Double.parseDouble(k9Menu.get(i).getPrice());
                            tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                            tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                        }
                    }
                    items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.K9Menu_count,0);
                    ReviewOrderAdapter adapter3 = new ReviewOrderAdapter(k9Menu, "k9Menu", 1, data -> {
                        total_price =0;
                        items_count =0;
                        for (int i = 0; i < k9Menu.size(); i++) {
                            items_count += k9Menu.get(i).getCount();
                            if (k9Menu.get(i).getCount() >= 0) {
                                total_price += k9Menu.get(i).getCount() * Double.parseDouble(k9Menu.get(i).getPrice());
                                tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                            }
                        }

                        GlobalClass.editor.putInt(GlobalClass.K9Menu_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.K9Menu_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }
                    });
                    order_summary_recyclerview.setLayoutManager(layoutManager);
                    order_summary_recyclerview.setAdapter(adapter3);
                    break;


                case "laundry":
                    expandableListView.setVisibility(View.VISIBLE);
                    order_summary_recyclerview.setVisibility(View.GONE);

                    Gson gson = new Gson();
                    String json = GlobalClass.sharedPreferences.getString("Laundry", "");
                    if (json.isEmpty()) {
                       // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<Data>>() {
                        }.getType();
                         laundryList = new ArrayList(gson.fromJson(json,type));
                    }


                    items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.Laundry_count,0);
                    total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.Laundry_price,0);
                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));



                    ReviewOrderExpandableListAdapter adapter4 = new ReviewOrderExpandableListAdapter(laundryList, mContext, data -> {

                        items_count = 0;
                        total_price = 0;

                        for (int i = 0; i < laundryList.size(); i++) {


                            for (int j = 0; j < laundryList.get(i).getCategory_item().size(); j++) {

                                items_count += laundryList.get(i).getCategory_item().get(j).getCount();

                                if (laundryList.get(i).getCategory_item().get(j).getCount() >= 0) {
                                    total_price += laundryList.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundryList.get(i).getCategory_item().get(j).getPrice());
                                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));
                                }
                            }

                        }
                        GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }


                    });
                    expandableListView.setAdapter(adapter4);
                    break;


                case "dining":
                    expandableListView.setVisibility(View.VISIBLE);
                    order_summary_recyclerview.setVisibility(View.GONE);

                    Gson gson1 = new Gson();
                    String json1 = GlobalClass.sharedPreferences.getString(title, "");
                    if (json1.isEmpty()) {
                        Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<com.mobisprint.aurika.coorg.pojo.dining.Data>>() {
                        }.getType();
                        diningList = new ArrayList(gson1.fromJson(json1,type));
                    }


                    items_count=GlobalClass.sharedPreferences.getInt(title+"Count",0);
                    total_price = GlobalClass.sharedPreferences.getFloat(title + "Price",0);
                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                    Log.d("dining_size", String.valueOf(diningList.size()));


                    ReviewOrderExpandableListAdapter adapter5 = new ReviewOrderExpandableListAdapter(diningList, title, mContext, (data) -> {

                        items_count = 0;
                        total_price = 0;

                        for(int i=0;i<diningList.size() ;i++){
                            com.mobisprint.aurika.coorg.pojo.dining.Data data1=diningList.get(i);
                            List<Dining__1> category_items=new ArrayList<>();
                            for (int j=0; j<diningList.get(i).getDiningList().size();j++){
                                items_count += diningList.get(i).getDiningList().get(j).getCount();

                                if (diningList.get(i).getDiningList().get(j).getCount() >= 0 ){
                                    category_items.add(diningList.get(i).getDiningList().get(j));

                                    total_price +=diningList.get(i).getDiningList().get(j).getCount() * Double.parseDouble(diningList.get(i).getDiningList().get(j).getPrice()) ;
                                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));
                                }
                            }

                        }


                        GlobalClass.editor.putInt(title + "Count", items_count);
                        GlobalClass.editor.putFloat(title +"Price", (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }


                    });
                    expandableListView.setAdapter(adapter5);
                    break;

                case "sleepwell":
                    expandableListView.setVisibility(View.VISIBLE);
                    order_summary_recyclerview.setVisibility(View.GONE);

                    Gson sleepWellGson = new Gson();
                    String sleepWellJson = GlobalClass.sharedPreferences.getString("SleepWell", "");
                    if (sleepWellJson.isEmpty()) {
                        // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                    } else {
                        Type type = new TypeToken<List<Data>>() {
                        }.getType();
                        sleepWellList = new ArrayList(sleepWellGson.fromJson(sleepWellJson,type));
                    }

                    items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.SleepWell_count,0);
                    total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.SleepWell_price,0);
                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));

                    ReviewOrderSleeWellAdapter adapter6 = new ReviewOrderSleeWellAdapter(sleepWellList,mContext,data -> {

                        items_count= 0;
                        total_price = 0;


                        for(int i=0;i<sleepWellList.size() ;i++){

                            for (int j=0; j<sleepWellList.get(i).getSleepwellList().size();j++){

                                items_count += sleepWellList.get(i).getSleepwellList().get(j).getCount();
                                if (sleepWellList.get(i).getSleepwellList().get(j).getCount() >= 0 ){
                                    total_price +=sleepWellList.get(i).getSleepwellList().get(j).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(j).getPrice()) ;
                                    tv_item_total_price.setText("₹" + " " + GlobalClass.round(total_price,2));
                                    tv_total_price.setText("₹" + " " + GlobalClass.round((total_price + service_tax),2));
                                }
                            }
                        }


                        GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
                        GlobalClass.editor.commit();
                        if (items_count == 0){
                            getActivity().getSupportFragmentManager().popBackStack();
                        }

                    });

                    expandableListView.setAdapter(adapter6);
                    break;
            }


            btn_place_your_order.setOnClickListener(v -> {

                if (items_count >0){
                showBottomSheetDialog();
                }else
                    GlobalClass.ShowAlert(mContext,"Alert","Select atleast one item to place order");
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottom_dailog_box);


        Button bt_today = bottomSheetDialog.findViewById(R.id.bt_today);
        Button bt_tomorrow = bottomSheetDialog.findViewById(R.id.bt_tomorrow);
        CardView select_date = bottomSheetDialog.findViewById(R.id.select_date);
        ImageView img_up_hr = bottomSheetDialog.findViewById(R.id.img_up_hr);
        ImageView img_down_hr = bottomSheetDialog.findViewById(R.id.img_down_hr);

        LinearLayout lyt_calendar = bottomSheetDialog.findViewById(R.id.lyt_calendar);
        LinearLayout lyt_select_date = bottomSheetDialog.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        ImageView img_up_min = bottomSheetDialog.findViewById(R.id.img_up_min);
        ImageView img_down_min = bottomSheetDialog.findViewById(R.id.img_down_min);

        TextView tv_hr = bottomSheetDialog.findViewById(R.id.tv_hr);
        TextView tv_min = bottomSheetDialog.findViewById(R.id.tv_min);


        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);

        CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);



        bt_back.setOnClickListener(v -> {
            lyt_calendar.setVisibility(View.GONE);
            lyt_select_date.setVisibility(View.VISIBLE);
        });

        bt_save.setOnClickListener(v -> {
            lyt_calendar.setVisibility(View.GONE);
            lyt_select_date.setVisibility(View.VISIBLE);
        });

        bt_today.setOnClickListener(v -> {

            bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_today.setTextColor(Color.WHITE);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.BLACK);


        });

        bt_tomorrow.setOnClickListener(v -> {

            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_tomorrow.setTextColor(Color.WHITE);
            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);

        });


        select_date.setOnClickListener(v -> {


            lyt_calendar.setVisibility(View.VISIBLE);
            lyt_select_date.setVisibility(View.GONE);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

            long date = calendar.getTime().getTime();
            calendar_view.setMinDate(date);

            /*DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();*/


            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.BLACK);

        });


        bottomSheetDialog.show();

    }

}