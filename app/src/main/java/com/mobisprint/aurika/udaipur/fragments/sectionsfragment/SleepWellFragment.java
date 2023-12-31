package com.mobisprint.aurika.udaipur.fragments.sectionsfragment;


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
import com.mobisprint.aurika.udaipur.adapter.SleepWellAdapter;


import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.pojo.testing.AppDatum;

import com.mobisprint.aurika.udaipur.pojo.testing.Item_;
import com.mobisprint.aurika.udaipur.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class SleepWellFragment extends Fragment {


   private TextView toolbar_title,tv_sleep_well_desc;
    private List<Item_> item_=new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_sleep_well, container, false);
       try {
           Context context = view.getContext();
           ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
           toolbar_title = getActivity().findViewById(R.id.toolbar_title);
           tv_sleep_well_desc = view.findViewById(R.id.tv_sleep_well_desc);
           RecyclerView sleppwell_recycler = view.findViewById(R.id.sleppwell_recycler);
           backBtn.setVisibility(View.VISIBLE);
           parsejson();
           SleepWellAdapter adapter = new SleepWellAdapter(context, item_);
           sleppwell_recycler.setAdapter(adapter);
           sleppwell_recycler.setLayoutManager(new LinearLayoutManager(context));
           getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
       }catch (Exception e){
           e.printStackTrace();
       }

        return view;
    }

    public void parsejson(){
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing sleepWellPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = sleepWellPojo.getAppData();
            item_ = appDatum.get(0).getItems().get(1).getItems();
            tv_sleep_well_desc.setText(appDatum.get(0).getItems().get(1).getSubTitle());
            toolbar_title.setText(appDatum.get(0).getItems().get(1).getItemName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
