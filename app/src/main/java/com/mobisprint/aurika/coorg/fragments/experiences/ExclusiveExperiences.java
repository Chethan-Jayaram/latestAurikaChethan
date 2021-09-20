package com.mobisprint.aurika.coorg.fragments.experiences;

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
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.ExclusiveExperiencesAdapter;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.controller.ExclusiveExperiencesController;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderConfirmedFragment;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.experiences.Data;
import com.mobisprint.aurika.coorg.pojo.experiences.Experiences;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.ticketing.Detail;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit2.Response;


public class ExclusiveExperiences extends Fragment implements ApiListner {

    private TextView tv_exclusive_experience_desc, toolbar_title;
    private RecyclerView recyclerView;
    private Button bt_call_back;
    private Context mContext;
    private ExclusiveExperiencesController controller;
    private ImageView img_back;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;
    private TicketModle ticketModle;
    private BottomDailogController bottomDailogController;
    private List<Detail> list = new ArrayList<>();
    private Detail detail = new Detail();
    private String category, title;
    private String booking = "1";
    private String requestDate, reqtime, str_special_instruction;
    private int hr, min;
    private Calendar calendar;
    private Boolean buttonClicked = true;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_exclusive_experiences, container, false);

        tv_exclusive_experience_desc = view.findViewById(R.id.tv_exclusive_experience_desc);
        recyclerView = view.findViewById(R.id.exlusive_experiences_recycler);
        bt_call_back = view.findViewById(R.id.bt_exclusive_experiences_call_back);
        mContext = getContext();
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        calendar = Calendar.getInstance();
        hr = calendar.get(Calendar.HOUR);
        min = calendar.get(Calendar.MINUTE);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        reqtime = dtf.format(now);


        requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;
        ticketModle = new TicketModle();
        bottomDailogController = new BottomDailogController(this);

        coordinatorLayout = view.findViewById(R.id.lyt);
        coordinatorLayout.setVisibility(View.GONE);

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        category = "exclusive-experience";

        Bundle bundle = getArguments();

        toolbar_title.setText(bundle.getString("title"));

        controller = new ExclusiveExperiencesController(this);

        controller.getExperiencesMenu();

        title = category + " Ticket";


        bt_call_back.setOnClickListener(v -> {

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
            detail.setTitle("exclusive-experience");
            list.add(detail);
            ticketModle.setDetails(list);
            ticketModle.setDepartment(category);
            ticketModle.setTitle(title);
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

            if (response.body() instanceof Experiences) {
                Experiences experiences = (Experiences) response.body();
                List<Data> experiencesData = experiences.getData();
                ExclusiveExperiencesAdapter adapter = new ExclusiveExperiencesAdapter(mContext, experiencesData);
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
            } else if (response.body() instanceof General) {
                bt_call_back.setBackground(getResources().getDrawable(R.drawable.btn_clicked_background));
                bt_call_back.setText("Call back scheduled");
                bt_call_back.setTextColor(Color.parseColor("#a5a5a5"));
                buttonClicked = false;
            }


        }

    }

    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext, "Alert", error);

    }


}