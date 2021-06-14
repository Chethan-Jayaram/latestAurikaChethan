package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReviewOrderAdapter extends RecyclerView.Adapter<ReviewOrderAdapter.ViewHolder> {

    private List<Data> amenitiesList;
    private List<K9Data> k9Amenities,k9Menu;
    private List<Data> houskeepingList;
    private GlobalClass.AmenitiesAdapterListener mListener;
    private GlobalClass.K9AdapterListener k9Listener;
    private Context mContext;

    public ReviewOrderAdapter(List<Data> amenitiesList,String amenities,GlobalClass.AmenitiesAdapterListener mListener) {
        this.amenitiesList = amenitiesList;
        this.mListener = mListener;
    }

    public ReviewOrderAdapter(List<K9Data> k9Amenities, int i,GlobalClass.K9AdapterListener k9Listener) {
        this.k9Amenities = k9Amenities;
        this.k9Listener = k9Listener;
    }

    public ReviewOrderAdapter(List<Data> houskeepingList) {
        this.houskeepingList = houskeepingList;
    }

    public ReviewOrderAdapter(List<K9Data> k9Menu, String k9Menu1, int i) {
        this.k9Menu = k9Menu;
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

           /* if (amenitiesList.get(position).getPrice() ==null ||
                    amenitiesList.get(position).getPrice().isEmpty() ||
                    amenitiesList.get(position).getPrice().equals("0.00")){
                holder.tv_item_price.setVisibility(View.GONE);
            }else {
                holder.tv_item_price.setVisibility(View.VISIBLE);
            }*/

            if (amenitiesList.get(position).getCount()>0){
                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
                holder.tv_item_price.setText(amenitiesList.get(position).getPrice());
                holder.tv_quantity.setText(""+amenitiesList.get(position).getCount());

                holder.img_add.setOnClickListener(v -> {
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked( amenitiesList);
                    pushData(amenitiesList);
                });

                holder.img_remove.setOnClickListener(v -> {
                    if (amenitiesList.get(position).getCount() ==1 ){
                        Alert(mContext,position);
                    } else if (amenitiesList.get(position).getCount()>0){
                        amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                        mListener.onItemClicked( amenitiesList);
                        pushData(amenitiesList);
                    }
                });

            }

        }else if (k9Amenities != null){

            if (k9Amenities.get(position).getCount()>0){
                holder.lyt_items.setVisibility(View.VISIBLE);
                holder.tv_item_name.setText(k9Amenities.get(position).getTitle());
                holder.tv_item_price.setText(k9Amenities.get(position).getPrice());
                holder.tv_quantity.setText(""+k9Amenities.get(position).getCount());

                holder.img_add.setOnClickListener(v -> {
                    k9Amenities.get(position).setCount( k9Amenities.get(position).getCount()+1);
                    holder.tv_quantity.setText(Integer.toString(k9Amenities.get(position).getCount()));
                    k9Listener.onItemClicked( k9Amenities);
                    pushDataK9(k9Amenities);
                });

                holder.img_remove.setOnClickListener(v -> {
                    if (k9Amenities.get(position).getCount() ==1 ){
                       K9Alert(mContext,position);

                    } else if (k9Amenities.get(position).getCount()>0){
                        k9Amenities.get(position).setCount( k9Amenities.get(position).getCount()-1);
                        holder.tv_quantity.setText(Integer.toString(k9Amenities.get(position).getCount()));
                        k9Listener.onItemClicked( k9Amenities);
                        pushDataK9(k9Amenities);
                    }
                });
            }



        }else if (houskeepingList != null){

            /*if (houskeepingList.get(position).getPrice() ==null ||
                    houskeepingList.get(position).getPrice().isEmpty() ||
                    houskeepingList.get(position).getPrice().equals("0.00")){
                holder.tv_item_price.setVisibility(View.GONE);
            }else {
                holder.tv_item_price.setVisibility(View.VISIBLE);
            }*/
            holder.lyt_items.setVisibility(View.VISIBLE);
            holder.tv_item_name.setText(houskeepingList.get(position).getName());
            holder.tv_item_price.setText(houskeepingList.get(position).getPrice());
            holder.tv_quantity.setText(""+houskeepingList.get(position).getCount());
        }else if (k9Menu != null){
            holder.lyt_items.setVisibility(View.VISIBLE);
            holder.tv_item_name.setText(k9Menu.get(position).getTitle());
            holder.tv_item_price.setText(k9Menu.get(position).getPrice());
            holder.tv_quantity.setText(""+k9Menu.get(position).getCount());
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


    private void Alert(Context mContext,int position) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    amenitiesList.get(position).setCount(amenitiesList.get(position).getCount() - 1);
                    pushData(amenitiesList);
                    mListener.onItemClicked(amenitiesList);
                    notifyDataSetChanged();

                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        alert.show();
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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            lyt_items = itemView.findViewById(R.id.lyt_items);
        }
    }
}
