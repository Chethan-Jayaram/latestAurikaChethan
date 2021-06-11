package com.mobisprint.aurika.coorg.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
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
    private ImageView img_back;
    private String[] gender = {"Male","Female","Other"};
    private String[] maritual_status = {"Single","Married"};
    private Spinner maritual_ststus_spinner,gender_spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);


        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("My Profile");

        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        gender_spinner = view.findViewById(R.id.gender_spinner);
        maritual_ststus_spinner = view.findViewById(R.id.maritual_ststus_spinner);

        email_id = view.findViewById(R.id.email_id);
        mobile_num = view.findViewById(R.id.mobile_num);





        ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,gender);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        gender_spinner.setAdapter(arrayAdapter);


        ArrayAdapter arrayAdapter1 = new ArrayAdapter(getContext(),R.layout.support_simple_spinner_dropdown_item,maritual_status);
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        maritual_ststus_spinner.setAdapter(arrayAdapter1);

        return  view;
    }
}