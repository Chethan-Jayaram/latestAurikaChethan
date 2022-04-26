package com.mobisprint.aurika.coorg.activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.Settings;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.mobisprint.aurika.SelectLocationActivity;
import com.mobisprint.aurika.coorg.adapter.NavigationAdapter;
import com.mobisprint.aurika.coorg.controller.GuestReservationController;
import com.mobisprint.aurika.coorg.controller.HomeActivityController;
import com.mobisprint.aurika.coorg.controller.MyStayController;
import com.mobisprint.aurika.coorg.fragments.CoorgNotificationFragment;
import com.mobisprint.aurika.coorg.fragments.HomeFragment;
import com.mobisprint.aurika.coorg.fragments.ProfileFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.LoginFragment;
import com.mobisprint.aurika.coorg.pojo.guestbooking.GuestBooking;
import com.mobisprint.aurika.coorg.pojo.location.SelectLocation;
import com.mobisprint.aurika.coorg.pojo.navigation.Data;
import com.mobisprint.aurika.coorg.pojo.navigation.Navigation;
import com.mobisprint.aurika.coorg.pojo.reservation.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.reservation.GuestReservation;
import com.mobisprint.aurika.coorg.pojo.reservation.Room;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.RouteName;
import com.mobisprint.aurika.udaipur.fragments.HomeGridFragment;
import com.mobisprint.aurika.unlock.MobileKeysApiFacade;
import com.mobisprint.aurika.unlock.MobileKeysApiFactory;
import com.razorpay.PaymentResultListener;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Response;

public class HomeActivity extends AppCompatActivity implements ApiListner, MobileKeysApiFacade,
        ReaderConnectionListener,
        MobileKeysCallback,
        HceConnectionListener {

    private ExpandableListView navigation_expandableListView;
    private HomeActivityController controller;
    private Context mContext;
    private NavigationView navigationView;
    private DrawerLayout drawer;
    public TextView coorg_toolbar_title, tv_logout,tv_privacy,tv_conditions;
    public ImageView bt_bck;
    private boolean status = true;
    private RelativeLayout lyt_notification, lyt_logout, lyt_notification_tool_bar;
    private FloatingActionButton fab;
    private GuestReservationController guestReservationController;
    private MyStayController myStayController;
    private List<com.mobisprint.aurika.coorg.pojo.guestbooking.ActiveBooking> guestList ;

    private String room;


    //Doorunlock
    private MobileKeys mobileKeys;
    private ReaderConnectionCallback readerConnectionCallback;
    private HceConnectionCallback hceConnectionCallback;
    private ReaderConnectionController readerConnectionController;
    private MobileKeysApiFactory mobileKeysApiFactory;
    private ScanConfiguration scanConfiguration;
    private MobileKeysApiFacade mobileKeysApiFacade;
    private Vibrator vibrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        try {
            /*txt1 = findViewById(R.id.txt1);*/
            tv_privacy = findViewById(R.id.txt1);
            tv_conditions = findViewById(R.id.txt3);
            controller = new HomeActivityController(this);
            mContext = getApplicationContext();
            navigationView = findViewById(R.id.nav_view);
            drawer = findViewById(R.id.drawer_layout);
            lyt_logout = findViewById(R.id.lyt_logout);
            guestReservationController = new GuestReservationController(this);
            myStayController = new MyStayController(this);

            tv_logout = findViewById(R.id.tv_logout);
            lyt_notification = findViewById(R.id.lyt_drawer_notification);
            lyt_notification_tool_bar = findViewById(R.id.lyt_notification);

            coorg_toolbar_title = findViewById(R.id.toolbar_title);

            bt_bck = findViewById(R.id.naviagation_hamberger);

            coorg_toolbar_title.setText("Welcome to Aurika, Coorg");
            fab = findViewById(R.id.fab);

            vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);

            onCreated();
            readCallBack();

            /*txt1.setText(Html.fromHtml("<body>\n" +
                    "        <p" +
                    "          style=\"color:#000000\"><a href=\"https://www.lemontreehotels.com/term-condition.aspx\" style=\"color:#000000\">Terms &amp; Conditions</a>\n" +
                    "         and  <a href=\"https://www.lemontreehotels.com/privacy-policy.aspx\" style=\"color:#000000\">Privacy Policy</a>\n" +
                    "        </p>" +
                    "    </body>"));*/


            tv_privacy.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lemontreehotels.com/privacy-policy.aspx"));
                startActivity(browserIntent);
            });


            tv_conditions.setOnClickListener(v -> {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.lemontreehotels.com/term-condition.aspx"));
                startActivity(browserIntent);
            });

            bt_bck.setOnClickListener(v -> {
                onBackPressed();
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
                drawer.closeDrawers();
            });

            lyt_notification_tool_bar.setOnClickListener(v -> {
                Fragment fragment = new CoorgNotificationFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
            });

            /*if (GlobalClass.user_token != null) {
                if (GlobalClass.user_token.isEmpty()) {

                    tv_logout.setText("Exit");
                } else {
                    tv_logout.setText("Logout");
                }
            } else {
                tv_logout.setText("Exit");
            }*/


            if (GlobalClass.logged_in){
                tv_logout.setText("Logout");
            }else{
                tv_logout.setText("Exit");
            }

            lyt_logout.setOnClickListener(v -> {
               /* GlobalClass.user_token = "";*/
                GlobalClass.logged_in = false;
                GlobalClass.editor.putBoolean("isMobileKeyDownloaded",false);
                GlobalClass.editor.commit();

                Intent intent = new Intent(this, SelectLocationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                this.finish();

                try {
                    if (!mobileKeysApiFacade.getMobileKeys().listMobileKeys().isEmpty()) {
                        mobileKeysApiFacade.getMobileKeys().listMobileKeys().clear();
                        mobileKeysApiFacade.getMobileKeys().unregisterEndpoint(this);
                    }
                } catch (MobileKeysException e) {
                    e.printStackTrace();
                }

            });


            navigation_expandableListView = findViewById(R.id.navigation_expandable_list_view);
            if (!GlobalClass.user_token.isEmpty()) {
                myStayController.getGuestBooking(GlobalClass.user_token);
               // guestReservationController.checkReservation(GlobalClass.user_token);
            }
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


        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    //on create initialize all sdk keys
    private void onCreated() {
        mobileKeysApiFacade = this;
        mobileKeysApiFactory = (MobileKeysApiFactory) getApplication();
        mobileKeys = mobileKeysApiFactory.getMobileKeys();
        readerConnectionController = mobileKeysApiFactory.getReaderConnectionController();
        scanConfiguration = mobileKeysApiFactory.getScanConfiguration();
    }

    //read connetion call backs from sdk
    private void readCallBack() {
        readerConnectionCallback = new ReaderConnectionCallback(getApplicationContext());
        readerConnectionCallback.registerReceiver(this);
        hceConnectionCallback = new HceConnectionCallback(getApplicationContext());
        hceConnectionCallback.registerReceiver(this);
    }


    //unregister calbacks and receivers
    @Override
    protected void onDestroy() {
        super.onDestroy();
        readerConnectionCallback.unregisterReceiver();
        hceConnectionCallback.unregisterReceiver();
    }

  /*  private void onClicked() {
        try {
            onResume();
            super.onBackPressed();
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(bt_bck.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        } catch (Exception e) {
            e.printStackTrace();

        }
    }*/

    @Override
    public void onFetchProgress() {

    }


    @Override
    public void onBackPressed() {

        int count = getSupportFragmentManager().getBackStackEntryCount();
        if (count > 0) {
            super.onBackPressed();
            // Toast.makeText(this, "Last", Toast.LENGTH_SHORT).show();
        } else {
            Intent intent = new Intent(this, SelectLocationActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            this.finish();
        }
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(bt_bck.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
        }
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null) {

            /*Navigation navigation = (Navigation) response.body();
            List<Data> navigationList = navigation.getData();
            NavigationAdapter adapter = new NavigationAdapter(mContext,navigationList);
            navigation_expandableListView.setAdapter(adapter);*/
            if (response.body() instanceof GuestBooking) {

                try {

                    GuestBooking reservation = (GuestBooking) response.body();

                    if (reservation.getData().getActiveBooking().isEmpty()){
                        GlobalClass.user_active_booking = false;
                    }else{      
                        GlobalClass.user_active_booking = true;
                        room = reservation.getData().getActiveBooking().get(0).getRoom().get(0).getRoom().getRoomNo();

                        GlobalClass.ROOM_NO = room;

                        GlobalClass.Guest_Id = reservation.getData().getActiveBooking().get(0).getId();
                        GlobalClass.check_out = reservation.getData().getActiveBooking().get(0).getCheckoutDateTime();

                    }


                }catch (Exception e){
                    e.printStackTrace();
                }




            }else if (response.body() instanceof Navigation) {
                try {
                    Navigation navigation = (Navigation) response.body();
                    List<Data> navigationList = navigation.getData();
                    NavigationAdapter adapter = new NavigationAdapter(mContext, navigationList, (GroupPosition, ChildPosition, Decider) -> {
                        try {
                            String route = "";
                            if (Decider) {
                                route = RouteName.getHomeScreenRoutes(navigationList.get(GroupPosition).getMobileRoute().getRouteName());
                            } else {
                                if (navigationList.get(GroupPosition).getRoutesSubcategory().get(ChildPosition).getMobileRoute().getRouteName().equalsIgnoreCase("my-stay")) {
                                    Intent intent = new Intent(this, MyStayActivity.class);
                                    startActivity(intent);
                                    drawer.closeDrawers();
                                } else {
                                    route = RouteName.getHomeScreenRoutes(navigationList.get(GroupPosition).getRoutesSubcategory().get(ChildPosition).getMobileRoute().getRouteName());
                                }
                            }
                            if (!route.isEmpty()) {
                                Class<?> className = Class.forName(route);
                                Bundle bundle = new Bundle();
                                bundle.putString("title", navigationList.get(GroupPosition).getTitle());
                                Fragment fragment = (Fragment) className.newInstance();
                                fragment.setArguments(bundle);
//                            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
//                            fragmentTransaction.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
//                            fragmentTransaction.replace(R.id.fragment_coorg_container, fragment).addToBackStack(null);
//                            fragmentTransaction.commit();
                                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).addToBackStack(null).commit();
                                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                                imm.hideSoftInputFromWindow(bt_bck.getWindowToken(), InputMethodManager.RESULT_UNCHANGED_SHOWN);
                                drawer.closeDrawers();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });

                    navigation_expandableListView.setAdapter(adapter);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(HomeActivity.this, "Alert", error);

    }

    @Override
    public MobileKeys getMobileKeys() {
        return mobileKeysApiFactory.getMobileKeys();
    }

    @Override
    public void onStartUpComplete() {
        Log.d("coorg set up complete", "Application onStartUpComplete()");
        showEndpointSetupFragmentIfNotSetup();
    }

    @Override
    public void onEndpointSetUpComplete() {
    }

    @Override
    public void endpointNotPersonalized() {

    }


    @Override
    public void onReaderConnectionOpened(Reader reader, OpeningType openingType) {

    }

    @Override
    public void onReaderConnectionClosed(Reader reader, OpeningResult openingResult) {
        try {
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
                    .beginTransaction().replace(R.id.fragment_coorg_container,
                    new HomeFragment()).commit();
            getSupportFragmentManager().popBackStack();
        } catch (Exception e) {
            e.printStackTrace();
        }
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
            e.printStackTrace();
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
    public void handleMobileKeysTransactionCompleted() {

    }

    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException e) {

    }



}