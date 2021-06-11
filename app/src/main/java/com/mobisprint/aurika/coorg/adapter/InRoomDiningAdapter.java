package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InRoomDiningAdapter extends RecyclerView.Adapter<InRoomDiningAdapter.ViewHolder> {
    private Context mContext;
    private List<Data> dataList;
    private GlobalClass.AdapterListener mListener;
    DateFormat dateFormat = new SimpleDateFormat("hh:mm a");

    public InRoomDiningAdapter(Context mContext, List<Data> dataList, GlobalClass.AdapterListener mListener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_main_screen, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.title.setText(dataList.get(position).getTitle());
        holder.select.setOnClickListener(view -> {
            mListener.onItemClicked(position);
        });

        if (!GlobalClass.timeComparator(dataList.get(position).getFromTime(), dataList.get(position).getToTime())) {
            holder.select.setClickable(false);
            holder.select.getBackground().setAlpha(175);
        }


    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        RelativeLayout select;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);
            select = itemView.findViewById(R.id.select);
        }
    }
}
