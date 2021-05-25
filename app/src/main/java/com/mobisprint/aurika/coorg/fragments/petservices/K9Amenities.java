package com.mobisprint.aurika.coorg.fragments.petservices;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.K9AmenitiesAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9AmenitiesController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class K9Amenities extends Fragment implements ApiListner {

    private TextView tv_k9_amenities_desc,toolbar_title,tv_num_of_items,tv_total_price;
    private RecyclerView k9_amenities_recycler;
    private K9AmenitiesController controller;
    private Context mContext;
    private ImageView img_back;
    private List<K9Data> selectedList;
    private Integer items_count = 0;
    private double total_price = 0;
    private TextView view_order;
    private String order_category = "k9amenities";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_k9_amenities, container, false);


        tv_k9_amenities_desc = view.findViewById(R.id.tv_k9_amenities_desc);
        k9_amenities_recycler = view.findViewById(R.id.k9_amenities_recycler);
        controller = new K9AmenitiesController(this);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);

        Bundle bundle = getArguments();
         tv_k9_amenities_desc.setText(bundle.getString("desc"));
         toolbar_title.setText(bundle.getString("sub_title"));


        controller.getAmenities();

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
        
        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response!=null){

            PetServices services = (PetServices) response.body();
            List<K9Data> amenitiesList = services.getData();

            K9AmenitiesAdapter adapter = new K9AmenitiesAdapter(amenitiesList,Position -> {

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



            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
            k9_amenities_recycler.setLayoutManager(layoutManager);
            k9_amenities_recycler.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}