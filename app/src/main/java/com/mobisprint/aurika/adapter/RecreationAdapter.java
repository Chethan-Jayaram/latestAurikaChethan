package com.mobisprint.aurika.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.pojo.testing.Item_;

import java.util.List;

public class RecreationAdapter extends RecyclerView.Adapter<RecreationAdapter.MyViewHolder> {

    private List<Item_> item_;
    private Context context;
    private  String subtitle;

    public RecreationAdapter(List<Item_> item_,String subtitle) {
        this.item_ = item_;
        this.subtitle=subtitle;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recreation_content, parent, false);
        context = parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            try{
            holder.tv_recretion_description.setText(item_.get(position).getItemDescription());

            Glide.with(context).load(item_.get(position).getImageUrl()).into(holder.img_recreation);
            holder.tv_recretion_timing.setText(item_.get(position).getTiming());
            holder.tv_recretion_location.setText(item_.get(position).getLocation());
            holder.tv_recretion_title.setText(item_.get(position).getItemName());
            if(position==0){
                holder.lyt_tv_desc.setVisibility(View.VISIBLE);
                holder.tv_subtitle_description.setText(subtitle);
            }
            }catch (Exception e){
                e.printStackTrace();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


     class MyViewHolder extends RecyclerView.ViewHolder

    {
        private  TextView tv_recretion_description, tv_recretion_timing, tv_recretion_location, tv_recretion_title,tv_subtitle_description;
        private ImageView img_recreation;
        private LinearLayout lyt_tv_desc;

         MyViewHolder(View view) {
        super(view);
            tv_subtitle_description= view.findViewById(R.id.tv_subtitle_description);
            lyt_tv_desc= view.findViewById(R.id.lyt_tv_desc);
        img_recreation = view.findViewById(R.id.img_recreation);
        tv_recretion_description = view.findViewById(R.id.tv_recretion_description);
        tv_recretion_timing = view.findViewById(R.id.tv_recretion_timing);
        tv_recretion_location = view.findViewById(R.id.tv_recretion_location);
        tv_recretion_title = view.findViewById(R.id.tv_recretion_title);
    }
    }
}
