package com.mobisprint.aurika.coorg.fragments.petservices;

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

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.PetFriendlyAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.PetFriendlyController;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

import retrofit2.Response;


public class CoorgPetFriendly extends Fragment implements ApiListner {

    private RecyclerView recyclerView;
    private Context mContext;
    private PetFriendlyController controller;
    private TextView toolbar_title;
    private ImageView img_back;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_pet_friendly, container, false);

        recyclerView = view.findViewById(R.id.coorg_pet_friendly_recyclerview);
        mContext = getContext();
        controller = new PetFriendlyController(this);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title"));

        controller.getServices();

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!=null){

            PetServices services = (PetServices) response.body();
            List<K9Data> servicesList = services.getData();

            PetFriendlyAdapter adapter = new PetFriendlyAdapter(mContext,servicesList,Position -> {

                try {

                    Class<?> className = Class.forName(RouteName.getPetServiceRoutes(servicesList.get(Position).getRouteName().getRouteName()));
                    Fragment fragment = (Fragment) className.newInstance();

                    Bundle bundle = getArguments();
                    bundle.putString("desc",servicesList.get(Position).getDescription());
                    bundle.putString("sub_title",servicesList.get(Position).getTitle());
                    fragment.setArguments(bundle);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();

                }catch (Exception e){
                    e.printStackTrace();
                }


            });

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }


    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}