package com.mobisprint.aurika.coorg.fragments.petservices;

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
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.K9MenuAdapter;
import com.mobisprint.aurika.coorg.controller.petservices.K9MenuController;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;


public class K9Menu extends Fragment implements ApiListner {


    private TextView tv_k9_menu_desc,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private RecyclerView recyclerView;

    private ImageView img_back;

    private K9MenuController controller;


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


        Bundle bundle = getArguments();
        tv_k9_menu_desc.setText(bundle.getString("desc"));
        toolbar_title.setText(bundle.getString("sub_title"));

        controller = new K9MenuController(this);

        controller.getMenu();

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
                GlobalClass.ShowAlert(getContext(),"Alert","Select atleast one item");
            }


        });

        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!= null){

            PetServices services = (PetServices) response.body();
            List<K9Data> menuList = services.getData();

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
                            tv_total_price.setText("₹ "+ " "+total_price);

                        }


                        if (menuList.get(i).getCount() != 0){
                            selectedList.add(menuList.get(i));
                        }

                    }



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

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}