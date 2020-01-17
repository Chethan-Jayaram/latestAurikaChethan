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
import com.mobisprint.aurika.adapter.SaloonAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MenuListner;
import com.mobisprint.aurika.pojo.testing.AppDatum;


import com.mobisprint.aurika.pojo.testing.Menu;
import com.mobisprint.aurika.pojo.testing.Testing;


import java.util.ArrayList;
import java.util.List;

public class SaloonMenuFragment extends Fragment {

    private TextView tv_spa_assistance,tv_spa_time,tv_spa_loc;
    private List<MenuListner> mMenuList;
    private TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_saloon_menu, container, false);
        try {
            Context context = view.getContext();
             toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            tv_spa_assistance = view.findViewById(R.id.tv_spa_assistance);
            tv_spa_time = view.findViewById(R.id.tv_spa_time);
            tv_spa_loc = view.findViewById(R.id.tv_spa_loc);
            RecyclerView saloom_menu_recycler = view.findViewById(R.id.saloom_menu_recycler);
            backBtn.setVisibility(View.VISIBLE);
            parsejson();
            SaloonAdapter adapter = new SaloonAdapter(mMenuList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            saloom_menu_recycler.setLayoutManager(mLayoutManager);
            saloom_menu_recycler.setHasFixedSize(true);
            saloom_menu_recycler.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing generalPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = generalPojo.getAppData();

            tv_spa_loc.setText(appDatum.get(0).getItems().get(5).getLocation());
            tv_spa_time.setText(appDatum.get(0).getItems().get(5).getTiming());
            tv_spa_assistance.setText(appDatum.get(0).getItems().get(5).getAssistance());
            toolbar_title.setText(appDatum.get(0).getItems().get(5).getItems().get(1).getMenuItem());
            List<Menu> item__ = appDatum.get(0).getItems().get(5).getItems().get(1).getMenu();
            mMenuList = new ArrayList<>();
            for (int i = 0; i < item__.size(); i++) {
                mMenuList.add(item__.get(i));
                mMenuList.addAll(item__.get(i).getItems());

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}

