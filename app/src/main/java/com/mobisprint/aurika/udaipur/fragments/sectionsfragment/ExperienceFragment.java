package com.mobisprint.aurika.udaipur.fragments.sectionsfragment;


import android.content.Context;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
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

import com.mobisprint.aurika.udaipur.adapter.ExperienceAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.pojo.testing.AppDatum;
import com.mobisprint.aurika.udaipur.pojo.testing.Item_;
import com.mobisprint.aurika.udaipur.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperienceFragment extends Fragment {


    private List<Item_> item_ = new ArrayList<>();
    private String subtitle,assistance;
    private  TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience, container, false);
        try {
            Context context = view.getContext();
           // tv_exp_desc = view.findViewById(R.id.tv_exp_desc);
            RecyclerView experience_recycler = view.findViewById(R.id.experience_recycler);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            TextView tv_experience_assistance = view.findViewById(R.id.tv_experience_assistance);
            TextView tv_exp_desc = view.findViewById(R.id.tv_exp_desc);
            parsejson();
            ExperienceAdapter adapter = new ExperienceAdapter(item_);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            experience_recycler.setLayoutManager(mLayoutManager);
            experience_recycler.setItemAnimator(new DefaultItemAnimator());
            experience_recycler.setAdapter(adapter);
            tv_exp_desc.setText(subtitle);
            tv_experience_assistance.setText(assistance);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);

            getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
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
            Testing testing = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = testing.getAppData();
            item_ = appDatum.get(0).getItems().get(4).getItems();
            subtitle = appDatum.get(0).getItems().get(4).getSubTitle();
            assistance=appDatum.get(0).getItems().get(4).getAssistance();
            toolbar_title.setText(appDatum.get(0).getItems().get(4).getItemName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
