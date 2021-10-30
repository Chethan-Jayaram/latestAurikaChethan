package com.mobisprint.aurika.udaipur.fragments.doorunlockfragments;


import android.Manifest;
import android.app.Notification;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import com.assaabloy.mobilekeys.api.MobileKey;
import com.assaabloy.mobilekeys.api.MobileKeysApi;
import com.assaabloy.mobilekeys.api.MobileKeysCallback;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.assaabloy.mobilekeys.api.ReaderConnectionController;
import com.google.android.material.navigation.NavigationView;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.bluetooth.ClosestLockTrigger;
import com.mobisprint.aurika.coorg.fragments.HomeFragment;
import com.mobisprint.aurika.udaipur.fragments.HomeGridFragment;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.notification.UnlockNotification;
import com.mobisprint.aurika.unlock.MobileKeysApiFacade;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class DoorUnlockingFragment extends Fragment
        implements View.OnClickListener, MobileKeysCallback,
        ClosestLockTrigger.LockInRangeListener, SwipeRefreshLayout.OnRefreshListener {

    private MobileKeysApiFacade mobileKeysApiFacade;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView tv_unlock_status, registor_status, tv_search, tv_unlock_msg;
    private ClosestLockTrigger closestLockTrigger = new ClosestLockTrigger(this);
    private BluetoothAdapter mBluetoothAdapter;
    private static final int MY_PERMISSIONS_REQUEST_LOCATION = 99;
    private Handler handler;
    private NavigationView navigationView;
    private Context mContext;

    private String mDestination="";


    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_door_unlocking, container, false);
        try {
            mContext=view.getContext();
            FrameLayout containerView = view.findViewById(R.id.door_unlock_fragment);
            tv_unlock_status = view.findViewById(R.id.tv_unlock_status);
            swipeRefreshLayout = view.findViewById(R.id.swipeRefreshLayout);
            registor_status = view.findViewById(R.id.registor_status);
            tv_search = view.findViewById(R.id.tv_search);
            tv_unlock_msg = view.findViewById(R.id.tv_unlock_msg);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
             getActivity().findViewById(R.id.lyt_notification).setVisibility(View.GONE);
            navigationView = getActivity().findViewById(R.id.nav_view);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);
            toolbar_title.setText("");
            mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
            statusCheck();
            GlobalClass.flow = true;
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
            }
            tv_unlock_status.setOnClickListener(this);
            swipeRefreshLayout.setOnRefreshListener(this);
            updateApiAction();
            tv_search.setVisibility(View.GONE);
            tv_unlock_status.setVisibility(View.GONE);
            tv_unlock_msg.setText("Please wait while we are activating your mobile key");
            registor_status.setText("INACTIVE");
            registor_status.setBackgroundColor(Color.parseColor("#6e7a75"));
            onRefresh();
            view.clearFocus();


            Bundle bundle = getArguments();
            mDestination=bundle.getString("Key","");

            handler = new Handler();
            handler.postDelayed(() -> {
                try {
                    if (mDestination.equalsIgnoreCase("Udaipur")) {
                        getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeGridFragment()).commit();
                    }else{
                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, new HomeFragment()).commit();
                        getActivity().getSupportFragmentManager().popBackStack();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, 20000);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MobileKeysApiFacade)) {
            throw new IllegalArgumentException("Error: attaching to context that doesn't implement MobileKeysApiFacade");
        }
        mobileKeysApiFacade = (MobileKeysApiFacade) context;
    }

    private void updateApiAction() {
        swipeRefreshLayout.setRefreshing(true);
        mobileKeysApiFacade.getMobileKeys().endpointUpdate(this);
        mobileKeysApiFacade.isEndpointSetUpComplete();
    }


    /**
     * Load mobile keys api
     */
    private void loadKeys() {
        if (isVisible()) {
            List<MobileKey> data = null;
            try {
                data = mobileKeysApiFacade.getMobileKeys().listMobileKeys();
                Log.d("keys", String.valueOf(mobileKeysApiFacade.getMobileKeys().listMobileKeys().size()));
            } catch (MobileKeysException e) {
                e.printStackTrace();
                e.getMessage();
            }
            if (data == null) {
                data = Collections.emptyList();
            }

            //Update scanning based if we have keys
            if (data.isEmpty()) {
                stopScanning();
            } else {
                startScanning();
            }
        }
    }

    /**
     * Start BLE scanning or request permission
     */
    private void startScanning() {

        if (hasLocationPermissions()) {
            if (!mBluetoothAdapter.isEnabled()) {
                mBluetoothAdapter.enable();
                startReading();
            } else {
                startReading();
            }
        } else {
            if (!sharedPreferences.getBoolean("requestedRuntimePermission", false)) {
                GlobalClass.editor.putBoolean("requestedRuntimePermission", true);
                GlobalClass.editor.apply();
                GlobalClass.showPermissionDialoug(getActivity());
            }else {
                requestLocationPermission();
            }
        }
    }

    private void startReading() {
        ReaderConnectionController controller = MobileKeysApi.getInstance().getReaderConnectionController();
        controller.enableHce();
        Notification notification = UnlockNotification.create(requireContext());
        controller.startForegroundScanning(notification);
        setUiComponents();
    }

    /**
     * Stop BLE scanning or
     */
    private void stopScanning() {
        try {
            ReaderConnectionController controller = MobileKeysApi.getInstance().getReaderConnectionController();
            controller.stopScanning();
//        controller.disableHce();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Check if app has location permission
     *
     * @return
     */
    private boolean hasLocationPermissions() {
        return (ContextCompat.checkSelfPermission(requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(requireContext(),
                        Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);

    }

    /**
     * Request location permission, location permission is required for BLE scanning when running Marshmallow or above
     */
    private void requestLocationPermission() {
        if (!hasLocationPermissions()) {
            ActivityCompat.requestPermissions(getActivity(),
                    getPermissions(),
                    MY_PERMISSIONS_REQUEST_LOCATION);
        }
    }

    public static String[] getPermissions() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            return Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q ?
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_BACKGROUND_LOCATION} :
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION};
        } else {
            return new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};
        }
    }

    /**
     * Callback from closest lock trigger.
     * Toggle button
     *
     * @param lockInRange
     */
    @Override
    public void onLockInRange(final boolean lockInRange) {
        tv_unlock_status.post(() -> toggleOpenButton(lockInRange));
    }


    private void toggleOpenButton(boolean enabled) {
        if (enabled) {
            tv_unlock_status.setEnabled(true);
            tv_unlock_status.invalidate();
        } else {
            tv_unlock_status.setEnabled(false);
            tv_unlock_status.invalidate();
        }
    }

    public void statusCheck() {
        final LocationManager manager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps();

        }
    }


    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", (dialog, id) -> {
                    getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
                    getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeGridFragment()).commit();
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    /**
     * Mobile keys transaction success/completed callback
     */
    @Override
    public void handleMobileKeysTransactionCompleted() {
        swipeRefreshLayout.setRefreshing(false);
        loadKeys();
    }


    /**
     * Mobile keys failed callback
     *
     * @param mobileKeysException
     */
    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException mobileKeysException) {
        swipeRefreshLayout.setRefreshing(false);
        switch (mobileKeysException.getErrorCode()) {
            case ENDPOINT_NOT_SETUP:
                mobileKeysApiFacade.endpointNotPersonalized();
                break;
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        //Listen to lock changes
        try {
            loadKeys();
            toggleOpenButton(false);
            mobileKeysApiFacade.getScanConfiguration().getRootOpeningTrigger().add(closestLockTrigger);



            if (mDestination.equalsIgnoreCase("Udaipur")) {
                navigationView.getMenu().getItem(9).setVisible(false);
                View headerLayout = navigationView.getHeaderView(0);
                GlobalClass.SharedPreferences = getActivity().getSharedPreferences("aurika", 0);
                if (GlobalClass.SharedPreferences.getBoolean("verifed_otp", false)) {
                    navigationView.getMenu().getItem(9).setVisible(true);
                    GlobalClass.user_token = GlobalClass.SharedPreferences.getString("user_token", "");
                    GlobalClass.USER_NAME = GlobalClass.SharedPreferences.getString("UserName", "");
                    TextView name = headerLayout.findViewById(R.id.tv_customer_name);
                    name.setText(GlobalClass.USER_NAME);
                    headerLayout.setVisibility(View.VISIBLE);
                } else {
                    try {
                        mobileKeysApiFacade.getMobileKeys().unregisterEndpoint(this);
                        mobileKeysApiFacade.getMobileKeys().listMobileKeys().clear();
                    } catch (MobileKeysException e) {
                        e.printStackTrace();
                    }
                    headerLayout.setVisibility(View.GONE);
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        //Stop listening to lock changes
        toggleOpenButton(false);

        mobileKeysApiFacade.getScanConfiguration().getRootOpeningTrigger().remove(closestLockTrigger);
        //Stop scanning
        stopScanning();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
        }

        super.onPause();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_unlock_status:
                // Send broadcast to custom opening trigger to open closest reader
                closestLockTrigger.openClosestReader();
                break;
           /* case android.support.design.R.id.snackbar_action:
                updateApiAction();
                break;*/

        }
    }

    @Override
    public void onRefresh() {
        updateApiAction();
        loadKeys();
        try {
            if (mobileKeysApiFacade.getMobileKeys().listMobileKeys().size() > 0) {
                setUiComponents();
            }
        } catch (MobileKeysException e) {
            e.printStackTrace();
        }


    }

    private void setUiComponents() {
        tv_search.setVisibility(View.VISIBLE);
        tv_unlock_status.setVisibility(View.VISIBLE);
        tv_unlock_msg.setText("Place your phone near the door lock to open");
        registor_status.setText("ACTIVE");
        registor_status.setBackgroundColor(Color.parseColor("#76b833"));
    }


}
