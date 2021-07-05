package com.mobisprint.aurika.coorg.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.SelectLocationActivity;
import com.mobisprint.aurika.coorg.adapter.NavigationAdapter;
import com.mobisprint.aurika.coorg.controller.HomeActivityController;
import com.mobisprint.aurika.coorg.fragments.CoorgNotificationFragment;
import com.mobisprint.aurika.coorg.fragments.HomeFragment;
import com.mobisprint.aurika.coorg.fragments.ProfileFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.LoginFragment;
import com.mobisprint.aurika.coorg.pojo.location.SelectLocation;
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
    public TextView coorg_toolbar_title,tv_logout;
    public ImageView bt_bck;
    private boolean status= true;
    private RelativeLayout lyt_notification,lyt_logout;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        controller = new HomeActivityController(this);
        mContext = getApplicationContext();
        navigationView = findViewById(R.id.nav_view);
        drawer = findViewById(R.id.drawer_layout);
        lyt_logout = findViewById(R.id.lyt_logout);

        tv_logout = findViewById(R.id.tv_logout);
        lyt_notification = findViewById(R.id.lyt_notification);

        coorg_toolbar_title = findViewById(R.id.toolbar_title);

        bt_bck = findViewById(R.id.naviagation_hamberger);

        coorg_toolbar_title.setText("Welcome to Aurika, Coorg");
        fab = findViewById(R.id.fab);

        bt_bck.setOnClickListener(v -> {
            onClicked();
        });

        fab.setOnClickListener(view -> {
            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else
                drawer.openDrawer(GravityCompat.START);
        });

        lyt_notification.setOnClickListener(v -> {
            Fragment fragment = new CoorgNotificationFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();

        });

        if (GlobalClass.sharedPreferences.getBoolean("isMpinSetUpComplete",false)){
            tv_logout.setText("Logout");
        }else{
            tv_logout.setText("Exit");}

        lyt_logout.setOnClickListener(v -> {
            GlobalClass.editor.clear();
            GlobalClass.editor.commit();
            Intent intent = new Intent(this, SelectLocationActivity.class);
            startActivity(intent);

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
                NavigationAdapter adapter = new NavigationAdapter(mContext,navigationList,(GroupPosition,ChildPosition,Decider) -> {
                    try {
                        String route = "";
                        if(Decider){
                            route=RouteName.getHomeScreenRoutes(navigationList.get(GroupPosition).getMobileRoute().getRouteName());
                        }else {
                            route=RouteName.getHomeScreenRoutes(navigationList.get(GroupPosition).getRoutesSubcategory().get(ChildPosition).getMobileRoute().getRouteName());
                        }

                        if (!route.isEmpty()) {
                            Class<?> className = Class.forName(route);
                            Bundle bundle = new Bundle();
                            bundle.putString("title", navigationList.get(GroupPosition).getTitle());
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