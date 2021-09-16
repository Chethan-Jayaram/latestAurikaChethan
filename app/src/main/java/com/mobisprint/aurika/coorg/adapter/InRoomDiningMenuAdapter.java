package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
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

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentManager;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.fragments.BottomDailogFragment;
import com.mobisprint.aurika.coorg.fragments.dining.IrdCustomizationFrgament;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.MySwitc;
import com.mobisprint.aurika.helper.SharedPreferenceVariables;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.mobisprint.aurika.R.drawable.icon_veg;

public class InRoomDiningMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> dataList;
    private FragmentManager manager;
    private GlobalClass.ExpandableAdapterListenerIRD mListener;
    private boolean isItemSelected = false;
    private boolean isMultipleItemSelected = false;
    private String title;

    public InRoomDiningMenuAdapter(Context mContext, List<Data> dataList, String title, FragmentManager manager, GlobalClass.ExpandableAdapterListenerIRD mListener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.mListener = mListener;
        this.title = title;
        this.manager = manager;
    }

    @Override
    public int getGroupCount() {

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
        return dataList.get(groupPosition).getDiningList().get(childPosition);
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

        View lyt_view = convertView.findViewById(R.id.lyt_view);
        RelativeLayout lyt = convertView.findViewById(R.id.lyt);


        if (groupPosition == 0) {
            lyt_view.setVisibility(View.GONE);
            lyt.setVisibility(View.GONE);
        }

        TextView title = convertView.findViewById(R.id.tv_dining_menu_title);
        TextView desc = convertView.findViewById(R.id.tv_dining_menu_desc);

        if (dataList.get(groupPosition).getDescription() != null
                && !dataList.get(groupPosition).getDescription().isEmpty()
                && !dataList.get(groupPosition).getDescription().equals("")) {
            desc.setVisibility(View.VISIBLE);
            desc.setText(dataList.get(groupPosition).getDescription());
        } else {
            desc.setVisibility(View.GONE);
        }

        title.setText(dataList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView) parent;
        elv.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Dining__1 expandedListText = (Dining__1) getChild(groupPosition, childPosition);
        View ctx = convertView;
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
        RelativeLayout lyt_desc = convertView.findViewById(R.id.lyt_desc);
        lyt_desc.setVisibility(View.GONE);


        CardView lyt_counter = convertView.findViewById(R.id.lyt_counter);
        CardView lyt_add = convertView.findViewById(R.id.lyt_add);
        TextView bt_add = convertView.findViewById(R.id.bt_add);

        View bt_single = convertView.findViewById(R.id.bt_dining_single);
        View bt_multiple = convertView.findViewById(R.id.bt_dining_multiple);
        MySwitc switch4 = convertView.findViewById(R.id.switch4);


        if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() == 0) {
            lyt_add.setVisibility(View.VISIBLE);
            lyt_counter.setVisibility(View.GONE);
        } else {
            lyt_add.setVisibility(View.GONE);
            lyt_counter.setVisibility(View.VISIBLE);
        }

        bt_add.setOnClickListener(v -> {
            if (!GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false)) {
                if (dataList.get(groupPosition).getDiningList().get(childPosition).getDiningSubcategory().size() > 0) {

                    IrdCustomizationFrgament fragment = new IrdCustomizationFrgament();
                    Bundle bundle = new Bundle();
                    bundle.putString("item_name",dataList.get(groupPosition).getDiningList().get(childPosition).getTitle());
                    bundle.putString("item_type",dataList.get(groupPosition).getDiningList().get(childPosition).getItemType());
                    bundle.putString("item_price",dataList.get(groupPosition).getDiningList().get(childPosition).getPrice());
                    bundle.putParcelableArrayList("Sub-category",(ArrayList<? extends Parcelable>) dataList.get(groupPosition).getDiningList().get(childPosition).getDiningSubcategory());
                    fragment.setArguments(bundle);

                    /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                    fragment.show(manager,
                            "fragment_custom_sheet_dailog");
                    GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, true);
                    GlobalClass.editor.commit();
                    dataList.get(groupPosition).getDiningList().get(childPosition).setCount(expandedListText.getCount() + 1);
                    tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                    mListener.onItemClicked(dataList.get(groupPosition));
                    pushData(dataList);
                    lyt_add.setVisibility(View.GONE);
                    lyt_counter.setVisibility(View.VISIBLE);

                } else {
                    GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, true);
                    GlobalClass.editor.commit();
                    dataList.get(groupPosition).getDiningList().get(childPosition).setCount(expandedListText.getCount() + 1);
                    tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                    mListener.onItemClicked(dataList.get(groupPosition));
                    pushData(dataList);
                    lyt_add.setVisibility(View.GONE);
                    lyt_counter.setVisibility(View.VISIBLE);
                }

            } else {
                GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
            }
        });

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getItemselectorType().equalsIgnoreCase("single")) {
            bt_single.setVisibility(View.VISIBLE);
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() > 0) {
                GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, true);
                GlobalClass.editor.commit();
                switch4.setOn(true);
            } else {
                switch4.setOn(false);
            }

        } else {
            bt_single.setVisibility(View.GONE);
        }

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getItemselectorType().equalsIgnoreCase("multi")) {
            bt_multiple.setVisibility(View.VISIBLE);
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() > 0) {
                GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, true);
                GlobalClass.editor.commit();

            }
        } else {
            bt_multiple.setVisibility(View.GONE);
        }

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getItemType().isEmpty()) {
            img_veg_or_nonveg.setVisibility(View.INVISIBLE);
        } else {
            img_veg_or_nonveg.setVisibility(View.VISIBLE);
        }

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getItemType().equals("Veg")) {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
        } else {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_nonveg));
        }


        if (dataList.get(groupPosition).getDiningList().get(childPosition).getDescription() != null && !dataList.get(groupPosition).getDiningList().get(childPosition).getDescription().isEmpty()) {

            lyt_desc.setVisibility(View.VISIBLE);
            dining_menu_desc.setVisibility(View.VISIBLE);
            dining_menu_desc.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getDescription());

        } else {
            dining_menu_desc.setVisibility(View.GONE);
            lyt_desc.setVisibility(View.GONE);
        }


        sub_heading.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getTitle());
        dining_menu_price.setText("₹" + " " + dataList.get(groupPosition).getDiningList().get(childPosition).getPrice());
        /* dining_menu_desc.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getDescription());*/
        tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));

        img_add.setOnClickListener(v -> {
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getMaxCount() != null) {

                if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() < dataList.get(groupPosition).getDiningList().get(childPosition).getMaxCount()) {

                    if (dataList.get(groupPosition).getDiningList().get(childPosition).getDiningSubcategory().size() > 0) {

                        IrdCustomizationFrgament fragment = new IrdCustomizationFrgament();
                        Bundle bundle = new Bundle();
                        bundle.putString("item_name",dataList.get(groupPosition).getDiningList().get(childPosition).getTitle());
                        bundle.putString("item_type",dataList.get(groupPosition).getDiningList().get(childPosition).getItemType());
                        bundle.putString("item_price",dataList.get(groupPosition).getDiningList().get(childPosition).getPrice());
                        bundle.putParcelableArrayList("Sub-category",(ArrayList<? extends Parcelable>) dataList.get(groupPosition).getDiningList().get(childPosition).getDiningSubcategory());
                        fragment.setArguments(bundle);
                        /*getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();*/
                        fragment.show(manager,
                                "fragment_custom_sheet_dailog");
                        GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, true);
                        GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false);
                        GlobalClass.editor.commit();

                        dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() + 1);
                        tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                        pushData(dataList);
                        mListener.onItemClicked(dataList.get(groupPosition));

                    } else {
                        GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, true);
                        GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false);
                        GlobalClass.editor.commit();

                        dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() + 1);
                        tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                        pushData(dataList);
                        mListener.onItemClicked(dataList.get(groupPosition));
                    }

                } else {
                    GlobalClass.ShowAlert(mContext, "Alert", "Maximum count for this item has been reached");
                }
            }


        });

        img_remove.setOnClickListener(v -> {
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() == 1) {
                lyt_add.setVisibility(View.VISIBLE);
                lyt_counter.setVisibility(View.GONE);
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                mListener.onItemClicked(dataList.get(groupPosition));
                pushData(dataList);
            } else if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() > 0) {
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                pushData(dataList);
                mListener.onItemClicked(dataList.get(groupPosition));

            }
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() == 0) {
                if (GlobalClass.sharedPreferences.getInt(GlobalClass.Dining_count, 0) == 0) {
                    GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, false);
                    GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false);
                    GlobalClass.editor.commit();
                }
            }
        });


        switch4.setOnClickListener(v -> {

            if ((GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false) && !dataList.get(groupPosition).getDiningList().get(childPosition).isItemSelected()) || GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, false)) {
                switch4.setOn(true);
                GlobalClass.ShowAlert(mContext, "Alert", "Please place individual orders for individual requests  ");
            } else if (GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false) && dataList.get(groupPosition).getDiningList().get(childPosition).isItemSelected()) {
                switch4.setEnabled(false);
                GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false);
                GlobalClass.editor.commit();
                dataList.get(groupPosition).getDiningList().get(childPosition).setItemSelected(false);
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                pushData(dataList);
                mListener.onItemClicked(dataList.get(groupPosition));
            } else if ((!GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, false) && !dataList.get(groupPosition).getDiningList().get(childPosition).isItemSelected()) || !GlobalClass.sharedPreferences.getBoolean(title + SharedPreferenceVariables.Dining_IsMultipleItemSelected, false)) {
                switch4.setEnabled(true);
                GlobalClass.editor.putBoolean(title + SharedPreferenceVariables.Dining_IsSingleItemSelected, true);
                GlobalClass.editor.commit();
                dataList.get(groupPosition).getDiningList().get(childPosition).setItemSelected(true);
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() + 1);
                pushData(dataList);
                mListener.onItemClicked(dataList.get(groupPosition));
            }

                /*if (isMultipleItemSelected){
                    holder.switch4.setOn(true);
                    GlobalClass.ShowAlert(holder.itemView.getContext(), "Alert", "Only one item can be selected, Please raise a new request for different item ");
                }*/

        });

        convertView.setSelected(true);
        return convertView;
    }

    private void pushData(List<Data> dataList) {

        Set<Data> set = new LinkedHashSet<>(dataList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString(title, json);
        GlobalClass.editor.commit();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
