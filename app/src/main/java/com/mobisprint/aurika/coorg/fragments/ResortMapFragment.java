
package com.mobisprint.aurika.coorg.fragments;

import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.ResortMapController;
import com.mobisprint.aurika.coorg.pojo.resortmap.Data;
import com.mobisprint.aurika.coorg.pojo.resortmap.ResortMap;
import com.mobisprint.aurika.coorg.pojo.winedine.WineAndDine;
import com.mobisprint.aurika.helper.ApiListner;

import java.util.List;

import retrofit2.Response;


public class ResortMapFragment extends Fragment implements ApiListner {

    private ImageView img_resort_map;
    private ResortMapController controller;
    private TextView toolbar_title;
    private ImageView img_back;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);*/
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_resort_map, container, false);


        img_resort_map = view.findViewById(R.id.img_resort_map);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        Bundle bundle = getArguments();

        toolbar_title.setText(bundle.getString("title"));
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        controller = new ResortMapController(this);

        controller.getData();

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!= null){

            ResortMap resortMapServices = (ResortMap) response.body();
            List<Data> resortMapImage = resortMapServices.getData();
            Glide.with(getContext()).load(resortMapImage.get(0).getImage()).into(img_resort_map);
        }
    }

    @Override
    public void onFetchError(String error) {

    }


    @Override
    public void onStop() {
        super.onStop();
        /*getActivity().setRequestedOrientation(
                ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);*/
    }
}
