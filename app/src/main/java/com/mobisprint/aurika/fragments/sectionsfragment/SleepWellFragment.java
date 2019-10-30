package com.mobisprint.aurika.fragments.sectionsfragment;


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
import com.mobisprint.aurika.adapter.SleepWellAdapter;


import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.AppDatum;

import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;



/**
 * A simple {@link Fragment} subclass.
 */
public class SleepWellFragment extends Fragment {


   private TextView toolbar_title,tv_sleep_well_desc;
   private ImageView backBtn;
    private List<Item_> item_=new ArrayList<>();

     private SleepWellAdapter adapter;
     private Context context;
     private RecyclerView sleppwell_recycler;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_sleep_well, container, false);
       try {
           context = view.getContext();
           backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
           toolbar_title = getActivity().findViewById(R.id.toolbar_title);
           tv_sleep_well_desc = view.findViewById(R.id.tv_sleep_well_desc);
           sleppwell_recycler = view.findViewById(R.id.sleppwell_recycler);
           toolbar_title.setText("Sleep Well");
           backBtn.setVisibility(View.VISIBLE);
           parsejson();
           adapter = new SleepWellAdapter(context,item_);
           sleppwell_recycler.setAdapter(adapter);
           sleppwell_recycler.setLayoutManager(new LinearLayoutManager(context));
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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
