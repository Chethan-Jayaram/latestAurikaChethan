package com.mobisprint.aurika.udaipur.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;

import com.mobisprint.aurika.udaipur.pojo.testing.Item_;

import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SpaSalonAdapter extends RecyclerView.Adapter<SpaSalonAdapter.MyViewHolder> {

    private List<Item_> item_;
    private Context context;
    private  OnItemClickListener listener;

    public SpaSalonAdapter(List<Item_> item_,OnItemClickListener listener) {
        this.item_ = item_;
        this.listener = listener;

    }


    public interface OnItemClickListener {
        void onItemClick(String S1);
    }



    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.spa_saloon_content, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        try {
          //  holder.tv_salon_spa_title.setVisibility(View.GONE);
            holder.tv_salon_spa_title.setText(item_.get(position).getMenuItem());
            holder.img_spa_salon.setOnClickListener(view -> {
                listener.onItemClick(item_.get(position).getMenuItem());
            });
            Glide.with(context).load(item_.get(position).getImageUrl()).into(holder.img_spa_salon);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_salon_spa_title;
        private ImageView img_spa_salon;

        MyViewHolder(View view) {
            super(view);
            img_spa_salon = view.findViewById(R.id.img_spa_salon);
            tv_salon_spa_title = view.findViewById(R.id.tv_salon_spa_title);
        }
    }
}