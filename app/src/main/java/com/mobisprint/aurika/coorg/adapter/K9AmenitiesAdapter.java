package com.mobisprint.aurika.coorg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class K9AmenitiesAdapter extends RecyclerView.Adapter<K9AmenitiesAdapter.ViewHolder> {

    private List<K9Data> amenitiesList;
    private GlobalClass.AdapterListener mListener;


    public K9AmenitiesAdapter(List<K9Data> amenitiesList, GlobalClass.AdapterListener mListener) {
        this.amenitiesList = amenitiesList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.amenities_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
        holder.tv_item_price.setText("₹"+" "+amenitiesList.get(position).getPrice());
        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));


        holder.img_add.setOnClickListener(v -> {
            amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
            holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
            mListener.onItemClicked(position);
        });

        holder.img_remove.setOnClickListener(v -> {
            if (amenitiesList.get(position).getCount()>0){
                amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                mListener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {

        if (amenitiesList != null){
            return amenitiesList.size();
        }

        return amenitiesList.size();
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
