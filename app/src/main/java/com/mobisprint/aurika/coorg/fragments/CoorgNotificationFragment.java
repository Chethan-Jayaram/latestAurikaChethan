package com.mobisprint.aurika.coorg.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.fragments.services.OtherAssistance;


public class CoorgNotificationFragment extends Fragment {

    private TextView toolbar_title,tv_other_assisatance;
    private ImageView navigation_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_coorg_notification, container, false);


       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       navigation_back = getActivity().findViewById(R.id.naviagation_hamberger);
       navigation_back.setVisibility(View.VISIBLE);
       toolbar_title.setText("Notifications");
       tv_other_assisatance = view.findViewById(R.id.tv_other_assisatance);

       tv_other_assisatance.setOnClickListener(v -> {

         /*  Fragment fragment = new OtherAssistance();
           getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container,fragment).addToBackStack(null).commit();

*/       });

       return view;
    }
}