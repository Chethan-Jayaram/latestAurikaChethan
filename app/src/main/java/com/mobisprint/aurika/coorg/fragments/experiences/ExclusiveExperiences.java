package com.mobisprint.aurika.coorg.fragments.experiences;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.ExclusiveExperiencesAdapter;
import com.mobisprint.aurika.coorg.controller.ExclusiveExperiencesController;
import com.mobisprint.aurika.coorg.pojo.experiences.Data;
import com.mobisprint.aurika.coorg.pojo.experiences.Experiences;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.helper.ApiListner;

import java.util.List;

import retrofit2.Response;


public class ExclusiveExperiences extends Fragment implements ApiListner {

    private TextView tv_exclusive_experience_desc,toolbar_title;
    private RecyclerView recyclerView;
    private Button bt_call_back;
    private Context mContext;
    private ExclusiveExperiencesController controller;
    private ImageView img_back;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_exclusive_experiences, container, false);

       tv_exclusive_experience_desc = view.findViewById(R.id.tv_exclusive_experience_desc);
       recyclerView = view.findViewById(R.id.exlusive_experiences_recycler);
       bt_call_back = view.findViewById(R.id.bt_exclusive_experiences_call_back);
       mContext = getContext();
       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       img_back = getActivity().findViewById(R.id.naviagation_hamberger);
       img_back.setVisibility(View.VISIBLE);


       Bundle bundle = getArguments();

       toolbar_title.setText(bundle.getString("title"));

       controller = new ExclusiveExperiencesController(this);

       controller.getExperiencesMenu();



        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!=null){
            Experiences experiences = (Experiences) response.body();
            List<Data> experiencesData = experiences.getData();
            ExclusiveExperiencesAdapter adapter = new ExclusiveExperiencesAdapter(mContext,experiencesData);
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {

    }
}