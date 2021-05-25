package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.SpaMenu;

import java.util.List;

public class SpaMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> spaList;

    public SpaMenuAdapter(Context mContext, List<Data> spaList) {
        this.mContext = mContext;
        this.spaList = spaList;
    }

    @Override
    public int getGroupCount() {
        return spaList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return spaList.get(groupPosition).getSpaMenuList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return spaList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return spaList.get(groupPosition).getSpaMenuList();
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
            convertView = layoutInflater.inflate(R.layout.single_item_coorg_spa_menu, null);
        }

        TextView tv_spa_menu_title = convertView.findViewById(R.id.tv_spa_menu_title);

        tv_spa_menu_title.setText(spaList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final List<SpaMenu> expandedListText = (List<SpaMenu>) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_item_spa_menu_sub_items, null);
        }

        TextView tv_spa_menu_heading = convertView.findViewById(R.id.tv_spa_menu_heading);
        TextView tv_spa_menu_price = convertView.findViewById(R.id.tv_spa_menu_price);
        TextView tv_spa_menu_desc = convertView.findViewById(R.id.tv_spa_menu_desc);
        TextView tv_spa_time = convertView.findViewById(R.id.tv_spa_time);

       tv_spa_menu_heading.setText(expandedListText.get(childPosition).getTitle());
       tv_spa_menu_desc.setText(expandedListText.get(childPosition).getDescription());
       tv_spa_menu_price.setText("₹"+" "+expandedListText.get(childPosition).getPrice());
       tv_spa_time.setText(expandedListText.get(childPosition).getDuration());

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
