package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.Image;
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
import android.widget.CheckBox;
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
import com.mobisprint.aurika.coorg.adapter.HouseKeepingAdapter;
import com.mobisprint.aurika.coorg.controller.services.HouseKeepingController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.lang.reflect.Type;
import java.net.ConnectException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Response;


public class    HouseKeeping extends Fragment implements ApiListner {

    private RecyclerView recyclerView;
    private TextView tv_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private HouseKeepingController houseKeepingController;
    private Context mContext;
    private ImageView img_back;
    private List<Data> k9ArrDataPackage;
    private String order_category = "house_keeping" ;
    private Integer items_count = 0;
    private double total_price = 0;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;
    private Boolean hours = false;
    private Integer count = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_keeping, container, false);

        try {

            tv_desc = view.findViewById(R.id.tv_houseKeeping_desc);
            mContext = getContext();
            tv_num_of_items = view.findViewById(R.id.tv_num_items);
            tv_total_price = view.findViewById(R.id.tv_total_price);
            houseKeepingController = new HouseKeepingController(this);
            recyclerView = view.findViewById(R.id.house_keeping_recyclerview);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            view_order = view.findViewById(R.id.view_order);
            img_back.setVisibility(View.VISIBLE);
            lyt = view.findViewById(R.id.lyt);
            lyt.setVisibility(View.GONE);
            progressBar = view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            houseKeepingController.getServices();

            Bundle bundle = getArguments();

            tv_desc.setText(bundle.getString("desc"));
            toolbar_title.setText(bundle.getString("title"));

            /*items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.HouseKeeping_count,0);
            tv_num_of_items.setText(items_count+" " +"items");

            total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.HouseKeeping_price,0);
            tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));*/

            view_order.setOnClickListener(v -> {
                if (items_count >0) {

                    if (GlobalClass.user_token.isEmpty()){
                        alertBox();

                    }else{
                        showBottomSheetDialog();
                    }

                    /*Fragment fragment = new OrderSummary();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("category",order_category);
                    fragment.setArguments(bundle1);

                    GlobalClass.editor.putInt(GlobalClass.HouseKeeping_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.HouseKeeping_price, (float) total_price);
                    GlobalClass.editor.commit();
                    getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                }else
                {
                    GlobalClass.ShowAlert(mContext,"Alert","Select atleast one item");
                }


            });


        }catch (Exception e){
            e.printStackTrace();
        }


        return view;
    }

    private void alertBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Login to place your order")
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
    public void onResume() {
        super.onResume();
        items_count = 0;
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottom_dailog_box);


        Button bt_today = bottomSheetDialog.findViewById(R.id.bt_today);
        Button bt_tomorrow = bottomSheetDialog.findViewById(R.id.bt_tomorrow);
        CardView select_date = bottomSheetDialog.findViewById(R.id.select_date);
        ImageView img_up_hr = bottomSheetDialog.findViewById(R.id.img_up_hr);
        ImageView img_down_hr = bottomSheetDialog.findViewById(R.id.img_down_hr);
        LinearLayout select_hours = bottomSheetDialog.findViewById(R.id.select_hours);
        LinearLayout folded_hanger = bottomSheetDialog.findViewById(R.id.folded_hanger);

        LinearLayout lyt_calendar = bottomSheetDialog.findViewById(R.id.lyt_calendar);
        LinearLayout lyt_select_date = bottomSheetDialog.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        ImageView img_up_min = bottomSheetDialog.findViewById(R.id.img_up_min);
        ImageView img_down_min = bottomSheetDialog.findViewById(R.id.img_down_min);

        TextView tv_hr = bottomSheetDialog.findViewById(R.id.tv_hr);
        TextView tv_min = bottomSheetDialog.findViewById(R.id.tv_min);


        ImageView img_hour_add = bottomSheetDialog.findViewById(R.id.img_hour_add);
        ImageView img_hour_minus = bottomSheetDialog.findViewById(R.id.img_hour_minus);

        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);
        TextView tv_hour = bottomSheetDialog.findViewById(R.id.tv_hour);

        Button bt_save_order = bottomSheetDialog.findViewById(R.id.bt_save_order);
        Button next_order = bottomSheetDialog.findViewById(R.id.next_order);

        CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);

        CheckBox checkbox_folded = bottomSheetDialog.findViewById(R.id.checkbox_folded);
        CheckBox checkbox_hanger = bottomSheetDialog.findViewById(R.id.checkbox_hanger);


        img_hour_add.setOnClickListener(v -> {
            if (count < 12){
                count = count+1;
                tv_hour.setText(count + " hours");
            }
        });

        img_hour_minus.setOnClickListener(v -> {
            if (count > 1){
                count = count -1;
                tv_hour.setText(count+" hours");
            }
        });

        /*bt_save_order.setOnClickListener(v -> {
            lyt_select_date.setVisibility(View.GONE);
            folded_hanger.setVisibility(View.VISIBLE);

        });*/

        /*checkbox_folded.setOnClickListener(v -> {
            if (checkbox_folded.isChecked()){
                checkbox_hanger.setEnabled(false);
            }
        });

        checkbox_hanger.setOnClickListener(v -> {
            if (checkbox_hanger.isChecked()){
                checkbox_folded.setEnabled(false);
            }
        });*/



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
            bt_tomorrow.setTextColor(Color.parseColor("#a5a5a5"));


        });

        bt_tomorrow.setOnClickListener(v -> {

            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_tomorrow.setTextColor(Color.WHITE);
            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.parseColor("#a5a5a5"));

        });

        if (hours){
            select_hours.setVisibility(View.VISIBLE);
        }else {
            select_hours.setVisibility(View.GONE);
        }


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
            bt_today.setTextColor(Color.parseColor("#a5a5a5"));
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.parseColor("#a5a5a5"));

        });


        bottomSheetDialog.show();
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response!=null){
            CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
            List<Data> houseKeepingList = servicesPojo.getData();


            Gson houseKeepingGson = new Gson();
            String houseKeepingJson = GlobalClass.sharedPreferences.getString("HouseKeeping", "");
            if (houseKeepingJson.isEmpty()) {
                // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                k9ArrDataPackage = new ArrayList(houseKeepingGson.fromJson(houseKeepingJson,type));
            }


            /*try {

                if (k9ArrDataPackage !=null){

                    items_count = 0;
                    total_price = 0;

                    for (int i=0;i<houseKeepingList.size();i++){
                        for (int j=0;j<k9ArrDataPackage.size();j++){
                            *//*if (houseKeepingList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())){
                                houseKeepingList.remove(i);
                                houseKeepingList.add(i,k9ArrDataPackage.get(j));
                            }*//*

                            if (houseKeepingList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())){
//                                if (k9ArrDataPackage.get(i).getCount() >1 &&  houseKeepingList.get(j).getItemselectorType().equals("single")){
//
//                                }else if (houseKeepingList.get(j).getItemselectorType().equals("single") && k9ArrDataPackage.get(i).getCount() == 1){
//                                    houseKeepingList.get(i).setCount(k9ArrDataPackage.get(j).getCount());
//                                    houseKeepingList.get(i).setItemSelected(true);
//
//                                    items_count+=houseKeepingList.get(i).getCount();
//                                    tv_num_of_items.setText(items_count +" items");
//
//                                    if (houseKeepingList.get(i).getCount() >= 0 ){
//                                        total_price += houseKeepingList.get(i).getCount() * Double.parseDouble(houseKeepingList.get(i).getPrice()) ;
//                                        tv_total_price.setText("₹ "+GlobalClass.round(total_price, 2));
//                                    }
//                                }else if (k9ArrDataPackage.get(i).getCount() > 0){
//                                    houseKeepingList.get(i).setCount(k9ArrDataPackage.get(j).getCount());
//                                    items_count+=houseKeepingList.get(i).getCount();
//                                    tv_num_of_items.setText(items_count  +" items");
//
//                                    if (houseKeepingList.get(i).getCount() >= 0 ){
//                                        total_price += houseKeepingList.get(i).getCount() * Double.parseDouble(houseKeepingList.get(i).getPrice()) ;
//                                        tv_total_price.setText("₹ "+GlobalClass.round(total_price, 2));
//                                    }
//                                }

                                if (!(k9ArrDataPackage.get(j).getItemselectorType()).equalsIgnoreCase(houseKeepingList.get(i).getItemselectorType())){
                                    k9ArrDataPackage.clear();
                                }else if((k9ArrDataPackage.get(j).getItemselectorType()).equalsIgnoreCase(houseKeepingList.get(i).getItemselectorType())
                                        && k9ArrDataPackage.get(j).getCount() > 0){
                                    k9ArrDataPackage.get(j).setCount(k9ArrDataPackage.get(j).getCount());
                                    houseKeepingList.get(i).setItemSelected(true);


                                    items_count += houseKeepingList.get(i).getCount();
                                    tv_num_of_items.setText(items_count + " " + "items");


                                    if (houseKeepingList.get(i).getCount() >= 0) {
                                        total_price += houseKeepingList.get(i).getCount() * Double.parseDouble(houseKeepingList.get(i).getPrice());
                                        tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                                    }
                                }

                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.HouseKeeping_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.HouseKeeping_price, (float) total_price);
                    Set<Data> set = new LinkedHashSet<>(houseKeepingList);
                    Gson gson = new Gson();
                    String json = gson.toJson(set);
                    GlobalClass.editor.putString("HouseKeeping", json);
                    GlobalClass.editor.commit();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }*/


            HouseKeepingAdapter adapter = new HouseKeepingAdapter(houseKeepingList,Position -> {

                try {

                    items_count= 0;
                    total_price = 0;
                    for (int i = 0; i<= houseKeepingList.size() - 1;i++){
                        items_count+=houseKeepingList.get(i).getCount();
                        tv_num_of_items.setText(items_count  +" items");



                        if (houseKeepingList.get(i).getCount() >= 0 ){
                            total_price += houseKeepingList.get(i).getCount() * Double.parseDouble(houseKeepingList.get(i).getPrice()) ;
                            tv_total_price.setText("₹ " +GlobalClass.round(total_price, 2));

                        }


                    }

                    if (items_count>0){
                        if (houseKeepingList.get(Position).getIshourbounded()){
                            hours = true;
                        }else {
                            hours = false;
                        }
                    }

                    /*GlobalClass.editor.putInt(GlobalClass.HouseKeeping_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.HouseKeeping_price, (float) total_price);
                    GlobalClass.editor.commit();*/

                }catch (Exception e){
                    e.printStackTrace();
                }

            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}