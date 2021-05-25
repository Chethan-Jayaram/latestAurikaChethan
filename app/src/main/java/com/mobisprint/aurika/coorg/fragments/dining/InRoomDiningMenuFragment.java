package com.mobisprint.aurika.coorg.fragments.dining;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.InRoomDiningMenuAdapter;
import com.mobisprint.aurika.coorg.controller.ird.InRoomDiningMenuContoller;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit2.Response;


public class InRoomDiningMenuFragment extends Fragment implements ApiListner {

    private ExpandableListView expandableListView;
    private InRoomDiningMenuContoller contoller;
    private TextView tv_dining_menu_desc,tv_coorg_dining_start_timing,tv_coorg_dining_end_timing,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private ImageView img_back;
    private Integer category_id;
    private Context mContext;
    private List<Data> mdiningList;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "dining";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_room_dining_menu, container, false);

        tv_dining_menu_desc = view.findViewById(R.id.tv_dining_menu_desc);
        tv_coorg_dining_start_timing = view.findViewById(R.id.tv_coorg_dining_start_timing);
        tv_coorg_dining_end_timing = view.findViewById(R.id.tv_coorg_dining_end_timing);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        expandableListView = view.findViewById(R.id.dining_menu_expandable_listview);
        contoller = new InRoomDiningMenuContoller(this);
        mContext = getContext();
        tv_num_of_items = view.findViewById(R.id.tv_num_items);
        tv_total_price = view.findViewById(R.id.tv_total_price);
        view_order = view.findViewById(R.id.view_order);

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title"));
        tv_dining_menu_desc.setText(bundle.getString("desc"));
        category_id = bundle.getInt("category_id");



        try{
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt1 = _24HourSDF.parse(bundle.getString("from_time"));
            Date _24HourDt2 = _24HourSDF.parse(bundle.getString("to_time"));
            tv_coorg_dining_start_timing.setText(_12HourSDF.format(_24HourDt1));
            tv_coorg_dining_end_timing.setText(_12HourSDF.format(_24HourDt2));
        }catch (Exception e){
            e.printStackTrace();
        }


        contoller.getDiningMenu(category_id);


        img_back.setVisibility(View.VISIBLE);


        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!= null){
            Dining dining = (Dining) response.body();
            List<Data> dataList = dining.getData();
            Log.d("list", String.valueOf(dataList.size()));
            InRoomDiningMenuAdapter adapter = new InRoomDiningMenuAdapter(mContext,dataList,(groupPosition,childPosition) ->{


                try {

                    mdiningList=new ArrayList<>();
                    items_count= 0;
                    total_price = 0;


                    for(int i=0;i<dataList.size() ;i++){
                        Data data=dataList.get(i);
                        List<Dining__1> category_items=new ArrayList<>();
                        for (int j=0; j<dataList.get(i).getDiningList().size();j++){

                            items_count += dataList.get(i).getDiningList().get(j).getCount();
                            tv_num_of_items.setText(items_count+" " +"items");


                            if (dataList.get(i).getDiningList().get(j).getCount() >= 0 ){
                                category_items.add(dataList.get(i).getDiningList().get(j));
                                total_price +=dataList.get(i).getDiningList().get(j).getCount() * Double.parseDouble(dataList.get(i).getDiningList().get(j).getPrice()) ;
                                tv_total_price.setText("₹ "+ " "+total_price);
                            }
                        }
                        data.setDiningList(category_items);
                        mdiningList.add(data);

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }




            });
            expandableListView.setAdapter(adapter);
        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(mContext,"Alert",error);

        Log.d("error",error);
    }
}