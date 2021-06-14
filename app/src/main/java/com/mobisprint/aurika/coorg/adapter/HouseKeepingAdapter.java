package com.mobisprint.aurika.coorg.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public  class HouseKeepingAdapter extends RecyclerView.Adapter<HouseKeepingAdapter.ViewHolder> {

    List<Data> houseKeepingList;
    private GlobalClass.AdapterListener mListener;

    public HouseKeepingAdapter(List<Data> houseKeepingList,GlobalClass.AdapterListener mListener) {

        this.houseKeepingList = houseKeepingList;
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

        holder.tv_item_name.setText(houseKeepingList.get(position).getName());
        holder.tv_item_price.setText("₹"+" "+houseKeepingList.get(position).getPrice());
        holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));

        holder.img_add.setOnClickListener(v -> {
            houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()+1);
            holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
            mListener.onItemClicked(position);
        });

        /*if (houseKeepingList.get(position).getPrice() ==null ||
                houseKeepingList.get(position).getPrice().isEmpty() ||
                houseKeepingList.get(position).getPrice().equals("0.00")){
            holder.tv_item_price.setVisibility(View.GONE);
        }else {
            holder.tv_item_price.setVisibility(View.VISIBLE);
        }*/

        holder.img_remove.setOnClickListener(v -> {
            if (houseKeepingList.get(position).getCount()>0){
                houseKeepingList.get(position).setCount( houseKeepingList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(houseKeepingList.get(position).getCount()));
                mListener.onItemClicked(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return houseKeepingList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_item_name, tv_item_price,tv_quantity;
        ImageView img_add,img_remove;
        CardView add_to_cart;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_amenities_item_name);
            tv_item_price = itemView.findViewById(R.id.tv_amenities_item_price);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            add_to_cart = itemView.findViewById(R.id.add_to_cart);
        }
    }
}
