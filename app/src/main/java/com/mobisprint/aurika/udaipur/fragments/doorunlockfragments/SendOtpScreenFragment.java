 package com.mobisprint.aurika.udaipur.fragments.doorunlockfragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;

import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.TokenAutentication;
import com.mobisprint.aurika.udaipur.pojo.doorunlock.Validation;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.udaipur.services.APIMethods;

import java.net.SocketTimeoutException;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class SendOtpScreenFragment extends Fragment {

    private Context context;
    private ProgressDialog dialog;
    private CheckBox chekcbox;
    private EditText tv_otp_room_no, tv_otp_ph_email;
    private Validation validation;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_send_otp_screen, container, false);
        try {
            context = view.getContext();
            TextView terms_conditions_textview = view.findViewById(R.id.terms_conditions_textview);
            chekcbox = view.findViewById(R.id.chekcbox);
            tv_otp_room_no = view.findViewById(R.id.tv_otp_room_no);
            tv_otp_ph_email = view.findViewById(R.id.tv_otp_ph_email);
            dialog = new ProgressDialog(context);

            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);
            toolbar_title.setText("");
            Spanned policy = Html.fromHtml(getString(R.string.agree_terms_privacy));
            terms_conditions_textview.setText(policy);
            terms_conditions_textview.setMovementMethod(LinkMovementMethod.getInstance());
            GlobalClass.SharedPreferences = context.getSharedPreferences("aurika", 0);
            if (GlobalClass.SharedPreferences.getBoolean("verifed_otp", false)) {
                getActivity()
                        .getSupportFragmentManager()
                        .beginTransaction().replace(R.id.fragment_container,
                        new DoorUnlockingFragment()).commit();

            }
            SpannableString spannableString = new SpannableString("Enter Email/Mobile Number");
            SpannableString enterRoomNumber = new SpannableString("Enter Room Number");
            tv_otp_room_no.setHint(enterRoomNumber);
            tv_otp_ph_email.setHint(spannableString);
            TextView submitBtn = view.findViewById(R.id.submit_btn);
            submitBtn.setOnClickListener(view1 -> {

                if (tv_otp_ph_email.getText().toString().isEmpty() || tv_otp_room_no.getText().toString().isEmpty()) {
                    CustomMessageHelper showDialog = new CustomMessageHelper(context);
                    showDialog.showCustomMessage((Activity) context, "Alert!!", "Please enter mobile number / Room Number", false, false);
                } else {
                    if (chekcbox.isChecked()) {
                        GlobalClass.PH_NO = tv_otp_ph_email.getText().toString();
                        GlobalClass.ROOM_NO = tv_otp_room_no.getText().toString();
                        LoginApiCall(GlobalClass.PH_NO, GlobalClass.ROOM_NO);
                    } else {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Error", "Please accept the Terms and Conditions to continue", false, false);
                    }
                }
            });

            getActivity().findViewById(R.id.lyt_notification).setVisibility(View.VISIBLE);
        }catch (Exception e){
            e.printStackTrace();
        }
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!haveNetworkConnection()) {
            ShowAlet("No Internet Connection", "Turn on your internet and continue");
        }
    }

    private void ShowAlet(String Title, String Message) {
        try {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
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

    private boolean haveNetworkConnection() {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;
        try {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
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

    private void LoginApiCall(String mobile_no, String room_no) {
        try {
            dialog.setMessage("please wait...");
            dialog.setCancelable(false);
            dialog.show();
            Map map = new HashMap();
            map.put("mobile_number", mobile_no);
            map.put("room_no", room_no);

            APIMethods api = ClientServiceGenerator.getUrlClient(GlobalClass.UDAIPUR_DOOR_UNLOCK).create(APIMethods.class);
            Call<TokenAutentication> call = api.userAuthentication(map);
            call.enqueue(new Callback<TokenAutentication>() {
                @Override
                public void onResponse(Call<TokenAutentication> call, Response<TokenAutentication> response) {
                    try {
                        if (response.isSuccessful()) {
                            Log.d("response",response.body().getStatus());
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                Log.d("api success","yes");
                                validation = new Validation();
                                validation = response.body().getResult();
                                GlobalClass.user_token = validation.getToken();
                                sharedPreferences = context.getSharedPreferences("aurika", 0);
                                edit = sharedPreferences.edit();
                                edit.putString("user_token", GlobalClass.user_token);
                                edit.apply();
                                Log.d("login_sucessful","yes");
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.fragment_container,
                                        new OtpScreenFragment()).addToBackStack(null).commit();
                                dismissDialog();
                            } else {
                                dismissDialog();
                                Log.d("api success","diff response");
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                            }
                        } else {
                            dismissDialog();
                            Log.d("api success","api fail");
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        }
                    } catch (Exception e) {
                        dismissDialog();
                        Log.d("api success","exception");
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                        e.printStackTrace(


                        );
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<TokenAutentication> call, Throwable t) {
                    try {
                        if (t instanceof SocketTimeoutException) {
                            dismissDialog();

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {
                        dismissDialog();
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            dismissDialog();
            e.getMessage();
            e.printStackTrace();
        }
    }


    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

}
