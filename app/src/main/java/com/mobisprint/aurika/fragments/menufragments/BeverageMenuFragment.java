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

public class BeverageMenuFragment extends Fragment {

    private TextView toolbar_title,tv_beverages_assistance,tv_beverages_timings,tv_beverage_menu_desc;
    private ImageView backBtn;
    private BreakfastMenuListnerAdapter adapter;
    private RecyclerView beverages_menu_recycler;
    Context context;
    private List<MenuListner> mMenuList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_beverage_menu, container, false);
        context=view.getContext();
        toolbar_title =  getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("The Beverage List");
        backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
        tv_beverages_assistance=view.findViewById(R.id.tv_beverages_assistance);
        tv_beverages_timings=view.findViewById(R.id.tv_beverages_timings);
        beverages_menu_recycler=view.findViewById(R.id.beverages_menu_recycler);
        tv_beverage_menu_desc=view.findViewById(R.id.tv_beverage_menu_desc);
        backBtn.setVisibility(View.VISIBLE);
        parsejson();
        adapter = new BreakfastMenuListnerAdapter(mMenuList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
        beverages_menu_recycler.setLayoutManager(mLayoutManager);
        beverages_menu_recycler.setHasFixedSize(true);
        beverages_menu_recycler.setAdapter(adapter);
        return view;
    }


    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing generalPojo = gson.fromJson(GlobalClass.APPDATA,Testing.class);
            List<AppDatum> appDatum = generalPojo.getAppData();

            tv_beverages_timings.setText(appDatum.get(0).getItems().get(3).getItems().get(4).getTiming());
            tv_beverages_assistance.setText(appDatum.get(0).getItems().get(3).getItems().get(4).getAssistance());
            tv_beverage_menu_desc.setText(appDatum.get(0).getItems().get(3).getItems().get(4).getMenuDescription());
            List<Item__> item__=appDatum.get(0).getItems().get(3).getItems().get(4).getItems();

            mMenuList= new ArrayList<>();
            for(int i=0;i<item__.size();i++){
                mMenuList.add(item__.get(i));
                for(int j=0;j<item__.get(i).getItems().size();j++){
                    if(item__.get(i).getItems().get(j).getPriceList()==null){
                        mMenuList.add(item__.get(i).getItems().get(j));
                    }
                    else{
                        mMenuList.add(item__.get(i).getItems().get(j));
                        for(int k=0;k<item__.get(i).getItems().get(j).getPriceList().size();k++){
                            mMenuList.add(item__.get(i).getItems().get(j).getPriceList().get(k));
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

}
