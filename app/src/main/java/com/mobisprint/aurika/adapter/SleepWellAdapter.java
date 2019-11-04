package com.mobisprint.aurika.adapter;

import android.content.Context;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.pojo.testing.Item_;
import com.mobisprint.aurika.pojo.testing.Item__;

import java.util.List;

public class SleepWellAdapter extends RecyclerView.Adapter<SleepWellAdapter.ViewHolder> {


   private Context context;
   private List<Item_> titleList;
   private List<Item__> contentList;
   private SleepWellInternalAdapter adapter;

  //  Contentadapter adapter;


    public SleepWellAdapter(Context context, List<Item_> titleList) {
        this.context = context;
        this.titleList = titleList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sleep_well_title_content, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        //holder.weekday.setImageResource(projectModels.get(position).getProjectImage());
        try {
            titleList.get(position).setExpandcontrast(false);
            holder.tv_category_desc.setVisibility(View.GONE);
            holder.item_content_recycler.setVisibility(View.GONE);
            holder.tv_category_title.setText(titleList.get(position).getCategoryTitle());
            holder.tv_category_desc.setText(titleList.get(position).getCategoryDescription());
            holder.img_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_right_arrow));

            holder.lyt_down.setOnClickListener(view -> {
                try {
                    if (titleList.get(position).getExpandcontrast()) {
                        holder.img_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_right_arrow));
                        titleList.get(position).setExpandcontrast(false);
                        holder.tv_category_desc.setVisibility(View.GONE);
                        holder.item_content_recycler.setVisibility(View.GONE);
                    } else {
                        holder.img_arrow.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_down_arrow));
                        titleList.get(position).setExpandcontrast(true);
                        holder.tv_category_desc.setVisibility(View.VISIBLE);
                        holder.item_content_recycler.setVisibility(View.VISIBLE);

                        contentList = titleList.get(position).getItems();
                        adapter = new SleepWellInternalAdapter(context, contentList);
                        holder.item_content_recycler.setHasFixedSize(true);
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(context);
                        holder.item_content_recycler.setLayoutManager(layoutManager);
                        holder.item_content_recycler.setAdapter(adapter);


                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return titleList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

      private  TextView tv_category_title,tv_category_desc;
      private   ImageView img_arrow;
       private RecyclerView item_content_recycler;
     private   LinearLayout lyt_down;

        public ViewHolder(View view) {
            super(view);
            tv_category_title = view.findViewById(R.id.tv_category_title);
            tv_category_desc = view.findViewById(R.id.tv_category_desc);
            img_arrow = view.findViewById(R.id.img_arrow);
            item_content_recycler = view.findViewById(R.id.item_content_recycler);
            lyt_down= view.findViewById(R.id.lyt_down);
        }
    }
}
