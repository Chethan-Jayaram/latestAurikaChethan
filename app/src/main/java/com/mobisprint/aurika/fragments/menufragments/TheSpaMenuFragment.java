package com.mobisprint.aurika.fragments.menufragments;

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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.adapter.SpaMenuAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MenuListner;


import com.mobisprint.aurika.pojo.testing.AppDatum;

import com.mobisprint.aurika.pojo.testing.Menu;

import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;

public class TheSpaMenuFragment extends Fragment {

    private TextView tv_spa_assistance, tv_spa_time, tv_spa_loc;
    private List<MenuListner> mMenuList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa_menu, container, false);
        Context context = view.getContext();
        TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("The Spa Menu");
        ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
        tv_spa_assistance = view.findViewById(R.id.tv_spa_assistance);
        tv_spa_time = view.findViewById(R.id.tv_spa_time);
        tv_spa_loc = view.findViewById(R.id.tv_spa_loc);
        RecyclerView spa_menu_recycler = view.findViewById(R.id.spa_menu_recycler);
        backBtn.setVisibility(View.VISIBLE);
        parsejson();
        SpaMenuAdapter adapter = new SpaMenuAdapter(mMenuList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        spa_menu_recycler.setLayoutManager(mLayoutManager);
        spa_menu_recycler.setHasFixedSize(true);
        spa_menu_recycler.setAdapter(adapter);
        return view;
    }

    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing spaSalonPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = spaSalonPojo.getAppData();
            tv_spa_loc.setText(appDatum.get(0).getItems().get(5).getLocation());
            tv_spa_time.setText(appDatum.get(0).getItems().get(5).getTiming());
            tv_spa_assistance.setText(appDatum.get(0).getItems().get(5).getAssistance());


            List<Menu> menu = appDatum.get(0).getItems().get(5).getItems().get(0).getMenu();

            mMenuList = new ArrayList<>();
            for (int i = 0; i < menu.size(); i++) {
                mMenuList.add(menu.get(i));
                for (int j = 0; j < menu.get(i).getItems().size(); j++) {
                    if (menu.get(i).getItems().get(j).getPriceList() == null) {
                        mMenuList.add(menu.get(i).getItems().get(j));
                    } else {
                        mMenuList.add(menu.get(i).getItems().get(j));
                        mMenuList.addAll(menu.get(i).getItems().get(j).getPriceList());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
