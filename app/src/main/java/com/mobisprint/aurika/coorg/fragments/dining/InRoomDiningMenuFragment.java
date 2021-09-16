package com.mobisprint.aurika.coorg.fragments.dining;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.UserAuthenticationActivity;
import com.mobisprint.aurika.coorg.adapter.InRoomDiningMenuAdapter;
import com.mobisprint.aurika.coorg.controller.BottomDailogController;
import com.mobisprint.aurika.coorg.controller.ird.InRoomDiningMenuContoller;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.OrderConfirmedFragment;
import com.mobisprint.aurika.coorg.fragments.OrderSummary;
import com.mobisprint.aurika.coorg.modle.DiningModle;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.SharedPreferenceVariables;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import retrofit2.Response;


public class InRoomDiningMenuFragment extends Fragment implements ApiListner {

    private ExpandableListView expandableListView;
    private InRoomDiningMenuContoller contoller;
    private TextView tv_dining_menu_desc,tv_coorg_dining_start_timing,tv_coorg_dining_end_timing,toolbar_title,tv_num_of_items,tv_total_price,view_order;
    private ImageView img_back;
    private Integer category_id;
    private Context mContext;
    private List<Data> diningArrPackagedata;
    private Integer items_count = 0;
    private double total_price = 0;
    private String order_category = "dining", title;
    private CoordinatorLayout lyt;
    private ProgressBar progressBar;
    private Bundle bundle;
    private int hr,min;
    private Calendar calendar;
    private List<Data> dataList;
    private List<Dining__1> selectedList = new ArrayList<>();
    private DiningModle diningModle = new DiningModle();
    private BottomDailogController ticketController;
    private String booking = "1";
    private String requestDate,reqtime;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_room_dining_menu, container, false);

        try {


            tv_dining_menu_desc = view.findViewById(R.id.tv_dining_menu_desc);
            tv_coorg_dining_start_timing = view.findViewById(R.id.tv_coorg_dining_start_timing);
            tv_coorg_dining_end_timing = view.findViewById(R.id.tv_coorg_dining_end_timing);
            toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            img_back = getActivity().findViewById(R.id.naviagation_hamberger);
            expandableListView = view.findViewById(R.id.dining_menu_expandable_listview);
            contoller = new InRoomDiningMenuContoller(this);
            ticketController = new BottomDailogController(this);
            mContext = getContext();
            tv_num_of_items = view.findViewById(R.id.tv_num_items);
            tv_total_price = view.findViewById(R.id.tv_total_price);
            view_order = view.findViewById(R.id.view_order);
            lyt = view.findViewById(R.id.lyt);
            lyt.setVisibility(View.GONE);
            progressBar = view.findViewById(R.id.progress_bar);
            progressBar.setVisibility(View.GONE);
            calendar = Calendar.getInstance();
            hr = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);

            bundle = getArguments();
            toolbar_title.setText(bundle.getString("title"));
            tv_dining_menu_desc.setText(bundle.getString("desc"));
            category_id = bundle.getInt("category_id");

            calendar = Calendar.getInstance();
            hr = calendar.get(Calendar.HOUR);
            min = calendar.get(Calendar.MINUTE);
            reqtime = hr + ":" + min;
            requestDate = String.valueOf(java.time.LocalDate.now()) + " " + reqtime;

            try{
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                @SuppressLint("SimpleDateFormat") SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt1 = _24HourSDF.parse(bundle.getString("from_time"));
                Date _24HourDt2 = _24HourSDF.parse(bundle.getString("to_time"));
                tv_coorg_dining_start_timing.setText(_12HourSDF.format(_24HourDt1));
                tv_coorg_dining_end_timing.setText(_12HourSDF.format(_24HourDt2));
            }catch (Exception e){
                e.printStackTrace();
            }





            img_back.setVisibility(View.VISIBLE);

            /*items_count=GlobalClass.sharedPreferences.getInt(bundle.getString("title")+"Count",0);
            tv_num_of_items.setText(items_count+" " +"items");

            total_price = GlobalClass.sharedPreferences.getFloat(bundle.getString("title") + "Price",0);
            tv_total_price.setText("₹ "+GlobalClass.round(total_price,2));*/



            view_order.setOnClickListener(v -> {
                if (items_count >0) {


                    if (GlobalClass.user_token.isEmpty()){
                        alertBox();

                    }else if (GlobalClass.user_active_booking){
                        /*showBottomSheetDialog();*/
                        selectedList.clear();
                        for (int i=0;i<dataList.size();i++){
                            for (int j=0;j<dataList.get(i).getDiningList().size();j++){
                                if (dataList.get(i).getDiningList().get(j).getCount()>0){
                                    dataList.get(i).getDiningList().get(j).setItem_id(dataList.get(i).getDiningList().get(j).getId());
                                    dataList.get(i).getDiningList().get(j).setQuantity(dataList.get(i).getDiningList().get(j).getCount());
                                    selectedList.add(dataList.get(i).getDiningList().get(j));
                                }
                            }
                        }

                       /* title = "Dining "+bundle.getString("title") + " Ticket";

                        diningModle.setDetails(selectedList);
                        diningModle.setDepartment("in-room-dining");
                        diningModle.setTitle(title);
                        diningModle.setBooking(booking);
                        diningModle.setRoomNumber(GlobalClass.ROOM_NO);
                        diningModle.setRequestTime(reqtime);
                        diningModle.setRequestDate(requestDate);
                        ticketController.diningTicketCreation(diningModle);*/

                        BottomDailogFragment fragment = new BottomDailogFragment();
                        Bundle bundle1 = new Bundle();
                        bundle1.putString("Category","Dining"+bundle.getString("title"));
                        bundle1.putString("Category","in-room-dining");
                        bundle1.putParcelableArrayList("List", (ArrayList<? extends Parcelable>) selectedList);
                        fragment.setArguments(bundle1);
                        /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                        fragment.show(getActivity().getSupportFragmentManager(),
                                "fragment_bottom_sheet_dailog");
                    }else {
                        GlobalClass.ShowAlert(mContext,"Alert","You don't have active booking to place order");

                    }
                    /*Log.d("items_count", String.valueOf(items_count));

                    *//*String json =gson.toJson(selectedList);
                    editor.putString("selected_list",json);
                    editor.commit();*//*

                    Fragment fragment = new OrderSummary();
                    Bundle bundle1 = new Bundle();
                    *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        bundle1.putParcelableArrayList("list", (ArrayList<? extends Parcelable>) mdiningList.stream().distinct().collect(Collectors.toList()));
                    }else{
                        //todo
                    }*//*

                    GlobalClass.editor.putInt(bundle.getString("title")+"Count", items_count);
                    GlobalClass.editor.putFloat(bundle.getString("title") + "Price", (float) total_price);
                    GlobalClass.editor.commit();


                    bundle1.putString("title",bundle.getString("title"));
                    bundle1.putString("category",order_category);

                   *//* Gson gson = new Gson();
                    String json = gson.toJson(set);
                    GlobalClass.editor.putString("Dining", json);
                    GlobalClass.editor.commit();*//*



                    fragment.setArguments(bundle1);
                    getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
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



    private void alertBox() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Please login to place your request")
                .setCancelable(false)
                .setPositiveButton("Okay", (dialog, id) -> {
                    Intent intent = new Intent(mContext, UserAuthenticationActivity.class);
                    startActivity(intent);
                    getActivity().finish();

                });

        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }



    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);

    }

    @Override
    public void onResume() {
        super.onResume();
        items_count = 0;
        tv_num_of_items.setText("0 items");
        tv_total_price.setText("₹ 0.00");
        contoller.getDiningMenu(category_id);
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        progressBar.setVisibility(View.GONE);
        lyt.setVisibility(View.VISIBLE);





        if (response!= null){

            if (response.body() instanceof Dining){

                Dining dining = (Dining) response.body();
                dataList = dining.getData();

                if (diningArrPackagedata != null) {
                    diningArrPackagedata.clear();
                }


                Gson gson = new Gson();
                String json = GlobalClass.sharedPreferences.getString(bundle.getString("title"), "");
                if (json.isEmpty()) {
                    //Toast.makeText(mContext, "Something went worng", Toast.LENGTH_LONG).show();
                } else {
                    Type type = new TypeToken<List<Data>>() {
                    }.getType();
                    diningArrPackagedata = new ArrayList(gson.fromJson(json,type));
                }

                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsSingleItemSelected,false);
                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsMultipleItemSelected,false);
                GlobalClass.editor.commit();

            /*try {

                if (diningArrPackagedata !=null){

                    items_count = 0;
                    total_price = 0;

                    for (int i =0; i<dataList.size();i++){
                        for (int j = 0;j<diningArrPackagedata.size();j++){
                            *//*if(dataList.get(i).getId().equals(diningArrPackagedata.get(j).getId())){
                                dataList.remove(i);
                                dataList.add(i,diningArrPackagedata.get(j));
                            }*//*
                 *//* if (laundry_service_list.get(i).getCategory_item().get(j).getId().equals(arrPackageData.get(i).getCategory_item().get(j).getId()) ){
                                laundry_service_list.get(i).getCategory_item().get(j).setCount(arrPackageData.get(i).getCategory_item().get(j).getCount());
                            }*//*


                            for (int x=0; x<dataList.get(i).getDiningList().size(); x++){
                                for (int y=0; y<diningArrPackagedata.get(j).getDiningList().size(); y++){
                                    if (dataList.get(i).getDiningList().get(x).getId().equals(diningArrPackagedata.get(j).getDiningList().get(y).getId())){

                                        if (!(diningArrPackagedata.get(j).getDiningList().get(y).getItemselectorType().equalsIgnoreCase(dataList.get(i).getDiningList().get(x).getItemselectorType()))){
                                            diningArrPackagedata.clear();
                                            GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsSingleItemSelected,false);
                                            GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsMultipleItemSelected,false);
                                            GlobalClass.editor.commit();
                                        }else if (diningArrPackagedata.get(j).getDiningList().get(y).getItemselectorType().equalsIgnoreCase(dataList.get(i).getDiningList().get(x).getItemselectorType())
                                                && diningArrPackagedata.get(j).getDiningList().get(y).getCount()>0){
                                            dataList.get(i).getDiningList().get(x).setCount(diningArrPackagedata.get(j).getDiningList().get(y).getCount());
                                            dataList.get(i).getDiningList().get(x).setItemSelected(true);

                                            if (diningArrPackagedata.get(j).getDiningList().get(y).getItemselectorType().equalsIgnoreCase("single")){


                                                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsSingleItemSelected,true);
                                                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsMultipleItemSelected,false);
                                                GlobalClass.editor.commit();

                                            }else if (diningArrPackagedata.get(j).getDiningList().get(y).getItemselectorType().equalsIgnoreCase("multi")){


                                                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsSingleItemSelected,false);
                                                GlobalClass.editor.putBoolean(bundle.getString("title") + SharedPreferenceVariables.Dining_IsMultipleItemSelected,true);
                                                GlobalClass.editor.commit();

                                            }

                                            items_count += dataList.get(i).getDiningList().get(x).getCount();
                                            tv_num_of_items.setText(items_count+" " +"items");


                                            if (dataList.get(i).getDiningList().get(x).getCount() >= 0 ){
                                                total_price +=dataList.get(i).getDiningList().get(x).getCount() * Double.parseDouble(dataList.get(i).getDiningList().get(x).getPrice()) ;
                                                tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                            }

                                        }
                                    }
                                }
                            }
                        }
                    }

                    GlobalClass.editor.putInt(bundle.getString("title") + "Count", items_count);
                    GlobalClass.editor.putFloat(bundle.getString("title") + "Price", (float) total_price);
                    Set<Data> set = new LinkedHashSet<>(dataList);
                    Gson diningGson = new Gson();
                    String diningJson = diningGson.toJson(set);
                    GlobalClass.editor.putString(bundle.getString("title"), diningJson);
                    GlobalClass.editor.commit();

                }


            } catch (Exception e) {
                e.printStackTrace();
            }*/


                InRoomDiningMenuAdapter adapter = new InRoomDiningMenuAdapter(mContext,dataList,bundle.getString("title"),getActivity().getSupportFragmentManager(),data ->{


                    try {

                        items_count= 0;
                        total_price = 0;


                        for(int i=0;i<dataList.size() ;i++){
                            Data data1=dataList.get(i);
                            List<Dining__1> category_items=new ArrayList<>();
                            for (int j=0; j<dataList.get(i).getDiningList().size();j++){

                                items_count += dataList.get(i).getDiningList().get(j).getCount();
                                tv_num_of_items.setText(items_count+" " +"items");


                                if (dataList.get(i).getDiningList().get(j).getCount() >= 0 ){
                                    category_items.add(dataList.get(i).getDiningList().get(j));
                                    total_price +=dataList.get(i).getDiningList().get(j).getCount() * Double.parseDouble(dataList.get(i).getDiningList().get(j).getPrice()) ;
                                    tv_total_price.setText("₹ "+ " "+GlobalClass.round(total_price,2));
                                }
                            }

                        }

                        GlobalClass.editor.putInt(bundle.getString("title") + "Count", items_count);
                        GlobalClass.editor.putFloat(bundle.getString("title") + "Price", (float) total_price);
                        GlobalClass.editor.commit();


                    }catch (Exception e){
                        e.printStackTrace();
                    }



                });
                expandableListView.setAdapter(adapter);

            }else if (response.body() instanceof Ticket){

                Fragment fragment1 = new OrderConfirmedFragment();
                Bundle bundle = new Bundle();
                fragment1.setArguments(bundle);
                /*closeBottomSheetFragment();*/
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment1).addToBackStack(null).commit();

            }



        }

    }

    @Override
    public void onFetchError(String error) {
        progressBar.setVisibility(View.GONE);

        GlobalClass.ShowAlert(mContext,"Alert",error);

        Log.d("error",error);
    }
}