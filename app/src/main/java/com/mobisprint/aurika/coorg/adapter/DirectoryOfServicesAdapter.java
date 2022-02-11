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

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;

import java.util.HashMap;
import java.util.List;

public class DirectoryOfServicesAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> directory_of_service_list;


    public DirectoryOfServicesAdapter(Context mContext, List<Data> directory_of_service_list) {

        this.mContext = mContext;
        this.directory_of_service_list = directory_of_service_list;

    }

    @Override
    public int getGroupCount() {
        return this.directory_of_service_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return directory_of_service_list.get(groupPosition);
    }


    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return directory_of_service_list.get(groupPosition).getDescription();
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

        RelativeLayout lyt_header_view = convertView.findViewById(R.id.lyt_header_view);
        ImageView img_dropdown = convertView.findViewById(R.id.img_dropdown);

        if (isExpanded){
            lyt_header_view.setVisibility(View.GONE);
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down_arrow));
        }else {
            lyt_header_view.setVisibility(View.GONE);
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_right_arrow));
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_directory_of_service_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(directory_of_service_list.get(groupPosition).getTitle());



        /*ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);*/
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.directory_of_service_child, null);

        }

        TextView expandedListTextView = (TextView) convertView
                .findViewById(R.id.directory_of_service_child);
        expandedListTextView.setText(expandedListText);

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
