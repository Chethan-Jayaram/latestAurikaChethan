package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import static com.mobisprint.aurika.R.drawable.icon_veg;

public class InRoomDiningMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> dataList;
    private GlobalClass.ExpandableAdapterListener mListener;

    public InRoomDiningMenuAdapter(Context mContext, List<Data> dataList,GlobalClass.ExpandableAdapterListener mListener) {
        Log.d("size", String.valueOf(dataList.size()));
        this.mContext = mContext;
        this.dataList = dataList;
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {
        Log.d("listSize", String.valueOf(dataList.size()));
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).getDiningList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getDiningList();
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
            convertView = layoutInflater.inflate(R.layout.dining_menu_heading, null);
        }

        TextView title = convertView.findViewById(R.id.tv_dining_menu_title);

        title.setText(dataList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final List<Dining__1> expandedListText = (List<Dining__1>) getChild(groupPosition,childPosition);
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.single_item_dining_menu, null);
        }

        TextView sub_heading = convertView.findViewById(R.id.dining_menu_sub_heading);
        TextView dining_menu_desc = convertView.findViewById(R.id.dining_menu_desc);
        TextView dining_menu_price = convertView.findViewById(R.id.dining_menu_price);
        ImageView img_veg_or_nonveg = convertView.findViewById(R.id.img_veg_or_nonveg);
        TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);
        ImageView img_add = convertView.findViewById(R.id.img_add);
        ImageView img_remove = convertView.findViewById(R.id.img_remove);
        RelativeLayout lyt_view = convertView.findViewById(R.id.lyt_view);

        if (expandedListText.get(childPosition).getItemType().equals("Veg")) {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
        }else {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_nonveg));
        }

        /*if (expandedListText.get(childPosition).getDescription().isEmpty()){
            dining_menu_desc.setVisibility(View.GONE);
        }else {
            dining_menu_desc.setText(expandedListText.get(childPosition).getDescription());
        }*/
        sub_heading.setText(expandedListText.get(childPosition).getTitle());
        dining_menu_price.setText("₹"+" "+expandedListText.get(childPosition).getPrice());
        dining_menu_desc.setText(expandedListText.get(childPosition).getDescription());
        tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));

        img_add.setOnClickListener(v -> {
            expandedListText.get(childPosition).setCount( expandedListText.get(childPosition).getCount()+1);
            tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));
            mListener.onItemClicked(groupPosition,childPosition);
        });

        img_remove.setOnClickListener(v -> {
            if (expandedListText.get(childPosition).getCount()>0){
                expandedListText.get(childPosition).setCount( expandedListText.get(childPosition).getCount()-1);
                tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));
                mListener.onItemClicked(groupPosition,childPosition);
            }
        });

        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
