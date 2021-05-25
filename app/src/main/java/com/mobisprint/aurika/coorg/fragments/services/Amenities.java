package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.controller.services.AmenitiesController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;
import static com.mobisprint.aurika.helper.GlobalClass.editor;


public class Amenities extends Fragment implements ApiListner {

    private RecyclerView amenities_recyclerview;
    private AmenitiesController amenitiesController;
    private Context mContext;
    private TextView toolbar_title,tv_amenities_desc,tv_num_of_items,tv_total_price,view_order;
    private Integer items_count = 0;
    private double total_price = 0;
    private ImageView img_back;

    private String order_category = "amenities" ;

    private List<Data> selectedList;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_amenities, container, false);

        try {

            amenities_recyclerview = view.findViewById(R.id.amenities_recycler);
            amenitiesController = new AmenitiesController(this);
            mContext = getContext();
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            tv_amenities_desc = view.findViewById(R.id.tv_amenities_desc);
            tv_num_of_items = view.findViewById(R.id.tv_num_items);
            tv_total_price = view.findViewById(R.id.tv_total_price);
            view_order = view.findViewById(R.id.view_order);
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            img_back.setVisibility(View.VISIBLE);



            Bundle bundle = getArguments();
            tv_amenities_desc.setText(bundle.getString("desc"));
            toolbar_title.setText(bundle.getString("title"));

            amenitiesController.getServices();
/*
            Gson gson = new Gson();
            String json = GlobalClass.sharedPreferences.getString("selected_list", "");
            if (!json.isEmpty()) {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                selectedList = gson.fromJson(json, type);
                Log.d("selList", selectedList.size() + "This is my size");
            }*/

            view_order.setOnClickListener(v -> {
                if (selectedList!= null && selectedList.size() >0) {

                    /*String json =gson.toJson(selectedList);
                    editor.putString("selected_list",json);
                    editor.commit();*/

                    Fragment fragment = new OrderSummary();
                    Bundle bundle1 = new Bundle();
                    bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) selectedList);
                    bundle1.putString("category",order_category);
                    for (int i = 0; i<selectedList.size(); i++){
                        Log.i("slected item: ", selectedList.get(i).getTitle());
                    }
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



    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        try {
            if (response!=null){
                CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
                List<Data> amenitiesList = servicesPojo.getData();

               AmenitiesAdapter adapter = new AmenitiesAdapter(amenitiesList,Position -> {


                   try {
                       Log.i("item added: ", amenitiesList.get(Position).getTitle());



                       selectedList = new ArrayList<>();
                       items_count= 0;
                       total_price = 0;
                       for (int i = 0; i<= amenitiesList.size() - 1;i++){
                           items_count+=amenitiesList.get(i).getCount();
                           tv_num_of_items.setText(items_count + " " +"items");



                           if (amenitiesList.get(i).getCount() >= 0 ){
                               total_price += amenitiesList.get(i).getCount() * Double.parseDouble(amenitiesList.get(i).getPrice()) ;
                               tv_total_price.setText("₹ "+ " "+total_price);

                           }


                           if (amenitiesList.get(i).getCount() != 0){
                               selectedList.add(amenitiesList.get(i));
                           }

                       }



                   }catch (Exception e){
                       e.printStackTrace();
                   }


               });
                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                amenities_recyclerview.setLayoutManager(layoutManager);
                amenities_recyclerview.setAdapter(adapter);
                adapter.notifyDataSetChanged();



            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void onFetchError(String error) {

    }
}