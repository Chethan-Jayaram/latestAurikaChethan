package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;

import java.util.List;

public class MiniBarAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> minibarList;

    public MiniBarAdapter(Context mContext, List<Data> minibarList) {
        this.mContext = mContext;
        this.minibarList = minibarList;
    }

    @Override
    public int getGroupCount() {
        return minibarList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return minibarList.get(groupPosition).getMinibarList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return minibarList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return minibarList.get(groupPosition).getMinibarList().get(childPosition);
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
            convertView = layoutInflater.inflate(R.layout.single_item_mini_bar_recyclerview, null);


            TextView tv_minibar_title = convertView.findViewById(R.id.tv_minibar_title);

            tv_minibar_title.setText(minibarList.get(groupPosition).getTitle());
            ExpandableListView elv = (ExpandableListView)  parent;
            elv.expandGroup(groupPosition);


            elv.setOnGroupClickListener((arg0, itemView, itemPosition, itemId) -> {
                elv.expandGroup(itemPosition);
                return true;
            });

        }

        return convertView;
    }



    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        convertView=null;
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.minibar_internal_view, null);


            TextView tv_item_name = convertView.findViewById(R.id.tv_item_name);
            TextView tv_item_quantity = convertView.findViewById(R.id.tv_item_quantity);
            TextView tv_item_price = convertView.findViewById(R.id.tv_item_price);

            tv_item_name.setText(minibarList.get(groupPosition).getMinibarList().get(childPosition).getTitle() + "\n(and services)");
            tv_item_price.setText("₹ " + minibarList.get(groupPosition).getMinibarList().get(childPosition).getPrice());


            tv_item_quantity.setText(String.valueOf(minibarList.get(groupPosition).getMinibarList().get(childPosition).getQuantity()));
        }

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
