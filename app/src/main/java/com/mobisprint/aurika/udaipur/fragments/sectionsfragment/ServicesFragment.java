package com.mobisprint.aurika.udaipur.fragments.sectionsfragment;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;


import androidx.annotation.RequiresApi;

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
import com.mobisprint.aurika.udaipur.adapter.ServiceAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.pojo.testing.AppDatum;

import com.mobisprint.aurika.udaipur.pojo.testing.Item_;

import com.mobisprint.aurika.udaipur.pojo.testing.Testing;


import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ServicesFragment extends Fragment {

   private TextView toolbar_title;
    private List<Item_> item_=new ArrayList<>();


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
      View view =  inflater.inflate(R.layout.fragment_services, container, false);
      try {
          Context context = view.getContext();

          RecyclerView service_recycler = view.findViewById(R.id.service_recycler);
          ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
          toolbar_title = getActivity().findViewById(R.id.toolbar_title);

          parsejson();

          ServiceAdapter adapter = new ServiceAdapter(item_);
          RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
          service_recycler.setLayoutManager(mLayoutManager);
          service_recycler.setItemAnimator(new DefaultItemAnimator());
          service_recycler.setAdapter(adapter);

          backBtn.setVisibility(View.VISIBLE);
          getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
      } catch (Exception e){
          e.printStackTrace();
      }
        return view;
    }


    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing services = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = services.getAppData();
            item_ = appDatum.get(0).getItems().get(0).getItems();
            toolbar_title.setText(appDatum.get(0).getItems().get(0).getItemName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
