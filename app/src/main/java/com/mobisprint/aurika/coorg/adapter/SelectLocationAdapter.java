package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.location.Data;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

public class SelectLocationAdapter extends RecyclerView.Adapter<SelectLocationAdapter.ViewHolder> {

   private List<Data> mSelectLocation;
   private Context mContext;

    public SelectLocationAdapter(Context context,List<Data> selectLocation) {
        this.mSelectLocation=selectLocation;
        this.mContext=context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.location_recyclerview,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SelectLocationAdapter.ViewHolder holder, int position) {


        Glide.with(mContext)
                .load(mSelectLocation.get(position).getImage())
                .into(holder.image);

        holder.tv_location_title.setText(mSelectLocation.get(position).getTitle());
        holder.image.setOnClickListener(view -> {
            try {
                Class<?> className = Class.forName(RouteName.getLocationRoutes(mSelectLocation.get(position).getRouteName()));
                view.getContext().startActivity(new Intent(view.getContext(), className));

            } catch (Exception e) {
                e.printStackTrace();
            }
        });

    }


    @Override
    public int getItemCount() {
        Log.d("size", String.valueOf(mSelectLocation.size()));
        return mSelectLocation.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        private TextView tv_location_title;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.img_location);
            tv_location_title = itemView.findViewById(R.id.tv_location_title);
        }
    }
}
