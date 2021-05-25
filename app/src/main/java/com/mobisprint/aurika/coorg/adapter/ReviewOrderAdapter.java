package com.mobisprint.aurika.coorg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;

import java.util.List;

public class ReviewOrderAdapter extends RecyclerView.Adapter<ReviewOrderAdapter.ViewHolder> {

    private List<Data> amenitiesList;
    private List<K9Data> k9Amenities,k9Menu;
    private List<Data> houskeepingList;


    public ReviewOrderAdapter(List<Data> amenitiesList,String amenities) {
        this.amenitiesList = amenitiesList;
    }

    public ReviewOrderAdapter(List<K9Data> k9Amenities, int i) {
        this.k9Amenities = k9Amenities;
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

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (amenitiesList != null){
            holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
            holder.tv_item_price.setText(amenitiesList.get(position).getPrice());
            holder.tv_quantity.setText(""+amenitiesList.get(position).getCount());
        }else if (k9Amenities != null){
            holder.tv_item_name.setText(k9Amenities.get(position).getTitle());
            holder.tv_item_price.setText(k9Amenities.get(position).getPrice());
            holder.tv_quantity.setText(""+k9Amenities.get(position).getCount());
        }else if (houskeepingList != null){
            holder.tv_item_name.setText(houskeepingList.get(position).getName());
            holder.tv_item_price.setText(houskeepingList.get(position).getPrice());
            holder.tv_quantity.setText(""+houskeepingList.get(position).getCount());
        }else if (k9Menu != null){
            holder.tv_item_name.setText(k9Menu.get(position).getTitle());
            holder.tv_item_price.setText(k9Menu.get(position).getPrice());
            holder.tv_quantity.setText(""+k9Menu.get(position).getCount());
        }


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


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
        }
    }
}
