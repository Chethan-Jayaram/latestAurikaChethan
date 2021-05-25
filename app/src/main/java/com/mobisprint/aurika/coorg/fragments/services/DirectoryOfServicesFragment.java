package com.mobisprint.aurika.coorg.fragments.services;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.DirectoryOfServicesAdapter;
import com.mobisprint.aurika.coorg.controller.services.DirectoryOfServicesController;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Response;

public class DirectoryOfServicesFragment extends Fragment implements ApiListner {

    private ExpandableListView expandableListView;
    private DirectoryOfServicesAdapter directoryOfServicesAdapter;
    private DirectoryOfServicesController controller;
    private TextView toolbar_title;
    private ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory_of_services, container, false);

        expandableListView = view.findViewById(R.id.expandable_list);

        controller = new DirectoryOfServicesController(this);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();

        toolbar_title.setText(bundle.getString("title"));

        controller.getServices();


        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!=null){
            CoorgServicesPojo servicesPojo= (CoorgServicesPojo) response.body();
            List<Data> directory_of_service_list = servicesPojo.getData();

            directoryOfServicesAdapter = new DirectoryOfServicesAdapter(getContext(),directory_of_service_list);
            expandableListView.setAdapter(directoryOfServicesAdapter);
        }

    }

    @Override
    public void onFetchError(String error) {

    }
}