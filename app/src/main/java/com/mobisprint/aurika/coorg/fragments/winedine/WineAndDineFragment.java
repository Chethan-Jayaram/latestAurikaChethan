package com.mobisprint.aurika.coorg.fragments.winedine;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.WineAndDineAdapter;
import com.mobisprint.aurika.coorg.controller.WineAndDineController;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.winedine.Data;
import com.mobisprint.aurika.coorg.pojo.winedine.WineAndDine;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import retrofit2.Response;


public class WineAndDineFragment extends Fragment implements ApiListner {

    private RecyclerView recyclerView;
    private WineAndDineController controller;
    private Context mContext;
    private ImageView img_back;
    private TextView toolbar_title;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_wine_and_dine, container, false);


        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title"));
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        lyt = view.findViewById(R.id.lyt);
        lyt.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);



        recyclerView = view.findViewById(R.id.wine_and_dine_recycler);
        controller = new WineAndDineController(this);
        mContext = getContext();
        controller.getWineAndDineMenu();



        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response!= null){

            WineAndDine wineAndDineServices = (WineAndDine) response.body();

            List<Data> wineAndDineList = wineAndDineServices.getData();
            WineAndDineAdapter adapter = new WineAndDineAdapter(mContext,wineAndDineList,Position -> {

                Fragment fragment = new WineAndDineBookTable();
                Bundle bundle = new Bundle();
                bundle.putString("title",((WineAndDine) response.body()).getData().get(Position).getTitle());
                bundle.putString("img",((WineAndDine) response.body()).getData().get(Position).getImage());
                bundle.putString("desc",((WineAndDine) response.body()).getData().get(Position).getDescription());
                bundle.putInt("id",((WineAndDine) response.body()).getData().get(Position).getId());
                fragment.setArguments(bundle);


//                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//                fragmentTransaction.replace(R.id.fragment_coorg_container, fragment).addToBackStack(null);
//                fragmentTransaction.commit();

                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container,fragment).addToBackStack(null).commit();

            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}