package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class K9ServicesAdapter extends RecyclerView.Adapter<K9ServicesAdapter.ViewHolder>{

    private List<K9Data> serviceList;
    private Context context;
    private GlobalClass.AdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;

    public K9ServicesAdapter(List<K9Data> serviceList, Context context, GlobalClass.AdapterListener mListener) {
        this.context = context;
        this.serviceList =serviceList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.k9_menu_internal_list,parent,false);
        return new K9ServicesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(serviceList.get(position).getTitle());
        holder.desc.setVisibility(View.VISIBLE);
        holder.desc.setVisibility(View.GONE);
        holder.desc.setText(serviceList.get(position).getDescription());

        holder.price.setText("₹ "+serviceList.get(position).getPrice());
        holder.tv_quantity.setText(Integer.toString(serviceList.get(position).getCount()));


        if (serviceList.get(position).getCount() == 0){
            holder.lyt_add.setVisibility(View.VISIBLE);
            holder.lyt_counter.setVisibility(View.GONE);
        }else{
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        }

        if (serviceList.get(position).getItemselectorType().equalsIgnoreCase("single")){
            holder.bt_single.setVisibility(View.VISIBLE);
            holder.bt_multiple.setVisibility(View.GONE);
            if (serviceList.get(position).getCount()>0){
                holder.switch4.setOn(true);
            }

        }else if (serviceList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
            holder.bt_multiple.setVisibility(View.VISIBLE);
            holder.bt_single.setVisibility(View.GONE);
            if (serviceList.get(position).getCount()>0){
            }
        }
        holder.bt_add.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking) {

                if (!isItemSelected) {
                    isMultipleItemSelected = true;
                    serviceList.get(position).setCount(serviceList.get(position).getCount() + 1);
                    holder.tv_quantity.setText(Integer.toString(serviceList.get(position).getCount()));
                    holder.lyt_add.setVisibility(View.GONE);
                    holder.lyt_counter.setVisibility(View.VISIBLE);
                    pushDataK9(serviceList);
                    mListener.onItemClicked(position);
                } else {
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", " Please place individual orders for individual requests");
                }
            }else{
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "You don't have an active booking. You can place order only during the stay at property.");

            }

        });

        holder.img_add.setOnClickListener(v -> {

            if (serviceList.get(position).getMaxCount() != null){

                if (serviceList.get(position).getCount() < serviceList.get(position).getMaxCount()){
                    isMultipleItemSelected=true;
                    isItemSelected = false;
                    serviceList.get(position).setCount( serviceList.get(position).getCount()+1);
                    holder.tv_quantity.setText(Integer.toString(serviceList.get(position).getCount()));
                    pushDataK9(serviceList);
                    mListener.onItemClicked(position);
                }else if (serviceList.get(position).getCount() == serviceList.get(position).getMaxCount()){
                    GlobalClass.ShowAlert(context,"Alert","Maximum count for this item has been reached");
                }
            }




        });

        holder.img_remove.setOnClickListener(v -> {
            if (serviceList.get(position).getCount() == 1){
                holder.lyt_add.setVisibility(View.VISIBLE);
                holder.lyt_counter.setVisibility(View.GONE);
                serviceList.get(position).setCount( serviceList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(serviceList.get(position).getCount()));
                pushDataK9(serviceList);
                mListener.onItemClicked(position);
            }
            if (serviceList.get(position).getCount()>0){
                serviceList.get(position).setCount( serviceList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(serviceList.get(position).getCount()));
                pushDataK9(serviceList);
                mListener.onItemClicked(position);
            }
            if(serviceList.get(position).getCount()==0){
                if (GlobalClass.sharedPreferences.getInt(GlobalClass.K9Menu_count,0) == 0){
                    isMultipleItemSelected = false;
                    isItemSelected=false;
                }
            }

        });


        holder.switch4.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking) {

                if ((isItemSelected && !serviceList.get(position).isItemSelected()) || isMultipleItemSelected) {
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests");
                } else if (isItemSelected && serviceList.get(position).isItemSelected()) {
                    holder.switch4.setEnabled(false);
                    isItemSelected = false;
                    serviceList.get(position).setItemSelected(false);
                    serviceList.get(position).setCount(serviceList.get(position).getCount() - 1);
                    pushDataK9(serviceList);
                    mListener.onItemClicked(position);
                } else if ((!isItemSelected && !serviceList.get(position).isItemSelected()) || !isMultipleItemSelected) {
                    holder.switch4.setEnabled(true);
                    isItemSelected = true;
                    serviceList.get(position).setItemSelected(true);
                    serviceList.get(position).setCount(serviceList.get(position).getCount() + 1);
                    pushDataK9(serviceList);
                    mListener.onItemClicked(position);
                }
            }else {
                holder.switch4.setOn(true);
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "You don't have an active booking. You can place order only during the stay at property.");

            }

                /*if (isMultipleItemSelected){
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Only one item can be selected, Please raise a new request for different item ");
                }*/

        });

    }

    private void pushDataK9(List<K9Data> serviceList) {

        Set<K9Data> set = new LinkedHashSet<>(serviceList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("K9Services", json);
        GlobalClass.editor.commit();
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,desc,price,tv_quantity;
        ImageView img_add,img_remove;
        MySwitc switch4;
        View bt_single,bt_multiple;
        CardView lyt_counter ;
        CardView lyt_add ;
        TextView bt_add ;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_coorg_sleep_well_item_name);
            desc = itemView.findViewById(R.id.tv_coorg_sleep_well_item_desc);
            price = itemView.findViewById(R.id.tv_coorg_sleep_well_item_price);
            switch4 = itemView.findViewById(R.id.switch4);
            bt_single = itemView.findViewById(R.id.bt_single);
            bt_multiple = itemView.findViewById(R.id.bt_multiple);
            lyt_add =itemView.findViewById(R.id.lyt_add);
            lyt_counter = itemView.findViewById(R.id.lyt_counter);
            bt_add = itemView.findViewById(R.id.bt_add);


            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
        }
    }
}
