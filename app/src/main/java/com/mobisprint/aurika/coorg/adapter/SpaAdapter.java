package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.spa.Data;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class SpaAdapter extends RecyclerView.Adapter<SpaAdapter.ViewHolder> {


    private List<Data> spaList;
    private Context mContext;

    public SpaAdapter(Context mContext, List<Data> spaList) {
        this.spaList = spaList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.spa,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_spa_title.setText(spaList.get(position).getTitle());
        holder.tv_spa_assistance.setText("Please call 2001 for assiatnce");
        /*holder.tv_spa_start_timing.setText(spaList.get(position).getFromTime());
        holder.tv_spa_end_timing.setText(spaList.get(position).getToTime());*/
        Glide.with(mContext).load(spaList.get(position).getImage()).centerCrop().into(holder.img_spa);

        try{
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt1 = _24HourSDF.parse(spaList.get(position).getFromTime());
            Date _24HourDt2 = _24HourSDF.parse(spaList.get(position).getToTime());
            holder.tv_spa_start_timing.setText(_12HourSDF.format(_24HourDt1));
            holder.tv_spa_end_timing.setText(_12HourSDF.format(_24HourDt2));
        }catch (Exception e){
            e.printStackTrace();
        }

        holder.btn_spa_menu.setOnClickListener(v -> {
            
        });

    }


    @Override
    public int getItemCount() {
        return spaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_spa_title,tv_spa_assistance,tv_spa_end_timing,tv_spa_start_timing;
        Button btn_spa_menu,btn_spa_appointment;
        ImageView img_spa;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_spa_title = itemView.findViewById(R.id.tv_spa_title);
            tv_spa_assistance = itemView.findViewById(R.id.tv_spa_assistance);
            tv_spa_start_timing = itemView.findViewById(R.id.tv_spa_start_timing);
            tv_spa_end_timing = itemView.findViewById(R.id.tv_spa_end_timing);
            btn_spa_menu = itemView.findViewById(R.id.btn_spa_menu);
            btn_spa_appointment = itemView.findViewById(R.id.btn_spa_appointment);
            img_spa = itemView.findViewById(R.id.img_spa);
        }
    }
}
