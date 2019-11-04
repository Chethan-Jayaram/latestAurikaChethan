package com.mobisprint.aurika.adapter;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.pojo.testing.Item_;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.MyViewHolder> {

    private List<Item_> item_;

    public ServiceAdapter(List<Item_> item_) {
        this.item_ = item_;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.service_content, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        try{
        holder.tv_serviceDescription.setText(item_.get(position).getServiceDescription());
        holder.tv_serviceTitle.setText(item_.get(position).getServiceTitle());
//        Glide.with(context).load(imageUrls.get(i).getImageUrl()).into(viewHolder.img);
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_serviceDescription, tv_serviceTitle;

         MyViewHolder(View view) {
            super(view);
            tv_serviceDescription = view.findViewById(R.id.tv_serviceDescription);
            tv_serviceTitle = view.findViewById(R.id.tv_serviceTitle);
        }
    }
}