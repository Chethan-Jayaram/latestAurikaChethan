package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.MiniBarAdapter;
import com.mobisprint.aurika.coorg.controller.MiniBarController;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.NonScrollExpandableListView;

import java.util.List;

import retrofit2.Response;


public class MiniBar extends Fragment implements ApiListner {

    private TextView tv_coorg_minibar_desc,toolbar_title;
    private RecyclerView mini_bar_recycler;
    private MiniBarController controller;
    private Context mContext;
    private ImageView img_back;
    private ExpandableListView expandableListView;
    private CoordinatorLayout coordinatorLayout;
    private ProgressBar progressBar;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        try {

        }catch (Exception e){
            e.printStackTrace();
        }

        View view = inflater.inflate(R.layout.fragment_mini_bar, container, false);

        tv_coorg_minibar_desc = view.findViewById(R.id.tv_coorg_minibar_desc);
        controller = new MiniBarController(this);
        mContext = getContext();
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        expandableListView = view.findViewById(R.id.mini_bar_expandable_listview);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        coordinatorLayout = view.findViewById(R.id.lyt);
        coordinatorLayout.setVisibility(View.GONE);


        Bundle bundle = getArguments();

        tv_coorg_minibar_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("title"));


        controller.getMiniBarContent();



        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);

        if (response!=null){

            CoorgServicesPojo minibar = (CoorgServicesPojo) response.body();
            List<Data> minibarList = minibar.getData();
            MiniBarAdapter adapter = new MiniBarAdapter(mContext,minibarList);
            expandableListView.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}