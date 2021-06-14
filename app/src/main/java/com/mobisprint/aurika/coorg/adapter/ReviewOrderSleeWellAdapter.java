package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ReviewOrderSleeWellAdapter extends BaseExpandableListAdapter {

    private List<Data> sleepWellList;
    private Context mContext;
    private GlobalClass.ExpandableAdapterListener mListener;

    public ReviewOrderSleeWellAdapter(List<Data> sleepWellList, Context mContext, GlobalClass.ExpandableAdapterListener mListener) {
        this.sleepWellList = sleepWellList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {
        return sleepWellList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sleepWellList.get(groupPosition).getSleepwellList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sleepWellList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sleepWellList.get(groupPosition).getSleepwellList();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.directory_of_services_title, null);
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_directory_of_service_title);
        ImageView imageView = convertView.findViewById(R.id.img_dropdown);
        imageView.setVisibility(View.GONE);
        RelativeLayout relativeLayout = convertView.findViewById(R.id.lyt_header_view);
        relativeLayout.setVisibility(View.GONE);
        RelativeLayout group_lyt=convertView.findViewById(R.id.group_lyt);
        RelativeLayout lyt_view = convertView.findViewById(R.id.lyt_view);

        lyt_view.setVisibility(View.GONE);

        for(int i=0;i<sleepWellList.get(groupPosition).getSleepwellList().size();i++){
            if(sleepWellList.get(groupPosition).getSleepwellList().get(i).getCount()>0){
                group_lyt.setVisibility(View.VISIBLE);
                break;
            }else{

                group_lyt.setVisibility(View.GONE);
            }
        }

        listTitleTextView.setText(sleepWellList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView) parent;
        elv.expandGroup(groupPosition);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final List<SleepwellList> expandedListText = (List<SleepwellList>) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.coorg_sleep_well_internal_list, null);
        }


        TextView itemName = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_name);
        TextView itemDesc = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_desc);
        TextView itemPrice = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_price);

        RelativeLayout lyt = convertView.findViewById(R.id.lyt_view);
        lyt.setVisibility(View.GONE);

        if (expandedListText.get(childPosition).getCount()>0){
            lyt.setVisibility(View.VISIBLE);
            ImageView img_add = convertView.findViewById(R.id.img_add);
            ImageView img_remove = convertView.findViewById(R.id.img_remove);
            TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);

            RelativeLayout lyt_desc = convertView.findViewById(R.id.lyt_desc);

            itemName.setText(expandedListText.get(childPosition).getTitle());



            if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getDescription() != null && !sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getDescription().isEmpty() ){
                lyt_desc.setVisibility(View.VISIBLE);
                itemDesc.setVisibility(View.VISIBLE);
                itemDesc.setText(expandedListText.get(childPosition).getDescription());
            }else {
                lyt_desc.setVisibility(View.GONE);
            }

            /*if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getPrice() == null || sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getPrice().equals("0.00")){
                itemPrice.setVisibility(View.GONE);
            }else {
                itemPrice.setVisibility(View.VISIBLE);
            }*/

            itemPrice.setText("₹"+" "+expandedListText.get(childPosition).getPrice());
            tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));

            img_add.setOnClickListener(v -> {
                try {
                    sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() + 1);
                    tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));
                    mListener.onItemClicked(sleepWellList.get(groupPosition));
                    pushData(sleepWellList);
                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() == 1){
                    Alert(parent.getContext(),groupPosition,childPosition,lyt_desc);
                }else if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() > 0) {
                    sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() - 1);
                    tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));
                    mListener.onItemClicked(sleepWellList.get(groupPosition));
                    pushData(sleepWellList);
                    lyt_desc.setVisibility(View.VISIBLE);
                }

            });

        }




        return convertView;
    }

    private void pushData(List<Data> sleepWellList) {

        Set<Data> set = new LinkedHashSet<>(sleepWellList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("SleepWell", json);
        GlobalClass.editor.commit();
    }


    private void Alert(Context mContext, int groupPosition, int childPosition, RelativeLayout lyt_items) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() - 1);
                    mListener.onItemClicked(sleepWellList.get(groupPosition));
                    lyt_items.setVisibility(View.GONE);
                    pushData(sleepWellList);
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
