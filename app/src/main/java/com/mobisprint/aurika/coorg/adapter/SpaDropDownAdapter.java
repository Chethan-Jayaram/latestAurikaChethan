package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class SpaDropDownAdapter extends RecyclerView.Adapter<SpaDropDownAdapter.ViewHolder> {

    private Context mContext;
    private List<Data> spaList;
    private GlobalClass.AdapterListener mListener;
    public SpaDropDownAdapter(Context mContext, List<Data> spaList, GlobalClass.AdapterListener mListener) {
        this.mContext = mContext;
        this.spaList = spaList;
        this.mListener = mListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.single_item_spa_therapy_list,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (spaList.get(position).getPrice() == null || spaList.get(position).getPrice().isEmpty() || spaList.get(position).getPrice().equals("0.00")){
            holder.spa_price.setVisibility(View.GONE);
        }

        if (spaList.get(position).getDuration() == null || spaList.get(position).getDuration().isEmpty() || spaList.get(position).getDuration().equals("0")){
            holder.spa_time.setVisibility(View.GONE);
            holder.spa_heading.setText(Html.fromHtml("<font color=#000000>"+ spaList.get(position).getTitle() +"</font> <font color=#A7A7A7>"));
        }else{
            holder.spa_heading.setText(Html.fromHtml("<font color=#000000>"+ spaList.get(position).getTitle() +"<br> </font> <font color=#A7A7A7>"+"("+spaList.get(position).getDuration()+" mins)" +"</font>"));
        }
           /* holder.spa_heading.setText(spaList.get(position).getTitle());
            holder.spa_time.setText("("+spaList.get(position).getDuration()+ " mins"+")");*/

     /*   holder.spa_heading.setText(Html.fromHtml("<body>" +
                "        <p" + spaList.get(position).getTitle() +" style=\"color:#1e0028\">" + spaList.get(position).getDuration()+" mins" + "</a>"+
                "        </p>" +
                "    </body>"));*/



            holder.spa_price.setText("₹"+" "+spaList.get(position).getPrice());

            holder.lyt_select_therapy.setOnClickListener(v -> {
                mListener.onItemClicked(position);
            });





    }

    @Override
    public int getItemCount() {
        return spaList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView spa_heading,spa_time,spa_price;
        RelativeLayout lyt_select_therapy;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            spa_heading = itemView.findViewById(R.id.spa_heading);
            spa_time = itemView.findViewById(R.id.spa_time);
            spa_price = itemView.findViewById(R.id.spa_price);
            lyt_select_therapy = itemView.findViewById(R.id.lyt_select_therapy);
        }
    }
}
