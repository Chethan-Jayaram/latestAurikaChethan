package com.mobisprint.aurika.coorg.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.github.angads25.toggle.LabeledSwitch;
import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class AmenitiesAdapter extends RecyclerView.Adapter<AmenitiesAdapter.ViewHolder> {

    private List<Data> amenitiesList;
    private GlobalClass.AmenitiesAdapterListener mListener;
    private Context mContext;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;

    public AmenitiesAdapter(List<Data> amenitiesList, GlobalClass.AmenitiesAdapterListener mListener) {
        this.amenitiesList = amenitiesList;
        this.mListener = mListener;
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



        if (amenitiesList.get(position).getItemselectorType().equalsIgnoreCase("single")){
            holder.bt_single.setVisibility(View.VISIBLE);
            holder.bt_multiple.setVisibility(View.GONE);
            if (amenitiesList.get(position).getCount()>0){
                isItemSelected =true;
                holder.switch4.setOn(true);
            }

        }else if (amenitiesList.get(position).getItemselectorType().equalsIgnoreCase("multi")){
            holder.bt_multiple.setVisibility(View.VISIBLE);
            holder.bt_single.setVisibility(View.GONE);
            if (amenitiesList.get(position).getCount()>0){
                isMultipleItemSelected = true;
            }

        }

        if (amenitiesList.get(position).getCount() == 0){
            holder.lyt_add.setVisibility(View.VISIBLE);
            holder.lyt_counter.setVisibility(View.GONE);
        }else{
            holder.lyt_add.setVisibility(View.GONE);
            holder.lyt_counter.setVisibility(View.VISIBLE);
        }

        holder.bt_add.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking) {

                if (!isItemSelected) {
                    isMultipleItemSelected = true;
                    amenitiesList.get(position).setCount(amenitiesList.get(position).getCount() + 1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    holder.lyt_add.setVisibility(View.GONE);
                    holder.lyt_counter.setVisibility(View.VISIBLE);
                    pushData(amenitiesList);
                    mListener.onItemClicked(position);
                } else {
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests  ");
                }
            }else{
                GlobalClass.ShowAlert(mContext, "Alert", "You don't have an active booking. You can place order only during the stay at property.");

            }

        });

        /*if (amenitiesList.get(position).getPrice() ==null ||
                amenitiesList.get(position).getPrice().isEmpty() ||
                amenitiesList.get(position).getPrice().equals("0.00")){
            holder.tv_item_price.setVisibility(View.GONE);
        }else {
            holder.tv_item_price.setVisibility(View.VISIBLE);
        }*/


        holder.switch4.setOnClickListener(v -> {

            if (GlobalClass.user_active_booking) {

                if ((isItemSelected && !amenitiesList.get(position).isItemSelected()) || isMultipleItemSelected) {
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests  ");
                } else if (isItemSelected && amenitiesList.get(position).isItemSelected()) {
                    holder.switch4.setEnabled(false);
                    isItemSelected = false;
                    amenitiesList.get(position).setItemSelected(false);
                    amenitiesList.get(position).setCount(amenitiesList.get(position).getCount() - 1);
                    pushData(amenitiesList);
                    mListener.onItemClicked(position);
                } else if ((!isItemSelected && !amenitiesList.get(position).isItemSelected()) || !isMultipleItemSelected) {
                    holder.switch4.setEnabled(true);
                    isItemSelected = true;
                    amenitiesList.get(position).setItemSelected(true);
                    amenitiesList.get(position).setCount(amenitiesList.get(position).getCount() + 1);
                    pushData(amenitiesList);
                    mListener.onItemClicked(position);
                }
            }else{
                holder.switch4.setOn(true);
                GlobalClass.ShowAlert(mContext, "Alert", "You don't have an active booking. You can place order only during the stay at property.");

            }

        });


            holder.tv_item_name.setText(amenitiesList.get(position).getTitle());
            holder.tv_item_price.setText("₹"+" "+amenitiesList.get(position).getPrice());
            holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));


            holder.img_add.setOnClickListener(v -> {
                if (amenitiesList.get(position).getMaxCount() !=null){
                    if (amenitiesList.get(position).getCount() < amenitiesList.get(position).getMaxCount()){
                        amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                        mListener.onItemClicked(position);
                        pushData(amenitiesList);
                    }else {
                        GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                    }
                }


            });

            holder.img_remove.setOnClickListener(v -> {
                if (amenitiesList.get(position).getCount() == 1){
                    holder.lyt_add.setVisibility(View.VISIBLE);
                    holder.lyt_counter.setVisibility(View.GONE);
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked(position);
                    pushData(amenitiesList);
                }
                if (amenitiesList.get(position).getCount()>0){
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked(position);
                    pushData(amenitiesList);
                }

                if(amenitiesList.get(position).getCount()==0){
                    if (GlobalClass.sharedPreferences.getInt(GlobalClass.Amenities_count,0) == 0){
                        isMultipleItemSelected = false;
                        isItemSelected=false;
                    }
                }
            });



    }

    private void pushData(List<Data> amenitiesList) {

        Set<Data> set = new LinkedHashSet<>(amenitiesList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("Amenities", json);
        GlobalClass.editor.commit();
    }


    @Override
    public int getItemCount() {
        return amenitiesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name, tv_item_price,tv_quantity;
        ImageView img_add,img_remove;
        CardView add_to_cart;
        View selector,add;
        RelativeLayout lyt_items;
        CardView lyt_counter ;
        CardView lyt_add ;
        TextView bt_add ;
        View bt_single,bt_multiple;
        MySwitc switch4;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
            lyt_items = itemView.findViewById(R.id.lyt_items);
            lyt_add =itemView.findViewById(R.id.lyt_add);
            lyt_counter = itemView.findViewById(R.id.lyt_counter);
            bt_add = itemView.findViewById(R.id.bt_add);
            bt_single = itemView.findViewById(R.id.bt_amen_single);
            bt_multiple = itemView.findViewById(R.id.bt_amen_multiple);
            switch4 = itemView.findViewById(R.id.switch4);

        }
    }
}
