package com.mobisprint.aurika.coorg.fragments.services;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.AmenitiesAdapter;
import com.mobisprint.aurika.coorg.adapter.CoorgSleepWellAdapter;
import com.mobisprint.aurika.coorg.controller.services.CoorgSleepWellFragmentController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.udaipur.adapter.SleepWellAdapter;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class CoorgSleepWellFragment extends Fragment implements ApiListner {

    private TextView tv_sleepwell_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private CoorgSleepWellFragmentController coorgSleepWellFragmentController;
    private ExpandableListView sleepwell_list;
    private Context mContext;
    private ImageView img_back;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "sleepwell";
    private List<Data> arrPackageData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_coorg_sleep_well, container, false);

        tv_sleepwell_desc = view.findViewById(R.id.tv_coorg_sleep_well_desc);
        coorgSleepWellFragmentController = new CoorgSleepWellFragmentController(this);
        sleepwell_list = view.findViewById(R.id.sleepwell_list);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);

        toolbar_title = getActivity().findViewById(R.id.toolbar_title);

        lyt = view.findViewById(R.id.lyt);
        lyt.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = getArguments();
        tv_sleepwell_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("title"));

        coorgSleepWellFragmentController.getServices();

        items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.SleepWell_count,0);
        tv_num_of_items.setText(items_count+" " +"items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.SleepWell_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));


        view_order.setOnClickListener(v -> {
            if (items_count>0)  {

                Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();

                GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
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
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);
        if (response!= null){

            CoorgServicesPojo servicesPojo = (CoorgServicesPojo) response.body();
            List<Data> sleepWellList = servicesPojo.getData();

            if (arrPackageData != null) {
                arrPackageData.clear();
            }

            Gson gson = new Gson();
            String json = GlobalClass.sharedPreferences.getString("SleepWell", "");
            if (json.isEmpty()) {
                //Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<Data>>() {
                }.getType();
                arrPackageData = new ArrayList(gson.fromJson(json,type));
            }


            try {

                if (arrPackageData !=null){

                    for (int i =0; i<sleepWellList.size();i++){
                        for (int j = 0;j<arrPackageData.size();j++){
                            if(sleepWellList.get(i).getId().equals(arrPackageData.get(j).getId())){
                                sleepWellList.remove(i);
                                sleepWellList.add(i,arrPackageData.get(j));
                            }
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }



            CoorgSleepWellAdapter adapter = new CoorgSleepWellAdapter(getContext(),sleepWellList,data -> {

                try {


                    items_count= 0;
                    total_price = 0;


                    for(int i=0;i<sleepWellList.size() ;i++){

                        for (int j=0; j<sleepWellList.get(i).getSleepwellList().size();j++){

                            items_count += sleepWellList.get(i).getSleepwellList().get(j).getCount();
                            tv_num_of_items.setText(items_count+" " +"items");
                            if (sleepWellList.get(i).getSleepwellList().get(j).getCount() >= 0 ){
                                total_price +=sleepWellList.get(i).getSleepwellList().get(j).getCount() * Double.parseDouble(sleepWellList.get(i).getSleepwellList().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                            }
                        }
                    }

                    GlobalClass.editor.putInt(GlobalClass.SleepWell_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.SleepWell_price, (float) total_price);
                    GlobalClass.editor.commit();

                }catch (Exception e){
                    e.printStackTrace();
                }

            });
            sleepwell_list.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}