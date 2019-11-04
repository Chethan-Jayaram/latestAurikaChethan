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

public class SightSeeingAdapter extends RecyclerView.Adapter<SightSeeingAdapter.MyViewHolder> {

    private List<Item_> item_;
    private  String subtitle;
    private Context context;

    public SightSeeingAdapter(List<Item_> item_) {
        this.item_ = item_;
        this.subtitle=subtitle;
    }

    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.sight_seeing_content, parent, false);
        context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        try{
        holder.place_title.setText(item_.get(position).getItemName());
        holder.place_description.setText(item_.get(position).getItemDescription());
      Glide.with(context).load(item_.get(position).getImageUrl()).into(holder.img_sight_seeing);
    }catch (Exception e){
        e.printStackTrace();
    }
    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder {
        private  TextView  place_title, place_description;
        private ImageView img_sight_seeing;
         MyViewHolder(View view) {
            super(view);
            place_title = view.findViewById(R.id.place_title);
            place_description = view.findViewById(R.id.place_description);
            img_sight_seeing = view.findViewById(R.id.img_sight_seeing);

        }
    }
}