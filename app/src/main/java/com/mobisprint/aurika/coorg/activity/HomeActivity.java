package com.mobisprint.aurika.coorg.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.adapter.NavigationAdapter;
import com.mobisprint.aurika.coorg.controller.HomeActivityController;
import com.mobisprint.aurika.coorg.fragments.HomeFragment;
import com.mobisprint.aurika.coorg.fragments.ProfileFragment;
import com.mobisprint.aurika.coorg.pojo.navigation.Data;
import com.mobisprint.aurika.coorg.pojo.navigation.Navigation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.RouteName;

import java.util.List;

import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ApiListner  {

    private ExpandableListView navigation_expandableListView;
    private HomeActivityController controller;
    private Context mContext;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public ActionBarDrawerToggle actionBarDrawerToggle;
    public TextView coorg_toolbar_title;
    public ImageView bt_bck;
    private boolean status= true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        controller = new HomeActivityController(this);
        mContext = getApplicationContext();
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);


        coorg_toolbar_title = findViewById(R.id.toolbar_title);

        bt_bck = findViewById(R.id.naviagation_hamberger);

        coorg_toolbar_title.setText("Welcome to Aurika, Coorg");

        bt_bck.setOnClickListener(v -> {
            onClicked();
        });




        navigation_expandableListView = findViewById(R.id.navigation_expandable_list_view);
        controller.getNavigationMenu();
      /*  drawer = findViewById(R.id.coorg_drawer_layout);

        navigationView = findViewById(R.id.navigation_view);
        navigation_expandableListView = findViewById(R.id.navigation_expandable_list_view);
        controller = new HomeActivityController(this);
        mContext = getApplicationContext();

        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_open, R.string.nav_close);

        // pass the Open and Close toggle for the drawer layout listener
        // to toggle the button
        drawer.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        // to make the Navigation drawer icon always appear on the action bar
      //  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_home, new HomeFragment()).commit();

        controller.getNavigationMenu();
*/

        this.getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, new HomeFragment()).commit();

    }

    private void onClicked() {
        try {
            onResume();
            super.onBackPressed();
        } catch (Exception e) {
            e.printStackTrace();


        }
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!= null){

            /*Navigation navigation = (Navigation) response.body();
            List<Data> navigationList = navigation.getData();
            NavigationAdapter adapter = new NavigationAdapter(mContext,navigationList);
            navigation_expandableListView.setAdapter(adapter);*/

            try {
                Navigation navigation = (Navigation) response.body();
                List<Data> navigationList = navigation.getData();
                NavigationAdapter adapter = new NavigationAdapter(mContext,navigationList,Position -> {
                    try {
                        String route=RouteName.getHomeScreenRoutes(navigationList.get(Position).getMobileRoute().getRouteName());
                        if (!route.isEmpty()) {
                            Class<?> className = Class.forName(route);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", navigationList.get(Position).getTitle());
                            Fragment fragment = (Fragment) className.newInstance();
                            fragment.setArguments(bundle);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
                            drawer.closeDrawers();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                });

                navigation_expandableListView.setAdapter(adapter);


            }catch (Exception e){
                e.printStackTrace();
            }

        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(mContext,"Alert",error);

    }
}