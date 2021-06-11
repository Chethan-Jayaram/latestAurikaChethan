package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.adapter.HouseKeepingAdapter;
import com.mobisprint.aurika.coorg.controller.services.HouseKeepingController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.net.ConnectException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class HouseKeeping extends Fragment implements ApiListner {

    private RecyclerView recyclerView;
    private TextView tv_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private HouseKeepingController houseKeepingController;
    private Context mContext;
    private ImageView img_back;
    private List<Data> selectedList;
    private String order_category = "house_keeping" ;
    private Integer items_count = 0;
    private double total_price = 0;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_keeping, container, false);

        try {

            tv_desc = view.findViewById(R.id.tv_houseKeeping_desc);
            mContext = getContext();
            tv_num_of_items = view.findViewById(R.id.tv_num_items);
            tv_total_price = view.findViewById(R.id.tv_total_price);
            houseKeepingController = new HouseKeepingController(this);
            recyclerView = view.findViewById(R.id.house_keeping_recyclerview);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            view_order = view.findViewById(R.id.view_order);
            img_back.setVisibility(View.VISIBLE);
            lyt = view.findViewById(R.id.lyt);
            lyt.setVisibility(View.GONE);
            progressBar = view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);

            houseKeepingController.getServices();

            Bundle bundle = getArguments();

            tv_desc.setText(bundle.getString("desc"));
            toolbar_title.setText(bundle.getString("title"));

            view_order.setOnClickListener(v -> {
                if (selectedList!= null && selectedList.size() >0) {

                    /*String json =gson.toJson(selectedList);
                    editor.putString("selected_list",json);
                    editor.commit();*/

                    Fragment fragment = new OrderSummary();
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) selectedList);
                    bundle1.putString("category",order_category);
                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
                }else
                {
                    GlobalClass.ShowAlert(mContext,"Alert","Select atleast one item");
                }


            });


        }catch (Exception e){
            e.printStackTrace();
        }


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

        if (response!=null){
            CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
            List<Data> houseKeepingList = servicesPojo.getData();

            HouseKeepingAdapter adapter = new HouseKeepingAdapter(houseKeepingList,Position -> {

                try {



                    selectedList = new ArrayList<>();
                    items_count= 0;
                    total_price = 0;
                    for (int i = 0; i<= houseKeepingList.size() - 1;i++){
                        items_count+=houseKeepingList.get(i).getCount();
                        tv_num_of_items.setText(items_count + " " +"items");



                        if (houseKeepingList.get(i).getCount() >= 0 ){
                            total_price += houseKeepingList.get(i).getCount() * Double.parseDouble(houseKeepingList.get(i).getPrice()) ;
                            tv_total_price.setText("₹ "+ " "+total_price);

                        }


                        if (houseKeepingList.get(i).getCount() != 0){
                            selectedList.add(houseKeepingList.get(i));
                        }

                    }



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
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}