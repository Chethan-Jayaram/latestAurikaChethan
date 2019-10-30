package com.mobisprint.aurika.fragments.sectionsfragment;


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

import com.mobisprint.aurika.adapter.ExperienceAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExperienceFragment extends Fragment {


    private ImageView backBtn;
    private TextView toolbar_title, tv_exp_desc,tv_experience_assistance;
    private RecyclerView experience_recycler;
    private Context context;
    private List<Item_> item_ = new ArrayList<>();
    private String subtitle,assistance;
    private ExperienceAdapter adapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_experience, container, false);
        try {
            context = view.getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
           // tv_exp_desc = view.findViewById(R.id.tv_exp_desc);
            experience_recycler = view.findViewById(R.id.experience_recycler);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            tv_experience_assistance= view.findViewById(R.id.tv_experience_assistance);
            tv_exp_desc= view.findViewById(R.id.tv_exp_desc);
            toolbar_title.setText("Exclusive Experiences");
            parsejson();

            adapter = new ExperienceAdapter(item_);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            experience_recycler.setLayoutManager(mLayoutManager);
            experience_recycler.setItemAnimator(new DefaultItemAnimator());
            experience_recycler.setAdapter(adapter);

            tv_exp_desc.setText(subtitle);

            tv_experience_assistance.setText(assistance);
            backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
