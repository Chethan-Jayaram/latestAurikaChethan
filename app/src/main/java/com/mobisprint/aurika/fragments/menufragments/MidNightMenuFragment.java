package com.mobisprint.aurika.fragments.menufragments;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.adapter.BreakfastMenuListnerAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MenuListner;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Item__;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;

public class MidNightMenuFragment  extends Fragment {

    private TextView tv_MidNight_assistance;
    private TextView tv_MidNight_timings;
    private TextView tv_night_menu_desc;
    private List<MenuListner> mMenuList;
    private TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_midnight_menu, container, false);
        try {
            Context context = view.getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            tv_MidNight_assistance = view.findViewById(R.id.tv_MidNight_assistance);
            tv_MidNight_timings = view.findViewById(R.id.tv_MidNight_timings);
            RecyclerView midNight_menu_recycler = view.findViewById(R.id.MidNight_menu_recycler);
            tv_night_menu_desc = view.findViewById(R.id.tv_night_menu_desc);
            backBtn.setVisibility(View.VISIBLE);
            parsejson();
            BreakfastMenuListnerAdapter adapter = new BreakfastMenuListnerAdapter(mMenuList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            midNight_menu_recycler.setLayoutManager(mLayoutManager);
            midNight_menu_recycler.setHasFixedSize(true);
            midNight_menu_recycler.setAdapter(adapter);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing generalPojo = gson.fromJson(GlobalClass.APPDATA,Testing.class);
            List<AppDatum> appDatum = generalPojo.getAppData();

            tv_MidNight_timings.setText(appDatum.get(0).getItems().get(3).getItems().get(5).getTiming());
            tv_MidNight_assistance.setText(appDatum.get(0).getItems().get(3).getItems().get(5).getAssistance());
            tv_night_menu_desc.setText(appDatum.get(0).getItems().get(3).getItems().get(5).getMenuDescription());
            toolbar_title.setText(appDatum.get(0).getItems().get(3).getItems().get(5).getHeaderTitle());
            List<Item__> item__=appDatum.get(0).getItems().get(3).getItems().get(5).getItems();

            mMenuList= new ArrayList<>();
            for(int i=0;i<item__.size();i++){
                mMenuList.add(item__.get(i));
                for(int j=0;j<item__.get(i).getItems().size();j++){
                    if(item__.get(i).getItems().get(j).getPriceList()==null){
                        mMenuList.add(item__.get(i).getItems().get(j));
                    }
                    else{
                        mMenuList.add(item__.get(i).getItems().get(j));
                        mMenuList.addAll(item__.get(i).getItems().get(j).getPriceList());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
