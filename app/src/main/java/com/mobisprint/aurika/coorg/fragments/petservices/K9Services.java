package com.mobisprint.aurika.coorg.fragments.petservices;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.K9MenuAdapter;
import com.mobisprint.aurika.coorg.adapter.K9ServicesAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9ServicesController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;


public class K9Services extends Fragment implements ApiListner {

    private TextView tv_k9_services_desc,toolbar_title,view_order,tv_total_price,tv_num_of_items;
    private RecyclerView recyclerView;
    private K9ServicesController controller;
    private ImageView img_back;
    private ProgressBar progressBar;
    private CoordinatorLayout k9_Services_lyt;
    private Integer items_count = 0;
    private double total_price = 0;
    private Boolean hours = false;
    private int hr,min;
    private Calendar calendar;
    private Integer count = 1;
    private List<K9Data> serviceList;
    private List<K9Data> selectedList = new ArrayList<>();
    private Context mContext;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_k9_services, container, false);

        mContext = getContext();
        controller = new K9ServicesController(this);
        tv_k9_services_desc = view.findViewById(R.id.tv_k9_services_desc);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        recyclerView = view.findViewById(R.id.k9_services_recyclerview);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        img_back.setVisibility(View.VISIBLE);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        view_order = view.findViewById(R.id.view_order);
        k9_Services_lyt = view.findViewById(R.id.k9_Services_lyt);
        k9_Services_lyt.setVisibility(View.GONE);
        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        Bundle bundle = getArguments();
        tv_k9_services_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("sub_title"));




        view_order.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking) {

                if (items_count > 0) {
                    if (GlobalClass.user_token.isEmpty()) {
                        alertBox();

                    } else if (GlobalClass.user_active_booking) {
                        /*showBottomSheetDialog();*/
                        selectedList.clear();
                        for (int i = 0; i < serviceList.size(); i++) {
                            if (serviceList.get(i).getCount() > 0) {
                                serviceList.get(i).setItem_id(serviceList.get(i).getId());
                                serviceList.get(i).setQuantity(serviceList.get(i).getCount());
                                selectedList.add(serviceList.get(i));
                            }
                        }
                        BottomDailogFragment fragment = new BottomDailogFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Category", "k9-services");
                        bundle1.putBoolean("hours", hours);
                        bundle1.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) selectedList);
                        fragment.setArguments(bundle1);
                        /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                        fragment.show(getActivity().getSupportFragmentManager(),
                                "fragment_bottom_sheet_dailog");
                    } else {
                        GlobalClass.ShowAlert(mContext, "Alert", "You don't have active booking to place order");
                    }
                }
            }else{
                GlobalClass.ShowAlert(mContext, "Alert", "You don't have an active booking. You can place order only during the stay at property.");

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
        controller.getData();
    }

    private void alertBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        builder.setMessage("Please login to place your request")
                .setCancelable(false)
                .setPositiveButton("Okay", (dialog, id) -> {
                    Intent intent = new Intent(getContext(), UserAuthenticationActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                });

        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    private void showBottomSheetDialog() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(getContext());
        bottomSheetDialog.setContentView(R.layout.bottom_dailog_box);


        Button bt_today = bottomSheetDialog.findViewById(R.id.bt_today);
        Button bt_tomorrow = bottomSheetDialog.findViewById(R.id.bt_tomorrow);
        CardView select_date = bottomSheetDialog.findViewById(R.id.select_date);
        ImageView img_up_hr = bottomSheetDialog.findViewById(R.id.img_up_hr);
        ImageView img_down_hr = bottomSheetDialog.findViewById(R.id.img_down_hr);
        LinearLayout select_hours = bottomSheetDialog.findViewById(R.id.select_hours);

        LinearLayout lyt_calendar = bottomSheetDialog.findViewById(R.id.lyt_calendar);
        LinearLayout lyt_select_date = bottomSheetDialog.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        ImageView img_up_min = bottomSheetDialog.findViewById(R.id.img_up_min);
        ImageView img_down_min = bottomSheetDialog.findViewById(R.id.img_down_min);

        TextView tv_pm = bottomSheetDialog.findViewById(R.id.tv_pm);
        TextView tv_am = bottomSheetDialog.findViewById(R.id.tv_am);

        TextView tv_hr = bottomSheetDialog.findViewById(R.id.tv_hr);
        TextView tv_min = bottomSheetDialog.findViewById(R.id.tv_min);

        ImageView img_hour_add = bottomSheetDialog.findViewById(R.id.img_hour_add);
        ImageView img_hour_minus = bottomSheetDialog.findViewById(R.id.img_hour_minus);


        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);

        TextView tv_hour = bottomSheetDialog.findViewById(R.id.tv_hour);

        CalendarView calendar_view = bottomSheetDialog.findViewById(R.id.calendar_view);

        bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
        bt_today.setTextColor(Color.WHITE);

        int am = calendar.get(Calendar.HOUR_OF_DAY);

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

        tv_am.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.parseColor("#a5a5a5"));
            tv_am.setTextColor(Color.BLACK);
        });

        tv_pm.setOnClickListener(v -> {
            tv_pm.setTextColor(Color.BLACK);
            tv_am.setTextColor(Color.parseColor("#a5a5a5"));
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


        img_down_min.setOnClickListener(v -> {
            if (min>1){
                min = min - 1;
                tv_min.setText(String.valueOf(min) + " m");
            }
        });

        img_hour_add.setOnClickListener(v -> {
            if (count < 12){
                count = count+1;
                if (count ==1){
                    tv_hour.setText(count + " hour");
                }else {
                    tv_hour.setText(count + " hours");
                }
            }
        });

        img_hour_minus.setOnClickListener(v -> {
            if (count > 1){
                count = count -1;
                if (count ==1){
                    tv_hour.setText(count + " hour");
                }else {
                    tv_hour.setText(count + " hours");
                }
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
        k9_Services_lyt.setVisibility(View.VISIBLE);
        if (response!=null){
            PetServices services = (PetServices) response.body();
            serviceList = services.getData();

            K9ServicesAdapter k9ServicesAdapter = new K9ServicesAdapter(serviceList,getContext(),Position -> {
                try {


                    /*selectedList = new ArrayList<>();*/
                    items_count= 0;
                    total_price = 0;
                    for (int i = 0; i<= serviceList.size() - 1;i++){
                        items_count+=serviceList.get(i).getCount();
                        tv_num_of_items.setText(items_count + " items");



                        if (serviceList.get(i).getCount() >= 0 ){
                            total_price += serviceList.get(i).getCount() * Double.parseDouble(serviceList.get(i).getPrice()) ;
                            tv_total_price.setText("₹ "+ GlobalClass.round(total_price,2));

                        }


                        /*if (serviceList.get(i).getCount() != 0){
                            selectedList.add(serviceList.get(i));
                        }*/

                    }

                    if (items_count>0){
                        if (serviceList.get(Position).getIshourbounded()){
                            hours = true;
                        }else {
                            hours = false;
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(k9ServicesAdapter);
        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

    }
}