package com.mobisprint.aurika.fragments;


import android.content.Context;

import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.assaabloy.mobilekeys.api.MobileKeys;
import com.assaabloy.mobilekeys.api.MobileKeysCallback;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.mobisprint.aurika.fragments.doorunlockfragments.SendOtpScreenFragment;
import com.mobisprint.aurika.fragments.menufragments.InRoomDiningFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.ExperienceFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.RecreationFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.ServicesFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.SightSeeingFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.SleepWellFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.SpaSaloonFragment;
import com.mobisprint.aurika.fragments.sectionsfragment.WineDineFragment;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.HomeAdapter;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.unlock.MobileKeysApiFacade;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeGridFragment extends Fragment implements MobileKeysCallback {

    private static final String TAG = HomeGridFragment.class.getName();

    private MobileKeysApiFacade mobileKeysApiFacade;
    private Toolbar toolbar_cart;

    private  GridView gridView;
    private  ImageView backBtn;
    private  TextView toolbar_title;
    private  String[] homeNames = {
            "Unlock Your Door",
            "Services",
            "Sleep Well",
            "Wine and Dine",
            "In-Room Dining",
            "Experiences",
            "Spa and Saloon",
            "Recreation",
            "Sightseeing"

    };
    private  int[] homeImages = {
            R.drawable.ic_unlock_door,
            R.drawable.ic_services,
            R.drawable.ic_sleep_well,
            R.drawable.ic_wine_dine,
            R.drawable.ic_inroom_dining,
            R.drawable.ic_exclusive_experiences,
            R.drawable.ic_spa_salon,
            R.drawable.ic_recreation,
            R.drawable.ic_sightseeing

    };


    private LinearLayout ll_conent;
    private  ImageView img_view;
private  Context context;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MobileKeysApiFacade)) {
            throw new IllegalArgumentException("Error: attaching to context that doesn't implement MobileKeysApiFacade");
        }
        mobileKeysApiFacade = (MobileKeysApiFacade) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home_grid, container, false);

        mobileKeysStartup();
        context=view.getContext();
        backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        toolbar_title.setText(getResources().getString(R.string.toolbar_title));
        ll_conent =  view.findViewById(R.id.ll_conent);
        ll_conent.setVisibility(View.VISIBLE);
        HomeAdapter adapter = new HomeAdapter(getActivity(), homeNames, homeImages);
        gridView = view.findViewById(R.id.gridview);
        gridView.setAdapter(adapter);
        toolbar_cart=getActivity().findViewById(R.id.toolbar_cart);



        toolbar_cart.setVisibility(View.VISIBLE);

        backBtn.setVisibility(View.GONE);
        img_view =  view.findViewById(R.id.img_view);
        gridView.setOnItemClickListener((parent, view1, position, id) -> ChangeFragment(position));
        img_view.setImageResource(R.drawable.homescreenbanner);
        return view;
    }


    private void ChangeFragment(int position) {
         Fragment fragment;
        if(GlobalClass.previous!=position) {
            switch (position) {
                case 0:
                    fragment = new SendOtpScreenFragment();// Statements
                    break;
                case 1:
                    fragment = new ServicesFragment();
                    break;
                case 2:
                    fragment = new SleepWellFragment();
                    break;
                case 3:
                    fragment = new WineDineFragment();
                    break;
                case 4:
                    fragment = new InRoomDiningFragment();
                    break;
                case 5:
                    fragment = new ExperienceFragment();
                    break;
                case 6:
                    fragment = new SpaSaloonFragment();
                    break; // break is optional
                case 7:
                    fragment = new RecreationFragment();
                    break; // break is optional
                case 8:
                    fragment = new SightSeeingFragment();
                    break; // break is optional
                default:
                    fragment = new HomeGridFragment();
            }
            FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
            if (position != 0) {
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
            }
            fragmentTransaction.replace(R.id.fragment_container, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }



    private void mobileKeysStartup() {

        MobileKeys mobileKeys = mobileKeysApiFacade.getMobileKeys();
        mobileKeys.applicationStartup(this);
    }

    /**
     * Startup callback
     */
    @Override
    public void handleMobileKeysTransactionCompleted() {
        mobileKeysApiFacade.onStartUpComplete();
    }

    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException mobileKeysException) {
     mobileKeysException.printStackTrace();
    }




}
