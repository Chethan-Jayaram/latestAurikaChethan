package com.mobisprint.aurika.coorg.fragments.services;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mobisprint.aurika.R;

public class OtherAssistance extends Fragment {

    private TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
      View view = inflater.inflate(R.layout.fragment_other_assistance, container, false);

      toolbar_title = getActivity().findViewById(R.id.toolbar_title);


      Bundle bundle = getArguments();
      toolbar_title.setText(bundle.getString("title"));

        return view;
    }
}