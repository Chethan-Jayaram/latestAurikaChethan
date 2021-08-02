package com.mobisprint.aurika.coorg.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.angads25.toggle.LabeledSwitch;
import com.github.angads25.toggle.interfaces.OnToggledListener;
import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public  class HouseKeepingAdapter extends RecyclerView.Adapter<HouseKeepingAdapter.ViewHolder> {

    List<Data> houseKeepingList;
    private GlobalClass.AdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;

    public HouseKeepingAdapter(List<Data> houseKeepingList,GlobalClass.AdapterListener mListener) {

        this.houseKeepingList = houseKeepingList;
        this.mListener = mListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.housekeeping_item_selector,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_item_name.setText(houseKeepingList.get(position).getName());
        holder.tv_item_price.setText("₹"+" "+houseKeepingList.get(position).getPrice());
        holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));

        if (houseKeepingList.get(position).getDescription() !=null
                && !houseKeepingList.get(position).getDescription().equals("")
                && !houseKeepingList.get(position).getDescription().isEmpty()  ){
            holder.tv_amenities_item_desc.setVisibility(View.VISIBLE);
            holder.tv_amenities_item_desc.setText(houseKeepingList.get(position).getDescription());
        }else{
            holder.tv_amenities_item_desc.setVisibility(View.GONE);
        }

        if (houseKeepingList.get(position).getCount() == 0){
            holder.lyt_add.setVisibility(View.VISIBLE);
            holder.lyt_counter.setVisibility(View.GONE);
        }else{
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        }

        holder.bt_add.setOnClickListener(v -> {
            if (!isItemSelected){
                isMultipleItemSelected = true;
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()+1);
                holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
                holder.lyt_add.setVisibility(View.GONE);
                holder.lyt_counter.setVisibility(View.VISIBLE);
                pushData(houseKeepingList);
                mListener.onItemClicked(position);
            }else{
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests  ");
            }

        });

        if (houseKeepingList.get(position).getItemselectorType().equalsIgnoreCase("single")){
            holder.bt_single.setVisibility(View.VISIBLE);
            holder.bt_multiple.setVisibility(View.GONE);
            if (houseKeepingList.get(position).getCount()>0){
                isItemSelected =true;
                holder.switch4.setOn(true);
            }

        }else if (houseKeepingList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
            holder.bt_multiple.setVisibility(View.VISIBLE);
            holder.bt_single.setVisibility(View.GONE);
            if (houseKeepingList.get(position).getCount()>0){
                isMultipleItemSelected = true;
            }
        }

        holder.img_add.setOnClickListener(v -> {
            if (houseKeepingList.get(position).getMaxCount() != null){

                if (houseKeepingList.get(position).getCount() < houseKeepingList.get(position).getMaxCount()){
                    isMultipleItemSelected=true;
                    isItemSelected = false;
                    houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()+1);
                    holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
                    pushData(houseKeepingList);
                    mListener.onItemClicked(position);
                }else {
                    GlobalClass.ShowAlert(holder.itemView.getContext(),"Alert","Maximum count for this item has been reached");
                }
            }



        });

        holder.img_remove.setOnClickListener(v -> {
            if (houseKeepingList.get(position).getCount() == 1){
                holder.lyt_add.setVisibility(View.VISIBLE);
                holder.lyt_counter.setVisibility(View.GONE);
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
                pushData(houseKeepingList);
                mListener.onItemClicked(position);
            }
            if (houseKeepingList.get(position).getCount()>0){
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
                pushData(houseKeepingList);
                mListener.onItemClicked(position);
            }
            if(houseKeepingList.get(position).getCount()==0){
                if (GlobalClass.sharedPreferences.getInt(GlobalClass.HouseKeeping_count,0) == 0){
                    isMultipleItemSelected = false;
                    isItemSelected=false;
                }
            }

        });


        holder.switch4.setOnClickListener(v -> {
            if ((isItemSelected && !houseKeepingList.get(position).isItemSelected()) || isMultipleItemSelected) {
                holder.switch4.setOn(true);
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests  ");
            } else if (isItemSelected && houseKeepingList.get(position).isItemSelected() ){
                holder.switch4.setEnabled(false);
                isItemSelected = false;
                houseKeepingList.get(position).setItemSelected(false);
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()-1);
                pushData(houseKeepingList);
                mListener.onItemClicked(position);
            } else if ((!isItemSelected && !houseKeepingList.get(position).isItemSelected()) || !isMultipleItemSelected) {
                holder.switch4.setEnabled(true);
                isItemSelected = true;
                houseKeepingList.get(position).setItemSelected(true);
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()+1);
                pushData(houseKeepingList);
                mListener.onItemClicked(position);
            }
        });




    }

    @Override
    public int getItemCount() {
        return houseKeepingList.size();
    }

    private void pushData(List<Data> HouseKeepingData) {

        Set<Data> set = new LinkedHashSet<>(HouseKeepingData);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("HouseKeeping", json);
        GlobalClass.editor.commit();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name, tv_item_price,tv_quantity,tv_amenities_item_desc;
        ImageView img_add,img_remove;
        CardView add_to_cart;
        MySwitc switch4;
        View bt_single,bt_multiple;
        CardView lyt_counter ;
        CardView lyt_add ;
        TextView bt_add ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_amenities_item_desc = itemView.findViewById(R.id.tv_amenities_item_desc);
            switch4 = itemView.findViewById(R.id.switch4);
            bt_single = itemView.findViewById(R.id.bt_housekeeping_single);
            bt_multiple = itemView.findViewById(R.id.bt_housekeeping_multiple);
            lyt_add =itemView.findViewById(R.id.lyt_add);
            lyt_counter = itemView.findViewById(R.id.lyt_counter);
            bt_add = itemView.findViewById(R.id.bt_add);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
        }
    }
}
