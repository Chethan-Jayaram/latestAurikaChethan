package com.mobisprint.aurika.adapter;

import android.content.Context;
import android.graphics.Typeface;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.helper.MenuListner;
import com.mobisprint.aurika.pojo.testing.Item_____;
import com.mobisprint.aurika.pojo.testing.Menu;

import java.util.List;

public class SaloonAdapter extends RecyclerView.Adapter {

    private List<MenuListner> mMenuList;
    private Context context;


    public SaloonAdapter(List<MenuListner> mMenuList) {
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

        }
        return null;
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
                Menu menu = (Menu) mMenuList.get(position);
                tv_category_title.setText(menu.getCategoryName());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    class MenuItemViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_name, tv_item_desc, tv_item_price;


        MenuItemViewHolder(View view) {
            super(view);
            tv_item_name = view.findViewById(R.id.tv_item_name);
            tv_item_desc = view.findViewById(R.id.tv_item_desc);
            tv_item_price = view.findViewById(R.id.tv_item_price);
        }

        void bindView(int position) {
            try {
                Item_____ item____ = (Item_____) mMenuList.get(position);
                tv_item_desc.setVisibility(View.GONE);
                tv_item_name.setText(item____.getItemName());
                Typeface face = Typeface.createFromAsset(context.getAssets(), "Rupee_Foradian.ttf");
                tv_item_price.setTypeface(face);
                tv_item_price.setText(context.getResources().getString(R.string.rs) + item____.getItemPrice());


            } catch (Exception e) {
                Log.d("recycler error", e.getMessage());
                e.printStackTrace();
            }
        }
    }
}