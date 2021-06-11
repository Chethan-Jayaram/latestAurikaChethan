package com.mobisprint.aurika.coorg.fragments.spa;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import com.mobisprint.aurika.coorg.controller.spa.BookAnAppointmentController;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.Calendar;
import java.util.List;

import retrofit2.Response;

public class SpaBookAnAppointment extends Fragment implements ApiListner {

    private TextView toolbar_title,tv_spa_title,therapy_heading,therapy_time,therapy_price;
    private ImageView img_spa,img_drop_down,img_time;
    private Context mContext;
    private BookAnAppointmentController controller;
    private RecyclerView recyclerView;
    private RelativeLayout lyt_select_therapy,lyt_main;
    private LinearLayout linearLayout;
    private ProgressBar progressBar;
    private Boolean chech_status = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa_book_an_appointment, container, false);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Book an appointment");

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

        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);


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
            showBottomSheetDialog();
        });

        controller.getTherapyList();

        return view;
    }

    private void showBottomSheetDialog() {

        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(mContext);
        bottomSheetDialog.setContentView(R.layout.bottom_dailog_box);


        Button bt_today = bottomSheetDialog.findViewById(R.id.bt_today);
        Button bt_tomorrow = bottomSheetDialog.findViewById(R.id.bt_tomorrow);
        CardView select_date = bottomSheetDialog.findViewById(R.id.select_date);
        ImageView img_up_hr = bottomSheetDialog.findViewById(R.id.img_up_hr);
        ImageView img_down_hr = bottomSheetDialog.findViewById(R.id.img_down_hr);

        ImageView img_up_min = bottomSheetDialog.findViewById(R.id.img_up_min);
        ImageView img_down_min = bottomSheetDialog.findViewById(R.id.img_down_min);

        TextView tv_hr = bottomSheetDialog.findViewById(R.id.tv_hr);
        TextView tv_min = bottomSheetDialog.findViewById(R.id.tv_min);




        bt_today.setOnClickListener(v -> {

            bt_today.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_today.setTextColor(Color.WHITE);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.BLACK);


        });

        bt_tomorrow.setOnClickListener(v -> {

            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_tomorrow.setTextColor(Color.WHITE);
            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);

        });


        select_date.setOnClickListener(v -> {

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


            DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();



            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.BLACK);

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
        linearLayout.setVisibility(View.VISIBLE);

        if (response!=null){
            Spa spa = (Spa) response.body();
            List<Data> spaList = spa.getData();

            SpaDropDownAdapter adapter = new SpaDropDownAdapter(mContext,spaList,Position -> {

                Log.d("pos my click", String.valueOf(Position));
                therapy_heading.setText(spaList.get(Position).getTitle());


                if (spaList.get(Position).getDuration() == null || spaList.get(Position).getDuration().isEmpty() || spaList.get(Position).getDuration().equals("0")){
                    therapy_time.setVisibility(View.GONE);
                }else {
                    therapy_time.setText("(" + spaList.get(Position).getDuration()+" mins"+")");
                    therapy_time.setVisibility(View.VISIBLE);
                }


                img_drop_down.setVisibility(View.GONE);

                if (spaList.get(Position).getPrice() == null  || spaList.get(Position).getPrice().equals("0.00") ){
                    therapy_price.setVisibility(View.GONE);
                }else {
                    therapy_price.setVisibility(View.VISIBLE);
                    therapy_price.setText("₹"+" "+spaList.get(Position).getPrice());
                }

                recyclerView.setVisibility(View.GONE);


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