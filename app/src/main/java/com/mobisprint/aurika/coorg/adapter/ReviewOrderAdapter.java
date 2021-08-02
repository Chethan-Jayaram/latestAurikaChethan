package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.github.angads25.toggle.LabeledSwitch;
import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReviewOrderAdapter extends RecyclerView.Adapter<ReviewOrderAdapter.ViewHolder> {

    private List<Data> amenitiesList;
    private List<K9Data> k9Amenities,k9Menu;
    private List<Data> houskeepingList;
    private GlobalClass.AmenitiesAdapterListener mListener;
    private GlobalClass.K9AdapterListener k9Listener , k9MenuListener;
    private GlobalClass.HouseKeepingListener houseKeepingListener;
    private Context mContext;
    private boolean isSelected = false;

   private AlertDialog.Builder builder;

    public ReviewOrderAdapter(List<Data> amenitiesList,String amenities,GlobalClass.AmenitiesAdapterListener mListener) {
        this.amenitiesList = amenitiesList;
        this.mListener = mListener;
    }

    public ReviewOrderAdapter(List<K9Data> k9Amenities, int i,GlobalClass.K9AdapterListener k9Listener) {
        this.k9Amenities = k9Amenities;
        this.k9Listener = k9Listener;
    }

    public ReviewOrderAdapter(List<Data> houskeepingList,GlobalClass.HouseKeepingListener houseKeepingListener) {
        this.houseKeepingListener = houseKeepingListener;
        this.houskeepingList = houskeepingList;
    }

    public ReviewOrderAdapter(List<K9Data> k9Menu, String k9Menu1, int i,GlobalClass.K9AdapterListener k9MenuListener) {
        this.k9Menu = k9Menu;
        this.k9MenuListener = k9MenuListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.amenities_recyclerview,parent,false);
        mContext = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.lyt_items.setVisibility(View.GONE);

        if (amenitiesList != null){

            if (amenitiesList.get(position).getItemselectorType().equalsIgnoreCase("single")){
                holder.bt_single.setVisibility(View.VISIBLE);
                holder.bt_multiple.setVisibility(View.GONE);
                holder.switch4.setOn(true);
            }else if (amenitiesList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
                holder.bt_multiple.setVisibility(View.VISIBLE);
                holder.bt_single.setVisibility(View.GONE);

            }

            if (amenitiesList.get(position).getCount()>0) {
                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
                holder.tv_item_price.setText(amenitiesList.get(position).getPrice());
                holder.tv_quantity.setText("" + amenitiesList.get(position).getCount());
            }

            holder.img_add.setOnClickListener(v -> {
                if (amenitiesList.get(position).getMaxCount() != null){
                    if (amenitiesList.get(position).getCount() < amenitiesList.get(position).getMaxCount()){
                        amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                        mListener.onItemClicked( position);
                        pushData(amenitiesList);
                    }else {
                        GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                    }
                }

            });

                holder.img_remove.setOnClickListener(v -> {
                    if (amenitiesList.get(position).getCount() ==1 ){
                        Alert(mContext,position);
                    } else if (amenitiesList.get(position).getCount()>0){
                        amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                        mListener.onItemClicked( position);
                        pushData(amenitiesList);
                    }
                });

            holder.switch4.setOnClickListener(v -> {
                Alert(mContext,position);

            });


        }else if (k9Amenities != null){

            if (k9Amenities.get(position).getItemselectorType().equalsIgnoreCase("single")){
                holder.bt_single.setVisibility(View.VISIBLE);
                holder.switch4.setOn(true);
                holder.bt_multiple.setVisibility(View.GONE);
            }else if (k9Amenities.get(position).getItemselectorType().equalsIgnoreCase("multi")){
                holder.bt_multiple.setVisibility(View.VISIBLE);
                holder.bt_single.setVisibility(View.GONE);

            }

            if (k9Amenities.get(position).getCount()>0) {
                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(k9Amenities.get(position).getTitle());
                holder.tv_item_price.setText(k9Amenities.get(position).getPrice());
                holder.tv_quantity.setText("" + k9Amenities.get(position).getCount());
            }

                holder.img_add.setOnClickListener(v -> {
                    if (k9Amenities.get(position).getMaxCount() != null){

                        if (k9Amenities.get(position).getCount()< k9Amenities.get(position).getMaxCount()){
                            k9Amenities.get(position).setCount( k9Amenities.get(position).getCount()+1);
                            holder.tv_quantity.setText(Integer.toString(k9Amenities.get(position).getCount()));
                            k9Listener.onItemClicked( k9Amenities);
                            pushDataK9(k9Amenities);
                        }else {
                            GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                        }
                    }


                });

                holder.img_remove.setOnClickListener(v -> {
                    if (k9Amenities.get(position).getCount() ==1 ){
                       K9AlertAmenities(mContext,position);
                    } else if (k9Amenities.get(position).getCount()>0){
                        k9Amenities.get(position).setCount( k9Amenities.get(position).getCount()-1);
                        holder.tv_quantity.setText(Integer.toString(k9Amenities.get(position).getCount()));
                        k9Listener.onItemClicked( k9Amenities);
                        pushDataK9(k9Amenities);
                    }
                });

            holder.switch4.setOnClickListener(v -> {
                K9AlertAmenities(mContext,position);

            });




        }else if (houskeepingList != null){

            if (houskeepingList.get(position).getCount() >0){

                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(houskeepingList.get(position).getName());
                holder.tv_item_price.setText(houskeepingList.get(position).getPrice());
                holder.tv_quantity.setText(""+houskeepingList.get(position).getCount());
            }

            if (houskeepingList.get(position).getItemselectorType().equalsIgnoreCase("single")){
                holder.bt_single.setVisibility(View.VISIBLE);
                holder.bt_multiple.setVisibility(View.GONE);
                Log.d(houskeepingList.get(position).getName(), houskeepingList.get(position).getItemselectorType());
                holder.switch4.setOn(true);
            }else if (houskeepingList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
                holder.bt_multiple.setVisibility(View.VISIBLE);
                holder.bt_single.setVisibility(View.GONE);
                Log.d(houskeepingList.get(position).getName(), houskeepingList.get(position).getItemselectorType());

            }


            holder.img_add.setOnClickListener(v -> {
                if (houskeepingList.get(position).getMaxCount() != null){

                    if (houskeepingList.get(position).getCount() < houskeepingList.get(position).getMaxCount()){
                        houskeepingList.get(position).setCount( houskeepingList.get(position).getCount()+1);
                        holder.tv_quantity.setText(Integer.toString(houskeepingList.get(position).getCount()));
                        pushDataHouseKeeping(houskeepingList);
                        houseKeepingListener.onItemClicked(houskeepingList);
                    }else {
                        GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                    }
                }


            });

            holder.img_remove.setOnClickListener(v -> {
                if (houskeepingList.get(position).getCount() ==1 ){
                    HouseKeepingAlertMenu(mContext,position);

                } else if (houskeepingList.get(position).getCount()>0){
                    houskeepingList.get(position).setCount( houskeepingList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(houskeepingList.get(position).getCount()));
                    houseKeepingListener.onItemClicked( houskeepingList);
                    pushDataHouseKeeping(houskeepingList);
                }
            });

            holder.switch4.setOnClickListener(v -> {
                HouseKeepingAlertMenu(mContext,position);
            });

        }



        else if (k9Menu != null){

            if (k9Menu.get(position).getItemselectorType().equalsIgnoreCase("single")){
                holder.bt_single.setVisibility(View.VISIBLE);
                    holder.switch4.setOn(true);
                    holder.bt_multiple.setVisibility(View.GONE);
            }else if (k9Menu.get(position).getItemselectorType().equalsIgnoreCase("multi")){
                holder.bt_multiple.setVisibility(View.VISIBLE);
                holder.bt_single.setVisibility(View.GONE);

            }

            if (k9Menu.get(position).getCount()>0) {

                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(k9Menu.get(position).getTitle());
                holder.tv_item_price.setText("₹ "+k9Menu.get(position).getPrice());
                holder.tv_quantity.setText("" + k9Menu.get(position).getCount());
            }

            holder.img_add.setOnClickListener(v -> {
                if (k9Menu.get(position).getMaxCount() != null){

                    if (k9Menu.get(position).getCount()<k9Menu.get(position).getMaxCount()){
                        k9Menu.get(position).setCount( k9Menu.get(position).getCount()+1);
                        holder.tv_quantity.setText(Integer.toString(k9Menu.get(position).getCount()));
                        pushDataK9Menu(k9Menu);
                        k9MenuListener.onItemClicked(k9Menu);
                    }else if (k9Menu.get(position).getCount() == k9Menu.get(position).getMaxCount()){
                        GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                    }
                }


            });

            holder.img_remove.setOnClickListener(v -> {
                if (k9Menu.get(position).getCount() ==1 ){
                    K9AlertMenu(mContext,position);

                } else if (k9Menu.get(position).getCount()>0){
                    k9Menu.get(position).setCount( k9Menu.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(k9Menu.get(position).getCount()));
                    k9MenuListener.onItemClicked( k9Menu);
                    pushDataK9Menu(k9Menu);
                }
            });



            holder.switch4.setOnClickListener(v -> {
                K9AlertMenu(mContext,position);

            });



        }


    }

    private void K9Alert(Context mContext, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    k9Amenities.get(position).setCount(k9Amenities.get(position).getCount() - 1);
                    k9Listener.onItemClicked(k9Amenities);
                    pushDataK9(k9Amenities);
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    private void K9AlertMenu(Context mContext, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (k9Menu.get(position).getCount() ==1 ) {
                        k9Menu.get(position).setCount(k9Menu.get(position).getCount() - 1);
                        k9MenuListener.onItemClicked(k9Menu);
                        k9Menu.get(position).setItemSelected(false);
                        pushDataK9Menu(k9Menu);
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("No", (dialog, id) -> {
                    notifyDataSetChanged();
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    private void K9AlertAmenities(Context mContext, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (k9Amenities.get(position).getCount() ==1 ) {
                        k9Amenities.get(position).setCount(k9Amenities.get(position).getCount() - 1);
                        k9Listener.onItemClicked(k9Amenities);
                        k9Amenities.get(position).setItemSelected(false);
                        pushDataK9(k9Amenities);
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("No", (dialog, id) -> {
                    notifyDataSetChanged();
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    private void HouseKeepingAlertMenu(Context mContext, int position) {

        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    if (houskeepingList.get(position).getCount() ==1 ) {
                        houskeepingList.get(position).setCount(houskeepingList.get(position).getCount() - 1);
                        houseKeepingListener.onItemClicked(houskeepingList);
                        houskeepingList.get(position).setItemSelected(false);
                        pushDataHouseKeeping(houskeepingList);
                        notifyDataSetChanged();
                    }

                })
                .setNegativeButton("No", (dialog, id) -> {
                    notifyDataSetChanged();
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    private void Alert(Context mContext,int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {

                    if (amenitiesList.get(position).getCount() == 1){
                        amenitiesList.get(position).setCount(amenitiesList.get(position).getCount() - 1);
                        mListener.onItemClicked(position);
                        amenitiesList.get(position).setItemSelected(false);
                        pushData(amenitiesList);
                        notifyDataSetChanged();

                    }

                })
                .setNegativeButton("No", (dialog, id) -> {
                    notifyDataSetChanged();
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        if(!alert.isShowing()) {
            alert.show();
        }
    }

    private void pushData(List<Data> amenitiesList) {

        Set<Data> set = new LinkedHashSet<>(amenitiesList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("Amenities", json);
        GlobalClass.editor.commit();
    }

    private void pushDataK9(List<K9Data> k9Data) {

        Set<K9Data> set = new LinkedHashSet<>(k9Data);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("K9Amenities", json);
        GlobalClass.editor.commit();
    }


    private void pushDataK9Menu(List<K9Data> k9Data) {

        Set<K9Data> set = new LinkedHashSet<>(k9Data);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("K9Menu", json);
        GlobalClass.editor.commit();
    }


    private void pushDataHouseKeeping(List<Data> houseKeepingData) {

        Set<Data> set = new LinkedHashSet<>(houseKeepingData);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("HouseKeeping", json);
        GlobalClass.editor.commit();
    }




    @Override
    public int getItemCount() {
        if (amenitiesList != null){
            return amenitiesList.size();
        }else if (k9Amenities != null){
            return k9Amenities.size();
        }else if (houskeepingList != null){
            return houskeepingList.size();
        } else if (k9Menu != null){
            return k9Menu.size();
        }else {
        return 0;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView tv_item_name, tv_item_price,tv_quantity;
        ImageView img_add,img_remove;
        RelativeLayout lyt_items;
        View bt_single,bt_multiple;
        MySwitc switch4;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            lyt_items = itemView.findViewById(R.id.lyt_items);
            bt_single = itemView.findViewById(R.id.bt_amen_single);
            bt_multiple = itemView.findViewById(R.id.bt_amen_multiple);
            switch4 = itemView.findViewById(R.id.switch4);
        }
    }
}
