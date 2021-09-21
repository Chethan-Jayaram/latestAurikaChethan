package com.mobisprint.aurika.coorg.fragments.spa;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.SpaDropDownAdapter;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.controller.spa.BookAnAppointmentController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderConfirmedFragment;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

public class SpaBookAnAppointment extends Fragment implements ApiListner,GlobalClass.FragmentCallback {

    private TextView toolbar_title,tv_spa_title,therapy_heading,therapy_time,therapy_price,req_date_time;
    private ImageView img_spa,img_drop_down;
    private Context mContext;
    private BookAnAppointmentController controller;
    private RecyclerView recyclerView;
    private RelativeLayout lyt_select_therapy,lyt_main,img_time;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private Boolean chech_status = true,date_and_time = false;
    private TicketModle ticketModle;
    private Button btn_request_booking;
    private BottomDailogController bottomDailogController;
    private String price;
    private ProgressDialog dialog;
    private int hr,min;
    private Calendar calendar;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa_book_an_appointment, container, false);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Book an appointment");

        ticketModle = new TicketModle();
        req_date_time =view.findViewById(R.id.req_date_time);
        btn_request_booking = view.findViewById(R.id.btn_request_booking);
        tv_spa_title = view.findViewById(R.id.tv_coorg_spa_title);
        recyclerView = view.findViewById(R.id.spa_therapy_recyclerview);
        lyt_select_therapy = view.findViewById(R.id.lyt_select_therapy);
        img_spa = view.findViewById(R.id.img_coorg_spa);
        therapy_heading = view.findViewById(R.id.therapy_heading);
        therapy_price = view.findViewById(R.id.therapy_price);
        therapy_time = view.findViewById(R.id.therapy_time);
        img_drop_down = view.findViewById(R.id.img_drop_down);
        mContext = getContext();
        recyclerView.setVisibility(View.GONE);
        img_time = view.findViewById(R.id.img_time);
        lyt_main = view.findViewById(R.id.lyt_main);

        linearLayout = view.findViewById(R.id.lyt);
        linearLayout.setVisibility(View.GONE);
        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        bottomDailogController = new BottomDailogController(this);


        dialog = new ProgressDialog(mContext);

        Bundle bundle = getArguments();

        tv_spa_title.setText(bundle.getString("title"));
        Glide.with(mContext).load(bundle.getString("image")).centerCrop().into(img_spa);

        controller = new BookAnAppointmentController(this);


        lyt_select_therapy.setOnClickListener(v -> {
            if (chech_status){
                recyclerView.setVisibility(View.VISIBLE);
                chech_status = false;
                lyt_main.setOnClickListener(v1 -> {
                    recyclerView.setVisibility(View.GONE);
                    chech_status = true;
                });
            }else {
                recyclerView.setVisibility(View.GONE);
                chech_status = true;
            }


        });

        img_time.setOnClickListener(v -> {

            if (therapy_heading.getText().toString().equalsIgnoreCase("Select Therapy")){
                GlobalClass.ShowAlert(this.getContext(),"Alert","Select Therapy");
            }else{
                date_and_time = true;
                /*showBottomSheetDialog();*/
                BottomDailogFragment fragment = new BottomDailogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("Category","spa");
                bundle1.putString("title",therapy_heading.getText().toString());
                bundle1.putString("price",price);
                bundle1.putInt("item_id",bundle.getInt("id"));
                fragment.setFragmentCallback(this::onDataSent);
                fragment.setArguments(bundle1);
                /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                fragment.show(getActivity().getSupportFragmentManager(),
                        "fragment_bottom_sheet_dailog");
            }


        });

        controller.getTherapyList();


        btn_request_booking.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking){
                if (therapy_heading.getText().toString().equalsIgnoreCase("Select Therapy")){
                    GlobalClass.ShowAlert(this.getContext(),"Alert","Select Therapy");
                }else if (!date_and_time){
                    GlobalClass.ShowAlert(this.getContext(),"Alert","Select Preferred date and time");
                }else{
                    dialog.setMessage("Please wait while we are processing your request.");
                    dialog.setCancelable(false);
                    dialog.show();
                    bottomDailogController.generalTicket(ticketModle);
                }
            }else {
                GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place your request");
            }

        });

        return view;
    }



    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        linearLayout.setVisibility(View.VISIBLE);

        if (response!=null){

            if (response.body() instanceof Spa){
                Spa spa = (Spa) response.body();
                List<Data> spaList = spa.getData();

                SpaDropDownAdapter adapter = new SpaDropDownAdapter(mContext,spaList,Position -> {

                    Log.d("pos my click", String.valueOf(Position));
                    therapy_heading.setText(spaList.get(Position).getTitle());

                    if(spaList.get(Position).getDuration() == null || spaList.get(Position).getDuration().isEmpty() || spaList.get(Position).getDuration().equals("0")){
                        therapy_heading.setText(Html.fromHtml("<font color=#000000>"+ spaList.get(Position).getTitle() +"</font> <font color=#A7A7A7>"));
                    }else{
                        therapy_heading.setText(Html.fromHtml("<font color=#000000>"+ spaList.get(Position).getTitle() +" </font> <font color=#A7A7A7>"+"("+spaList.get(Position).getDuration()+" mins)" +"</font>"));
                    }


               /* if (spaList.get(Position).getDuration() == null || spaList.get(Position).getDuration().isEmpty() || spaList.get(Position).getDuration().equals("0")){
                    therapy_time.setVisibility(View.GONE);
                }else {
                    therapy_time.setText("(" + spaList.get(Position).getDuration()+" mins"+")");
                    therapy_time.setVisibility(View.VISIBLE);
                }*/


                    img_drop_down.setVisibility(View.VISIBLE);

                    if (spaList.get(Position).getPrice() == null  || spaList.get(Position).getPrice().equals("0.00") ){
                        therapy_price.setVisibility(View.GONE);
                    }else {
                        therapy_price.setVisibility(View.VISIBLE);
                        therapy_price.setText("₹"+" "+spaList.get(Position).getPrice());
                        price = spaList.get(Position).getPrice();
                    }

                    recyclerView.setVisibility(View.GONE);


                });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            }else if (response.body() instanceof General){
                Fragment fragment1 = new OrderConfirmedFragment();
                Bundle bundle = new Bundle();
                fragment1.setArguments(bundle);
                dismissDialog();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment1).addToBackStack(null).commit();
            }

        }
    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        dismissDialog();
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }

    @Override
    public void onDataSent(TicketModle ticketModle) {
        this.ticketModle = ticketModle;
        if (ticketModle!=null){
           /* req_date_time.setText(ticketModle.getRequestDate());*/
            if (!ticketModle.getPersonalisedTime().isEmpty()) {
                req_date_time.setText(ticketModle.getPersonalisedTime());
            }
        }
    }


    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}