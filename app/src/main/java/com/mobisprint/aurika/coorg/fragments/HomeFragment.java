package com.mobisprint.aurika.coorg.fragments;

import android.content.Context;
import android.icu.text.Transliterator;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.HomeScreenAdapter;
import com.mobisprint.aurika.coorg.controller.GuestReservationController;
import com.mobisprint.aurika.coorg.controller.HomeFragmentController;
import com.mobisprint.aurika.coorg.pojo.home.Data;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.reservation.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.reservation.GuestReservation;
import com.mobisprint.aurika.coorg.pojo.reservation.Room;
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
    private LinearLayout lyt;
    private ProgressBar progressBar;


    private ImageView img_view, img_bck;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view  = inflater.inflate(R.layout.fragment_home, container, false);
try {

    gridView = view.findViewById(R.id.gridview);
    img_view = view.findViewById(R.id.img_view);
    img_view.setImageResource(R.drawable.home_screen_coorg);
    controller = new HomeFragmentController(this);
    lyt = view.findViewById(R.id.ll_conent);
    progressBar = view.findViewById(R.id.progress_bar);
    progressBar.setVisibility(View.GONE);
    lyt.setVisibility(View.GONE);
    mContext = getContext();
    img_bck = getActivity().findViewById(R.id.naviagation_hamberger);
    img_bck.setVisibility(View.INVISIBLE);
        toolbar_title=getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Welcome to Aurika, Coorg");

}catch (Exception e){
    e.printStackTrace();
}

        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        controller.getHomeIcons();
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response!= null) {


            if (response.body() instanceof Home){
                try {

                    Home home = (Home) response.body();
                    List<Data> homeList = home.getData();
                    HomeScreenAdapter homeAdapter = new HomeScreenAdapter(mContext, homeList, Position -> {


                        try {

                            Class<?> className = Class.forName(RouteName.getHomeScreenRoutes(homeList.get(Position).getMobileRoute().getRouteName()));
                            Log.d("classname", className.getName());
                            Bundle bundle = new Bundle();

                            bundle.putString("title",homeList.get(Position).getTitle());
                            Fragment fragment = (Fragment) className.newInstance();
                            fragment.setArguments(bundle);


                        /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                        fragmentTransaction.replace(R.id.fragment_coorg_container, fragment).addToBackStack(null);
                        fragmentTransaction.commit();*/

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
    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}