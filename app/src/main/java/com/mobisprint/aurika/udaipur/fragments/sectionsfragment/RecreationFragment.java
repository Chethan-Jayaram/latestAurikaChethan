package com.mobisprint.aurika.udaipur.fragments.sectionsfragment;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.udaipur.activity.HomeScreenActivity;
import com.mobisprint.aurika.udaipur.adapter.RecreationAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.pojo.testing.AppDatum;
import com.mobisprint.aurika.udaipur.pojo.testing.Item_;
import com.mobisprint.aurika.udaipur.pojo.testing.Testing;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RecreationFragment extends Fragment {

 private String subtitle;

    private List<Item_> item_=new ArrayList<>();
    private TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recreation, container, false);
        try {
            Context context = view.getContext();
             toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            RecyclerView recreation_recycler = view.findViewById(R.id.recreation_recycler);

            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);

            parsejson();



            RecreationAdapter adapter = new RecreationAdapter(item_, subtitle);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            recreation_recycler.setLayoutManager(mLayoutManager);
            recreation_recycler.setItemAnimator(new DefaultItemAnimator());
            recreation_recycler.setAdapter(adapter);

            backBtn.setOnClickListener(v -> {
                try {
                    Intent startAct = new Intent(getActivity(), HomeScreenActivity.class);
                    //startAct.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(startAct);
                }catch (Exception e){
                    e.printStackTrace();
                }
            });

            getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
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
            Testing recreational = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            Log.d("pojo",GlobalClass.APPDATA);
            List<AppDatum> appDatum = recreational.getAppData();
            item_ = appDatum.get(0).getItems().get(6).getItems();
            subtitle=appDatum.get(0).getItems().get(6).getSubTitle();
            toolbar_title.setText(appDatum.get(0).getItems().get(6).getItemName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
