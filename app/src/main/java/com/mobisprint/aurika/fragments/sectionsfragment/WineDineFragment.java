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
import com.mobisprint.aurika.adapter.WineAndDineAdapter;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Testing;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class WineDineFragment extends Fragment {


    private ImageView backBtn;
    private TextView toolbar_title, tv_wine_dine_desc;
    private Context context;
    private WineAndDineAdapter adapter;
    private RecyclerView wine_dine_recycler;
    private String subtitle;
    private List<Item_> item_ = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wine_dine, container, false);
        try {
            context = view.getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            tv_wine_dine_desc = view.findViewById(R.id.tv_wine_dine_desc);
            backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            wine_dine_recycler = view.findViewById(R.id.wine_dine_recycler);
            toolbar_title.setText("Wine and Dine");
            parsejson();
            adapter = new WineAndDineAdapter(item_);
            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(context);
            wine_dine_recycler.setLayoutManager(mLayoutManager);
            wine_dine_recycler.setItemAnimator(new DefaultItemAnimator());
            wine_dine_recycler.setAdapter(adapter);
            backBtn.setVisibility(View.VISIBLE);
            tv_wine_dine_desc.setText(subtitle);
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
            Testing wineAndDinePojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = wineAndDinePojo.getAppData();
            item_ = appDatum.get(0).getItems().get(2).getItems();
            subtitle = appDatum.get(0).getItems().get(2).getSubTitle();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
