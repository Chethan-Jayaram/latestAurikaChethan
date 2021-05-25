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
import com.mobisprint.aurika.coorg.pojo.experiences.Data;

import java.util.List;

public class ExclusiveExperiencesAdapter extends RecyclerView.Adapter<ExclusiveExperiencesAdapter.ViewHolder> {
    private Context mContext;
    private List<Data> experiencesData;
    public ExclusiveExperiencesAdapter(Context mContext, List<Data> experiencesData) {
        this.experiencesData = experiencesData;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_sight_seeing,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_sight_seeing_title.setText(experiencesData.get(position).getTitle());
        holder.tv_sight_seeing_desc.setText(experiencesData.get(position).getDescription());

        Glide.with(mContext).load(experiencesData.get(position).getImage()).centerCrop().into(holder.img_coorg_sight_seeing);
    }

    @Override
    public int getItemCount() {
        return experiencesData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_sight_seeing_title,tv_sight_seeing_desc;
        ImageView img_coorg_sight_seeing;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_sight_seeing_title = itemView.findViewById(R.id.tv_sight_seeing_title);
            tv_sight_seeing_desc = itemView.findViewById(R.id.tv_sight_seeing_desc);
            img_coorg_sight_seeing = itemView.findViewById(R.id.img_coorg_sight_seeing);
        }
    }
}
