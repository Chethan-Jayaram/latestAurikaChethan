package com.mobisprint.aurika.coorg.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.fragments.HomeFragment;
import com.mobisprint.aurika.coorg.pojo.home.Data;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.List;

public class HomeScreenAdapter extends BaseAdapter {

    private List<Data> homeList;
    private  Context mContext;
    private GlobalClass.AdapterListener mListener;

    public HomeScreenAdapter(Context mContext, List<Data> homeList, GlobalClass.AdapterListener mListener) {
        this.homeList = homeList;
        this.mContext = mContext;
        this.mListener = mListener;
    }

    @Override
    public int getCount() {
        return homeList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View grid;
        LayoutInflater inflater = (LayoutInflater) mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        grid = inflater.inflate(R.layout.home_row, null);

        try{

            if (convertView == null) {
                TextView textView = grid.findViewById(R.id.grid_text);
                ImageView imageView = grid.findViewById(R.id.grid_image);

                textView.setText(homeList.get(position).getTitle());
                Glide.with(mContext)
                        .load(homeList.get(position).getImage())
                        .into(imageView);

                imageView.setOnClickListener(v->{
                    mListener.onItemClicked(position);
                });
            } else {
                grid = (View) convertView;
            }


        }catch (Exception e){
            e.printStackTrace();
        }



        return grid;
    }
}
