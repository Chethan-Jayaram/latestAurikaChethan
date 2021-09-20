package com.mobisprint.aurika.coorg.fragments.sightseeing;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.CoorgSightSeeingAdapter;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.controller.CoorgSightSeeingController;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.sightseeing.Data;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
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

public class CoorgSightSeeing extends Fragment implements ApiListner {

    private TextView tv_sight_seeing_desc, toolbar_title;
    private RecyclerView sight_seeing_recycler;
    private Button bt_sight_seeing_call_back;
    private CoorgSightSeeingController controller;
    private Context mContext;
    private ImageView img_back;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private TicketModle ticketModle;
    private BottomDailogController bottomDailogController;
    private List<Detail> list = new ArrayList<>();
    private Detail detail = new Detail();
    private String category;
    private String booking = "1", str_special_instruction;
    private String requestDate, reqtime;
    private int hr, min;
    private Calendar calendar;
    private Boolean buttonClicked = true;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_sight_seeing, container, false);

        try {

            tv_sight_seeing_desc = view.findViewById(R.id.tv_sight_seeing_desc);
            sight_seeing_recycler = view.findViewById(R.id.sight_seeing_recycler);
            bt_sight_seeing_call_back = view.findViewById(R.id.bt_sight_seeing_call_back);
            controller = new CoorgSightSeeingController(this);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            mContext = getContext();
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            img_back.setVisibility(View.VISIBLE);
            ticketModle = new TicketModle();
            bottomDailogController = new BottomDailogController(this);
            coordinatorLayout = view.findViewById(R.id.lyt);
            coordinatorLayout.setVisibility(View.GONE);

            progressBar = view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);


            calendar = Calendar.getInstance();
            hr = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            reqtime = dtf.format(now);

            requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;
            ticketModle = new TicketModle();

            Bundle bundle = getArguments();
            toolbar_title.setText(bundle.getString("title"));

            category = "sightseeing";
            controller.getSightSeeing();

            bt_sight_seeing_call_back.setOnClickListener(v -> {

                if (GlobalClass.user_active_booking) {
                    if (buttonClicked) {
                        showBottomSheet();
                    } else {
                        GlobalClass.ShowAlert(this.getContext(), "Alert", "Ticket have already been raised");
                    }

                } else {
                    GlobalClass.ShowAlert(mContext, "Alert", "You don't have active booking to place request");
                }

            });

        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private void showBottomSheet() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottomsheet_for_sight_seeing);
        EditText special_instruction = bottomSheetDialog.findViewById(R.id.special_instruction);
        Button bt_confirm = bottomSheetDialog.findViewById(R.id.bt_confirm_order);

        bt_confirm.setOnClickListener(v -> {
            str_special_instruction = special_instruction.getText().toString();
            list.clear();
            detail.setTitle("sightseeing");
            list.add(detail);
            ticketModle.setDetails(list);
            ticketModle.setDepartment(category);
            ticketModle.setTitle(category + " Ticket");
            ticketModle.setBooking(String.valueOf(GlobalClass.Guest_Id));
            ticketModle.setRequestDate(requestDate);
            ticketModle.setRoomNumber(GlobalClass.ROOM_NO);
            ticketModle.setRequestTime(reqtime);
            ticketModle.setSpecial_instructions(str_special_instruction);
            bottomDailogController.generalTicket(ticketModle);

            bottomSheetDialog.dismiss();
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
        coordinatorLayout.setVisibility(View.VISIBLE);

        if (response != null) {

            if (response.body() instanceof SightSeeing) {
                SightSeeing service = (SightSeeing) response.body();
                List<Data> dataList = service.getData();

                CoorgSightSeeingAdapter adapter = new CoorgSightSeeingAdapter(mContext, dataList);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                sight_seeing_recycler.setLayoutManager(layoutManager);
                sight_seeing_recycler.setAdapter(adapter);

            } else if (response.body() instanceof General) {
                bt_sight_seeing_call_back.setBackground(getResources().getDrawable(R.drawable.btn_clicked_background));
                bt_sight_seeing_call_back.setText("Call back scheduled");
                bt_sight_seeing_call_back.setTextColor(Color.parseColor("#a5a5a5"));
                buttonClicked = false;
            }


        }


    }

    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(getContext(), "Alert", error);

    }
}
