package com.mobisprint.aurika.coorg.fragments.sightseeing;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.CoorgSightSeeingAdapter;
import com.mobisprint.aurika.coorg.controller.CoorgSightSeeingController;
import com.mobisprint.aurika.coorg.pojo.sightseeing.Data;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import retrofit2.Response;

public class CoorgSightSeeing extends Fragment implements ApiListner {


    private TextView tv_sight_seeing_desc,toolbar_title;
    private RecyclerView sight_seeing_recycler;
    private Button bt_sight_seeing_call_back;
    private CoorgSightSeeingController controller;
    private Context mContext;
    private ImageView img_back;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_coorg_sight_seeing, container, false);

       tv_sight_seeing_desc = view.findViewById(R.id.tv_sight_seeing_desc);
       sight_seeing_recycler = view.findViewById(R.id.sight_seeing_recycler);
       bt_sight_seeing_call_back = view.findViewById(R.id.bt_sight_seeing_call_back);
       controller = new CoorgSightSeeingController(this);
       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);


       Bundle bundle = getArguments();
       toolbar_title.setText(bundle.getString("title"));

       controller.getSightSeeing();


        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!= null){

            SightSeeing service = (SightSeeing) response.body();
            List<Data> dataList = service.getData();

            CoorgSightSeeingAdapter adapter = new CoorgSightSeeingAdapter(mContext,dataList);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            sight_seeing_recycler.setLayoutManager(layoutManager);
            sight_seeing_recycler.setAdapter(adapter);


        }


    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}
