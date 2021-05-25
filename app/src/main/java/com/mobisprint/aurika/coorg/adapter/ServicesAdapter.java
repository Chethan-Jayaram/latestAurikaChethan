package com.mobisprint.aurika.coorg.adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.ServicesList;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

public class ServicesAdapter extends RecyclerView.Adapter<ServicesAdapter.ViewHolder> {

    private List<Data> servicesList;
    private GlobalClass.AdapterListener mListener;

    public ServicesAdapter(List<Data> servicesList, GlobalClass.AdapterListener mListener) {

        this.servicesList = servicesList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ServicesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_main_screen,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServicesAdapter.ViewHolder holder, int position) {

        holder.title.setText(servicesList.get(position).getTitle());
        holder.select.setOnClickListener(view -> {
          mListener.onItemClicked(position);
        });

    }

    @Override
    public int getItemCount() {
        Log.d("AdapterSize", String.valueOf(servicesList.size()));
        return servicesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        RelativeLayout select;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            select = itemView.findViewById(R.id.select);
        }
    }
}
