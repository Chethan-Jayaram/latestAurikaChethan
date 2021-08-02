package com.mobisprint.aurika.coorg.fragments.spa;

import android.content.Context;
import android.os.Bundle;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.SpaAdapter;
import com.mobisprint.aurika.coorg.controller.spa.SpaController;
import com.mobisprint.aurika.coorg.pojo.spa.Data;
import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import org.w3c.dom.Text;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import retrofit2.Response;


public class SpaFragment extends Fragment implements ApiListner {


    private TextView tv_spa_menu_desc,toolbar_title,tv_spa_title,tv_coorg_assistance,tv_spa_to_time,tv_spa_from_time;
    private RecyclerView spa_recyclerview;
    private SpaController controller;
    private Context mContext;
    private ImageView img_back,img_spa;
    private Button btn_spa_menu,btn_spa_appointment;
    private ProgressBar progressBar;
    private CoordinatorLayout coordinatorLayout;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_spa, container, false);

        tv_spa_title = view.findViewById(R.id.tv_spa_title);
        tv_coorg_assistance = view.findViewById(R.id.tv_coorg_assistance);
        tv_spa_from_time = view.findViewById(R.id.tv_spa_from_time);
        tv_spa_to_time = view.findViewById(R.id.tv_spa_to_time);
        img_spa = view.findViewById(R.id.img_spa);
        btn_spa_menu = view.findViewById(R.id.btn_spa_menu);
        btn_spa_appointment = view.findViewById(R.id.btn_spa_appointment);

        coordinatorLayout = view.findViewById(R.id.lyt);
        coordinatorLayout.setVisibility(View.GONE);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.GONE);

        tv_spa_menu_desc = view.findViewById(R.id.tv_spa_desc);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        controller = new SpaController(this);
        mContext = getContext();
        img_back = getActivity().findViewById(R.id.naviagation_hamberger);
        img_back.setVisibility(View.VISIBLE);

        Bundle bundle = getArguments();
        toolbar_title.setText(bundle.getString("title"));

        controller.getSpaMenu();
        tv_coorg_assistance.setText("Please call 2011 for assistance");





        return view;
    }

    @Override
    public void onFetchProgress() {

        progressBar.setVisibility(View.VISIBLE);


    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        progressBar.setVisibility(View.GONE);
        coordinatorLayout.setVisibility(View.VISIBLE);

        if (response != null){
            Spa spa = (Spa) response.body();
            List<Data> spaList = spa.getData();

            tv_spa_title.setText(spaList.get(0).getTitle());
            /*tv_spa_menu_desc.setText(spaList.get(0).getDescription());*/

            Glide.with(getContext()).load(spaList.get(0).getImage()).centerCrop().into(img_spa);


            try{
                SimpleDateFormat _24HourSDF = new SimpleDateFormat("HH:mm");
                SimpleDateFormat _12HourSDF = new SimpleDateFormat("hh:mm a");
                Date _24HourDt1 = _24HourSDF.parse(spaList.get(0).getFromTime());
                Date _24HourDt2 = _24HourSDF.parse(spaList.get(0).getToTime());
               tv_spa_from_time.setText(" "+_12HourSDF.format(_24HourDt1));
               tv_spa_to_time.setText(_12HourSDF.format(_24HourDt2));
            }catch (Exception e){
                e.printStackTrace();
            }

            btn_spa_menu.setOnClickListener(v -> {
                Fragment fragment = new CoorgSpaMenu();
                Bundle bundle = new Bundle();
                bundle.putString("title",spaList.get(0).getTitle());
                bundle.putString("image",spaList.get(0).getImage());
                fragment.setArguments(bundle);
                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.fragment_coorg_container, fragment).addToBackStack(null);
                fragmentTransaction.commit();*/

                getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
            });

            btn_spa_appointment.setOnClickListener(v -> {
                Fragment fragment = new SpaBookAnAppointment();
                Bundle bundle = new Bundle();
                bundle.putString("title",spaList.get(0).getTitle());
                bundle.putString("image",spaList.get(0).getImage());
                fragment.setArguments(bundle);
                /*FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                fragmentTransaction.replace(R.id.fragment_coorg_container, fragment).addToBackStack(null);
                fragmentTransaction.commit();*/
                getFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
            });



        }

    }

    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}