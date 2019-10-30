package com.mobisprint.aurika.activitys;


import android.content.Context;
import android.content.Intent;


import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.assaabloy.mobilekeys.api.MobileKeys;
import com.assaabloy.mobilekeys.api.MobileKeysCallback;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.assaabloy.mobilekeys.api.ReaderConnectionController;
import com.assaabloy.mobilekeys.api.ReaderConnectionInfoType;
import com.assaabloy.mobilekeys.api.ble.OpeningResult;
import com.assaabloy.mobilekeys.api.ble.OpeningStatus;
import com.assaabloy.mobilekeys.api.ble.OpeningType;
import com.assaabloy.mobilekeys.api.ble.Reader;
import com.assaabloy.mobilekeys.api.ble.ReaderConnectionCallback;
import com.assaabloy.mobilekeys.api.ble.ReaderConnectionListener;
import com.assaabloy.mobilekeys.api.ble.ScanConfiguration;
import com.assaabloy.mobilekeys.api.hce.HceConnectionCallback;
import com.assaabloy.mobilekeys.api.hce.HceConnectionListener;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.fragments.HomeGridFragment;

import com.mobisprint.aurika.notification.NotificationFragment;
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

import com.mobisprint.aurika.unlock.Aurika;
import com.mobisprint.aurika.unlock.MobileKeysApiFacade;
import com.mobisprint.aurika.unlock.MobileKeysApiFactory;
import com.onesignal.OneSignal;

import java.util.ArrayList;
import java.util.Collection;


public class HomeScreenActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        MobileKeysApiFacade,
        ReaderConnectionListener,
        MobileKeysCallback,
        HceConnectionListener {
    private static final String TAG = HomeScreenActivity.class.getName();
    private MobileKeys mobileKeys;
    private ImageView backBtn;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    private ReaderConnectionCallback readerConnectionCallback;
    private HceConnectionCallback hceConnectionCallback;
    private ReaderConnectionController readerConnectionController;
    private MobileKeysApiFactory mobileKeysApiFactory;
    private ScanConfiguration scanConfiguration;
    private MobileKeysApiFacade mobileKeysApiFacade;
    private AlertDialog.Builder builder;
    private Toolbar toolbar_cart;
    private Context context;
    private View headerLayout;
    private FloatingActionButton fab;
    private Vibrator vibrator;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private Aurika aurika;
    private  int notification;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        context = this;
        Intent intent = getIntent();
        notification = intent.getIntExtra("notification", 0);
        aurika = Aurika.getInstance();
        toolbar_cart = findViewById(R.id.toolbar_cart);
        toolbar_cart.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar_cart);
        drawer = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        backBtn = findViewById(R.id.naviagation_hamberger);
        fab = findViewById(R.id.fab);
        View navFooter1 = findViewById(R.id.footer_item_1);
        navFooter1.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lemontreehotels.com/privacy-policy.aspx"));
            startActivity(browserIntent);
        });
        View navFooter2 = findViewById(R.id.footer_item_2);
        navFooter2.setOnClickListener(v -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lemontreehotels.com/term-condition.aspx"));
            startActivity(browserIntent);
        });

        builder = new AlertDialog.Builder(this);
        overridePendingTransition(R.anim.enter_from_left, R.anim.exit_to_left);
        onCreated();
        navigationView.setItemIconTintList(null);
        navigationView.getMenu().getItem(9).setActionView(R.layout.menu_image);
        TextView tv_navigation = navigationView.getMenu().getItem(9).getActionView().findViewById(R.id.tv_notification);
        tv_navigation.setText("0");
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if(notification==1){
            ChangeFragment(10);
        }else{
            ChangeFragment(50);
        }
        readerConnectionCallback = new ReaderConnectionCallback(getApplicationContext());
        readerConnectionCallback.registerReceiver(this);
        hceConnectionCallback = new HceConnectionCallback(getApplicationContext());
        hceConnectionCallback.registerReceiver(this);
        backBtn.setOnClickListener(view -> {
            onBackPressed();
        });
        fab.setOnClickListener(view -> {

            if (drawer.isDrawerOpen(GravityCompat.START)) {
                drawer.closeDrawer(GravityCompat.START);
            } else
                drawer.openDrawer(GravityCompat.START);
        });
    }

    private void onCreated() {
        mobileKeysApiFacade = this;
        mobileKeysApiFactory = (MobileKeysApiFactory) getApplication();
        mobileKeys = mobileKeysApiFactory.getMobileKeys();
        readerConnectionController = mobileKeysApiFactory.getReaderConnectionController();
        scanConfiguration = mobileKeysApiFactory.getScanConfiguration();
    }


    @Override
    public void onBackPressed() {
        onResume();
        int count = getSupportFragmentManager().getBackStackEntryCount();

        if (GlobalClass.flow) {
            GlobalClass.flow = false;
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                getSupportFragmentManager().beginTransaction().remove(fragment).commit();
            }
            this
                    .getSupportFragmentManager()
                    .beginTransaction().replace(R.id.fragment_container,
                    new HomeGridFragment()).commit();
        }else if(notification==1){
            notification=0;
            ChangeFragment(50);
        }
        else if (count > 1) {
            super.onBackPressed();
            // Toast.makeText(this, "Last", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }
    }
    @Override
    protected void onResume() {
        headerLayout = navigationView.getHeaderView(0);
        navigationView.getMenu().getItem(10).setVisible(false);
        GlobalClass.SharedPreferences = context.getSharedPreferences("aurika", 0);
        if (GlobalClass.SharedPreferences.getBoolean("verifed_otp", false)) {
            navigationView.getMenu().getItem(10).setVisible(true);
            GlobalClass.user_token = GlobalClass.SharedPreferences.getString("user_token", "");
            GlobalClass.USER_NAME = GlobalClass.SharedPreferences.getString("UserName", "");
            TextView name = headerLayout.findViewById(R.id.tv_customer_name);
            name.setText(GlobalClass.USER_NAME);
            headerLayout.setVisibility(View.VISIBLE);
        } else {
            try {
                if(mobileKeysApiFacade!=null) {
                    mobileKeysApiFacade.getMobileKeys().listMobileKeys().clear();
                    mobileKeysApiFacade.getMobileKeys().unregisterEndpoint(this);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            headerLayout.setVisibility(View.GONE);
        }
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar_cart, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.setDrawerIndicatorEnabled(false);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        readerConnectionCallback.unregisterReceiver();
        hceConnectionCallback.unregisterReceiver();
    }

    @Override
    public MobileKeys getMobileKeys() {
        return mobileKeysApiFactory.getMobileKeys();
    }

    @Override
    public void onStartUpComplete() {
        Log.d(TAG, "Application onStartUpComplete()");
        showEndpointSetupFragmentIfNotSetup();
    }

    @Override
    public void onEndpointSetUpComplete() {

        try {
            if (mobileKeys.isEndpointSetupComplete()) {
            } else {
            }
        } catch (MobileKeysException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void endpointNotPersonalized() {

    }


    @Override
    public void onReaderConnectionOpened(Reader reader, OpeningType openingType) {

    }

    @Override
    public void onReaderConnectionClosed(Reader reader, OpeningResult openingResult) {
        if (Build.VERSION.SDK_INT >= 26) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            vibrator.vibrate(200);
        }
        for (Fragment fragment : getSupportFragmentManager().getFragments()) {
            getSupportFragmentManager().beginTransaction().remove(fragment).commit();
        }
        this
                .getSupportFragmentManager()
                .beginTransaction().replace(R.id.fragment_container,
                new HomeGridFragment()).commit();
    }


    @Override
    public void onReaderConnectionFailed(Reader reader, OpeningType openingType, OpeningStatus openingStatus) {

    }

    @Override
    public void onHceSessionOpened() {

    }

    @Override
    public void onHceSessionClosed(int i) {

    }

    @Override
    public void onHceSessionInfo(ReaderConnectionInfoType hceConnectionInfoType) {

    }

    @Override
    public boolean isEndpointSetUpComplete() {
        boolean isEndpointSetup = false;
        try {
            isEndpointSetup = mobileKeys.isEndpointSetupComplete();

        } catch (MobileKeysException e) {

        }
        return isEndpointSetup;
    }

    @Override
    public ReaderConnectionController getReaderConnectionController() {
        return readerConnectionController;
    }

    @Override
    public ScanConfiguration getScanConfiguration() {
        return scanConfiguration;
    }


    private void showEndpointSetupFragmentIfNotSetup() {
        try {

            if (mobileKeys.isEndpointSetupComplete()) {
                onEndpointSetUpComplete();
            }
        } catch (MobileKeysException exception) {
         exception.printStackTrace();
        }
    }

    public static boolean shouldRetry(MobileKeysException exception) {
        boolean shouldRetry = false;
        switch (exception.getErrorCode()) {
            case INTERNAL_ERROR:
            case SERVER_UNREACHABLE:
            case SDK_BUSY:
                shouldRetry = true;
                break;
            case INVALID_INVITATION_CODE:
            case DEVICE_SETUP_FAILED:
            case SDK_INCOMPATIBLE:
            case DEVICE_NOT_ELIGIBLE:
            case ENDPOINT_NOT_SETUP:
            default:
                break;
        }
        return shouldRetry;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

        int id = menuItem.getItemId();

        if (id == R.id.nav_unlock) {
            ChangeFragment(0);

        } else if (id == R.id.nav_services) {
            ChangeFragment(1);

        } else if (id == R.id.nav_sleep_well) {
            ChangeFragment(2);

        } else if (id == R.id.nav_wine_dine) {
            ChangeFragment(3);

        } else if (id == R.id.nav_room_dinning) {
            ChangeFragment(4);


        } else if (id == R.id.nav_experiences) {
            ChangeFragment(5);

        } else if (id == R.id.nav_spa_saloon) {
            ChangeFragment(6);

        } else if (id == R.id.nav_recreation) {
            ChangeFragment(7);

        } else if (id == R.id.nav_sight_seeing) {
            ChangeFragment(8);
        } else if (id == R.id.nav_logout) {
            ChangeFragment(9);
        }else if (id == R.id.nav_notification) {
            ChangeFragment(10);
        }


        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void ChangeFragment(int position) {
        Fragment fragment = null;

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
            case 9:
                performLogout();
                fragment = new HomeGridFragment();
                break; // break is optional
            case 10:
                fragment = new NotificationFragment();
                break; // break is optional
            default:
                fragment = new HomeGridFragment();
        }
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (position != 0) {
            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
        }
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();

    }

    private void performLogout() {
        try {
            if (!haveNetworkConnection()) {
                ShowAlet("No Internet Connection", "Turn on your internet and continue");
            }else {
                sharedPreferences = context.getSharedPreferences("aurika", 0);
                edit = sharedPreferences.edit();
                edit.putString("user_token", "");
                edit.putString("UserName", "");
                edit.putBoolean("verifed_otp", false);
                edit.apply();
                onResume();
                Collection<String> tempList = new ArrayList<String>();
                tempList.add("isCheckedIn");
                tempList.add("timeStamp");
                OneSignal.deleteTags(tempList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void handleMobileKeysTransactionCompleted() {

    }

    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException e) {

    }
    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) Aurika.getAppContext().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo[] netInfo = cm.getAllNetworkInfo();
            for (NetworkInfo ni : netInfo) {
                if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                    if (ni.isConnected())
                        haveConnectedWifi = true;
                if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                    if (ni.isConnected())
                        haveConnectedMobile = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
        return haveConnectedWifi || haveConnectedMobile;
    }
    private void ShowAlet(String Title, String Message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(Aurika.getAppContext());
            builder.setTitle(Title);
            builder.setMessage(Message)
                    .setCancelable(false)
                    .setPositiveButton("ok", (dialog, id) -> startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS)))
                    .setNegativeButton("Quit", (dialog, id) -> {
                    });
            AlertDialog alert = builder.create();
            alert.show();
            Button positiveButton = alert.getButton(AlertDialog.BUTTON_POSITIVE);
            Button negativeButton = alert.getButton(AlertDialog.BUTTON_NEGATIVE);
            positiveButton.setTextColor(getResources().getColor(R.color.black));
            negativeButton.setTextColor(getResources().getColor(R.color.black));
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
        }
    }
}



