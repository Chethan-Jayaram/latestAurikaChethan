package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.Services.SleepwellList;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;
import com.mobisprint.aurika.helper.SharedPreferenceVariables;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class CoorgSleepWellAdapter extends BaseExpandableListAdapter {

    private List<Data> sleepWellList;
    private Context mContext;
    private GlobalClass.ExpandableAdapterListener mListener;
    private boolean isItemSelected = false ;
    private boolean isMultipleItemSelected = false;

    public CoorgSleepWellAdapter(Context mContext, List<Data> sleepWellList, GlobalClass.ExpandableAdapterListener mListener) {
        this.sleepWellList = sleepWellList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public int getGroupCount() {
        return sleepWellList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return sleepWellList.get(groupPosition).getSleepwellList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return sleepWellList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return sleepWellList.get(groupPosition).getSleepwellList();
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
            convertView = layoutInflater.inflate(R.layout.coorg_sleep_well_title, null);
        }

        ImageView img_dropdown = convertView.findViewById(R.id.img_dropdown);
        RelativeLayout lyt_desc = convertView.findViewById(R.id.lyt_desc);

        if (isExpanded){
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_down_arrow));
        }else {
            img_dropdown.setImageDrawable(mContext.getResources().getDrawable(R.drawable.ic_right_arrow));
        }

        TextView listTitleTextView = (TextView) convertView
                .findViewById(R.id.tv_sleepwell_title);
        TextView desc = (TextView) convertView.findViewById(R.id.tv_coorg_sleepwell_desc);
        listTitleTextView.setTypeface(null, Typeface.BOLD);
        listTitleTextView.setText(sleepWellList.get(groupPosition).getTitle());

        if (isExpanded) {
            desc.setVisibility(View.VISIBLE);
            desc.setText(sleepWellList.get(groupPosition).getDescription());
            lyt_desc.setVisibility(View.VISIBLE);
        }else{
            desc.setVisibility(View.GONE);
            lyt_desc.setVisibility(View.GONE);
        }


        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final List<SleepwellList> expandedListText = (List<SleepwellList>) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.coorg_sleep_well_internal_list, null);
        }


        TextView itemName = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_name);
        TextView itemDesc = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_desc);
        TextView itemPrice = (TextView) convertView
                .findViewById(R.id.tv_coorg_sleep_well_item_price);
        ImageView img_add = convertView.findViewById(R.id.img_add);
        ImageView img_remove = convertView.findViewById(R.id.img_remove);
        TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);

        RelativeLayout lyt_desc = convertView.findViewById(R.id.lyt_desc);
        RelativeLayout lyt_view = convertView.findViewById(R.id.lyt_view);

        CardView lyt_counter = convertView.findViewById(R.id.lyt_counter);
        CardView lyt_add = convertView.findViewById(R.id.lyt_add);
        TextView bt_add = convertView.findViewById(R.id.bt_add);

        View bt_single = convertView.findViewById(R.id.bt_sleepwell_single);
        View bt_multiple = convertView.findViewById(R.id.bt_sleepwell_multiple);
        MySwitc switch4 = convertView.findViewById(R.id.switch4);

        itemName.setText(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getTitle());
        itemPrice.setText("₹"+" "+sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getPrice());
        tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));

        if ( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getItemselectorType().equalsIgnoreCase("single")){
            bt_single.setVisibility(View.VISIBLE);
            bt_multiple.setVisibility(View.GONE);
            if ( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()>0){
                isItemSelected =true;
                switch4.setOn(true);
            }else {
                switch4.setOn(false );
            }

        }else {
            bt_single.setVisibility(View.GONE);
        }

        if ( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getItemselectorType().equalsIgnoreCase("multi")){
            bt_multiple.setVisibility(View.VISIBLE);
            bt_single.setVisibility(View.GONE);
            if ( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()>0){
                isMultipleItemSelected = true;

                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,true);
            }
        }else {
            bt_multiple.setVisibility(View.GONE);
        }

        if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getDescription() != null && !sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getDescription().isEmpty() ){
            lyt_desc.setVisibility(View.VISIBLE);
            itemDesc.setVisibility(View.VISIBLE);
            itemDesc.setText(expandedListText.get(childPosition).getDescription());
        }else {
            lyt_desc.setVisibility(View.GONE);
        }

        if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() == 0){
            lyt_add.setVisibility(View.VISIBLE);
            lyt_counter.setVisibility(View.GONE);
        }else{
            lyt_add.setVisibility(View.GONE);
            lyt_counter.setVisibility(View.VISIBLE);
        }



        bt_add.setOnClickListener(v -> {
            if (!GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false)){
                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,true);
                isMultipleItemSelected = true;
                sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() + 1);
                tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));
                mListener.onItemClicked(sleepWellList.get(groupPosition));
                pushData(sleepWellList);
                lyt_add.setVisibility(View.GONE);
                lyt_counter.setVisibility(View.VISIBLE);
            }else {
                GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
            }

        });









           /* if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getPrice() == null || sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getPrice().equals("0.00")){
                itemPrice.setVisibility(View.GONE);
            }else {
                itemPrice.setVisibility(View.VISIBLE);
            }*/



            img_add.setOnClickListener(v -> {
                try {
                    if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getMaxCount() != null){

                        if ( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() < sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getMaxCount()){

                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                            GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,true);

                            isMultipleItemSelected=true;
                            isItemSelected = false;
                            sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() + 1);
                            tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));
                            mListener.onItemClicked(sleepWellList.get(groupPosition));
                            pushData(sleepWellList);
                        }else {
                            GlobalClass.ShowAlert(mContext,"Alert","Maximum count for this item has been reached");
                        }
                    }


                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() ==1){
                    lyt_add.setVisibility(View.VISIBLE);
                    lyt_counter.setVisibility(View.GONE);
                    sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() -1);
                    mListener.onItemClicked(sleepWellList.get(groupPosition));
                    pushData(sleepWellList);
                }
                if (sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() > 0) {
                    sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() -1);
                    tv_quantity.setText(Integer.toString(sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()));
                    mListener.onItemClicked(sleepWellList.get(groupPosition));
                    pushData(sleepWellList);
                }
                if( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount() ==0){
                    if (GlobalClass.sharedPreferences.getInt(GlobalClass.Laundry_count,0) == 0){


                        GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                        GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false);
                        isMultipleItemSelected = false;
                        isItemSelected=false;
                    }
                }
            });



        switch4.setOnClickListener(v -> {

            if ((GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false) && !sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).isItemSelected()) || GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false)) {
                switch4.setOn(true);
                GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
            } else if (GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false) && sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).isItemSelected() ){
                switch4.setEnabled(false);
                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false);
                isItemSelected = false;
                sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setItemSelected(false);
                sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()-1);
                pushData(sleepWellList);
                mListener.onItemClicked(sleepWellList.get(groupPosition));
            } else if ((!GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,false) && !sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).isItemSelected()) || !GlobalClass.sharedPreferences.getBoolean(SharedPreferenceVariables.SleepWell_IsMultipleItemSelected,false)) {
                switch4.setEnabled(true);
                GlobalClass.editor.putBoolean(SharedPreferenceVariables.SleepWell_IsSingleItemSelected,true);
                isItemSelected = true;
                sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setItemSelected(true);
                sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).setCount( sleepWellList.get(groupPosition).getSleepwellList().get(childPosition).getCount()+1);
                pushData(sleepWellList);
                mListener.onItemClicked(sleepWellList.get(groupPosition));
            }

                /*if (isMultipleItemSelected){
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Only one item can be selected, Please raise a new request for different item ");
                }*/

        });




        return convertView;
    }

    private void pushData(List<Data> sleepWellList) {

        Set<Data> set = new LinkedHashSet<>(sleepWellList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("SleepWell", json);
        GlobalClass.editor.commit();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
