package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.petservices.K9Data;

import java.util.List;

public class K9FacilitesAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<K9Data> amenitiesList;

    public K9FacilitesAdapter(Context context, List<K9Data> amenitiesList) {

        this.context = context;
        this.amenitiesList = amenitiesList;

    }

    @Override
    public int getGroupCount() {
        return amenitiesList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return amenitiesList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return amenitiesList.get(groupPosition).getDescription();
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
            LayoutInflater layoutInflater = (LayoutInflater) this.context.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.directory_of_services_title, null);
        }

        RelativeLayout lyt_header_view = convertView.findViewById(R.id.lyt_header_view);
        ImageView img_dropdown = convertView.findViewById(R.id.img_dropdown);

        if (isExpanded){
            lyt_header_view.setVisibility(View.GONE);
            img_dropdown.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_down_arrow));
        }else {
            lyt_header_view.setVisibility(View.GONE);
            img_dropdown.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_right_arrow));
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_directory_of_service_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(amenitiesList.get(groupPosition).getTitle());

        /*ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);*/

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final String expandedListText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
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
