package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.winedine.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class WineAndDineAdapter extends RecyclerView.Adapter<WineAndDineAdapter.ViewHolder> {

    private List<Data> wineAndDineList;
    private Context mContext;
    private GlobalClass.AdapterListener mListener;

    public WineAndDineAdapter(Context mContext, List<Data> wineAndDineList,GlobalClass.AdapterListener mListener) {
        this.wineAndDineList = wineAndDineList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_wine_n_dine,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_wine_dine_title.setText(wineAndDineList.get(position).getTitle());
        holder.tv_content_wine_dine_desc.setText(wineAndDineList.get(position).getDescription());
        holder.tv_assistance.setText("Please call 2005 for assistance");
        /*holder.tv_from_time_wine_dine.setText(wineAndDineList.get(position).getFromTime());*/
       /* holder.tv_to_time_wine_dine.setText(wineAndDineList.get(position).getToTime());*/
        Glide.with(mContext).load(wineAndDineList.get(position).getImage()).into(holder.img_coorg_wine_dine);

        try{
            SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
            SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
            Date _24HourDt1 = _24HourSDF.parse(wineAndDineList.get(position).getFromTime());
            Date _24HourDt2 = _24HourSDF.parse(wineAndDineList.get(position).getToTime());
            holder.tv_from_time_wine_dine.setText(_12HourSDF.format(_24HourDt1));
            holder.tv_to_time_wine_dine.setText(_12HourSDF.format(_24HourDt2));
        }catch (Exception e){
            e.printStackTrace();
        }


        holder.bt_wine_n_dine_reserve_table.setOnClickListener(v -> {
            mListener.onItemClicked(position);
        });

    }

    @Override
    public int getItemCount() {
        return wineAndDineList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView tv_wine_dine_title,tv_content_wine_dine_desc,tv_assistance,tv_from_time_wine_dine,tv_to_time_wine_dine;
        ImageView img_coorg_wine_dine;
        Button bt_wine_n_dine_reserve_table;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_wine_dine_title = itemView.findViewById(R.id.tv_wine_dine_title);
            tv_content_wine_dine_desc = itemView.findViewById(R.id.tv_content_wine_dine_desc);
            tv_assistance = itemView.findViewById(R.id.tv_coorg_assistance);
            tv_from_time_wine_dine = itemView.findViewById(R.id.tv_from_time_wine_dine);
            img_coorg_wine_dine = itemView.findViewById(R.id.img_coorg_wine_dine);
            tv_to_time_wine_dine = itemView.findViewById(R.id.tv_to_time_wine_dine);
            bt_wine_n_dine_reserve_table = itemView.findViewById(R.id.bt_wine_n_dine_reserve_table);

        }
    }
}
