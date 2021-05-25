package com.mobisprint.aurika.coorg.fragments.recreationalfacilities;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.RecreationalFacilitiesAdapter;
import com.mobisprint.aurika.coorg.controller.RecreationalFacilitiesController;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.recreational.Data;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreation;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreational;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.adapter.RecreationAdapter;

import java.util.List;

import retrofit2.Response;

public class RecreationalFacilities extends Fragment implements ApiListner {

    private RecyclerView recreational_recycler;
    private RecreationalFacilitiesController controller;
    private Context mContext;
    private TextView toolbar_title;
    private ImageView img_back;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recreational_facilities, container, false);

        recreational_recycler = view.findViewById(R.id.coorg_recreation_recycler);
        controller = new RecreationalFacilitiesController(this);
        mContext = getContext();
        controller.getRecreationalFacilities();
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();

        toolbar_title.setText(bundle.getString("title"));

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response != null){

            Recreational recreational = (Recreational) response.body();
            List<Data> recreationalList = recreational.getData();
            RecreationalFacilitiesAdapter adapter = new RecreationalFacilitiesAdapter(mContext,recreationalList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recreational_recycler.setLayoutManager(layoutManager);
            recreational_recycler.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(), "Alert", error);

    }
}