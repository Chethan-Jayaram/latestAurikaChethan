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
import com.mobisprint.aurika.adapter.BreakfastMenuListnerAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MenuListner;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Item__;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;

public class WineListMenuFragment  extends Fragment {

    private TextView tv_wine_assistance,tv_wine_menu_desc,tv_wine_timings;
    private List<MenuListner> mMenuList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wine_menu, container, false);
        try {
            Context context = view.getContext();
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            toolbar_title.setText("The Wine List");
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            tv_wine_assistance = view.findViewById(R.id.tv_wine_assistance);
            tv_wine_timings = view.findViewById(R.id.tv_wine_timings);
            RecyclerView wine_menu_recycler = view.findViewById(R.id.wine_menu_recycler);
            tv_wine_menu_desc = view.findViewById(R.id.tv_wine_menu_desc);
            backBtn.setVisibility(View.VISIBLE);
            parsejson();
            BreakfastMenuListnerAdapter adapter = new BreakfastMenuListnerAdapter(mMenuList);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            wine_menu_recycler.setLayoutManager(mLayoutManager);
            wine_menu_recycler.setHasFixedSize(true);
            wine_menu_recycler.setAdapter(adapter);
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
            tv_wine_timings.setText(appDatum.get(0).getItems().get(3).getItems().get(3).getTiming());
            tv_wine_assistance.setText(appDatum.get(0).getItems().get(3).getItems().get(3).getAssistance());
            tv_wine_menu_desc.setText(appDatum.get(0).getItems().get(3).getItems().get(3).getMenuDescription());
            List<Item__> item__=appDatum.get(0).getItems().get(3).getItems().get(3).getItems();
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
