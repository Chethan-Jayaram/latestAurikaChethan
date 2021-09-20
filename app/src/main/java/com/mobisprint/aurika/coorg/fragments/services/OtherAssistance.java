package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.ticketing.Detail;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

public class OtherAssistance extends Fragment implements ApiListner {

    private TextView toolbar_title;
    private ImageView bt_back;
    private BottomDailogController bottomDailogController;
    private Button btn_assi_submit;
    private EditText txt_assistance;
    private TicketModle ticketModle;
    private String requestDate, reqtime;
    private int hr, min;
    private Calendar calendar;
    private List<Detail> list = new ArrayList<>();
    private Detail detail = new Detail();
    private Context mContext;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_other_assistance, container, false);

        mContext = getContext();
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        bt_back = getActivity().findViewById(R.id.naviagation_hamberger);
        bt_back.setVisibility(View.VISIBLE);
        bottomDailogController = new BottomDailogController(this);
        btn_assi_submit = view.findViewById(R.id.btn_assi_submit);
        txt_assistance = view.findViewById(R.id.txt_assistance);
        ticketModle = new TicketModle();

        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        reqtime = dtf.format(now);

        requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title", "Other Assistance"));

        btn_assi_submit.setOnClickListener(v -> {

            if (GlobalClass.user_token.isEmpty()){

                alertBox();

            }else  if(GlobalClass.user_active_booking){
                if (txt_assistance.getText().toString().isEmpty()) {
                    GlobalClass.ShowAlert(this.getContext(), "Alert", "Type the requirement");
                } else {
                    list.clear();
                    detail.setDescription(txt_assistance.getText().toString());
                    list.add(detail);
                    ticketModle.setDetails(list);
                    ticketModle.setTitle("other-assistance Ticket");
                    ticketModle.setDepartment("other-assistance");
                    ticketModle.setBooking(String.valueOf(GlobalClass.Guest_Id));
                    ticketModle.setRequestDate(requestDate);
                    ticketModle.setRequestTime(reqtime);
                    ticketModle.setRoomNumber(GlobalClass.ROOM_NO);
                    bottomDailogController.generalTicket(ticketModle);

                }
            }else{
                GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place order");

            }


        });

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response.body() != null) {
            if (response.body() instanceof General) {
                GlobalClass.ShowAlert(this.getContext(), "Alert", "Your request has been raised");
                txt_assistance.getText().clear();
            }
        }

    }

    @Override
    public void onFetchError(String error) {

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
}