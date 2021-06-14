package com.mobisprint.aurika.coorg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class K9AmenitiesAdapter extends RecyclerView.Adapter<K9AmenitiesAdapter.ViewHolder> {

    private List<K9Data> amenitiesList;
    private GlobalClass.K9AdapterListener mListener;


    public K9AmenitiesAdapter(List<K9Data> amenitiesList, GlobalClass.K9AdapterListener mListener) {
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


        if (amenitiesList.get(position).getCount() == 0){
            holder.lyt_add.setVisibility(View.VISIBLE);
            holder.lyt_counter.setVisibility(View.GONE);
        }else{
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        }

        holder.bt_add.setOnClickListener(v -> {
            amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
            holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
            mListener.onItemClicked(amenitiesList);
            pushDataK9(amenitiesList);
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        });


        if (amenitiesList.get(position).getPrice() ==null ||
                amenitiesList.get(position).getPrice().isEmpty() ||
                amenitiesList.get(position).getPrice().equals("0.00")){
            holder.tv_item_price.setVisibility(View.GONE);
        }else {
            holder.tv_item_price.setVisibility(View.VISIBLE);
        }

            holder.lyt_items.setVisibility(View.VISIBLE);
            holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
            holder.tv_item_price.setText("₹"+" "+amenitiesList.get(position).getPrice());
            holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));


            holder.img_add.setOnClickListener(v -> {
                amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                mListener.onItemClicked(amenitiesList);
                pushDataK9(amenitiesList);
            });

            holder.img_remove.setOnClickListener(v -> {
                if (amenitiesList.get(position).getCount() == 1){
                    holder.lyt_add.setVisibility(View.VISIBLE);
                    holder.lyt_counter.setVisibility(View.GONE);
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked(amenitiesList);
                    pushDataK9(amenitiesList);
                }
                if (amenitiesList.get(position).getCount()>0){
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked(amenitiesList);
                    pushDataK9(amenitiesList);
                }
            });




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
        }

        return amenitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name, tv_item_price,tv_quantity;
        ImageView img_add,img_remove;
        RelativeLayout lyt_items;
        CardView lyt_counter ;
        CardView lyt_add ;
        TextView bt_add ;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            lyt_items = itemView.findViewById(R.id.lyt_items);
            lyt_add =itemView.findViewById(R.id.lyt_add);
            lyt_counter = itemView.findViewById(R.id.lyt_counter);
            bt_add = itemView.findViewById(R.id.bt_add);
        }
    }
}
