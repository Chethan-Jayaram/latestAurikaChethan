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

public class K9MenuAdapter extends RecyclerView.Adapter<K9MenuAdapter.ViewHolder> {

    private Context context;
    private List<K9Data> menuList;
    private GlobalClass.AdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;

    public K9MenuAdapter(Context context, List<K9Data> menuList,GlobalClass.AdapterListener mListener) {
        this.context = context;
        this.menuList = menuList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.k9_menu_internal_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {



        holder.title.setText(menuList.get(position).getTitle());
        holder.desc.setVisibility(View.VISIBLE);
        holder.desc.setText(menuList.get(position).getDescription());
        holder.price.setText("₹ "+menuList.get(position).getPrice());
        holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));


        if (menuList.get(position).getCount() == 0){
            holder.lyt_add.setVisibility(View.VISIBLE);
            holder.lyt_counter.setVisibility(View.GONE);
        }else{
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        }

        holder.bt_add.setOnClickListener(v -> {
            if (!isItemSelected){
                isMultipleItemSelected = true;
                menuList.get(position).setCount( menuList.get(position).getCount()+1);
                holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
                holder.lyt_add.setVisibility(View.GONE);
                holder.lyt_counter.setVisibility(View.VISIBLE);
                pushDataK9(menuList);
                mListener.onItemClicked(position);
            }else{
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests");
            }

        });


        if (menuList.get(position).getItemselectorType().equalsIgnoreCase("single")){
            holder.bt_single.setVisibility(View.VISIBLE);
            holder.bt_multiple.setVisibility(View.GONE);
            if (menuList.get(position).getCount()>0){
                isItemSelected =true;
                holder.switch4.setOn(true);
            }

            }else if (menuList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
            holder.bt_multiple.setVisibility(View.VISIBLE);
            holder.bt_single.setVisibility(View.GONE);
            if (menuList.get(position).getCount()>0){
                isMultipleItemSelected = true;
            }

        }


        holder.img_add.setOnClickListener(v -> {

            if (menuList.get(position).getMaxCount() != null){

                if (menuList.get(position).getCount() < menuList.get(position).getMaxCount()){
                    isMultipleItemSelected=true;
                    isItemSelected = false;
                    menuList.get(position).setCount( menuList.get(position).getCount()+1);
                    holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
                    pushDataK9(menuList);
                    mListener.onItemClicked(position);
                }else if (menuList.get(position).getCount() == menuList.get(position).getMaxCount()){
                    GlobalClass.ShowAlert(context,"Alert","Maximum count for this item has been reached");
                }
            }




        });

        holder.img_remove.setOnClickListener(v -> {
            if (menuList.get(position).getCount() == 1){
                holder.lyt_add.setVisibility(View.VISIBLE);
                holder.lyt_counter.setVisibility(View.GONE);
                menuList.get(position).setCount( menuList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
                pushDataK9(menuList);
                mListener.onItemClicked(position);
            }
            if (menuList.get(position).getCount()>0){
                menuList.get(position).setCount( menuList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
                pushDataK9(menuList);
                mListener.onItemClicked(position);
            }
            if(menuList.get(position).getCount()==0){
                if (GlobalClass.sharedPreferences.getInt(GlobalClass.K9Menu_count,0) == 0){
                    isMultipleItemSelected = false;
                    isItemSelected=false;
                }
            }

        });


        holder.switch4.setOnClickListener(v -> {

                if ((isItemSelected && !menuList.get(position).isItemSelected()) || isMultipleItemSelected) {
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests");
                } else if (isItemSelected && menuList.get(position).isItemSelected() ){
                    holder.switch4.setEnabled(false);
                    isItemSelected = false;
                    menuList.get(position).setItemSelected(false);
                    menuList.get(position).setCount( menuList.get(position).getCount()-1);
                    pushDataK9(menuList);
                    mListener.onItemClicked(position);
                } else if ((!isItemSelected && !menuList.get(position).isItemSelected()) || !isMultipleItemSelected) {
                    holder.switch4.setEnabled(true);
                    isItemSelected = true;
                    menuList.get(position).setItemSelected(true);
                    menuList.get(position).setCount( menuList.get(position).getCount()+1);
                    pushDataK9(menuList);
                    mListener.onItemClicked(position);
                }

                /*if (isMultipleItemSelected){
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Only one item can be selected, Please raise a new request for different item ");
                }*/

        });


    }

    private void pushDataK9(List<K9Data> k9Data) {

        Set<K9Data> set = new LinkedHashSet<>(k9Data);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("K9Menu", json);
        GlobalClass.editor.commit();
    }

    @Override
    public int getItemCount() {
        return menuList.size();
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
