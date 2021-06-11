package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.GlobalClass;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        return laundry_service_list.get(groupPosition).getCategory_item().get(childPosition);
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
            final Category_item expandedListText =(Category_item) getChild(groupPosition, childPosition);



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
            CardView lyt_counter = convertView.findViewById(R.id.lyt_counter);
            CardView lyt_add = convertView.findViewById(R.id.lyt_add);
            TextView bt_add = convertView.findViewById(R.id.bt_add);


            if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() == 0){
                lyt_add.setVisibility(View.VISIBLE);
                lyt_counter.setVisibility(View.GONE);
            }else{
                lyt_add.setVisibility(View.GONE);
                lyt_counter.setVisibility(View.VISIBLE);
            }

            bt_add.setOnClickListener(v -> {
                laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.getCount() + 1);
                tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                mListener.onItemClicked(laundry_service_list.get(groupPosition));
                pushData(laundry_service_list);
                lyt_add.setVisibility(View.GONE);
                lyt_counter.setVisibility(View.VISIBLE);
            });



            item_name.setText(expandedListText.getName());
            item_price.setText("₹" + " " + expandedListText.getPrice());
            tv_quantity.setText(Integer.toString(expandedListText.getCount()));


            img_add.setOnClickListener(v -> {
                try {
                    laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.getCount() + 1);
                    tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                    mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    pushData(laundry_service_list);
                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() == 1){
                    lyt_add.setVisibility(View.VISIBLE);
                    lyt_counter.setVisibility(View.GONE);
                    laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() -1);
                    mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    pushData(laundry_service_list);
                }else if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() > 0) {
                    laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() -1);
                    tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                    mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    pushData(laundry_service_list);
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    private void pushData(List<Data> arrPackageData) {

        Set<Data> set = new HashSet<>(arrPackageData);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("Laundry", json);
        GlobalClass.editor.commit();
    }




    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
