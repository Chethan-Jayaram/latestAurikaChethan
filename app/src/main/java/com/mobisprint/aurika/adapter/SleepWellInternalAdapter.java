package com.mobisprint.aurika.adapter;

import android.content.Context;
import android.graphics.Typeface;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;

import com.mobisprint.aurika.pojo.testing.Item__;

import java.util.List;

public class SleepWellInternalAdapter extends RecyclerView.Adapter<SleepWellInternalAdapter.ViewHolder> {


    private Context context;

    private List<Item__> contentList;

    //  Contentadapter adapter;


    public SleepWellInternalAdapter(Context context, List<Item__> contentList) {
        this.context = context;
        this.contentList = contentList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_internal_content_sleepwell, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //holder.weekday.setImageResource(projectModels.get(position).getProjectImage());
        try {

            holder.img_veg_nonveg.setVisibility(View.GONE);
            holder.tv_item_name.setText(contentList.get(position).getServiceTitle());

            if (contentList.get(position).getServiceDescription().isEmpty()) {
                holder.tv_item_desc.setVisibility(View.GONE);
            } else {
                holder.tv_item_desc.setText(contentList.get(position).getServiceDescription());
            }
            Typeface face = Typeface.createFromAsset(context.getAssets(), "Rupee_Foradian.ttf");
            holder.tv_item_price.setTypeface(face);
            holder.tv_item_price.setText(contentList.get(position).getServicePrice());
            if (contentList.get(position).getServicePrice().isEmpty()) {
                holder.tv_item_price.setText("");
            } else {
                holder.tv_item_price.setText(context.getResources().getString(R.string.rs) + contentList.get(position).getServicePrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return contentList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_name, tv_item_desc, tv_item_price;
        private ImageView img_veg_nonveg;

        public ViewHolder(View view) {
            super(view);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_desc = view.findViewById(R.id.tv_item_desc);
            tv_item_price = view.findViewById(R.id.tv_item_price);
            img_veg_nonveg = view.findViewById(R.id.img_veg_nonveg);
        }
    }
}
