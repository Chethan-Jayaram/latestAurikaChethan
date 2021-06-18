package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class K9MenuAdapter extends RecyclerView.Adapter<K9MenuAdapter.ViewHolder> {

    private Context context;
    private List<K9Data> menuList;
    private GlobalClass.AdapterListener mListener;

    public K9MenuAdapter(Context context, List<K9Data> menuList,GlobalClass.AdapterListener mListener) {
        this.context = context;
        this.menuList = menuList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.k9_menu_internal_list,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.title.setText(menuList.get(position).getTitle());
        holder.desc.setVisibility(View.VISIBLE);
        holder.desc.setText(menuList.get(position).getDescription());
        holder.price.setText("₹"+" "+menuList.get(position).getPrice());

       /* holder.img_add.setOnClickListener(v -> {
            menuList.get(position).setCount( menuList.get(position).getCount()+1);
            holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
            mListener.onItemClicked(position);
        });

        holder.img_remove.setOnClickListener(v -> {
            if (menuList.get(position).getCount()>0){
                menuList.get(position).setCount( menuList.get(position).getCount()-1);
                holder.tv_quantity.setText(Integer.toString(menuList.get(position).getCount()));
                mListener.onItemClicked(position);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return menuList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView title,desc,price,tv_quantity;
        ImageView img_add,img_remove;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_coorg_sleep_well_item_name);
            desc = itemView.findViewById(R.id.tv_coorg_sleep_well_item_desc);
            price = itemView.findViewById(R.id.tv_coorg_sleep_well_item_price);
            /*img_add = itemView.findViewById(R.id.img_add);
            img_remove = itemView.findViewById(R.id.img_remove);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);*/
        }
    }
}
