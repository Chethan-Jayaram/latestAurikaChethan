package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.adapter.CoorgSleepWellAdapter;
import com.mobisprint.aurika.coorg.controller.services.CoorgSleepWellFragmentController;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.udaipur.adapter.SleepWellAdapter;

import java.util.List;

import retrofit2.Response;


public class CoorgSleepWellFragment extends Fragment implements ApiListner {

    private TextView tv_sleepwell_desc,toolbar_title;
    private CoorgSleepWellFragmentController coorgSleepWellFragmentController;
    private ExpandableListView sleepwell_list;
    private Context mContext;
    private ImageView img_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_sleep_well, container, false);

        tv_sleepwell_desc = view.findViewById(R.id.tv_coorg_sleep_well_desc);
        coorgSleepWellFragmentController = new CoorgSleepWellFragmentController(this);
        sleepwell_list = view.findViewById(R.id.sleepwell_list);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        Bundle bundle = getArguments();
        tv_sleepwell_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("title"));

        coorgSleepWellFragmentController.getServices();

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!= null){

            CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
            List<Data> sleepWellList = servicesPojo.getData();
            CoorgSleepWellAdapter adapter = new CoorgSleepWellAdapter(getContext(),sleepWellList);
            sleepwell_list.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {

    }
}