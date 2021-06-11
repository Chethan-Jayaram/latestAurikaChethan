package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;

import com.google.gson.Gson;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.pojo.Services.Category_item;
import com.mobisprint.aurika.coorg.pojo.Services.Data;
import com.mobisprint.aurika.coorg.pojo.dining.Dining__1;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.mobisprint.aurika.R.drawable.icon_veg;

public class ReviewOrderExpandableListAdapter extends BaseExpandableListAdapter {

    private List<Data> arrPackageData;
    private List<com.mobisprint.aurika.coorg.pojo.dining.Data> diningArrPackageData;
    private GlobalClass.ExpandableAdapterListener mListener;
    private GlobalClass.ExpandableAdapterListenerIRD mListenerIRD;

    public ReviewOrderExpandableListAdapter(List<Data> arrPackageData, Context mContext,GlobalClass.ExpandableAdapterListener mListener) {
        this.arrPackageData = arrPackageData;
        this.mListener = mListener;
    }


    public ReviewOrderExpandableListAdapter(List<com.mobisprint.aurika.coorg.pojo.dining.Data> diningArrPackageData, String dining,GlobalClass.ExpandableAdapterListenerIRD mListenerIRD) {
        this.diningArrPackageData = diningArrPackageData;
        this.mListenerIRD = mListenerIRD;
    }

    @Override
    public int getGroupCount() {
        if (arrPackageData!=null) {
            return arrPackageData.size();
        }else if(diningArrPackageData!=null){
            return diningArrPackageData.size();
        }
        return 0;
    }

    @Override
    public int getChildrenCount(int groupPosition) {

        if (arrPackageData!=null) {
            return arrPackageData.get(groupPosition).getCategory_item().size();
        }else if(diningArrPackageData!=null){
            return diningArrPackageData.get(groupPosition).getDiningList().size();
        }
        return 0;

    }

    @Override
    public Object getGroup(int groupPosition) {

        if (arrPackageData!=null) {
            return arrPackageData.get(groupPosition);
        }else if(diningArrPackageData!=null){
            return diningArrPackageData.get(groupPosition);
        }
        return 0;

    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {

        if (arrPackageData!=null) {
            return arrPackageData.get(groupPosition).getCategory_item().get(childPosition);
        }else if(diningArrPackageData!=null){
            return diningArrPackageData.get(groupPosition).getDiningList().get(childPosition);
        }
        return 0;

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

        try{

            if (arrPackageData != null) {


                if (convertView == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().
                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.directory_of_services_title, null);
                }

                TextView listTitleTextView = (TextView) convertView
                        .findViewById(R.id.tv_directory_of_service_title);
                ImageView imageView = convertView.findViewById(R.id.img_dropdown);
                imageView.setVisibility(View.GONE);
                RelativeLayout relativeLayout = convertView.findViewById(R.id.lyt_header_view);
                relativeLayout.setVisibility(View.GONE);
                RelativeLayout group_lyt=convertView.findViewById(R.id.group_lyt);

                if (arrPackageData.get(groupPosition).getCategory_item().size() != 0) {
                    listTitleTextView.setTypeface(null, Typeface.BOLD);
                    listTitleTextView.setText(arrPackageData.get(groupPosition).getName());
                }

                ExpandableListView elv = (ExpandableListView) parent;
                elv.expandGroup(groupPosition);

              for(int i=0;i<arrPackageData.get(groupPosition).getCategory_item().size();i++){
                    if(arrPackageData.get(groupPosition).getCategory_item().get(i).getCount()>0){
                        group_lyt.setVisibility(View.VISIBLE);
                        break;
                    }else{

                        group_lyt.setVisibility(View.GONE);
                    }
                }


            }else if (diningArrPackageData!=null){

                if (convertView == null) {
                    LayoutInflater layoutInflater = (LayoutInflater) parent.getContext().
                            getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    convertView = layoutInflater.inflate(R.layout.dining_menu_heading, null);
                }

                TextView title = convertView.findViewById(R.id.tv_dining_menu_title);
                RelativeLayout group_lyt = convertView.findViewById(R.id.group_lyt);
                View lyt_view = convertView.findViewById(R.id.lyt_view);

                lyt_view.setVisibility(View.GONE);

                title.setText(diningArrPackageData.get(groupPosition).getTitle());

                ExpandableListView elv = (ExpandableListView)  parent;
                elv.expandGroup(groupPosition);


                for(int i=0;i<diningArrPackageData.get(groupPosition).getDiningList().size();i++){
                    if(diningArrPackageData.get(groupPosition).getDiningList().get(i).getCount()>0){
                        group_lyt.setVisibility(View.VISIBLE);
                        break;
                    }else{

                        group_lyt.setVisibility(View.GONE);
                    }
                }

            }




        } catch (Exception e) {
            e.printStackTrace();
        }

        return convertView;


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        if (arrPackageData!=null){
            final Category_item expandedListText = (Category_item) getChild(groupPosition,childPosition);

            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.amenities_recyclerview, null);
            }

            TextView item_name = convertView.findViewById(R.id.tv_amenities_item_name);
            TextView item_price = convertView.findViewById(R.id.tv_amenities_item_price);
            TextView tv_quantity = convertView.findViewById(R.id.tv_quantity);
            ImageView img_add = convertView.findViewById(R.id.img_add);
            ImageView img_remove = convertView.findViewById(R.id.img_remove);
            RelativeLayout lyt_items = convertView.findViewById(R.id.lyt_items);
            lyt_items.setVisibility(View.GONE);


            if (expandedListText.getCount()>0){
                lyt_items.setVisibility(View.VISIBLE);
                item_name.setText(expandedListText.getName());
                item_price.setText("₹" + " " + expandedListText.getPrice());
                tv_quantity.setText(Integer.toString(expandedListText.getCount()));
            }





            img_add.setOnClickListener(v -> {
                try {
                    arrPackageData.get(groupPosition).getCategory_item().get(childPosition).setCount(expandedListText.getCount() + 1);
                    tv_quantity.setText(Integer.toString(expandedListText.getCount()));
                    mListener.onItemClicked(arrPackageData.get(groupPosition));
                    pushData(arrPackageData);
                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (arrPackageData.get(groupPosition).getCategory_item().get(childPosition).getCount() == 1){
                     Alert(parent.getContext(),groupPosition,childPosition,lyt_items);
                }else if (arrPackageData.get(groupPosition).getCategory_item().get(childPosition).getCount() > 0) {
                    arrPackageData.get(groupPosition).getCategory_item().get(childPosition).setCount(arrPackageData.get(groupPosition).getCategory_item().get(childPosition).getCount() - 1);
                    tv_quantity.setText(Integer.toString(expandedListText.getCount()));
                    mListener.onItemClicked(arrPackageData.get(groupPosition));
                   pushData(arrPackageData);
                   lyt_items.setVisibility(View.VISIBLE);
                }

            });

        }

        if (diningArrPackageData!=null){
            final Dining__1 expandedListText = (Dining__1) getChild(groupPosition,childPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) parent.getContext()
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
            RelativeLayout lyt_view = convertView.findViewById(R.id.lyt_item_view);
            lyt_view.setVisibility(View.GONE);
            RelativeLayout lyt_desc = convertView.findViewById(R.id.lyt_desc);
            lyt_desc.setVisibility(View.GONE);

            if (diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getDescription() !=null &&  !diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getDescription().isEmpty()){

                lyt_desc.setVisibility(View.VISIBLE);
                dining_menu_desc.setVisibility(View.VISIBLE);
                dining_menu_desc.setText(diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getDescription());


            }else {
                dining_menu_desc.setVisibility(View.GONE);
                lyt_desc.setVisibility(View.GONE);
            }

            if (expandedListText.getCount()>0){
                lyt_view.setVisibility(View.VISIBLE);
                if (expandedListText.getItemType().equals("Veg")) {
                    img_veg_or_nonveg.setImageDrawable(parent.getContext().getResources().getDrawable(icon_veg));
                }else {
                    img_veg_or_nonveg.setImageDrawable(parent.getContext().getResources().getDrawable(R.drawable.icon_nonveg));
                }
                sub_heading.setText(expandedListText.getTitle());
                dining_menu_price.setText("₹"+" "+expandedListText.getPrice());
                dining_menu_desc.setText(expandedListText.getDescription());
                tv_quantity.setText(Integer.toString(expandedListText.getCount()));

            }


            img_add.setOnClickListener(v -> {
                try {
                    diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).setCount(diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount() + 1);
                    tv_quantity.setText(Integer.toString(diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount()));
                    mListenerIRD.onItemClicked(diningArrPackageData.get(groupPosition));
                    pushDataDining(diningArrPackageData);
                }catch (Exception e){
                    e.printStackTrace();

                }
            });

            img_remove.setOnClickListener(v -> {
                if (diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount() == 1){
                    AlertIRD(parent.getContext(),groupPosition,childPosition,lyt_view);
                }else if (diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount() > 0) {
                    diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).setCount(diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                    tv_quantity.setText(Integer.toString(expandedListText.getCount()));
                    mListenerIRD.onItemClicked(diningArrPackageData.get(groupPosition));
                    pushDataDining(diningArrPackageData);
                    lyt_view.setVisibility(View.VISIBLE);
                }

            });



        /*if (expandedListText.get(childPosition).getDescription().isEmpty()){
            dining_menu_desc.setVisibility(View.GONE);
        }else {
            dining_menu_desc.setText(expandedListText.get(childPosition).getDescription());
        }*/

            /*img_add.setOnClickListener(v -> {
                expandedListText.setCount( expandedListText.getCount()+1);
                tv_quantity.setText(Integer.toString(expandedListText.getCount()));
                mListener.onItemClicked(dataList.get(groupPosition));
            });

            img_remove.setOnClickListener(v -> {
                if (expandedListText.getCount()>0){
                    expandedListText.setCount( expandedListText.getCount()-1);
                    tv_quantity.setText(Integer.toString(expandedListText.getCount()));
                    mListener.onItemClicked(dataList.get(groupPosition));
                }
            });*/
        }





        return convertView;
    }

    private void Alert(Context mContext, int groupPosition, int childPosition, RelativeLayout lyt_items) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    arrPackageData.get(groupPosition).getCategory_item().get(childPosition).setCount(arrPackageData.get(groupPosition).getCategory_item().get(childPosition).getCount() - 1);
                    mListener.onItemClicked(arrPackageData.get(groupPosition));
                    lyt_items.setVisibility(View.GONE);
                    pushData(arrPackageData);
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private void AlertIRD(Context mContext, int groupPosition, int childPosition, RelativeLayout lyt_items) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

        builder.setMessage("Are you sure you want to remove this item from cart?")
                .setCancelable(false)
                .setPositiveButton("Yes", (dialog, id) -> {
                    diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).setCount(diningArrPackageData.get(groupPosition).getDiningList().get(childPosition).getCount() - 1);
                    mListenerIRD.onItemClicked(diningArrPackageData.get(groupPosition));
                    lyt_items.setVisibility(View.GONE);
                    pushDataDining(diningArrPackageData);
                    notifyDataSetChanged();
                })
                .setNegativeButton("No", (dialog, id) -> {
                    dialog.dismiss();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }



    private void pushData(List<Data> arrPackageData) {

        Set<Data> set = new HashSet<>(arrPackageData);
        Gson gson = new Gson();
        String json = gson.toJson(set);
        GlobalClass.editor.putString("Laundry", json);
        GlobalClass.editor.commit();
    }


    private void pushDataDining(List<com.mobisprint.aurika.coorg.pojo.dining.Data> diningArrPackageData) {

        Set<com.mobisprint.aurika.coorg.pojo.dining.Data> set = new HashSet<>(diningArrPackageData);
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
