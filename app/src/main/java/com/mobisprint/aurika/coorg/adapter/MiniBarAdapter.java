package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;

import java.util.List;

public class MiniBarAdapter extends RecyclerView.Adapter<MiniBarAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> minibarList;


    public MiniBarAdapter(Context mContext, List<Data> minibarList) {

        this.mContext = mContext;
        this.minibarList = minibarList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_mini_bar_recyclerview,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.item_name.setText(minibarList.get(position).getTitle());
        holder.item_price.setText(minibarList.get(position).getPrice());
        holder.item_quantity.setText(minibarList.get(position).getQuantity());

    }

    @Override
    public int getItemCount() {
        return minibarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_name,item_quantity,item_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            item_name = itemView.findViewById(R.id.item_name);
            item_price = itemView.findViewById(R.id.item_price);
            item_quantity = itemView.findViewById(R.id.item_qty);
        }
    }
}
