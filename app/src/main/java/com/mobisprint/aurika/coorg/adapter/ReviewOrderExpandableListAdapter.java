package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;

import java.util.List;

public class ReviewOrderExpandableListAdapter extends BaseExpandableListAdapter {

    private  Context mContext;
    private List<Data> laundryList;

    public ReviewOrderExpandableListAdapter(List<Data> laundryList, Context mContext) {
        this.laundryList = laundryList;
        this.mContext = mContext;
    }

    @Override
    public int getGroupCount() {
        return laundryList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return laundryList.get(groupPosition).getCategory_item().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return laundryList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return laundryList.get(groupPosition).getCategory_item().get(childPosition);
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

        if (laundryList.get(groupPosition).getCategory_item().size()!=0) {
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(laundryList.get(groupPosition).getName());
        }

        ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        final Category_item expandedListText = (Category_item) getChild(groupPosition,childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.amenities_recyclerview, null);
        }

        TextView item_name = convertView.findViewById(R.id.tv_amenities_item_name);
        TextView item_price = convertView.findViewById(R.id.tv_amenities_item_price);
        TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);
        ImageView img_add = convertView.findViewById(R.id.img_add);
        ImageView img_remove = convertView.findViewById(R.id.img_remove);

            item_name.setText(expandedListText.getName());
            item_price.setText("₹" + " " + expandedListText.getPrice());
            tv_quantity.setText(Integer.toString(expandedListText.getCount()));


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
