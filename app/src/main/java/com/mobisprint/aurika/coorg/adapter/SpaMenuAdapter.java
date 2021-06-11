package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.RelativeLayout;
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
        return spaList.get(groupPosition).getSpaMenuList().get(childPosition);
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

        View lyt_view = convertView.findViewById(R.id.lyt_view);

        if (groupPosition==0){
            lyt_view.setVisibility(View.GONE);
        }

        TextView tv_spa_menu_title = convertView.findViewById(R.id.tv_spa_menu_title);

        tv_spa_menu_title.setText(spaList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

     final   SpaMenu menu=spaList.get(groupPosition).getSpaMenuList().get(childPosition);


        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_item_spa_menu_sub_items, null);
        }

        TextView tv_spa_menu_heading = convertView.findViewById(R.id.tv_spa_menu_heading);
        TextView tv_spa_menu_price = convertView.findViewById(R.id.tv_spa_menu_price);
        TextView tv_spa_menu_desc = convertView.findViewById(R.id.tv_spa_menu_desc);
        TextView tv_spa_time = convertView.findViewById(R.id.tv_spa_time);
        RelativeLayout lyt_spa_time = convertView.findViewById(R.id.lyt_spa_time);

        tv_spa_menu_heading.setText(menu.getTitle());
        tv_spa_menu_desc.setText(menu.getDescription());
        tv_spa_menu_price.setVisibility(View.INVISIBLE);


        if (menu.getPrice() == null
                || menu.getPrice().isEmpty()
                || menu.getPrice().equals("0.00")){
            tv_spa_menu_price.setVisibility(View.INVISIBLE);
        }else{
            tv_spa_menu_price.setVisibility(View.VISIBLE);
            tv_spa_menu_price.setText("₹"+" "+menu.getPrice());

        }

        if (menu.getDuration() == null
                || menu.getDuration().isEmpty()
                || menu.getDuration().equals("0")){
           lyt_spa_time.setVisibility(View.GONE);
        }else{
            lyt_spa_time.setVisibility(View.VISIBLE);
            tv_spa_time.setText(" " +menu.getDuration() + " mins");

        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
