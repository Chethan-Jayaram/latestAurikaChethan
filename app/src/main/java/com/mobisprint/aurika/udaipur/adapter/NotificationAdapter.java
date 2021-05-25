package com.mobisprint.aurika.udaipur.adapter;

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
import com.mobisprint.aurika.udaipur.pojo.notification.Result;


import org.jetbrains.annotations.NotNull;


import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.MyViewHolder> {

    private List<Result> item_;
    private Context context;



    public NotificationAdapter(List<Result> item_) {
        this.item_ = item_;
    }



    @NotNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.notification_content, parent, false);
        context=parent.getContext();
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        try{


            holder.tv_notification_title.setText(item_.get(position).getTitle());
            holder.tv_notification_short_desc.setText(item_.get(position).getShortDescription());
          if(item_.get(position).getImage().isEmpty()) {
              holder.notification_img.setVisibility(View.GONE);
          }else{
              Glide.with(context).load(item_.get(position).getImage()).into(holder.notification_img);
          }

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return item_.size();
    }


    class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_notification_title, tv_notification_short_desc;
        private ImageView notification_img;
        private LinearLayout notification_card;
        MyViewHolder(View view) {
            super(view);
            tv_notification_title = view.findViewById(R.id.tv_notification_title);
            tv_notification_short_desc = view.findViewById(R.id.tv_notification_short_desc);
            notification_img= view.findViewById(R.id.notification_img);
            notification_card= view.findViewById(R.id.notification_card);
        }
    }
}