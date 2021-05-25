package com.mobisprint.aurika.coorg.fragments.winedine;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;


public class WineAndDineBookTable extends Fragment {

    private TextView tv_selected_wine_dine_title,prefered_time_wine_and_dine,toolbar_title;
    private ImageView img_selected_wine_and_dine;
    private ImageView img_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_wine_and_dine_book_table, container, false);


        tv_selected_wine_dine_title = view.findViewById(R.id.tv_selected_wine_dine_title);
        img_selected_wine_and_dine = view.findViewById(R.id.img_selected_wine_and_dine);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        toolbar_title.setText("Book a Table");


        Bundle bundle = getArguments();

        tv_selected_wine_dine_title.setText(bundle.getString("title"));
        Glide.with(getContext()).load(bundle.getString("img")).centerCrop().into(img_selected_wine_and_dine);

        return view;
    }
}