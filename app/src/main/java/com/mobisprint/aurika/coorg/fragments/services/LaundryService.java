package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.LaundryServiceAdapter;
import com.mobisprint.aurika.coorg.controller.services.LaundryServiceController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Response;


public class LaundryService extends Fragment implements ApiListner {

    private TextView toolbar_title,tv_laundry_desc,tv_num_of_items,tv_total_price,view_order,laundry_instructions;
    private ExpandableListView laundry_expandable_listview;
    private LaundryServiceController controller;
    private Context mContext;
    private ImageView img_back;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "laundry";
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;
    private int hr,min;
    private Calendar calendar;
    private List<Data> laundry_service_list;
    private List<Category_item> selectedList = new ArrayList<>();


   // private List<Data> mlaundryList;
    private List<Data> arrPackageData;


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_laundry_service, container, false);

       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       tv_laundry_desc = view.findViewById(R.id.laundry_desc);
       laundry_expandable_listview = view.findViewById(R.id.laundry_expandable_listview);

        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);
        lyt = view.findViewById(R.id.lyt);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.GONE);
        laundry_instructions = view.findViewById(R.id.laundry_instructions);
        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        /*String sourceString = "1. " + "<b>" + "Garments returned for ironing are returned folded." + "</b> " + "Please infrom laundry if you would like them delivered on a hanger instead.";
        laundry_instructions.setText(Html.fromHtml(sourceString));*/


       controller = new LaundryServiceController(this);
       mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

       Bundle bundle = getArguments();

       tv_laundry_desc.setText(bundle.getString("desc"));
       toolbar_title.setText(bundle.getString("title"));


        /*items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.Laundry_count,0);
        tv_num_of_items.setText(items_count +" items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.Laundry_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));*/




        view_order.setOnClickListener(v -> {

            if (items_count>0)  {
                if (GlobalClass.user_token.isEmpty()){
                    alertBox();

                }else if (GlobalClass.user_active_booking) {
                    /*showBottomSheetDialog();*/
                    selectedList.clear();
                    for (int i=0;i<laundry_service_list.size();i++){
                        for (int j=0;j<laundry_service_list.get(i).getCategory_item().size();j++){
                            if (laundry_service_list.get(i).getCategory_item().get(j).getCount()>0){
                                laundry_service_list.get(i).getCategory_item().get(j).setItem_id(laundry_service_list.get(i).getCategory_item().get(j).getId());
                                laundry_service_list.get(i).getCategory_item().get(j).setQuantity(laundry_service_list.get(i).getCategory_item().get(j).getCount());
                                selectedList.add(laundry_service_list.get(i).getCategory_item().get(j));
                            }
                        }
                    }
                    BottomDailogFragment fragment = new BottomDailogFragment();
                    Bundle bundle1 = new Bundle();
                    bundle1.putString("Category","laundry");
                    bundle1.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) selectedList);
                    fragment.setArguments(bundle1);
                    fragment.show(getActivity().getSupportFragmentManager(),
                            "fragment_bottom_sheet_dailog");
                } else{
                    GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place order");
                }

                /*Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();

                GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
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



    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottom_dailog_box);


        Button bt_today = bottomSheetDialog.findViewById(R.id.bt_today);
        Button bt_tomorrow = bottomSheetDialog.findViewById(R.id.bt_tomorrow);
        CardView select_date = bottomSheetDialog.findViewById(R.id.select_date);
        ImageView img_up_hr = bottomSheetDialog.findViewById(R.id.img_up_hr);
        ImageView img_down_hr = bottomSheetDialog.findViewById(R.id.img_down_hr);
        LinearLayout folded_hanger = bottomSheetDialog.findViewById(R.id.folded_hanger);
        Button bt_save_order = bottomSheetDialog.findViewById(R.id.bt_save_order);

        LinearLayout lyt_calendar = bottomSheetDialog.findViewById(R.id.lyt_calendar);
        LinearLayout lyt_select_date = bottomSheetDialog.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        TextView tv__bottomsheet_tittle = bottomSheetDialog.findViewById(R.id.tv__bottomsheet_tittle);

        ImageView img_up_min = bottomSheetDialog.findViewById(R.id.img_up_min);
        ImageView img_down_min = bottomSheetDialog.findViewById(R.id.img_down_min);

        TextView tv_pm = bottomSheetDialog.findViewById(R.id.tv_pm);
        TextView tv_am = bottomSheetDialog.findViewById(R.id.tv_am);

        TextView tv_hr = bottomSheetDialog.findViewById(R.id.tv_hr);
        TextView tv_min = bottomSheetDialog.findViewById(R.id.tv_min);


        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);

        CheckBox checkbox_hanger = bottomSheetDialog.findViewById(R.id.checkbox_hanger);
        CheckBox checkbox_folded = bottomSheetDialog.findViewById(R.id.checkbox_folded);


        CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);

        bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
        bt_today.setTextColor(Color.WHITE);

        tv__bottomsheet_tittle.setText("Preferred pick-up time?");

        int am = calendar.get(Calendar.HOUR_OF_DAY);

        checkbox_folded.setOnClickListener(v -> {
           checkbox_hanger.setChecked(false);
        });

        checkbox_hanger.setOnClickListener(v -> {
            checkbox_folded.setChecked(false);
        });

        tv_min.setText(String.valueOf(min) + " m");
        tv_hr.setText(String.valueOf(am) + " h");

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

        img_up_min.setOnClickListener(v -> {
            if (min<60){
                min = min +1;
                tv_min.setText(String.valueOf(min) + " m");
            }
        });

        tv_am.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.parseColor("#a5a5a5"));
            tv_am.setTextColor(Color.BLACK);
        });

        tv_pm.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.BLACK);
            tv_am.setTextColor(Color.parseColor("#a5a5a5"));
        });


        img_down_min.setOnClickListener(v -> {
            if (min>1){
                min = min - 1;
                tv_min.setText(String.valueOf(min) + " m");
            }
        });

        bt_save_order.setOnClickListener(v -> {
            lyt_select_date.setVisibility(View.GONE);
            folded_hanger.setVisibility(View.VISIBLE);

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
        tv_num_of_items.setText("0 items");
        tv_total_price.setText("₹ 0.00");
        controller.getLaundryServices();
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response != null){
            CoorgServicesPojo servicesPojo= (CoorgServicesPojo) response.body();
            laundry_service_list = servicesPojo.getData();


            if (arrPackageData != null) {
                arrPackageData.clear();
            }

            Gson gson = new Gson();
            String json = GlobalClass.sharedPreferences.getString("Laundry", "");
            if (json.isEmpty()) {
                //Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                arrPackageData = new ArrayList(gson.fromJson(json,type));
            }

            GlobalClass.editor.putBoolean("isItemSelected",false);
            GlobalClass.editor.putBoolean("isMultipleItemSelected",false);
            GlobalClass.editor.commit();

            /*try {

                if (arrPackageData !=null){

                    items_count = 0;
                    total_price = 0;

                    for (int i =0; i<laundry_service_list.size();i++){
                        for (int j = 0;j<arrPackageData.size();j++){
                            *//*if(laundry_service_list.get(i).getId().equals(arrPackageData.get(j).getId())){
                                *//**//*laundry_service_list.remove(i);
                                laundry_service_list.add(i,arrPackageData.get(j));*//**//*


                                if(arrPackageData.get(i).getCount() > 1 && laundry_service_list.get(i).getItemselectorType().equals("single")){
                                }else if (laundry_service_list.get(i).getItemselectorType().equals("single") && arrPackageData.get(i).getCount() == 1){
                                    laundry_service_list.get(i).setCount(arrPackageData.get(j).getCount());
                                    laundry_service_list.get(i).setItemSelected(true);
                                    GlobalClass.editor.putBoolean("isItemSelected",true);
                                    GlobalClass.editor.commit();
                                    items_count += laundry_service_list.get(i).getCategory_item().get(j).getCount();
                                    tv_num_of_items.setText(items_count+" " +"items");

                                    if (laundry_service_list.get(i).getCategory_item().get(j).getCount() >= 0 ){
                                        ;                                total_price +=laundry_service_list.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(j).getPrice()) ;
                                        tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                    }
                                }else if (arrPackageData.get(i).getCount() > 0){
                                    laundry_service_list.get(i).setCount(arrPackageData.get(j).getCount());
                                    items_count += laundry_service_list.get(i).getCategory_item().get(j).getCount();
                                    tv_num_of_items.setText(items_count+" " +"items");

                                    if (laundry_service_list.get(i).getCategory_item().get(j).getCount() >= 0 ){
                                        total_price +=laundry_service_list.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(j).getPrice()) ;
                                        tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                    }
                                }
                           }*//*

                            for (int x=0; x<laundry_service_list.get(i).getCategory_item().size(); x++){
                                for (int y=0; y<arrPackageData.get(j).getCategory_item().size(); y++){
                                    if (laundry_service_list.get(i).getCategory_item().get(x).getId().equals(arrPackageData.get(j).getCategory_item().get(y).getId())){

                                        if (!(arrPackageData.get(j).getCategory_item().get(y).getItemselectorType().equalsIgnoreCase(laundry_service_list.get(i).getCategory_item().get(x).getItemselectorType()))){
                                            arrPackageData.clear();
                                            GlobalClass.editor.putBoolean("isItemSelected",false);
                                            GlobalClass.editor.putBoolean("isMultipleItemSelected",false);
                                            GlobalClass.editor.commit();
                                        }else if (arrPackageData.get(j).getCategory_item().get(y).getItemselectorType().equalsIgnoreCase(laundry_service_list.get(i).getCategory_item().get(x).getItemselectorType())
                                        && arrPackageData.get(j).getCategory_item().get(y).getCount()>0){
                                            laundry_service_list.get(i).getCategory_item().get(x).setCount(arrPackageData.get(j).getCategory_item().get(y).getCount());
                                            laundry_service_list.get(i).getCategory_item().get(x).setItemSelected(true);

                                            if (arrPackageData.get(j).getCategory_item().get(y).getItemselectorType().equalsIgnoreCase("single")){
                                                GlobalClass.editor.putBoolean("isItemSelected",true);
                                                GlobalClass.editor.putBoolean("isMultipleItemSelected",false);
                                                GlobalClass.editor.commit();
                                            }else if (arrPackageData.get(j).getCategory_item().get(y).getItemselectorType().equalsIgnoreCase("multi")){
                                                GlobalClass.editor.putBoolean("isItemSelected",false);
                                                GlobalClass.editor.putBoolean("isMultipleItemSelected",true);
                                                GlobalClass.editor.commit();
                                            }

                                            items_count += laundry_service_list.get(i).getCategory_item().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (laundry_service_list.get(i).getCategory_item().get(x).getCount() >= 0 ){
                                                total_price +=laundry_service_list.get(i).getCategory_item().get(x).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }

                                        }


                                        *//*GlobalClass.editor.putBoolean("isItemSelected",false);
                                        GlobalClass.editor.putBoolean("isMultipleItemSelected",false);*//*
                                        *//*if (arrPackageData.get(j).getCategory_item().get(y).getCount() > 1 && laundry_service_list.get(i).getCategory_item().get(x).getItemselectorType().equals("single")){
                                        }else if (laundry_service_list.get(i).getCategory_item().get(x).getItemselectorType().equals("single") && arrPackageData.get(j).getCategory_item().get(y).getCount() ==1){
                                            laundry_service_list.get(i).getCategory_item().get(x).setCount(arrPackageData.get(j).getCategory_item().get(y).getCount());
                                            laundry_service_list.get(i).getCategory_item().get(x).setItemSelected(true);
                                            GlobalClass.editor.putBoolean("isItemSelected",true);
                                            GlobalClass.editor.putBoolean("isMultipleItemSelected",false);


                                            items_count += laundry_service_list.get(i).getCategory_item().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (laundry_service_list.get(i).getCategory_item().get(x).getCount() >= 0 ){
                                                total_price +=laundry_service_list.get(i).getCategory_item().get(x).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }

                                        }else if (arrPackageData.get(j).getCategory_item().get(y).getCount() > 0){
                                            laundry_service_list.get(i).getCategory_item().get(x).setCount(arrPackageData.get(j).getCategory_item().get(y).getCount());

                                            GlobalClass.editor.putBoolean("isItemSelected",false);
                                            GlobalClass.editor.putBoolean("isMultipleItemSelected",true);

                                            items_count += laundry_service_list.get(i).getCategory_item().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (laundry_service_list.get(i).getCategory_item().get(x).getCount() >= 0 ){
                                                total_price +=laundry_service_list.get(i).getCategory_item().get(x).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }
                                        }*//*
                                    }
                                }
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
                    Set<Data> set = new LinkedHashSet<>(laundry_service_list);
                    Gson LaundryGson = new Gson();
                    String LaundryJson = LaundryGson.toJson(set);
                    GlobalClass.editor.putString("Laundry", LaundryJson);
                    GlobalClass.editor.commit();


                }


            } catch (Exception e) {
                e.printStackTrace();
            }*/





            LaundryServiceAdapter adapter = new LaundryServiceAdapter(mContext,laundry_service_list, data -> {

                try {

                    items_count= 0;
                    total_price = 0;

                    for(int i=0;i<laundry_service_list.size() ;i++){

                        for (int j=0; j<laundry_service_list.get(i).getCategory_item().size();j++){

                                items_count += laundry_service_list.get(i).getCategory_item().get(j).getCount();
                                tv_num_of_items.setText(items_count+" " +"items");


                            if (laundry_service_list.get(i).getCategory_item().get(j).getCount() >= 0 ){
;                                total_price +=laundry_service_list.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
                    GlobalClass.editor.commit();



                }catch (Exception e){
                    e.printStackTrace();
                }





            });
            laundry_expandable_listview.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}