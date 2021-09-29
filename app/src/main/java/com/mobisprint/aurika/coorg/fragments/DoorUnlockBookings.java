package com.mobisprint.aurika.coorg.fragments;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.assaabloy.mobilekeys.api.EndpointSetupConfiguration;
import com.assaabloy.mobilekeys.api.MobileKeysCallback;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.HomeActivity;
import com.mobisprint.aurika.coorg.adapter.DoorUnlockAdapter;
import com.mobisprint.aurika.coorg.controller.DoorUnlockController;
import com.mobisprint.aurika.coorg.controller.GuestReservationController;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.invitationcode.InvitationCode;
import com.mobisprint.aurika.coorg.pojo.mobilekey.MobileKey;
import com.mobisprint.aurika.coorg.pojo.reservation.ActiveBooking;
import com.mobisprint.aurika.coorg.pojo.reservation.Data;
import com.mobisprint.aurika.coorg.pojo.reservation.GuestReservation;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.udaipur.activity.HomeScreenActivity;
import com.mobisprint.aurika.udaipur.fragments.doorunlockfragments.DoorUnlockingFragment;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Guest;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.OtpAutentication;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Result;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.TokenAutentication;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Validation;

import com.mobisprint.aurika.unlock.MobileKeysApiFacade;
import com.mobisprint.aurika.unlock.SnackbarFactory;

import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class DoorUnlockBookings extends Fragment implements ApiListner, MobileKeysCallback {


    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private SnackbarFactory snackbarFactory;
    private MobileKeysApiFacade mobileKeysApiFacade;
    private String Keytoken;
    private Handler handler;
    private Validation validation;
    private DoorUnlockController controller;
    private RecyclerView door_unlock_recyclerview;
    private List<ActiveBooking> guestList = new ArrayList<>();
    private Context mContext;
    private TextView toolbar_title;
    private ImageView bt_bck;
    private GuestReservationController guestReservationController;
    private ProgressBar progressBar;
    private RelativeLayout lyt_content;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_door_unlock, container, false);

        door_unlock_recyclerview = view.findViewById(R.id.door_unlock_recyclerview);

        guestReservationController = new GuestReservationController(this);

        controller = new DoorUnlockController(this);
        mContext = getContext();
        dialog = new ProgressDialog(mContext);
        snackbarFactory = new SnackbarFactory(container);
        toolbar_title = getActivity().findViewById(R.id.toolbar_title);
        progressBar = view.findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.VISIBLE);
        lyt_content = view.findViewById(R.id.lyt_content);
        lyt_content.setVisibility(View.GONE);

        Bundle bundle = getArguments();

        toolbar_title.setText(bundle.getString("title"));

        bt_bck = getActivity().findViewById(R.id.naviagation_hamberger);
        bt_bck.setVisibility(View.VISIBLE);

        sharedPreferences = mContext.getSharedPreferences("aurika", 0);
        edit = sharedPreferences.edit();

        /*controller.getDetails(GlobalClass.user_token);*/

        if (!sharedPreferences.getBoolean("requestedRuntimePermission", false)) {
            edit.putBoolean("requestedRuntimePermission", true);
            edit.commit();
            GlobalClass.showPermissionDialoug(getActivity());
        }

        return view;
    }

    @Override
    public void onFetchProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        guestReservationController.checkReservation(GlobalClass.user_token);
    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response != null) {

            progressBar.setVisibility(View.GONE);
            lyt_content.setVisibility(View.VISIBLE);

            if (response.body() instanceof GuestReservation) {


                GuestReservation guestReservation = (GuestReservation) response.body();

                    guestList = guestReservation.getData().getActiveBookings();

                    Log.d("sizeeee", String.valueOf(guestReservation.getData().getActiveBookings().size()));

                    if (!guestList.isEmpty()){
                        DoorUnlockAdapter adapter = new DoorUnlockAdapter(guestList,RoomNumber -> {
           /*         dialog.setMessage("please wait, while we are registering your phone for mobile key");
                    dialog.setCancelable(false);
                    dialog.show();
                    controller.getInvitationCode(RoomNumber);*/

                            if (!mobileKeysApiFacade.isEndpointSetUpComplete()) {
                                dialog.setMessage("please wait, while we are registering your phone for mobile key");
                                dialog.setCancelable(false);
                                dialog.show();
                                controller.getInvitationCode(RoomNumber);
                            }else if(!sharedPreferences.getBoolean("isMobileKeyDownloaded",false)){
                                controller.mobilekeyapi(Keytoken);
                            }else{
                                navigateToDoorUnlockView();
                            }
                        });
                        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(mContext);
                        door_unlock_recyclerview.setLayoutManager(layoutManager);
                        door_unlock_recyclerview.setAdapter(adapter);
                    }else{
                        GlobalClass.ShowAlert(mContext,"Alert","You don't have active bookings");
                    }





            } else if (response.body() instanceof InvitationCode) {

                try {
                    if (response.isSuccessful()) {
                        InvitationCode code = (InvitationCode) response.body();

                        if (code.getStatus()) {
                            Keytoken = code.getData().getToken();
                            submitInvitationCode(code.getData().getInvitationCode());
                        } else {
                            dismissDialog();
                            GlobalClass.ShowAlert(mContext, "Alert", code.getMessage());
                        }
                    } else {
                        dismissDialog();
                        GlobalClass.ShowAlert(mContext, "Alert", getString(R.string.ERROR));
                    }
                } catch (Exception e) {
                    dismissDialog();
                    e.printStackTrace();
                    e.getMessage();
                }

            } else if (response.body() instanceof MobileKey) {
                try {
                    if (response.isSuccessful()) {
                        MobileKey general = (MobileKey) response.body();
                        if (general.getStatus()) {
                            dismissDialog();
                            edit.putBoolean("isMobileKeyDownloaded", true);
                            edit.apply();
                           navigateToDoorUnlockView();
                        } else {
                            GlobalClass.ShowAlert(mContext, "Alert", "failed");
                        }
                    } else {
                        GlobalClass.ShowAlert(mContext, "Alert", getString(R.string.ERROR));
                    }
                } catch (Exception e) {
                    GlobalClass.ShowAlert(mContext, "Alert", "failed");
                    e.printStackTrace();
                    e.getMessage();
                }
            }


        }

    }

    private void navigateToDoorUnlockView() {
        Fragment fragment = new DoorUnlockingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("Key", "Coorg");
        fragment.setArguments(bundle);
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_coorg_container, fragment).commit();
    }


    @Override
    public void onFetchError(String error) {

        progressBar.setVisibility(View.GONE);
        GlobalClass.ShowAlert(mContext, "Alert", error);


    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (!(context instanceof MobileKeysApiFacade)) {
            throw new IllegalArgumentException("Error: attaching to mContext that doesn't implement MobileKeysApiFacade");
        }
        mobileKeysApiFacade = (MobileKeysApiFacade) context;
    }


    @Override
    public void onPause() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onPause();
    }


    /**
     * Mobile keys transaction success/completed callback
     */
    @Override
    public void handleMobileKeysTransactionCompleted() {
        if (isVisible()) {
            mobileKeysApiFacade.onEndpointSetUpComplete();
        }
    }


    private void submitInvitationCode(String invitation) {
        mobileKeysApiFacade.getMobileKeys().endpointSetup(this, invitation, new EndpointSetupConfiguration.Builder().build());
        checkInvitionComplet();

    }


    @Override
    public void onStart() {
        super.onStart();
        if (mobileKeysApiFacade.isEndpointSetUpComplete()) {
            mobileKeysApiFacade.onEndpointSetUpComplete();
        }
    }


    /**
     * Mobile keys transaction failed callback
     *
     * @param mobileKeysException failed description
     */
    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException mobileKeysException) {
     /*   if (isVisible()) {
            //  handler.postDelayed(unlockUiRunnable, UNLOCK_UI_DELAY);
            snackbarFactory.createAndShow(mobileKeysException,
                    HomeScreenActivity.shouldRetry(mobileKeysException) ? this : null);
        }*/
    }


    private void checkInvitionComplet() {
        try {
            new Handler().postDelayed(() -> {
                if (mobileKeysApiFacade.isEndpointSetUpComplete()) {
                    dialog.dismiss();
                    dialog.setMessage("please wait, while we are creating your mobile key");
                    dialog.setCancelable(false);
                    dialog.show();
                    controller.mobilekeyapi(Keytoken);
                } else {
                    checkInvitionComplet();
                }
            }, 5000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }





}