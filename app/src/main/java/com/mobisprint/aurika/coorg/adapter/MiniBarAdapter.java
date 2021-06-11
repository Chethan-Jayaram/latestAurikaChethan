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



    }

    @Override
    public int getItemCount() {
        return minibarList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView item_name,item_quantity,item_price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);


        }
    }
}
