package com.mobisprint.aurika.udaipur.adapter;

import android.content.Context;
import android.graphics.Typeface;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.helper.MenuListner;
import com.mobisprint.aurika.udaipur.pojo.testing.Item__;
import com.mobisprint.aurika.udaipur.pojo.testing.Item___;
import com.mobisprint.aurika.udaipur.pojo.testing.PriceList;


import java.util.List;


public class BreakfastMenuListnerAdapter extends RecyclerView.Adapter {

    private List<MenuListner> mMenuList;
    private Context context;


    public BreakfastMenuListnerAdapter(List<MenuListner> mMenuList) {
        this.mMenuList = mMenuList;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView;
        context = parent.getContext();
        switch (viewType) {
            case MenuListner.TYPE_CATEGORY:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_title_content, parent, false);
                return new MenuCategoryViewHolder(itemView);
            case MenuListner.TYPE_ITEM:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_content, parent, false);
                return new MenuItemViewHolder(itemView);
            default:
                itemView = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.price_list_content, parent, false);
                return new PriceListViewHolder(itemView);
        }


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        switch (getItemViewType(position)) {
            case MenuListner.TYPE_CATEGORY:
                ((MenuCategoryViewHolder) holder).bindView(position);
                break;
            case MenuListner.TYPE_ITEM:
                ((MenuItemViewHolder) holder).bindView(position);
                break;
            case MenuListner.TYPE_PRICELIST:
                ((PriceListViewHolder) holder).bindView(position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        if (mMenuList == null) {
            return 0;
        } else {
            return mMenuList.size();
        }
    }

    @Override
    public int getItemViewType(int position) {
        Log.d("item_type",String.valueOf(mMenuList.get(position).getType()));
        return mMenuList.get(position).getType();
    }


    class MenuCategoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_category_title;

        MenuCategoryViewHolder(View view) {
            super(view);
            tv_category_title = view.findViewById(R.id.tv_category_title);

        }

        void bindView(int position) {
            try {
                Item__ item__ = (Item__) mMenuList.get(position);
                tv_category_title.setText(item__.getCategoryTitle());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_name, tv_item_desc, tv_item_price;
        private ImageView img_veg_nonveg;

        MenuItemViewHolder(View view) {
            super(view);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_desc = view.findViewById(R.id.tv_item_desc);
            tv_item_price = view.findViewById(R.id.tv_item_price);
            img_veg_nonveg = view.findViewById(R.id.img_veg_nonveg);
        }

        void bindView(int position) {
            try {
                img_veg_nonveg.setVisibility(View.INVISIBLE);
                tv_item_price.setVisibility(View.GONE);
                Item___ item___ = (Item___) mMenuList.get(position);
                if (item___.getItemName().isEmpty()) {
                    tv_item_name.setVisibility(View.GONE);
                } else {
                    tv_item_name.setVisibility(View.VISIBLE);
                    tv_item_name.setText(item___.getItemName());
                }
                if (item___.getItemDescription().isEmpty()) {
                    tv_item_desc.setVisibility(View.GONE);
                } else {
                    tv_item_desc.setVisibility(View.VISIBLE);
                    tv_item_desc.setText(item___.getItemDescription());
                }
                if (item___.getDisplayIcon()){
                    if (item___.getIsVeg()) {
                        img_veg_nonveg.setVisibility(View.VISIBLE);
                        img_veg_nonveg.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_veg));
                    } else {
                        img_veg_nonveg.setVisibility(View.VISIBLE);
                        img_veg_nonveg.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_nonveg));
                    }
                }
                if(!item___.getItemPrice().isEmpty()) {
                    tv_item_price.setVisibility(View.VISIBLE);
                    Typeface face = Typeface.createFromAsset(context.getAssets(), "Rupee_Foradian.ttf");
                    tv_item_price.setTypeface(face);
                    tv_item_price.setText(context.getResources().getString(R.string.rs) + item___.getItemPrice());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class PriceListViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_item_name, tv_item_price,tv_item_desc;
        private ImageView img_veg_nonveg;

        PriceListViewHolder(View view) {
            super(view);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_price = view.findViewById(R.id.tv_item_price);
            img_veg_nonveg = view.findViewById(R.id.img_veg_nonveg);
            tv_item_desc = view.findViewById(R.id.tv_item_desc);
        }

        void bindView(int position) {
            try {
                tv_item_desc.setVisibility(View.GONE);
                img_veg_nonveg.setVisibility(View.INVISIBLE);
                PriceList priceList = (PriceList) mMenuList.get(position);
                if(priceList.getItemName().isEmpty()){
                    tv_item_name.setVisibility(View.GONE);
                }else{
                    tv_item_name.setVisibility(View.VISIBLE);
                    tv_item_name.setText(priceList.getItemName());
                }
                if (priceList.getDisplayIcon()) {
                    if (priceList.getIsVeg()) {
                        img_veg_nonveg.setVisibility(View.VISIBLE);
                        img_veg_nonveg.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_veg));
                    } else {
                        img_veg_nonveg.setVisibility(View.VISIBLE);
                        img_veg_nonveg.setImageDrawable(context.getResources().getDrawable(R.drawable.icon_nonveg));
                    }
                }
                Typeface face = Typeface.createFromAsset(context.getAssets(), "Rupee_Foradian.ttf");
                tv_item_price.setTypeface(face);
                tv_item_price.setText(context.getResources().getString(R.string.rs) + priceList.getItemPrice());
                if(priceList.getItemDescription()!=null){
                    tv_item_desc.setVisibility(View.VISIBLE);
                    tv_item_desc.setText(priceList.getItemDescription());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}