package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.LaundryServiceAdapter;
import com.mobisprint.aurika.coorg.controller.services.LaundryServiceController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class LaundryService extends Fragment implements ApiListner {

    private TextView toolbar_title,tv_laundry_desc,tv_num_of_items,tv_total_price,view_order;
    private ExpandableListView laundry_expandable_listview;
    private LaundryServiceController controller;
    private Context mContext;
    private ImageView img_back;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "laundry";

    private List<Data> mlaundryList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view = inflater.inflate(R.layout.fragment_laundry_service, container, false);

       toolbar_title = getActivity().findViewById(R.id.toolbar_title);
       tv_laundry_desc = view.findViewById(R.id.laundry_desc);
       laundry_expandable_listview = view.findViewById(R.id.laundry_expandable_listview);

        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);


       controller = new LaundryServiceController(this);
       mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

       Bundle bundle = getArguments();

       tv_laundry_desc.setText(bundle.getString("desc"));
       toolbar_title.setText(bundle.getString("title"));

       controller.getLaundryServices();


        view_order.setOnClickListener(v -> {
            if (mlaundryList!= null && mlaundryList.size() >0) {

                    /*String json =gson.toJson(selectedList);
                    editor.putString("selected_list",json);
                    editor.commit();*/

                Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();
                bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mlaundryList);
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

        if (response != null){

            CoorgServicesPojo servicesPojo= (CoorgServicesPojo) response.body();
            List<Data> laundry_service_list = servicesPojo.getData();


            LaundryServiceAdapter adapter = new LaundryServiceAdapter(mContext,laundry_service_list, (groupPosition,childPosition) -> {


                try {

                    mlaundryList=new ArrayList<>();
                    items_count= 0;
                    total_price = 0;


                    for(int i=0;i<laundry_service_list.size() ;i++){
                        Data data=laundry_service_list.get(i);
                        List<Category_item> category_items=new ArrayList<>();
                        for (int j=0; j<laundry_service_list.get(i).getCategory_item().size();j++){

                                items_count += laundry_service_list.get(i).getCategory_item().get(j).getCount();
                                tv_num_of_items.setText(items_count+" " +"items");


                            if (laundry_service_list.get(i).getCategory_item().get(j).getCount() > 0 ){
                                category_items.add(laundry_service_list.get(i).getCategory_item().get(j));
;                                total_price +=laundry_service_list.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+total_price);
                            }
                        }
                        data.setCategory_item(category_items);
                        mlaundryList.add(data);
                    }


                    /*for (int i = 0; i<= category_items.size() - 1;i++){
                        items_count+=category_items.get(i).getCount();
                        tv_num_of_items.setText(items_count + " " +"items");



                        if (category_items.get(i).getCount() >= 0 ){
                            total_price += category_items.get(i).getCount() * Double.parseDouble(category_items.get(i).getPrice()) ;
                            tv_total_price.setText("₹ "+ " "+total_price);

                        }


                        if (category_items.get(i).getCount() != 0){
                            selectedList.add(category_items.get(i));
                        }

                    }*/



                }catch (Exception e){
                    e.printStackTrace();
                }





            });
            laundry_expandable_listview.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {

    }
}