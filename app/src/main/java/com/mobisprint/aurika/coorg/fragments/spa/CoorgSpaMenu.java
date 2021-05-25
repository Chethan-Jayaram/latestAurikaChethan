package com.mobisprint.aurika.coorg.fragments.spa;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.SpaMenuAdapter;
import com.mobisprint.aurika.coorg.controller.spa.SpaMenuController;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;

import java.util.List;

import retrofit2.Response;

public class CoorgSpaMenu extends Fragment implements ApiListner {


    private ExpandableListView coorg_spa_menu;
    private SpaMenuController controller;
    private Context mContext;
    private TextView toolbar_title;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_spa_menu, container, false);


        coorg_spa_menu = view.findViewById(R.id.coorg_spa_menu_recyclerview);
        controller = new SpaMenuController(this);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        toolbar_title.setText("Araya Therapies Menu");
        mContext = getContext();
        controller.getSpaMenu();

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!= null){
            Spa spa = (Spa) response.body();
            List<Data> spaList = spa.getData();
            SpaMenuAdapter adapter = new SpaMenuAdapter(mContext,spaList);
            coorg_spa_menu.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {

    }
}