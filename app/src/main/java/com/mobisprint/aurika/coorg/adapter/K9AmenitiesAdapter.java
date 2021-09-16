package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.text.Html;
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
import com.mobisprint.aurika.helper.MySwitc;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class K9AmenitiesAdapter extends RecyclerView.Adapter<K9AmenitiesAdapter.ViewHolder> {

    private List<K9Data> amenitiesList;
    private GlobalClass.AdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;
    private Context mContext;


    public K9AmenitiesAdapter(List<K9Data> amenitiesList, GlobalClass.AdapterListener mListener) {
        this.amenitiesList = amenitiesList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.k9_amenities_recyclerview,parent,false);
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
            if (!isItemSelected){
                isMultipleItemSelected = true;
                amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                mListener.onItemClicked(position);
                pushDataK9(amenitiesList);
                holder.lyt_add.setVisibility(View.GONE);
                holder.lyt_counter.setVisibility(View.VISIBLE);
            }else{
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", " Please place individual orders for individual requests");
            }

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

           /* holder.tv_item_name.setText(amenitiesList.get(position).getTitle()+Html.fromHtml(
                     "<p style=\"font-family:notosanstc_regular;font-size:2px;\">"+"(and services)</p>"));*/

            holder.tv_item_price.setText("₹"+" "+amenitiesList.get(position).getPrice());
            holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));


            holder.img_add.setOnClickListener(v -> {
                if (amenitiesList.get(position).getMaxCount() != null){

                    if (amenitiesList.get(position).getCount() < amenitiesList.get(position).getMaxCount()){
                        isMultipleItemSelected=true;
                        isItemSelected = false;
                        amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                        holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                        mListener.onItemClicked(position);
                        pushDataK9(amenitiesList);
                    }else if (amenitiesList.get(position).getCount() == amenitiesList.get(position).getMaxCount()){
                        GlobalClass.ShowAlert(holder.itemView.getContext(),"Alert","Maximum count for this item has been reached");
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
                    pushDataK9(amenitiesList);
                }
                if (amenitiesList.get(position).getCount()>0){
                    amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                    holder.tv_quantity.setText(Integer.toString(amenitiesList.get(position).getCount()));
                    mListener.onItemClicked(position);
                    pushDataK9(amenitiesList);
                }
                if(amenitiesList.get(position).getCount()==0){
                    if (GlobalClass.sharedPreferences.getInt(GlobalClass.K9Amenities_count,0) == 0){
                        isMultipleItemSelected = false;
                        isItemSelected=false;
                    }
                }
            });


        holder.switch4.setOnClickListener(v -> {

            if ((isItemSelected && !amenitiesList.get(position).isItemSelected()) || isMultipleItemSelected) {
                holder.switch4.setOn(true);
                GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Please place individual orders for individual requests");
            } else if (isItemSelected && amenitiesList.get(position).isItemSelected() ){
                holder.switch4.setEnabled(false);
                isItemSelected = false;
                amenitiesList.get(position).setItemSelected(false);
                amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()-1);
                pushDataK9(amenitiesList);
                mListener.onItemClicked(position);
            } else if ((!isItemSelected && !amenitiesList.get(position).isItemSelected()) || !isMultipleItemSelected) {
                holder.switch4.setEnabled(true);
                isItemSelected = true;
                amenitiesList.get(position).setItemSelected(true);
                amenitiesList.get(position).setCount( amenitiesList.get(position).getCount()+1);
                pushDataK9(amenitiesList);
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

        MySwitc switch4;
        View bt_single,bt_multiple;

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
            switch4 = itemView.findViewById(R.id.switch4);
            bt_single = itemView.findViewById(R.id.bt_amen_single);
            bt_multiple = itemView.findViewById(R.id.bt_amen_multiple);
        }
    }
}
