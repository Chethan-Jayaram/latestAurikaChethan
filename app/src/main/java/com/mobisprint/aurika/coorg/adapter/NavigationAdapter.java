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

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.navigation.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class NavigationAdapter extends BaseExpandableListAdapter {


    private Context mContext;
    private List<Data> navigationList;
    private GlobalClass.AdapterListener mListener;

    public NavigationAdapter(Context mContext, List<Data> navigationList, GlobalClass.AdapterListener mListener) {
        this.mContext = mContext;
        this.navigationList = navigationList;
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {
        return navigationList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return navigationList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return null;
    }

    @Override
    public long getGroupId(int groupPosition) {
        return 0;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return 0;
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
            convertView = layoutInflater.inflate(R.layout.single_item_navigation_bar, null);
        }

        ImageView imageView = (ImageView) convertView.findViewById(R.id.navigation_bar_image);
        RelativeLayout lyt_navigation = convertView.findViewById(R.id.lyt_navigation);

        Glide.with(mContext)
                .load(navigationList.get(groupPosition).getImage())
                .into(imageView);

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.navigation_bar_text);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(navigationList.get(groupPosition).getTitle());

        lyt_navigation.setOnClickListener(v -> {
            mListener.onItemClicked(groupPosition);
        });



        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        return null;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
