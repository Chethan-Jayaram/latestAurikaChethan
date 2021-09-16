package com.mobisprint.aurika.coorg.fragments.dining;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.IrdCustomizationAdapter;
import com.mobisprint.aurika.coorg.pojo.dining.DiningSubcategory;

import java.util.ArrayList;
import java.util.List;

import static com.mobisprint.aurika.R.drawable.icon_nonveg;
import static com.mobisprint.aurika.R.drawable.icon_veg;


public class IrdCustomizationFrgament extends BottomSheetDialogFragment {

    private List<DiningSubcategory> diningSubcategoryList = new ArrayList<>();
    private ImageView veg_or_nonveg,img_close;
    private TextView item_name,item_price;
    private ExpandableListView expandableListView;
    private Context mContext;
    private IrdCustomizationAdapter adapter;
    private Button bt_add;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ird_customization_frgament, container, false);

        mContext = getContext();
        Bundle bundle = getArguments();
        diningSubcategoryList =  bundle.getParcelableArrayList("Sub-category");

        veg_or_nonveg = view.findViewById(R.id.img_veg_nonveg);

        item_name = view.findViewById(R.id.tv_item_name);
        img_close = view.findViewById(R.id.img_close);

        item_price = view.findViewById(R.id.price);

        expandableListView = view.findViewById(R.id.sub_category_expandable_list);

        bt_add = view.findViewById(R.id.bt_add);


        item_name.setText(bundle.getString("item_name"));

        item_price.setText("₹ " + bundle.getString("item_price"));

        if (bundle.getString("item_type").isEmpty()){
            veg_or_nonveg.setVisibility(View.INVISIBLE);
        }else {
            veg_or_nonveg.setVisibility(View.VISIBLE);
        }


        if (bundle.getString("item_type").equalsIgnoreCase("veg")){
            veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_veg));
        }else{
            veg_or_nonveg.setImageDrawable(mContext.getResources().getDrawable(icon_nonveg));
        }

        adapter = new IrdCustomizationAdapter(mContext,diningSubcategoryList);
        expandableListView.setAdapter(adapter);


        img_close.setOnClickListener(v -> {
            this.dismiss();
        });
        bt_add.setOnClickListener(v->{
            this.dismiss();
        });

        return view;
    }
}