package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.dining.DiningSubcategory;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

import static com.mobisprint.aurika.R.drawable.icon_veg;

public class IrdCustomizationAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<DiningSubcategory> diningSubcategory;
    private GlobalClass.CustomExpandableAdapterListenerIRD listner;



    public IrdCustomizationAdapter(Context mContext, List<DiningSubcategory> diningSubcategory, GlobalClass.CustomExpandableAdapterListenerIRD listner) {
        this.diningSubcategory = diningSubcategory;
        this.mContext = mContext;
        this.listner=listner;

    }


    @Override
    public int getGroupCount() {
        return this.diningSubcategory.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return diningSubcategory.get(groupPosition).getSubcategoryItems().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return diningSubcategory.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return diningSubcategory.get(groupPosition).getSubcategoryItems();
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
            convertView = layoutInflater.inflate(R.layout.ird_subcategory_group, null);
        }


        TextView price = convertView.findViewById(R.id.sub_category_price);
        RelativeLayout lyt_item = convertView.findViewById(R.id.lyt_item);
        ImageView img_veg_nonveg = convertView.findViewById(R.id.img_veg_nonveg);
        RadioButton radio_btn = convertView.findViewById(R.id.radio_btn);
        CheckBox checkbox_btn = convertView.findViewById(R.id.checkbox_btn);
        TextView sub_category_title = convertView.findViewById(R.id.sub_category_title);

        radio_btn.setChecked(diningSubcategory.get(groupPosition)
                .getRadioSelected());


        checkbox_btn.setChecked(diningSubcategory.get(groupPosition)
                .getCheckBoxSelected());


        if (!diningSubcategory.get(groupPosition).getPrice().isEmpty() && !diningSubcategory.get(groupPosition).getPrice().equalsIgnoreCase("0.00") ){
            price.setText("+"+ " ₹ " + diningSubcategory.get(groupPosition).getPrice());
        }else{
            price.setVisibility(View.GONE);
        }

        if (diningSubcategory.get(groupPosition).getItemOption().isEmpty()) {
            sub_category_title.setVisibility(View.VISIBLE);
            lyt_item.setVisibility(View.GONE);
            sub_category_title.setText(diningSubcategory.get(groupPosition).getTitle());
        } else {
            sub_category_title.setVisibility(View.GONE);
            lyt_item.setVisibility(View.VISIBLE);

            if (diningSubcategory.get(groupPosition).getItemType().isEmpty()) {
                img_veg_nonveg.setVisibility(View.INVISIBLE);
            } else {
                img_veg_nonveg.setVisibility(View.VISIBLE);
            }

            if (diningSubcategory.get(groupPosition).getItemType().equalsIgnoreCase("veg")) {
                img_veg_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
            } else {
                img_veg_nonveg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_nonveg));
            }

            if (diningSubcategory.get(groupPosition).getItemOption().equalsIgnoreCase("checkbox")) {
                radio_btn.setVisibility(View.GONE);
                checkbox_btn.setVisibility(View.VISIBLE);
                checkbox_btn.setText(diningSubcategory.get(groupPosition).getTitle());
            } else if (diningSubcategory.get(groupPosition).getItemOption().equalsIgnoreCase("radio")) {
                radio_btn.setVisibility(View.VISIBLE);
                checkbox_btn.setVisibility(View.GONE);
                radio_btn.setText(diningSubcategory.get(groupPosition).getTitle());

            }


            radio_btn.setOnClickListener(v -> {
                    setResetRadiobutton(groupPosition,true);

               // listner.onItemClicked(groupPosition,0,diningSubcategory.get(groupPosition));


            });

            checkbox_btn.setOnClickListener(v -> {
                if(diningSubcategory.get(groupPosition).getCheckBoxSelected()) {
                    diningSubcategory.get(groupPosition).setCheckBoxSelected(false);
                }else{
                    setResetcheckboxbutton(groupPosition);
                }
                listner.onItemClicked(groupPosition,0,diningSubcategory);
            });


        }

        ExpandableListView elv = (ExpandableListView) parent;
        elv.expandGroup(groupPosition);


        return convertView;
    }

    private void setResetcheckboxbutton(int groupPosition) {
        int counter=0 ;


        for (int i = 0; i < diningSubcategory.size(); i++) {
           if( diningSubcategory.get(i).getCheckBoxSelected()){
               counter+=1;
           }

        }
        if(counter<diningSubcategory.get(groupPosition).getMaxNoItems()){
            diningSubcategory.get(groupPosition)
                    .setCheckBoxSelected(true);
        }else{
            GlobalClass.ShowAlert(mContext,"","Max options available to select is "+diningSubcategory.get(groupPosition).getMaxNoItems());
        }
        notifyDataSetChanged();

    }

    private void setResetRadiobutton(int groupPosition, boolean value) {


        for (int i = 0; i < diningSubcategory.size(); i++) {
            diningSubcategory.get(i).setRadioSelected(false);
        }
        diningSubcategory.get(groupPosition)
                .setRadioSelected(value);
        notifyDataSetChanged();

         listner.onItemClicked(groupPosition,0,diningSubcategory);
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.mContext.
                    getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.ird_subcategory_group, null);
        }
        TextView price = convertView.findViewById(R.id.sub_category_price);
        RelativeLayout lyt_item = convertView.findViewById(R.id.lyt_item);
        ImageView img_veg_nonveg = convertView.findViewById(R.id.img_veg_nonveg);
        RadioButton radio_btn = convertView.findViewById(R.id.radio_btn);
        CheckBox checkbox_btn = convertView.findViewById(R.id.checkbox_btn);
        TextView sub_category_title = convertView.findViewById(R.id.sub_category_title);
        sub_category_title.setVisibility(View.GONE);
        lyt_item.setVisibility(View.VISIBLE);
        radio_btn.setChecked(diningSubcategory.get(groupPosition)
                .getSubcategoryItems().get(childPosition).getRadioSelected());

        checkbox_btn.setChecked(diningSubcategory.get(groupPosition)
                .getSubcategoryItems().get(childPosition).getCheckBoxSelected());

        if (diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getItemType().isEmpty()) {
            img_veg_nonveg.setVisibility(View.INVISIBLE);
        } else {
            img_veg_nonveg.setVisibility(View.VISIBLE);
        }

        if (!diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getPrice().isEmpty() && !diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getPrice().equalsIgnoreCase("0.00")){
            price.setText("+"+ " ₹ " +diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getPrice());
        }else{
            price.setVisibility(View.GONE);
        }



        if (diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getItemType().equalsIgnoreCase("veg")) {
            img_veg_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
        } else {
            img_veg_nonveg.setImageDrawable(mContext.getResources().getDrawable(R.drawable.icon_nonveg));
        }

        if (diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getItemOption().equalsIgnoreCase("checkbox")) {
            radio_btn.setVisibility(View.GONE);
            checkbox_btn.setVisibility(View.VISIBLE);
            checkbox_btn.setText(diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getTitle());
        } else if (diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getItemOption().equalsIgnoreCase("radio")) {
            radio_btn.setVisibility(View.VISIBLE);
            checkbox_btn.setVisibility(View.GONE);
            radio_btn.setText(diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getTitle());

        }




        radio_btn.setOnClickListener(v -> {

                setResetChildRadiobutton(groupPosition,childPosition,true);



        });

        checkbox_btn.setOnClickListener(v -> {
            if(diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getCheckBoxSelected()) {
                diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).setCheckBoxSelected(false);
            }else{
                setResetChildCheckboxbutton(groupPosition,childPosition);
            }
            listner.onItemClicked(groupPosition,childPosition,diningSubcategory);
        });


        return convertView;
    }


    private void setResetChildCheckboxbutton(int groupPosition, int childPosition) {
        int counter=0 ;


        for (int i = 0; i < diningSubcategory.get(groupPosition).getSubcategoryItems().size(); i++) {
            if( diningSubcategory.get(groupPosition).getSubcategoryItems().get(i).getCheckBoxSelected()){
                counter+=1;
            }

        }
        if(counter<diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition).getMaxNoItems()){
            diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition)
                    .setCheckBoxSelected(true);
        }else{
            GlobalClass.ShowAlert(mContext,"","Max options available to select is "+diningSubcategory.get(groupPosition).getMaxNoItems());
        }
        notifyDataSetChanged();


    }

    private void setResetChildRadiobutton(int groupPosition , int childPosition, boolean value) {

        for (int i = 0; i < diningSubcategory.get(groupPosition).getSubcategoryItems().size(); i++) {
            diningSubcategory.get(groupPosition).getSubcategoryItems().get(i).setRadioSelected(false);
        }
        diningSubcategory.get(groupPosition).getSubcategoryItems().get(childPosition)
                .setRadioSelected(value);
        notifyDataSetChanged();
        listner.onItemClicked(groupPosition,childPosition,diningSubcategory);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
