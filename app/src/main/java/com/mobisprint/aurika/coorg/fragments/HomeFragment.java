package com.mobisprint.aurika.coorg.fragments;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.HomeScreenAdapter;
import com.mobisprint.aurika.coorg.controller.HomeFragmentController;
import com.mobisprint.aurika.coorg.pojo.home.Data;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.HomeAdapter;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

import retrofit2.Response;

public class HomeFragment extends Fragment implements ApiListner {

    private Toolbar toolbar_cart;
    private GridView gridView;
    private ImageView backBtn;
    private TextView toolbar_title;
    private HomeFragmentController controller;
    private Context mContext;


    private LinearLayout ll_conent;
    private ImageView img_view, img_bck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
try {

    gridView = view.findViewById(R.id.gridview);
    img_view = view.findViewById(R.id.img_view);
    img_view.setImageResource(R.drawable.homescreenbanner);
    controller = new HomeFragmentController(this);
    mContext = getContext();
    img_bck = getActivity().findViewById(R.id.naviagation_hamberger);
    img_bck.setVisibility(View.INVISIBLE);
        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Welcome to Aurika, Coorg");
    controller.getHomeIcons();
}catch (Exception e){
    e.printStackTrace();
}

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!= null) {
            try {

                Home home = (Home) response.body();
                List<Data> homeList = home.getData();
                HomeScreenAdapter homeAdapter = new HomeScreenAdapter(mContext, homeList, Position -> {


                    try {
                        Log.d("route_nme", homeList.get(Position).getMobileRoute().getRouteName());

                        Class<?> className = Class.forName(RouteName.getHomeScreenRoutes(homeList.get(Position).getMobileRoute().getRouteName()));
                        Log.d("classname", className.getName());
                        Bundle bundle = new Bundle();

                        bundle.putString("title",homeList.get(Position).getTitle());
                        Fragment fragment = (Fragment) className.newInstance();
                        fragment.setArguments(bundle);

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                });
                gridView.setAdapter(homeAdapter);


            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onFetchError(String error) {

    }
}