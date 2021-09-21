package com.mobisprint.aurika.coorg.fragments.winedine;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.common.internal.GmsLogger;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.controller.WineAndDineBookTableController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderConfirmedFragment;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.Calendar;

import retrofit2.Response;


public class WineAndDineBookTable extends Fragment implements GlobalClass.FragmentCallback, ApiListner {

    private TextView tv_selected_wine_dine_title,prefered_time_wine_and_dine,toolbar_title,tv_prtreferred_date_and_time;
    private ImageView img_selected_wine_and_dine;
    private ImageView img_back;
    private LinearLayout lyt;
    private ProgressBar progressBar;
    private Context mContext;
    private RelativeLayout img_select_time;
    private EditText number_of_guest;
    private Integer count = 0;
    private TicketModle ticketModle;
    private Button btn_reserve_table;
    private BottomDailogController controller;
    private Boolean date_time = false;
    private ProgressDialog dialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wine_and_dine_book_table, container, false);


        tv_selected_wine_dine_title = view.findViewById(R.id.tv_selected_wine_dine_title);
        img_selected_wine_and_dine = view.findViewById(R.id.img_selected_wine_and_dine);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        progressBar = view.findViewById(R.id.progress_bar);
        lyt = view.findViewById(R.id.lyt);
        progressBar.setVisibility(View.GONE);
        mContext = getContext();
        tv_prtreferred_date_and_time = view.findViewById(R.id.tv_prtreferred_date_and_time);
        btn_reserve_table = view.findViewById(R.id.btn_reserve_table);
        controller = new BottomDailogController(this);

        number_of_guest = view.findViewById(R.id.number_of_guest);
        toolbar_title.setText("Book a Table");

        img_select_time = view.findViewById(R.id.img_select_time);
        dialog = new ProgressDialog(mContext);

        Bundle bundle = getArguments();

        img_select_time.setOnClickListener(v -> {

            /*showBottomSheetDialog();*/

            if (number_of_guest.getText().toString().isEmpty()){
                GlobalClass.ShowAlert(this.getContext(),"Alert","Enter number of guests");
            }else{
                date_time = true;
                BottomDailogFragment fragment = new BottomDailogFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("Category","wine-and-dine");
                bundle1.putInt("GuestCount", Integer.parseInt(number_of_guest.getText().toString().trim()));
                bundle1.putString("title",bundle.getString("title"));
                bundle1.putInt("item_id",bundle.getInt("id"));
                fragment.setFragmentCallback(this::onDataSent);
                fragment.setArguments(bundle1);
                /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                fragment.show(getActivity().getSupportFragmentManager(),
                        "fragment_bottom_sheet_dailog");
            }


        });

        tv_selected_wine_dine_title.setText(bundle.getString("title"));
        Glide.with(getContext()).load(bundle.getString("img")).centerCrop().into(img_selected_wine_and_dine);


        number_of_guest.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() ==1){
                    count = Integer.parseInt(number_of_guest.getText().toString());
                    if (count>6){
                        GlobalClass.ShowAlert(mContext,"Alert","Guest count cannot exceed more than 6");
                        number_of_guest.getText().clear();
                    }
                }
            }
        });


        btn_reserve_table.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking){
                if (number_of_guest.getText().toString().isEmpty() || number_of_guest.getText().toString().equalsIgnoreCase("0") || number_of_guest.getText().toString().trim().equalsIgnoreCase("")){
                    GlobalClass.ShowAlert(this.getContext(),"Alert","Enter number of guests");
                }else if (!date_time){
                    GlobalClass.ShowAlert(this.getContext(),"Alert","Select Preferred date and time");
                }else{
                    dialog.setMessage("Please wait while we are processing your request.");
                    dialog.setCancelable(false);
                    dialog.show();
                    controller.wineDineTicket(ticketModle);
                }
            }else {
                GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place your request");
            }


        });



        return view;
    }


    @Override
    public void onDataSent(TicketModle ticketModle) {
        this.ticketModle = ticketModle;
        if (ticketModle!=null){
            /*tv_prtreferred_date_and_time.setText(ticketModle.getRequestDate());*/

            if (!ticketModle.getPersonalisedTime().isEmpty()) {
                tv_prtreferred_date_and_time.setText(ticketModle.getPersonalisedTime());
            }
        }

    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null){

            if (response.body() instanceof General){
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
        GlobalClass.ShowAlert(mContext,"Alert",error);
    }

    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}