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

import androidx.cardview.widget.CardView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;
import com.mobisprint.aurika.helper.SharedPreferenceVariables;

import org.w3c.dom.Text;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class LaundryServiceAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> laundry_service_list;
    private GlobalClass.ExpandableAdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;


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
                convertView = layoutInflater.inflate(R.layout.coorg_laundry_recyclerview, null);
            }

            TextView item_name = convertView.findViewById(R.id.tv_amenities_item_name);
            TextView item_price = convertView.findViewById(R.id.tv_amenities_item_price);
            TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);
            ImageView img_add = convertView.findViewById(R.id.img_add);
            ImageView img_remove = convertView.findViewById(R.id.img_remove);
            CardView lyt_counter = convertView.findViewById(R.id.lyt_counter);
            CardView lyt_add = convertView.findViewById(R.id.lyt_add);
            TextView bt_add = convertView.findViewById(R.id.bt_add);
            View bt_single = convertView.findViewById(R.id.bt_amen_single);
            View bt_multiple = convertView.findViewById(R.id.bt_amen_multiple);
            MySwitc switch4 = convertView.findViewById(R.id.switch4);

            item_name.setText(expandedListText.getName());
            item_price.setText("₹ "+ expandedListText.getPrice());
            tv_quantity.setText(Integer.toString(expandedListText.getCount()));

            if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() == 0){
                lyt_add.setVisibility(View.VISIBLE);
                lyt_counter.setVisibility(View.GONE);
            }else{
                lyt_add.setVisibility(View.GONE);
                lyt_counter.setVisibility(View.VISIBLE);
            }




            bt_add.setOnClickListener(v -> {

                if (GlobalClass.user_active_booking) {

                    if (!(GlobalClass.sharedPreferences.getBoolean("isItemSelected", false))) {
                        GlobalClass.editor.putBoolean("isMultipleItemSelected", true);
                        GlobalClass.editor.commit();
                        isMultipleItemSelected = true;
                        laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.getCount() + 1);
                        tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                        mListener.onItemClicked(laundry_service_list.get(groupPosition));
                        pushData(laundry_service_list);
                        lyt_add.setVisibility(View.GONE);
                        lyt_counter.setVisibility(View.VISIBLE);
                    } else {
                        GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
                    }
                }else{
                    GlobalClass.ShowAlert(mContext, "Alert", "You don't have an active booking. You can place order only during the stay at property.");
                }

            });


                if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getItemselectorType().equalsIgnoreCase("single")){
                    bt_single.setVisibility(View.VISIBLE);
                    bt_multiple.setVisibility(View.GONE);
                    if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()>0){
                        GlobalClass.editor.putBoolean("isItemSelected",true);
                        GlobalClass.editor.commit();
                        isItemSelected =true;
                        switch4.setOn(true);
                    }else {
                        switch4.setOn(false );
                    }

                }else {
                    bt_single.setVisibility(View.GONE);
                }

                    if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getItemselectorType().equalsIgnoreCase("multi")){
                    bt_multiple.setVisibility(View.VISIBLE);
                    bt_single.setVisibility(View.GONE);
                    if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()>0){
                        GlobalClass.editor.putBoolean("isMultipleItemSelected",true);
                        GlobalClass.editor.commit();
                        isMultipleItemSelected = true;
                    }
                }else {
                        bt_multiple.setVisibility(View.GONE);
                    }


            img_add.setOnClickListener(v -> {

                try {
                    if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getMaxCount() != null && !GlobalClass.sharedPreferences.getBoolean("isItemSelected",false)
                    ){

                        if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() < laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getMaxCount()){
                            isMultipleItemSelected=true;
                            isItemSelected = false;
                            GlobalClass.editor.putBoolean("isMultipleItemSelected",true);
                            GlobalClass.editor.putBoolean("isItemSelected",false);
                            GlobalClass.editor.commit();
                            laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.getCount() + 1);
                            tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                            mListener.onItemClicked(laundry_service_list.get(groupPosition));
                            pushData(laundry_service_list);
                        }else {
                            GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                        }
                    }


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
                } if (laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() > 0) {
                    laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() -1);
                    tv_quantity.setText(Integer.toString(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount()));
                    mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    pushData(laundry_service_list);
                }
                if( laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() ==0){
                    if (GlobalClass.sharedPreferences.getInt(GlobalClass.Laundry_count,0) == 0){
                        GlobalClass.editor.putBoolean("isMultipleItemSelected",false);
                        GlobalClass.editor.putBoolean("isItemSelected",false);
                        GlobalClass.editor.commit();
                        isMultipleItemSelected = false;
                        isItemSelected=false;
                    }
                }
            });


            switch4.setOnClickListener(v -> {

                if (GlobalClass.user_active_booking) {

                    if (((GlobalClass.sharedPreferences.getBoolean("isItemSelected", false)) && !laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).isItemSelected()) || (GlobalClass.sharedPreferences.getBoolean("isMultipleItemSelected", false))) {
                        switch4.setOn(true);
                        GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
                    } else if (GlobalClass.sharedPreferences.getBoolean("isItemSelected", false) && laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).isItemSelected()) {
                        switch4.setEnabled(false);
                        GlobalClass.editor.putBoolean("isItemSelected", false);
                        GlobalClass.editor.commit();
                        isItemSelected = false;
                        laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setItemSelected(false);
                        laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() - 1);
                        pushData(laundry_service_list);
                        mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    } else if ((!(GlobalClass.sharedPreferences.getBoolean("isItemSelected", false)) && !laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).isItemSelected()) || !(GlobalClass.sharedPreferences.getBoolean("isMultipleItemSelected", false))) {
                        switch4.setEnabled(true);
                        isItemSelected = true;
                        GlobalClass.editor.putBoolean("isItemSelected", true);
                        GlobalClass.editor.commit();
                        laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setItemSelected(true);
                        laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).setCount(laundry_service_list.get(groupPosition).getCategory_item().get(childPosition).getCount() + 1);
                        pushData(laundry_service_list);
                        mListener.onItemClicked(laundry_service_list.get(groupPosition));
                    }
                }else{
                    switch4.setOn(true);
                    GlobalClass.ShowAlert(mContext, "Alert", "You don't have an active booking. You can place order only during the stay at property.");

                }

                /*if (isMultipleItemSelected){
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Only one item can be selected, Please raise a new request for different item ");
                }*/

            });


        }catch (Exception e){
            e.printStackTrace();
        }


        return convertView;
    }

    private void pushData(List<Data> arrPackageData) {

        Set<Data> set = new LinkedHashSet<>(arrPackageData);
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
