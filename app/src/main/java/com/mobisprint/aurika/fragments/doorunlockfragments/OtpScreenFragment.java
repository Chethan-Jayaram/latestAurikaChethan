package com.mobisprint.aurika.fragments.doorunlockfragments;


import android.app.Activity;
import android.app.ProgressDialog;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.assaabloy.mobilekeys.api.EndpointSetupConfiguration;
import com.assaabloy.mobilekeys.api.MobileKeysCallback;
import com.assaabloy.mobilekeys.api.MobileKeysException;
import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.activitys.HomeScreenActivity;
import com.mobisprint.aurika.helper.CustomMessageHelper;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.SMSReceiver;
import com.mobisprint.aurika.pojo.doorunlock.ActiveBooking;
import com.mobisprint.aurika.pojo.doorunlock.Guest;
import com.mobisprint.aurika.pojo.doorunlock.OtpAutentication;
import com.mobisprint.aurika.pojo.doorunlock.Result;
import com.mobisprint.aurika.pojo.doorunlock.TokenAutentication;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;
import com.mobisprint.aurika.services.APIMethods;
import com.mobisprint.aurika.services.SchedulerService;
import com.mobisprint.aurika.unlock.MobileKeysApiFacade;
import com.mobisprint.aurika.unlock.SnackbarFactory;
import com.onesignal.OneSignal;
import org.json.JSONException;
import org.json.JSONObject;
import java.net.SocketTimeoutException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import static android.content.Context.JOB_SCHEDULER_SERVICE;

/**
 * A simple {@link Fragment} subclass.
 */
public class OtpScreenFragment extends Fragment implements SMSReceiver.OTPReceiveListener, MobileKeysCallback, View.OnClickListener {

    private Context context;
    private EditText tv_one, tv_two, tv_three, tv_four, tv_five, tv_six;
    private Button btn_otp;
    private TextView resend_code_text, tv_timer_text;
    private ProgressDialog dialog;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor edit;
    private String invitation = "";
    private SnackbarFactory snackbarFactory;
    private MobileKeysApiFacade mobileKeysApiFacade;
    private Result result;
    private Guest guest;
    private int time = 60;
    private Handler handler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_otp_screen, container, false);
        try {
            context = view.getContext();
            btn_otp = view.findViewById(R.id.btn_otp);
            container = view.findViewById(R.id.container);
            dialog = new ProgressDialog(context);
            snackbarFactory = new SnackbarFactory(container);
            btn_otp.setOnClickListener(this);
            resend_code_text = view.findViewById(R.id.resend_code_text);
            tv_timer_text = view.findViewById(R.id.tv_timer_text);
            resend_code_text.setVisibility(View.GONE);
            tv_timer_text.setVisibility(View.VISIBLE);
            getActivity().findViewById(R.id.lyt_notification).setVisibility(View.GONE);
            TextView toolbar_title = getActivity().findViewById(R.id.toolbar_title);
            ImageView backBtn = getActivity().findViewById(R.id.naviagation_hamberger);
            backBtn.setVisibility(View.VISIBLE);
            tv_one = view.findViewById(R.id.tv_one);
            tv_two = view.findViewById(R.id.tv_two);
            tv_three = view.findViewById(R.id.tv_three);
            tv_four = view.findViewById(R.id.tv_four);
            tv_five = view.findViewById(R.id.tv_five);
            tv_six = view.findViewById(R.id.tv_six);
            toolbar_title.setText("");
            startTimer();
            init();
            startSMSListener();
            resend_code_text.setOnClickListener(v -> {
                resend_code_text.setVisibility(View.GONE);
                tv_timer_text.setVisibility(View.VISIBLE);
                LoginApiCall();
                startTimer();
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void startTimer() {
        try {
            new CountDownTimer(60000, 1000) {
                public void onTick(long millisUntilFinished) {
                    tv_timer_text.setText("Resend OTP in " + millisUntilFinished / 1000 + " seconds");
                }

                public void onFinish() {
                    tv_timer_text.setVisibility(View.GONE);
                    resend_code_text.setVisibility(View.VISIBLE);
                    resend_code_text.setText("Click here to resend code");
                }

            }.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (!(context instanceof MobileKeysApiFacade)) {
            throw new IllegalArgumentException("Error: attaching to context that doesn't implement MobileKeysApiFacade");
        }
        mobileKeysApiFacade = (MobileKeysApiFacade) context;
    }


    private void OtpAutentication(String otp) {
        try {
            dialog.setMessage("please wait, while we are validating the OTP");
            dialog.setCancelable(false);
            dialog.show();
            Map map = new HashMap();
            map.put("otp", otp);
            map.put("token", GlobalClass.user_token);
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<OtpAutentication> call = api.otpAuthentication(map);
            call.enqueue(new Callback<OtpAutentication>() {
                @Override
                public void onResponse(Call<OtpAutentication> call, Response<OtpAutentication> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                dismissDialog();
                                result = new Result();
                                guest = new Guest();
                                result = response.body().getResult();
                                guest = result.getGuest();
                                ActiveBooking activeBooking = result.getActiveBooking().get(0);
                                String ExpirDateandTime = activeBooking.getCheckoutDateTime();
                                String expiryDate = GlobalClass.outputdateformat.format(GlobalClass.inputdateformat.parse(ExpirDateandTime));
                                Date date = GlobalClass.outputdateformat.parse(expiryDate);
                                Date currentTime = Calendar.getInstance().getTime();
                                long diff = date.getTime() - currentTime.getTime();
                                dismissDialog();
                                sendTags();
                                startScheduler(Math.abs(diff));
                                Log.d("difference", String.valueOf(Math.abs(diff)));
                                invitation = result.getInvitationCode();
                                GlobalClass.USER_NAME = guest.getSalutation() + " " +
                                        guest.getLastName();
                                submitInvitationCode(invitation);

                            } else {
                                dismissDialog();
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                            }
                        } else {
                            dismissDialog();
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        }
                    } catch (Exception e) {
                        dismissDialog();
                        e.printStackTrace();
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<OtpAutentication> call, Throwable t) {
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

    private void sendTags() {
        try {
            Date currentTime = Calendar.getInstance().getTime();
            JSONObject tags = new JSONObject();
            tags.put("isCheckedIn", "true");
            tags.put("timeStamp", currentTime);
            OneSignal.sendTags(tags);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    public void startScheduler(long diff) {

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                JobScheduler jobScheduler = (JobScheduler) context.getApplicationContext().getSystemService(JOB_SCHEDULER_SERVICE);
                ComponentName componentName = new ComponentName(context, SchedulerService.class);
                JobInfo info = new JobInfo.Builder(1, componentName)
                        .setRequiresDeviceIdle(false)
                        .setRequiresCharging(false)
                        .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                        .setPersisted(true)
                        .setBackoffCriteria(6000, JobInfo.BACKOFF_POLICY_LINEAR)
                        .setMinimumLatency(diff)
                        .setOverrideDeadline(diff)
                        .build();
                jobScheduler.schedule(info);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void init() {
        // enter_otp_btn = view.findViewById(R.id.enter_otp_btn);

        tv_one.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_two.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });


        tv_two.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_three.requestFocus();
                }
                if (s.toString().isEmpty()) {
                    tv_one.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        tv_three.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_four.requestFocus();
                }
                if (s.toString().isEmpty()) {
                    tv_two.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        tv_four.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_five.requestFocus();
                }
                if (s.toString().isEmpty()) {
                    tv_three.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        tv_five.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    tv_six.requestFocus();
                }
                if (s.toString().isEmpty()) {
                    tv_four.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        tv_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() == 1) {
                    btn_otp.onEditorAction(EditorInfo.IME_ACTION_DONE);
                }
                if (editable.toString().isEmpty()) {
                    tv_five.requestFocus();
                }
            }
        });
    }

    public void dismissDialog() {
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mobileKeysApiFacade.isEndpointSetUpComplete()) {
            mobileKeysApiFacade.onEndpointSetUpComplete();
        }
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

    /**
     * Mobile keys transaction failed callback
     *
     * @param mobileKeysException failed description
     */
    @Override
    public void handleMobileKeysTransactionFailed(MobileKeysException mobileKeysException) {
        if (isVisible()) {
            //  handler.postDelayed(unlockUiRunnable, UNLOCK_UI_DELAY);
            snackbarFactory.createAndShow(mobileKeysException,
                    HomeScreenActivity.shouldRetry(mobileKeysException) ? this : null);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_otp:
                try {
                    String otp = tv_one.getText().toString() + tv_two.getText().toString() +
                            tv_three.getText().toString() + tv_four.getText().toString() + tv_five.getText().toString() + tv_six.getText().toString();
                    btn_otp.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    OtpAutentication(otp);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void submitInvitationCode(String invitation) {
        mobileKeysApiFacade.getMobileKeys().endpointSetup(this, invitation, new EndpointSetupConfiguration.Builder().build());
        dialog.setMessage("please wait, while we are registering your phone for mobile key");
        dialog.setCancelable(false);
        dialog.show();
        checkInvitionComplet();

    }

    private void checkInvitionComplet() {
        try {
            new Handler().postDelayed(() -> {
                if (mobileKeysApiFacade.isEndpointSetUpComplete()) {
                    // mobilekeyapi();
                    dialog.dismiss();
                    sharedPreferences = context.getSharedPreferences("aurika", 0);
                    edit = sharedPreferences.edit();
                    edit.putBoolean("verifed_otp", true);
                    edit.putString("UserName", GlobalClass.USER_NAME);
                    edit.apply();
                    Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DoorUnlockingFragment()).commit();
                } else {
                    checkInvitionComplet();
                }
            }, 5000);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
/*

    private void mobilekeyapi() {
        try {
            dialog.dismiss();
            dialog.setMessage("please wait, while we are creating your mobile key");
            dialog.setCancelable(false);
            dialog.show();
            Map map = new HashMap();
            map.put("token", GlobalClass.user_token);
            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<TokenAutentication> call = api.mobilekeyapi(map);
            call.enqueue(new Callback<TokenAutentication>() {
                @Override
                public void onResponse(Call<TokenAutentication> call, Response<TokenAutentication> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                dismissDialog();
                                sharedPreferences = context.getSharedPreferences("aurika", 0);
                                edit = sharedPreferences.edit();
                                edit.putBoolean("verifed_otp", true);
                                edit.putString("UserName", GlobalClass.USER_NAME);
                                edit.apply();
                                Objects.requireNonNull(getActivity()).getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new DoorUnlockingFragment()).commit();
                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                            }
                        } else {

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        }
                    } catch (Exception e) {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                        e.printStackTrace();
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<TokenAutentication> call, Throwable t) {
                    try {

                        if (t instanceof SocketTimeoutException) {

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {
                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {

            e.getMessage();
            e.printStackTrace();
        }


    }
*/


    private void LoginApiCall() {
        try {
            Map map = new HashMap();
            map.put("token", GlobalClass.user_token);

            APIMethods api = ClientServiceGenerator.getUrlClient().create(APIMethods.class);
            Call<TokenAutentication> call = api.resendotp(map);
            call.enqueue(new Callback<TokenAutentication>() {
                @Override
                public void onResponse(Call<TokenAutentication> call, Response<TokenAutentication> response) {
                    try {
                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                             /*   validation = new Validation();
                                validation = response.body().getResult();
                                GlobalClass.user_token = response.body().getResult().getToken();
                                getActivity()
                                        .getSupportFragmentManager()
                                        .beginTransaction().replace(R.id.fragment_container,
                                        new OtpScreenFragment()).addToBackStack(null).commit();*/

                            } else {
                                CustomMessageHelper showDialog = new CustomMessageHelper(context);
                                showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                            }
                        } else {

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.ERROR), false, false);
                        }
                    } catch (Exception e) {
                        CustomMessageHelper showDialog = new CustomMessageHelper(context);
                        showDialog.showCustomMessage((Activity) context, "Alert!!", response.body().getError(), false, false);
                        e.printStackTrace();
                        e.getMessage();
                    }

                }

                @Override
                public void onFailure(Call<TokenAutentication> call, Throwable t) {
                    try {

                        if (t instanceof SocketTimeoutException) {


                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.SOCKET_ISSUE), false, false);
                        } else {

                            CustomMessageHelper showDialog = new CustomMessageHelper(context);
                            showDialog.showCustomMessage((Activity) context, "Alert!!", getString(R.string.NETWORK_ISSUE), false, false);
                        }
                    } catch (Exception e) {

                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.getMessage();
            e.printStackTrace();
        }
    }


    private void startSMSListener() {
        try {
            SMSReceiver smsReceiver = new SMSReceiver();
            smsReceiver.setOTPListener(this);
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SmsRetriever.SMS_RETRIEVED_ACTION);
            context.registerReceiver(smsReceiver, intentFilter);
            SmsRetrieverClient client = SmsRetriever.getClient(context);
            Task<Void> task = client.startSmsRetriever();
            task.addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void aVoid) {
                    // API successfully started

                }
            });
            task.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    // Fail to start API
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onOTPReceived(String otp) {
        try {
            tv_one.setText(otp.charAt(1) + "");
            tv_two.setText(otp.charAt(2) + "");
            tv_three.setText(otp.charAt(3) + "");
            tv_four.setText(otp.charAt(4) + "");
            tv_five.setText(otp.charAt(5) + "");
            tv_six.setText(otp.charAt(6) + "");
            handler = new Handler();
            handler.postDelayed(() -> {
                btn_otp.performClick();
            }, 2000);
        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        if (handler != null)
            handler.removeCallbacksAndMessages(null);
        super.onPause();
    }

    @Override
    public void onOTPTimeOut() {

    }

    @Override
    public void onOTPReceivedError(String error) {

    }
}
