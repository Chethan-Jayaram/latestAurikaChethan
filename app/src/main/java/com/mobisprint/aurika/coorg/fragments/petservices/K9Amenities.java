package com.mobisprint.aurika.coorg.fragments.petservices;

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

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.K9AmenitiesAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9AmenitiesController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
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

public class K9Amenities extends Fragment implements ApiListner {

    private TextView tv_k9_amenities_desc,toolbar_title,tv_num_of_items,tv_total_price;
    private RecyclerView k9_amenities_recycler;
    private K9AmenitiesController controller;
    private Context mContext;
    private ImageView img_back;
    private List<K9Data> k9ArrDataPackage;
    private Integer items_count = 0;
    private double total_price = 0;
    private TextView view_order;
    private String order_category = "k9amenities";
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_k9_amenities, container, false);


        tv_k9_amenities_desc = view.findViewById(R.id.tv_k9_amenities_desc);
        k9_amenities_recycler = view.findViewById(R.id.k9_amenities_recycler);
        controller = new K9AmenitiesController(this);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);
        lyt = view.findViewById(R.id.lyt);
        progressBar = view.findViewById(R.id.progress_bar);
        lyt.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = getArguments();
         tv_k9_amenities_desc.setText(bundle.getString("desc"));
         toolbar_title.setText(bundle.getString("sub_title"));


        controller.getAmenities();

        /*items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.K9Amenities_count,0);
        tv_num_of_items.setText(items_count+" " +"items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.K9Amenities_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));*/

        view_order.setOnClickListener(v -> {
            if (items_count>0) {

                if (GlobalClass.user_token.isEmpty()){
                    alertBox();

                }else{
                    showBottomSheetDialog();
                }
               /* Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();


                GlobalClass.editor.putInt(GlobalClass.K9Amenities_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.K9Amenities_price, (float) total_price);
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
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response!=null){

            PetServices services = (PetServices) response.body();
            List<K9Data> amenitiesList = services.getData();

            if (k9ArrDataPackage != null) {
                k9ArrDataPackage.clear();
            }


            Gson amenitiesGson = new Gson();
            String amenitiesJson = GlobalClass.sharedPreferences.getString("K9Amenities", "");
            if (amenitiesJson.isEmpty()) {
                // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<K9Data>>() {
                }.getType();
                k9ArrDataPackage = new ArrayList(amenitiesGson.fromJson(amenitiesJson,type));
            }

            /*try {

                if (k9ArrDataPackage !=null){

                    items_count = 0;
                    total_price = 0;

                    for (int i=0;i<amenitiesList.size();i++){
                        for (int j=0;j<k9ArrDataPackage.size();j++){
                            if (amenitiesList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())){
                                *//*amenitiesList.remove(i);
                                amenitiesList.add(i,k9ArrDataPackage.get(j));*//*

                                if (amenitiesList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())) {

                                    if (!(k9ArrDataPackage.get(i).getItemselectorType()).equalsIgnoreCase(amenitiesList.get(j).getItemselectorType())){
                                        k9ArrDataPackage.clear();
                                    }else if ((k9ArrDataPackage.get(i).getItemselectorType()).equalsIgnoreCase(amenitiesList.get(j).getItemselectorType())
                                            && k9ArrDataPackage.get(i).getCount() > 0) {
                                        amenitiesList.get(i).setCount(k9ArrDataPackage.get(j).getCount());
                                        amenitiesList.get(i).setItemSelected(true);


                                        items_count += amenitiesList.get(i).getCount();
                                        tv_num_of_items.setText(items_count + " " + "items");


                                        if (amenitiesList.get(i).getCount() >= 0) {
                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
                                        }
                                    }
                                }

//                                if (amenitiesList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())) {
//                                    if ((k9ArrDataPackage.get(i).getCount() > 1 && amenitiesList.get(i).getItemselectorType().equals("single"))) {
//                                    } else if (amenitiesList.get(i).getItemselectorType().equals("single") && k9ArrDataPackage.get(i).getCount() == 1) {
//                                        amenitiesList.get(i).setCount(k9ArrDataPackage.get(j).getCount());
//                                        amenitiesList.get(i).setItemSelected(true);
//
//
//                                        items_count += amenitiesList.get(i).getCount();
//                                        tv_num_of_items.setText(items_count + " " + "items");
//
//
//                                        if (amenitiesList.get(i).getCount() >= 0) {
//                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
//                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
//                                        }
//                                    } else if (k9ArrDataPackage.get(i).getCount() > 0) {
//
//                                        amenitiesList.get(i).setCount(k9ArrDataPackage.get(j).getCount());
//
//                                        items_count += amenitiesList.get(i).getCount();
//                                        tv_num_of_items.setText(items_count + " " + "items");
//
//
//                                        if (amenitiesList.get(i).getCount() >= 0) {
//                                            total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice());
//                                            tv_total_price.setText("₹ " + " " + GlobalClass.round(total_price, 2));
//                                        }
//
//                                    }
//
//                                }
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.K9Amenities_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.K9Amenities_price, (float) total_price);
                    Set<K9Data> set = new LinkedHashSet<>(amenitiesList);
                    Gson gson = new Gson();
                    String json = gson.toJson(set);
                    GlobalClass.editor.putString("K9Amenities", json);
                    GlobalClass.editor.commit();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }*/

            K9AmenitiesAdapter adapter = new K9AmenitiesAdapter(amenitiesList,data -> {


                items_count= 0;
                total_price = 0;
                for (int i = 0; i<= amenitiesList.size() - 1;i++){
                    items_count+=amenitiesList.get(i).getCount();
                    tv_num_of_items.setText(items_count + " " +"items");



                    if (amenitiesList.get(i).getCount() >= 0 ){
                        total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice()) ;
                        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));

                    }

                }

                GlobalClass.editor.putInt(GlobalClass.K9Amenities_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.K9Amenities_price, (float) total_price);
                GlobalClass.editor.commit();



            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            k9_amenities_recycler.setLayoutManager(layoutManager);
            k9_amenities_recycler.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}