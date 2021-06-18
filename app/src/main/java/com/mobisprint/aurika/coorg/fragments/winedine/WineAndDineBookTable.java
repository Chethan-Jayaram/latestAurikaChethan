package com.mobisprint.aurika.coorg.fragments.winedine;

import android.app.DatePickerDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.mobisprint.aurika.R;

import java.util.Calendar;


public class WineAndDineBookTable extends Fragment {

    private TextView tv_selected_wine_dine_title,prefered_time_wine_and_dine,toolbar_title;
    private ImageView img_selected_wine_and_dine;
    private ImageView img_back;
    private LinearLayout lyt;
    private ProgressBar progressBar;
    private Context mContext;
    private RelativeLayout img_select_time;


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

        toolbar_title.setText("Book a Table");

        img_select_time = view.findViewById(R.id.img_select_time);

        img_select_time.setOnClickListener(v -> {
            showBottomSheetDialog();
        });

        Bundle bundle = getArguments();

        tv_selected_wine_dine_title.setText(bundle.getString("title"));
        Glide.with(getContext()).load(bundle.getString("img")).centerCrop().into(img_selected_wine_and_dine);

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

        LinearLayout lyt_calendar = bottomSheetDialog.findViewById(R.id.lyt_calendar);
        LinearLayout lyt_select_date = bottomSheetDialog.findViewById(R.id.lyt_select_date);
        lyt_select_date.setVisibility(View.VISIBLE);

        Button bt_back = bottomSheetDialog.findViewById(R.id.bt_back);
        Button bt_save = bottomSheetDialog.findViewById(R.id.bt_save);

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
            bt_tomorrow.setTextColor(Color.BLACK);


        });

        bt_tomorrow.setOnClickListener(v -> {

            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.custom_purple));
            bt_tomorrow.setTextColor(Color.WHITE);
            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);

        });


        select_date.setOnClickListener(v -> {

            lyt_calendar.setVisibility(View.VISIBLE);
            lyt_select_date.setVisibility(View.GONE);

            Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);


            /*DatePickerDialog datePickerDialog = new DatePickerDialog(mContext,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {


                        }
                    }, year, month, dayOfMonth);
            datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
            datePickerDialog.show();*/



            bt_today.setBackgroundColor(getResources().getColor(R.color.white));
            bt_today.setTextColor(Color.BLACK);
            bt_tomorrow.setBackgroundColor(getResources().getColor(R.color.white));
            bt_tomorrow.setTextColor(Color.BLACK);

        });


        bottomSheetDialog.show();

    }
}