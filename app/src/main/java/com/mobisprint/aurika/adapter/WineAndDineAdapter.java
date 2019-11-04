package com.mobisprint.aurika.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.pojo.testing.Item_;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class WineAndDineAdapter extends RecyclerView.Adapter<WineAndDineAdapter.MyViewHolder> {

    private List<Item_> item_;
    private Context context;

    public WineAndDineAdapter(List<Item_> item_) {
        this.item_ = item_;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.wine_dine_content, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        try {
            holder.tv_loc_wine_dine.setText(item_.get(position).getLocation());
            holder.tv_time_wine_dine.setText(item_.get(position).getTiming());
            holder.tv_wine_dine_title.setText(item_.get(position).getItemName());
            holder.content_wine_dine_desc.setText(item_.get(position).getItemDescription());
            holder.tv_assistance.setText(item_.get(position).getAssistance());

            Glide.with(context).load(item_.get(position).getImageUrl()).into(holder.img_wine_dine);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_loc_wine_dine, tv_time_wine_dine, tv_wine_dine_title, content_wine_dine_desc, tv_assistance;
        private ImageView img_wine_dine;

        MyViewHolder(View view) {
            super(view);
            tv_loc_wine_dine = view.findViewById(R.id.tv_loc_wine_dine);
            tv_time_wine_dine = view.findViewById(R.id.tv_time_wine_dine);
            tv_wine_dine_title = view.findViewById(R.id.tv_wine_dine_title);
            content_wine_dine_desc = view.findViewById(R.id.tv_content_wine_dine_desc);
            img_wine_dine = view.findViewById(R.id.img_wine_dine);
            tv_assistance = view.findViewById(R.id.tv_assistance);
        }
    }
}