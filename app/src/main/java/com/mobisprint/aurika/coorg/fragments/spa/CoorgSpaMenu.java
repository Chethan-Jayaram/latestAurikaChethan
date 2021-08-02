package com.mobisprint.aurika.coorg.fragments.spa;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.SpaMenuAdapter;
import com.mobisprint.aurika.coorg.controller.spa.SpaMenuController;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import retrofit2.Response;

public class CoorgSpaMenu extends Fragment implements ApiListner {


    private ExpandableListView coorg_spa_menu;
    private SpaMenuController controller;
    private Context mContext;
    private TextView toolbar_title;
    private RelativeLayout relativeLayout;
    private ProgressBar progressBar;
    private Button bt_req_appointment;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_spa_menu, container, false);


        coorg_spa_menu = view.findViewById(R.id.coorg_spa_menu_recyclerview);
        controller = new SpaMenuController(this);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        relativeLayout = view.findViewById(R.id.lyt);
        relativeLayout.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        bt_req_appointment = view.findViewById(R.id.bt_request_appointment);

        toolbar_title.setText("Araya Spa Menu");
        mContext = getContext();
        controller.getSpaMenu();

        Bundle bundle = getArguments();

        bt_req_appointment.setOnClickListener(v -> {
            Fragment fragment = new SpaBookAnAppointment();
            Bundle bundle1 = new Bundle();
            bundle1.putString("title",bundle.getString("title"));
            bundle1.putString("image",bundle.getString("image"));
            fragment.setArguments(bundle1);
            getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
        });

        return view;
    }

    @Override
    public void onFetchProgress() {

        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        progressBar.setVisibility(View.GONE);
        relativeLayout.setVisibility(View.VISIBLE);

        if (response!= null){
            Spa spa = (Spa) response.body();
            List<Data> spaList = spa.getData();
            SpaMenuAdapter adapter = new SpaMenuAdapter(mContext,spaList);
            coorg_spa_menu.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"ALert",error);

    }
}