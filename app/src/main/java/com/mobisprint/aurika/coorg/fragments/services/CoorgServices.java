package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.ServicesAdapter;
import com.mobisprint.aurika.coorg.controller.services.CoorgServicesController;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.ServicesList;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

import retrofit2.Response;


public class CoorgServices extends Fragment implements ApiListner {

    private RecyclerView services_recyclerview;
    private CoorgServicesController coorgServicesController;
    private Context mContext;
    private TextView toolbar_title;
    private ImageView img_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_services, container, false);

        mContext = view.getContext();
        coorgServicesController = new CoorgServicesController(this);
        services_recyclerview = view.findViewById(R.id.services_recyclerview);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title"));


        coorgServicesController.getServices();


        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response != null) {
            CoorgServicesPojo services = (CoorgServicesPojo) response.body();

            List<Data> servicesList = services.getData();

            ServicesAdapter adapter = new ServicesAdapter(servicesList,Position -> {
                try {

                    Class<?> className = Class.forName(RouteName.getCoorgServiceRoutes(servicesList.get(Position).getRouteName().getRouteName()));
                    Bundle bundle = new Bundle();
                    bundle.putString("title",servicesList.get(Position).getTitle());
                    bundle.putString("desc",servicesList.get(Position).getDescription());



                    Fragment fragment = (Fragment) className.newInstance();
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container,fragment).addToBackStack(null).commit();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            services_recyclerview.setLayoutManager(layoutManager);
            services_recyclerview.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        GlobalClass.ShowAlert(mContext,"Alert",error);
    }
}