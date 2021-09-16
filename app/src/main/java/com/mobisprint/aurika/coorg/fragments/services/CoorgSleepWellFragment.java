package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.adapter.CoorgSleepWellAdapter;
import com.mobisprint.aurika.coorg.controller.services.CoorgSleepWellFragmentController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.SharedPreferenceVariables;
import com.mobisprint.aurika.udaipur.adapter.SleepWellAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Response;


public class CoorgSleepWellFragment extends Fragment implements ApiListner {

    private TextView tv_sleepwell_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private CoorgSleepWellFragmentController coorgSleepWellFragmentController;
    private ExpandableListView sleepwell_list;
    private Context mContext;
    private ImageView img_back;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "sleepwell";
    private List<Data> arrPackageData;
    private int hr,min;
    private Calendar calendar;
    private List<Data> sleepWellList;
    private List<SleepwellList> selectedList = new ArrayList<>();
    private Boolean dining = false, housekeeping = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_sleep_well, container, false);

        tv_sleepwell_desc = view.findViewById(R.id.tv_coorg_sleep_well_desc);
        coorgSleepWellFragmentController = new CoorgSleepWellFragmentController(this);
        sleepwell_list = view.findViewById(R.id.sleepwell_list);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        lyt = view.findViewById(R.id.lyt);
        lyt.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        tv_sleepwell_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("title"));



        /*items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.SleepWell_count,0);
        tv_num_of_items.setText(items_count+" " +"items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.SleepWell_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));*/


        view_order.setOnClickListener(v -> {
            if (items_count>0)  {

                if (GlobalClass.user_token.isEmpty()){
                    alertBox();

                }else if (GlobalClass.user_active_booking){
                    /*showBottomSheetDialog();*/
                    selectedList.clear();
                    for (int i=0;i<sleepWellList.size();i++){

                        for (int j=0;j<sleepWellList.get(i).getSleepwellList().size();j++){
                            if (sleepWellList.get(i).getSleepwellList().get(j).getCount()>0){

                                if (sleepWellList.get(i).getTitle().equalsIgnoreCase("Deep Sleep Menu")){
                                    housekeeping = true;

                                } else if (sleepWellList.get(i).getTitle().equalsIgnoreCase("Pillow Menu")){
                                    housekeeping = true;

                                }else if(sleepWellList.get(i).getTitle().equalsIgnoreCase("Room Fragrance")){
                                    housekeeping = true;
                                }else if (sleepWellList.get(i).getTitle().equalsIgnoreCase("Nightcaps")){
                                    dining = true;
                                }
                                sleepWellList.get(i).getSleepwellList().get(j).setItem_id(sleepWellList.get(i).getSleepwellList().get(j).getId());
                                sleepWellList.get(i).getSleepwellList().get(j).setQuantity(sleepWellList.get(i).getSleepwellList().get(j).getCount());
                                selectedList.add(sleepWellList.get(i).getSleepwellList().get(j));
                            }
                        }
                    }


                    if (!(housekeeping && dining)){
                        BottomDailogFragment fragment = new BottomDailogFragment();
                        Bundle bundle1 = new Bundle();

                        if (housekeeping){
                            bundle1.putString("ticketType","housekeeping");
                        }else if (dining){
                            bundle1.putString("ticketType","in-room-dining");
                        }
                        bundle1.putString("Category","sleepwell");
                        bundle1.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) selectedList);
                        Gson gson = new Gson();
                        String json = gson.toJson(selectedList);
                        Log.d("sleepwell slect items",json);
                        fragment.setArguments(bundle1);
                        /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                        fragment.show(getActivity().getSupportFragmentManager(),
                                "fragment_bottom_sheet_dailog");
                    }else {
                        housekeeping = false;
                        dining = false;
                        GlobalClass.ShowAlert(mContext,"Alert","Nightcaps cannot be clubbed with other service");

                    }
                } else  {

                    GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place order");
                }

                /*Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();

                GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
                GlobalClass.editor.commit();

                bundle1.putString("category",order_category);
                fragment.setArguments(bundle1);
                getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
            }else
            {
                GlobalClass.ShowAlert(mContext,"Alert","Select atleast one item");
            }

        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        items_count = 0;
        tv_num_of_items.setText("0 items");
        tv_total_price.setText("₹ 0.00");
        coorgSleepWellFragmentController.getServices();
        housekeeping = false;
        dining = false;
    }

    private void alertBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Please login to place your request")
                .setCancelable(false)
                .setPositiveButton("Okay", (dialog, id) -> {
                    Intent intent = new Intent(mContext, UserAuthenticationActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                });

        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }



    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);
        if (response!= null){

            CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
            sleepWellList = servicesPojo.getData();

            if (arrPackageData != null) {
                arrPackageData.clear();
            }

            Gson gson = new Gson();
            String json = GlobalClass.sharedPreferences.getString("SleepWell", "");
            if (json.isEmpty()) {
                //Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                arrPackageData = new ArrayList(gson.fromJson(json,type));
            }

            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);
            GlobalClass.editor.commit();

            /*try {

                if (arrPackageData !=null){

                    items_count= 0;
                    total_price = 0;

                    for (int i =0; i<sleepWellList.size();i++){
                        for (int j = 0;j<arrPackageData.size();j++){
                            *//*if(sleepWellList.get(i).getId().equals(arrPackageData.get(j).getId()))
                                sleepWellList.remove(i);
                                sleepWellList.add(i,arrPackageData.get(j));
                            }*//*


                            for (int x=0; x<sleepWellList.get(i).getSleepwellList().size(); x++){
                                for (int y=0; y<arrPackageData.get(j).getSleepwellList().size(); y++){
                                    if (sleepWellList.get(i).getSleepwellList().get(x).getId().equals(arrPackageData.get(j).getSleepwellList().get(y).getId())){

                                        if (!(arrPackageData.get(j).getSleepwellList().get(y).getItemselectorType().equalsIgnoreCase(sleepWellList.get(i).getSleepwellList().get(x).getItemselectorType()))){
                                            arrPackageData.clear();
                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);
                                            GlobalClass.editor.commit();
                                        }else if (arrPackageData.get(j).getSleepwellList().get(y).getItemselectorType().equalsIgnoreCase(sleepWellList.get(i).getSleepwellList().get(x).getItemselectorType())
                                                && arrPackageData.get(j).getSleepwellList().get(y).getCount()>0){
                                            sleepWellList.get(i).getSleepwellList().get(x).setCount(arrPackageData.get(j).getSleepwellList().get(y).getCount());
                                            sleepWellList.get(i).getSleepwellList().get(x).setItemSelected(true);

                                            if (arrPackageData.get(j).getSleepwellList().get(y).getItemselectorType().equalsIgnoreCase("single")){

                                                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,true);
                                                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);
                                                GlobalClass.editor.commit();

                                            }else if (arrPackageData.get(j).getSleepwellList().get(y).getItemselectorType().equalsIgnoreCase("multi")){

                                                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                                                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,true);
                                                GlobalClass.editor.commit();

                                            }

                                            items_count += sleepWellList.get(i).getSleepwellList().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (sleepWellList.get(i).getSleepwellList().get(x).getCount() >= 0 ){
                                                total_price +=sleepWellList.get(i).getSleepwellList().get(x).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }

                                        }

                                        *//*GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                                        GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);*//*

                                        *//*if (arrPackageData.get(j).getSleepwellList().get(y).getCount() > 1 && sleepWellList.get(i).getSleepwellList().get(x).getItemselectorType().equals("single")){
                                        }else if (sleepWellList.get(i).getSleepwellList().get(x).getItemselectorType().equals("single") && arrPackageData.get(j).getSleepwellList().get(y).getCount() ==1){
                                            sleepWellList.get(i).getSleepwellList().get(x).setCount(arrPackageData.get(j).getSleepwellList().get(y).getCount());
                                            sleepWellList.get(i).getSleepwellList().get(x).setItemSelected(true);



                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,true);
                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);

                                            items_count += sleepWellList.get(i).getSleepwellList().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (sleepWellList.get(i).getSleepwellList().get(x).getCount() >= 0 ){
                                                total_price +=sleepWellList.get(i).getSleepwellList().get(x).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }

                                        }else if (arrPackageData.get(j).getSleepwellList().get(y).getCount() > 0){
                                            sleepWellList.get(i).getSleepwellList().get(x).setCount(arrPackageData.get(j).getSleepwellList().get(y).getCount());

                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,true);

                                            items_count += sleepWellList.get(i).getSleepwellList().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (sleepWellList.get(i).getSleepwellList().get(x).getCount() >= 0 ){
                                                total_price +=sleepWellList.get(i).getSleepwellList().get(x).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }
                                        }*//*
                                    }
                                }
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
                    Set<Data> set = new LinkedHashSet<>(sleepWellList);
                    Gson sleepWellGson = new Gson();
                    String sleepWellJson = sleepWellGson.toJson(set);
                    GlobalClass.editor.putString("SleepWell", sleepWellJson);
                    GlobalClass.editor.commit();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }*/



            CoorgSleepWellAdapter adapter = new CoorgSleepWellAdapter(getContext(),sleepWellList,data -> {

                try {


                    items_count= 0;
                    total_price = 0;


                    for(int i=0;i<sleepWellList.size() ;i++){

                        for (int j=0; j<sleepWellList.get(i).getSleepwellList().size();j++){

                            items_count += sleepWellList.get(i).getSleepwellList().get(j).getCount();
                            tv_num_of_items.setText(items_count+" " +"items");
                            if (sleepWellList.get(i).getSleepwellList().get(j).getCount() >= 0 ){
                                total_price +=sleepWellList.get(i).getSleepwellList().get(j).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
                    GlobalClass.editor.commit();

                }catch (Exception e){
                    e.printStackTrace();
                }

            });
            sleepwell_list.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}