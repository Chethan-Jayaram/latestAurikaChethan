package com.mobisprint.aurika.fragments.menufragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.pojo.testing.AppDatum;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class InRoomDiningFragment extends Fragment {


    private TextView toolbar_title,top_description;
    private ImageView backBtn;
    private TextView break_fast_menu,dining_menu,children_menu,wine_menu,midnigt_menu,beverage_menu;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_in_room_dining, container, false);
        toolbar_title =  getActivity().findViewById(R.id.toolbar_title);
        backBtn =  getActivity().findViewById(R.id.naviagation_hamberger);
        dining_menu=  view.findViewById(R.id.dining_menu);
        wine_menu=  view.findViewById(R.id.wine_menu);
        midnigt_menu=  view.findViewById(R.id.midnigt_menu);
        children_menu=  view.findViewById(R.id.children_menu);
        beverage_menu=  view.findViewById(R.id.beverage_menu);
        backBtn.setVisibility(View.VISIBLE);
        toolbar_title.setText("In-Room Dining");
        top_description=  view.findViewById(R.id.top_description);
        parsejson();

        break_fast_menu =  view.findViewById(R.id.break_fast_menu);
        break_fast_menu.setOnClickListener(view1 -> {
            ChangeFragment(0);
        });
        dining_menu.setOnClickListener(view1 -> {
            ChangeFragment(1);
          
        });
        children_menu.setOnClickListener(view1 -> {
            ChangeFragment(2);
         
        });
        wine_menu.setOnClickListener(view1 -> {
            ChangeFragment(3);
           
        });
        midnigt_menu.setOnClickListener(view1 -> {
            ChangeFragment(4);
            MidNightMenuFragment aa = new MidNightMenuFragment();
           
        });
        beverage_menu.setOnClickListener(view1 -> {
            ChangeFragment(5);
        });

        return view;
    }
    private void ChangeFragment(int position) {
        Fragment fragment;

        switch (position) {
            case 0:
                fragment = new BreakFastMenuFragment();
                break;
            case 1:
                fragment = new AllDayDiningMenuFragment();
                break;
            case 2:
                fragment = new ChildrenMenuFragment();
                break;
            case 3:
                fragment = new WineListMenuFragment();
                break;
            case 4:
                fragment = new MidNightMenuFragment();
                break;
            case 5:
                fragment = new BeverageMenuFragment();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
        fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }



    public void parsejson() {
        try {
            GsonBuilder builder = new GsonBuilder();
            builder.setPrettyPrinting();
            Gson gson = builder.create();
            Testing generalPojo = gson.fromJson(GlobalClass.APPDATA, Testing.class);
            List<AppDatum> appDatum = generalPojo.getAppData();

            top_description.setText(appDatum.get(0).getItems().get(3).getSubTitle());

        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
