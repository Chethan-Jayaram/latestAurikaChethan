package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.LaundryServiceAdapter;
import com.mobisprint.aurika.coorg.controller.services.LaundryServiceController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Response;


public class LaundryService extends Fragment implements ApiListner {

    private TextView toolbar_title,tv_laundry_desc,tv_num_of_items,tv_total_price,view_order,laundry_instructions;
    private ExpandableListView laundry_expandable_listview;
    private LaundryServiceController controller;
    private Context mContext;
    private ImageView img_back;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "laundry";
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;


   // private List<Data> mlaundryList;
    private List<Data> arrPackageData;


    @RequiresApi(api = Build.VERSION_CODES.N)
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
        lyt = view.findViewById(R.id.lyt);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.GONE);
        laundry_instructions = view.findViewById(R.id.laundry_instructions);

        /*String sourceString = "1. " + "<b>" + "Garments returned for ironing are returned folded." + "</b> " + "Please infrom laundry if you would like them delivered on a hanger instead.";
        laundry_instructions.setText(Html.fromHtml(sourceString));*/


       controller = new LaundryServiceController(this);
       mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

       Bundle bundle = getArguments();

       tv_laundry_desc.setText(bundle.getString("desc"));
       toolbar_title.setText(bundle.getString("title"));


        items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.Laundry_count,0);
        tv_num_of_items.setText(items_count+" " +"items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.Laundry_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));
       controller.getLaundryServices();



        view_order.setOnClickListener(v -> {

            if (items_count>0)  {

                Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();

                GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
                GlobalClass.editor.commit();

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

        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);

        if (response != null){
            CoorgServicesPojo servicesPojo= (CoorgServicesPojo) response.body();
            List<Data> laundry_service_list = servicesPojo.getData();


            if (arrPackageData != null) {
                arrPackageData.clear();
            }

            Gson gson = new Gson();
            String json = GlobalClass.sharedPreferences.getString("Laundry", "");
            if (json.isEmpty()) {
                //Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                arrPackageData = new ArrayList(gson.fromJson(json,type));
            }


            try {

                if (arrPackageData !=null){

                    for (int i =0; i<laundry_service_list.size();i++){
                        for (int j = 0;j<arrPackageData.size();j++){
                            if(laundry_service_list.get(i).getId().equals(arrPackageData.get(j).getId())){
                                laundry_service_list.remove(i);
                                laundry_service_list.add(i,arrPackageData.get(j));
                            }
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }





            LaundryServiceAdapter adapter = new LaundryServiceAdapter(mContext,laundry_service_list, data -> {

                try {

                    items_count= 0;
                    total_price = 0;

                    for(int i=0;i<laundry_service_list.size() ;i++){

                        for (int j=0; j<laundry_service_list.get(i).getCategory_item().size();j++){

                                items_count += laundry_service_list.get(i).getCategory_item().get(j).getCount();
                                tv_num_of_items.setText(items_count+" " +"items");


                            if (laundry_service_list.get(i).getCategory_item().get(j).getCount() >= 0 ){
;                                total_price +=laundry_service_list.get(i).getCategory_item().get(j).getCount() * Double.parseDouble(laundry_service_list.get(i).getCategory_item().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.Laundry_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.Laundry_price, (float) total_price);
                    GlobalClass.editor.commit();



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
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}