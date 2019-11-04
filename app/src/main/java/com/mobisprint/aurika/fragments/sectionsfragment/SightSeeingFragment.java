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
import com.mobisprint.aurika.adapter.SightSeeingAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.List;

public class SightSeeingFragment extends Fragment {


  private ImageView backBtn;
  private TextView toolbar_title,tv_sight_seeing_assistance,tv_sight_desc;
  private RecyclerView sight_seeing_recycler;
  private Context context;
  private SightSeeingAdapter adapter;
  private List<Item_> item_;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sight_seeing, container, false);
        try {
            context = view.getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            toolbar_title.setText("Sightseeing");
            backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            sight_seeing_recycler=view.findViewById(R.id.sight_seeing_recycler);
            tv_sight_seeing_assistance=view.findViewById(R.id.tv_sight_seeing_assistance);
            tv_sight_desc=view.findViewById(R.id.tv_sight_desc);
            parsejson();

            adapter = new SightSeeingAdapter(item_);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            sight_seeing_recycler.setLayoutManager(mLayoutManager);
            sight_seeing_recycler.setItemAnimator(new DefaultItemAnimator());
            sight_seeing_recycler.setAdapter(adapter);

            backBtn.setVisibility(View.VISIBLE);
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
            Testing sightseeingPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = sightseeingPojo.getAppData();
            item_ = appDatum.get(0).getItems().get(7).getItems();
            tv_sight_desc.setText(appDatum.get(0).getItems().get(7).getSubTitle());
            tv_sight_seeing_assistance.setText(appDatum.get(0).getItems().get(7).getAssistance());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
