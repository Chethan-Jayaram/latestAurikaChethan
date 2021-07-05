package com.mobisprint.aurika.coorg.fragments.petservices;

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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.K9MenuAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9MenuController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class K9Menu extends Fragment implements ApiListner {


    private TextView tv_k9_menu_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private RecyclerView recyclerView;

    private ImageView img_back;

    private K9MenuController controller;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;

    private List<K9Data> k9ArrDataPackage;
    private Integer items_count = 0;
    private double total_price = 0;

    private String order_category = "k9menu" ;

    private List<K9Data> selectedList;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_k9_menu, container, false);

        tv_k9_menu_desc = view.findViewById(R.id.tv_k9_menu_desc);
        recyclerView = view.findViewById(R.id.k9_menu_recyclerview);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);
        progressBar = view.findViewById(R.id.progress_bar);
        lyt = view.findViewById(R.id.lyt);
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.GONE);


        Bundle bundle = getArguments();
        tv_k9_menu_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("sub_title"));

        controller = new K9MenuController(this);

        controller.getMenu();

        items_count=GlobalClass.sharedPreferences.getInt(GlobalClass.K9Menu_count,0);
        tv_num_of_items.setText(items_count+" " +"items");

        total_price = GlobalClass.sharedPreferences.getFloat(GlobalClass.K9Menu_price,0);
        tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));

        view_order.setOnClickListener(v -> {
            if (items_count >0) {

                    /*String json =gson.toJson(selectedList);
                    editor.putString("selected_list",json);
                    editor.commit();*/

                Fragment fragment = new OrderSummary();
                Bundle bundle1 = new Bundle();
                /* bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) selectedList);*/
                bundle1.putString("category",order_category);

                GlobalClass.editor.putInt(GlobalClass.K9Menu_count, items_count);
                GlobalClass.editor.putFloat(GlobalClass.K9Menu_price, (float) total_price);
                GlobalClass.editor.commit();


                fragment.setArguments(bundle1);
                getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
            }else
            {
                GlobalClass.ShowAlert(getContext(),"Alert","Select atleast one item");
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

            PetServices services = (PetServices) response.body();
            List<K9Data> menuList = services.getData();

            Gson amenitiesGson = new Gson();
            String amenitiesJson = GlobalClass.sharedPreferences.getString("K9Menu", "");
            if (amenitiesJson.isEmpty()) {
                // Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
            } else {
                Type type = new TypeToken<List<K9Data>>() {
                }.getType();
                k9ArrDataPackage = new ArrayList(amenitiesGson.fromJson(amenitiesJson,type));
            }


            try {

                if (k9ArrDataPackage !=null){

                    for (int i=0;i<menuList.size();i++){
                        for (int j=0;j<k9ArrDataPackage.size();j++){
                            if (menuList.get(i).getId().equals(k9ArrDataPackage.get(j).getId())){
                                menuList.remove(i);
                                menuList.add(i,k9ArrDataPackage.get(j));
                            }
                        }
                    }

                }


            } catch (Exception e) {
                e.printStackTrace();
            }

            K9MenuAdapter adapter = new K9MenuAdapter(getContext(),menuList,Position -> {

                try {


                    selectedList = new ArrayList<>();
                    items_count= 0;
                    total_price = 0;
                    for (int i = 0; i<= menuList.size() - 1;i++){
                        items_count+=menuList.get(i).getCount();
                        tv_num_of_items.setText(items_count + " " +"items");



                        if (menuList.get(i).getCount() >= 0 ){
                            total_price += menuList.get(i).getCount() * Double.parseDouble(menuList.get(i).getPrice()) ;
                            tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));

                        }


                        if (menuList.get(i).getCount() != 0){
                            selectedList.add(menuList.get(i));
                        }

                    }

                    GlobalClass.editor.putInt(GlobalClass.K9Menu_count, items_count);
                    GlobalClass.editor.putFloat(GlobalClass.K9Menu_price, (float) total_price);
                    GlobalClass.editor.commit();



                }catch (Exception e){
                    e.printStackTrace();
                }




            });
            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);

        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}