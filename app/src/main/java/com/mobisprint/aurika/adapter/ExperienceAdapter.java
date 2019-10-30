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

import java.util.List;

public class ExperienceAdapter extends RecyclerView.Adapter<ExperienceAdapter.MyViewHolder> {

    private List<Item_> item_;
    private Context context;

    public ExperienceAdapter(List<Item_> item_) {
        this.item_ = item_;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.expereinces_content, parent, false);
        context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try{
        holder.tv_experiences_descrioption.setText(item_.get(position).getItemDescription());
        holder.tv_experiences_title.setText(item_.get(position).getItemName());
        Glide.with(context).load(item_.get(position).getImageUrl()).into(holder.img_experiences);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_experiences_title, tv_experiences_descrioption;
        private ImageView img_experiences;
         MyViewHolder(View view) {
            super(view);
             tv_experiences_descrioption = view.findViewById(R.id.tv_experiences_descrioption);
             tv_experiences_title = view.findViewById(R.id.tv_experiences_title);
             img_experiences= view.findViewById(R.id.img_experiences);
        }
    }
}