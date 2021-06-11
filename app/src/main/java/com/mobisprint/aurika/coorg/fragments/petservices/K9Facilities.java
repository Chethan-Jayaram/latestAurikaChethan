package com.mobisprint.aurika.coorg.fragments.petservices;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.K9FacilitesAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9FacilitiesController;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import retrofit2.Response;


public class K9Facilities extends Fragment implements ApiListner {

    private ExpandableListView expandableListView;
    private K9FacilitiesController controller;
    private TextView toolbar_title,tv_k9_facilities_desc;
    private ImageView img_back;
    private Context mContext;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_k9_facilities, container, false);


       expandableListView = view.findViewById(R.id.expandable_list);
       controller = new K9FacilitiesController(this);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        tv_k9_facilities_desc = view.findViewById(R.id.tv_k9_facilities_desc);
        mContext = getContext();
        lyt= view.findViewById(R.id.lyt);
        progressBar = view.findViewById(R.id.progress_bar);
        lyt.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
       toolbar_title = getActivity().findViewById(R.id.toolbar_title);

       Bundle bundle =getArguments();

       toolbar_title.setText(bundle.getString("sub_title"));
       tv_k9_facilities_desc.setText(bundle.getString("desc"));

       controller.getFacilities();


        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);
        if (response!= null){

            PetServices services = (PetServices) response.body();
            List<K9Data> amenitiesList = services.getData();
            K9FacilitesAdapter adapter = new K9FacilitesAdapter(getContext(),amenitiesList);
            expandableListView.setAdapter(adapter);


        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(getContext(),"Alert",error);
    }
}