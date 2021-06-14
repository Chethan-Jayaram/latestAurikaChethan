package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
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

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.dining.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import static com.mobisprint.aurika.R.drawable.icon_veg;

public class InRoomDiningMenuAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<Data> dataList;
    private GlobalClass.ExpandableAdapterListenerIRD mListener;

    public InRoomDiningMenuAdapter(Context mContext, List<Data> dataList,GlobalClass.ExpandableAdapterListenerIRD mListener) {
        this.mContext = mContext;
        this.dataList = dataList;
        this.mListener = mListener;
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


        if (groupPosition==0){
            lyt_view.setVisibility(View.GONE);
        }

        TextView title = convertView.findViewById(R.id.tv_dining_menu_title);

        title.setText(dataList.get(groupPosition).getTitle());

        ExpandableListView elv = (ExpandableListView)  parent;
        elv.expandGroup(groupPosition);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        final Dining__1 expandedListText = (Dining__1) getChild(groupPosition,childPosition);
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


        if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() == 0){
            lyt_add.setVisibility(View.VISIBLE);
            lyt_counter.setVisibility(View.GONE);
        }else{
            lyt_add.setVisibility(View.GONE);
            lyt_counter.setVisibility(View.VISIBLE);
        }

        bt_add.setOnClickListener(v -> {
            dataList.get(groupPosition).getDiningList().get(childPosition).setCount(expandedListText.getCount() + 1);
            tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
            mListener.onItemClicked(dataList.get(groupPosition));
            pushData(dataList);
            lyt_add.setVisibility(View.GONE);
            lyt_counter.setVisibility(View.VISIBLE);
        });

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getItemType().equals("Veg")) {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
        }else {
            img_veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_nonveg));
        }

        if (dataList.get(groupPosition).getDiningList().get(childPosition).getDescription() !=null &&  !dataList.get(groupPosition).getDiningList().get(childPosition).getDescription().isEmpty()){

            lyt_desc.setVisibility(View.VISIBLE);
            dining_menu_desc.setVisibility(View.VISIBLE);
            dining_menu_desc.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getDescription());



        }else {
            dining_menu_desc.setVisibility(View.GONE);
            lyt_desc.setVisibility(View.GONE);
        }



        sub_heading.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getTitle());
        dining_menu_price.setText("₹"+" "+dataList.get(groupPosition).getDiningList().get(childPosition).getPrice());
       /* dining_menu_desc.setText(dataList.get(groupPosition).getDiningList().get(childPosition).getDescription());*/
        tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));

        img_add.setOnClickListener(v -> {
            dataList.get(groupPosition).getDiningList().get(childPosition).setCount( dataList.get(groupPosition).getDiningList().get(childPosition).getCount()+1);
            tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
            pushData(dataList);

           mListener.onItemClicked(dataList.get(groupPosition));

        });

        img_remove.setOnClickListener(v -> {
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount() == 1) {
                lyt_add.setVisibility(View.VISIBLE);
                lyt_counter.setVisibility(View.GONE);
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount(dataList.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                mListener.onItemClicked(dataList.get(groupPosition));
                pushData(dataList);
            }else
            if (dataList.get(groupPosition).getDiningList().get(childPosition).getCount()>0){
                dataList.get(groupPosition).getDiningList().get(childPosition).setCount( dataList.get(groupPosition).getDiningList().get(childPosition).getCount()-1);
                tv_quantity.setText(Integer.toString(dataList.get(groupPosition).getDiningList().get(childPosition).getCount()));
                pushData(dataList);

                mListener.onItemClicked(dataList.get(groupPosition));

            }
        });

        return convertView;
    }

    private void pushData(List<Data> dataList) {

        Set<Data> set = new LinkedHashSet<>(dataList);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("Dining", json);
        GlobalClass.editor.commit();
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
