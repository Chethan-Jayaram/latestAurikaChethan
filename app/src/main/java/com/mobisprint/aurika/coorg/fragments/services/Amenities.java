package com.mobisprint.aurika.coorg.fragments.services;

import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.HomeActivity;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.controller.services.AmenitiesController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.fragments.loginfragments.ForgotMpinFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.LoginFragment;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.mobisprint.aurika.helper.GlobalClass.editor;


public class Amenities extends Fragment implements ApiListner {

    private RecyclerView amenities_recyclerview;
    private AmenitiesController amenitiesController;
    private Context mContext;
    private TextView toolbar_title, tv_amenities_desc, tv_num_of_items, tv_total_price, view_order;
    private Integer items_count = 0;
    private double total_price = 0;
    private ImageView img_back;

    private String order_category = "amenities";

    private List<Data> arrDataPackage;

    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;

    private Calendar calendar;

    int hr,min;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities, container, false);

        try {

            amenities_recyclerview = view.findViewById(R.id.amenities_recycler);
            amenitiesController = new AmenitiesController(this);
            mContext = getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            tv_amenities_desc = view.findViewById(R.id.tv_amenities_desc);
            tv_num_of_items = view.findViewById(R.id.tv_num_items);
            tv_total_price = view.findViewById(R.id.tv_total_price);
            view_order = view.findViewById(R.id.view_order);
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            img_back.setVisibility(View.VISIBLE);
            calendar = Calendar.getInstance();

            progressBar = view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            hr = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);

            coordinatorLayout = view.findViewById(R.id.lyt);
            coordinatorLayout.setVisibility(View.GONE);


            Bundle bundle = getArguments();
            tv_amenities_desc.setText(bundle.getString("desc"));
            toolbar_title.setText(bundle.getString("title"));

            amenitiesController.getServices();


            view_order.setOnClickListener(v -> {
                if (items_count > 0) {

                    if (GlobalClass.user_token.isEmpty()){

                        alertBox();

                    }else{
                        showBottomSheetDialog();
                    }

                   /* Fragment fragment = new OrderSummary();
                    Bundle bundle1 = new Bundle();
                    *//* bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) selectedList);*//*
                    bundle1.putString("category", order_category);

                    GlobalClass.editor.putInt(GlobalClass.Amenities_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.Amenities_price, (float) total_price);
                    GlobalClass.editor.commit();


                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/



                } else {
                    GlobalClass.ShowAlert(mContext, "Alert", "Select atleast one item");
                }


            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public void onPause() {

        super.onPause();
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

    @RequiresApi(api = Build.VERSION_CODES.M)
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

        TextView tv_pm = bottomSheetDialog.findViewById(R.id.tv_pm);
        TextView tv_am = bottomSheetDialog.findViewById(R.id.tv_am);


        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);

        CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);



        int am = calendar.get(Calendar.HOUR_OF_DAY);

        tv_min.setText(String.valueOf(min) + " m");
        tv_hr.setText(String.valueOf(hr) + " h");

        if (am>12){
            tv_pm.setTextColor(Color.BLACK);
            tv_am.setTextColor(Color.parseColor("#a5a5a5"));
        }else {
            tv_pm.setTextColor(Color.parseColor("#a5a5a5"));
            tv_am.setTextColor(Color.BLACK);
        }

        img_up_hr.setOnClickListener(v -> {
            if (hr<12){
                hr = hr+1;
                tv_hr.setText(String.valueOf(hr) + " h");
            }
        });

        img_down_hr.setOnClickListener(v -> {
            if (hr>1){
                hr = hr-1;
                tv_hr.setText(String.valueOf(hr) + " h");
            }
        });

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
    public void onResume() {
        super.onResume();
        items_count = 0;
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);
        /*items_count = GlobalClass.sharedPreferences.getInt(GlobalClass.Amenities_count, 0);
        tv_num_of_items.setText(items_count + " " + "items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.Amenities_price, 0);
        tv_total_price.setText("₹ " + GlobalClass.round(total_price, 2));*/

        try {
            if (response != null) {
                CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
                List<Data> amenitiesList = servicesPojo.getData();

                if (arrDataPackage != null) {
                    arrDataPackage.clear();
                }

                Gson amenitiesGson = new Gson();
                String amenitiesJson = GlobalClass.sharedPreferences.getString("Amenities", "");
                if (amenitiesJson.isEmpty()) {
                    // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                } else {
                    Type type = new TypeToken<List<Data>>() {
                    }.getType();
                    arrDataPackage = new ArrayList(amenitiesGson.fromJson(amenitiesJson, type));
                }


                /*try {

                    if (arrDataPackage != null) {

                        items_count = 0;
                        total_price = 0;

                        for (int i = 0; i < amenitiesList.size(); i++) {
                            for (int j = 0; j < arrDataPackage.size(); j++) {
                                *//*if (amenitiesList.get(i).getId().equals(arrDataPackage.get(j).getId())){
                                    amenitiesList.remove(i);
                                    amenitiesList.add(i,arrDataPackage.get(j));
                                }*//*

                                if (amenitiesList.get(i).getId().equals(arrDataPackage.get(j).getId())) {

                                    if((arrDataPackage.get(i).getItemselectorType()).equalsIgnoreCase(amenitiesList.get(j).getItemselectorType())
                                    && arrDataPackage.get(i).getCount() > 0){
                                        amenitiesList.get(i).setCount(arrDataPackage.get(j).getCount());
                                        amenitiesList.get(i).setItemSelected(true);

                                        items_count += amenitiesList.get(i).getCount();
                                        tv_num_of_items.setText(items_count + " " + "items");


                                        if (amenitiesList.get(i).getCount() >= 0) {
                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                                        }
                                    }



                                    *//*if ((arrDataPackage.get(i).getCount() > 1 && amenitiesList.get(i).getItemselectorType().equals("single"))) {
                                    } else if (amenitiesList.get(i).getItemselectorType().equals("single") && arrDataPackage.get(i).getCount() == 1) {
                                        amenitiesList.get(i).setCount(arrDataPackage.get(j).getCount());
                                        amenitiesList.get(i).setItemSelected(true);


                                        items_count += amenitiesList.get(i).getCount();
                                        tv_num_of_items.setText(items_count + " " + "items");


                                        if (amenitiesList.get(i).getCount() >= 0) {
                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                                        }
                                    } else if (arrDataPackage.get(i).getCount() > 0) {

                                        amenitiesList.get(i).setCount(arrDataPackage.get(j).getCount());

                                        items_count += amenitiesList.get(i).getCount();
                                        tv_num_of_items.setText(items_count + " " + "items");


                                        if (amenitiesList.get(i).getCount() >= 0) {
                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                                        }

                                    }*//*

                                }

                            }
                        }

                        GlobalClass.editor.putInt(GlobalClass.Amenities_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.Amenities_price, (float) total_price);
                        Set<Data> set = new LinkedHashSet<>(amenitiesList);
                        Gson gson = new Gson();
                        String json = gson.toJson(set);
                        GlobalClass.editor.putString("Amenities", json);
                        GlobalClass.editor.commit();

                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }*/


                AmenitiesAdapter adapter = new AmenitiesAdapter(amenitiesList, data -> {


                    try {

                        items_count = 0;
                        total_price = 0;
                        for (int i = 0; i <= amenitiesList.size() - 1; i++) {
                            items_count += amenitiesList.get(i).getCount();
                            tv_num_of_items.setText(items_count + " " + "items");


                            if (amenitiesList.get(i).getCount() >= 0) {
                                total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                            }
                        }

                        /*GlobalClass.editor.putInt(GlobalClass.Amenities_count, items_count);
                        GlobalClass.editor.putFloat(GlobalClass.Amenities_price, (float) total_price);
                        GlobalClass.editor.commit();*/


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                amenities_recyclerview.setLayoutManager(layoutManager);
                amenities_recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    

    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext, "Alert", error);

    }
}