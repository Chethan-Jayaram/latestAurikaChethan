package com.mobisprint.aurika.fragments.sectionsfragment;


import android.content.Context;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.adapter.SpaSalonAdapter;

import com.mobisprint.aurika.fragments.menufragments.SaloonMenuFragment;
import com.mobisprint.aurika.fragments.menufragments.TheSpaMenuFragment;
import com.mobisprint.aurika.helper.GlobalClass;

import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.Testing;



import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class SpaSaloonFragment extends Fragment {


  private TextView toolbar_title,tv_salon_spa_desc,tv_spa_salon_assistance,tv_spa_salon_loc,tv_spa_salon_time;
  private ImageView backBtn;
    private String subtitle,location,timing,assistance;
    private List<Item_> item_ = new ArrayList<>();
    private SpaSalonAdapter adapter;
    private Context context;
    private RecyclerView spa_salon_recycler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa_saloon, container, false);
        context=view.getContext();
        toolbar_title =  getActivity().findViewById(R.id.toolbar_title);
        tv_salon_spa_desc=view.findViewById(R.id.tv_salon_spa_desc);
        backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
        spa_salon_recycler=view.findViewById(R.id.spa_salon_recycler);
        tv_spa_salon_assistance=view.findViewById(R.id.tv_spa_salon_assistance);
        tv_spa_salon_loc=view.findViewById(R.id.tv_spa_salon_loc);
        tv_spa_salon_time=view.findViewById(R.id.tv_spa_salon_time);
        toolbar_title.setText("Araya Spa and Saloon");
        parsejson();


        adapter = new SpaSalonAdapter(item_, S1 -> ChangeFragment(S1));
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        spa_salon_recycler.setLayoutManager(mLayoutManager);
        spa_salon_recycler.setItemAnimator(new DefaultItemAnimator());
        spa_salon_recycler.setAdapter(adapter);


        backBtn.setVisibility(View.VISIBLE);


        tv_salon_spa_desc.setText(subtitle);
        tv_spa_salon_assistance.setText(assistance);
        tv_spa_salon_loc.setText(location);
        tv_spa_salon_time.setText(timing);

        return view;
    }

    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing spaSalonPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = spaSalonPojo.getAppData();
            item_ = appDatum.get(0).getItems().get(5).getItems();
            subtitle = appDatum.get(0).getItems().get(5).getSubTitle();
            location= appDatum.get(0).getItems().get(5).getLocation();
            timing= appDatum.get(0).getItems().get(5).getTiming();
            assistance= appDatum.get(0).getItems().get(5).getAssistance();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void ChangeFragment(String str) {
        Fragment fragment;

        switch (str) {
            case "The Spa Menu":
                fragment = new TheSpaMenuFragment();// Statements
                break;
            case "The Salon Menu":
                fragment = new SaloonMenuFragment();
                break;


            default:
                throw new IllegalStateException("Unexpected value: " + str);
        }
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}
