package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
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
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;

import java.util.List;

public class CoorgSleepWellAdapter extends BaseExpandableListAdapter {

    private List<Data> sleepWellList;
    private Context mContext;

    public CoorgSleepWellAdapter(Context mContext, List<Data> sleepWellList) {
        this.sleepWellList = sleepWellList;
        this.mContext = mContext;
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
            convertView = layoutInflater.inflate(R.layout.coorg_sleep_well_title, null);
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_sleepwell_title);
        TextView desc = (TextView) convertView.findViewById(R.id.tv_coorg_sleepwell_desc);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(sleepWellList.get(groupPosition).getTitle());
        desc.setText(sleepWellList.get(groupPosition).getDescription());


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
        itemName.setText(expandedListText.get(childPosition).getTitle());

        if (expandedListText.get(childPosition).getDescription() != null){
            itemDesc.setVisibility(View.VISIBLE);
        itemDesc.setText(expandedListText.get(childPosition).getDescription());
        }

        itemPrice.setText("₹"+" "+expandedListText.get(childPosition).getPrice());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
