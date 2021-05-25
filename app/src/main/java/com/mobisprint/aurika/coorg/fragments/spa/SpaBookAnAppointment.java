package com.mobisprint.aurika.coorg.fragments.spa;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.SpaDropDownAdapter;
import com.mobisprint.aurika.coorg.controller.spa.BookAnAppointmentController;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;

import java.util.List;

import retrofit2.Response;

public class SpaBookAnAppointment extends Fragment implements ApiListner {

    private TextView toolbar_title,tv_spa_title,therapy_heading,therapy_time,therapy_price;
    private ImageView img_spa,img_drop_down;
    private Context mContext;
    private BookAnAppointmentController controller;
    private RecyclerView recyclerView;
    private RelativeLayout lyt_select_therapy;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa_book_an_appointment, container, false);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText("Book an appointment");

        tv_spa_title = view.findViewById(R.id.tv_coorg_spa_title);
        recyclerView = view.findViewById(R.id.spa_therapy_recyclerview);
        lyt_select_therapy = view.findViewById(R.id.lyt_select_therapy);
        img_spa = view.findViewById(R.id.img_coorg_spa);
        therapy_heading = view.findViewById(R.id.therapy_heading);
        therapy_price = view.findViewById(R.id.therapy_price);
        therapy_time = view.findViewById(R.id.therapy_time);
        img_drop_down = view.findViewById(R.id.img_drop_down);
        mContext = getContext();
        recyclerView.setVisibility(View.GONE);


        Bundle bundle = getArguments();

        tv_spa_title.setText(bundle.getString("title"));
        Glide.with(mContext).load(bundle.getString("image")).centerCrop().into(img_spa);

        controller = new BookAnAppointmentController(this);


        lyt_select_therapy.setOnClickListener(v -> {
            recyclerView.setVisibility(View.VISIBLE);

        });

        controller.getTherapyList();

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!=null){
            Spa spa = (Spa) response.body();
            List<Data> spaList = spa.getData();

            SpaDropDownAdapter adapter = new SpaDropDownAdapter(mContext,spaList,Position -> {

                Log.d("pos my click", String.valueOf(Position));
                therapy_heading.setText(spaList.get(Position).getTitle());
                therapy_time.setText("(" + spaList.get(Position).getDuration()+")");
                therapy_time.setVisibility(View.VISIBLE);
                img_drop_down.setVisibility(View.GONE);
                therapy_price.setVisibility(View.VISIBLE);
                therapy_price.setText("₹"+" "+spaList.get(Position).getPrice());
                recyclerView.setVisibility(View.GONE);


            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);


        }

    }

    @Override
    public void onFetchError(String error) {

    }
}