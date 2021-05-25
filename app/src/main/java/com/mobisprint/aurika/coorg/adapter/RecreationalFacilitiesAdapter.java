package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.recreational.Data;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreation;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class RecreationalFacilitiesAdapter extends RecyclerView.Adapter<RecreationalFacilitiesAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> recreationalList;


    public RecreationalFacilitiesAdapter(Context mContext, List<Data> recreationalList) {

        this.mContext = mContext;
        this.recreationalList = recreationalList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.coorg_recreational_content,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_recretion_title.setText(recreationalList.get(position).getTitle());
        holder.tv_recretion_description.setText(recreationalList.get(position).getDescription());
        /*holder.tv_recretion_start_timing.setText(recreationalList.get(position).getFromTime());
        holder.tv_recretion_end_timing.setText(recreationalList.get(position).getToTime());*/


        try{
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt1 = _24HourSDF.parse(recreationalList.get(position).getFromTime());
            Date _24HourDt2 = _24HourSDF.parse(recreationalList.get(position).getToTime());
            holder.tv_recretion_start_timing.setText(_12HourSDF.format(_24HourDt1));
            holder.tv_recretion_end_timing.setText(_12HourSDF.format(_24HourDt2));
        }catch (Exception e){
            e.printStackTrace();
        }


        Glide.with(mContext).load(recreationalList.get(position).getImage()).centerCrop().into(holder.img_recreation);
    }

    @Override
    public int getItemCount() {
        return recreationalList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_subtitle_description,tv_recretion_title,tv_recretion_description,tv_recretion_start_timing,tv_recretion_end_timing;
        ImageView img_recreation;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_subtitle_description = itemView.findViewById(R.id.tv_coorg_subtitle_description);
            tv_recretion_title = itemView.findViewById(R.id.tv_coorg_recretion_title);
            tv_recretion_description = itemView.findViewById(R.id.tv_coorg_recretion_description);
            tv_recretion_start_timing = itemView.findViewById(R.id.tv_coorg_recretion_start_timing);
            tv_recretion_end_timing = itemView.findViewById(R.id.tv_coorg_recretion_end_timing);

            img_recreation = itemView.findViewById(R.id.img_coorg_recreation);

        }
    }
}
