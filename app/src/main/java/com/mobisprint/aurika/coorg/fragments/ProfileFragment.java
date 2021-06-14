package com.mobisprint.aurika.coorg.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.mobisprint.aurika.R;


public class ProfileFragment extends Fragment {

    private TextView toolbar_title;
    private EditText mobile_num,email_id;
    private ImageView img_back,img_ph_num_clear,img_email_clear,img_profile;
    private String[] gender = {"Male","Female","Other"};
    private String[] maritual_status = {"Single","Married"};
    private Spinner maritual_ststus_spinner,gender_spinner;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");

        img_profile = view.findViewById(R.id.img_profile);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        img_email_clear = view.findViewById(R.id.img_clear_email);
        img_ph_num_clear = view.findViewById(R.id.img_clear_mobile);

        gender_spinner = view.findViewById(R.id.gender_spinner);
        maritual_ststus_spinner = view.findViewById(R.id.maritual_ststus_spinner);

        email_id = view.findViewById(R.id.email_id);
        mobile_num = view.findViewById(R.id.mobile_num);


        mobile_num.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {

                if (!mobile_num.getText().toString().isEmpty()){
                    img_ph_num_clear.setVisibility(View.VISIBLE);
                }
                else{
                    img_ph_num_clear.setVisibility(View.GONE);
                }
                return false;
            }
        });

        img_ph_num_clear.setOnClickListener(v -> {
            mobile_num.getText().clear();
        });

        img_profile.setOnClickListener(v -> {
            Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                    android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(pickPhoto , 1);

        });


        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,gender);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(arrayAdapter);


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,maritual_status);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        maritual_ststus_spinner.setAdapter(arrayAdapter1);

        return  view;
    }
}