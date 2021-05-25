package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class LaundryServiceAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> laundry_service_list;
    private GlobalClass.ExpandableAdapterListener mListener;


    public LaundryServiceAdapter(Context mContext, List<Data> laundry_service_list,GlobalClass.ExpandableAdapterListener mListener) {
        this.mContext = mContext;
        this.laundry_service_list = laundry_service_list;
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {
        return laundry_service_list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return laundry_service_list.get(groupPosition).getCategory_item().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return laundry_service_list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return laundry_service_list.get(groupPosition).getCategory_item();
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
            lyt_header_view.setVisibility(View.VISIBLE);
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down_arrow));
        }else {
            lyt_header_view.setVisibility(View.GONE);
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_right_arrow));
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_directory_of_service_title);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(laundry_service_list.get(groupPosition).getName());

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        try {
            final List<Category_item> expandedListText = (List<Category_item>) getChild(groupPosition, childPosition);
            Log.d("group position", String.valueOf(groupPosition));
            Log.d("child position", String.valueOf(childPosition));


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

            item_name.setText(expandedListText.get(childPosition).getName());
            item_price.setText("₹" + " " + expandedListText.get(childPosition).getPrice());
            tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));

            img_add.setOnClickListener(v -> {
                try {
                    laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.get(childPosition).getCount() + 1);
                    tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));
                    mListener.onItemClicked(groupPosition, childPosition);
                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (expandedListText.get(childPosition).getCount() > 0) {
                    expandedListText.get(childPosition).setCount(expandedListText.get(childPosition).getCount() - 1);
                    tv_quantity.setText(Integer.toString(expandedListText.get(childPosition).getCount()));
                    mListener.onItemClicked(groupPosition, childPosition);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
