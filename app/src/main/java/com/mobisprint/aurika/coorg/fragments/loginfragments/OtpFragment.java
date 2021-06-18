package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.login.OtpController;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Response;

import static android.app.Activity.RESULT_OK;


public class OtpFragment extends Fragment implements ApiListner {

 private EditText et_one,et_two,et_three,et_four,et_five,et_six;
 private OtpController controller;
 private String request_id;
 private TextView tv_resend_otp,tv_resend_otp_timer;
 private Integer counter;
 private Context mContext;
    private static final int REQ_USER_CONSENT = 200;
    private SmsBroadcastReceiver smsBroadcastReceiver;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_otp, container, false);

        controller = new OtpController(this);

        et_one = view.findViewById(R.id.et_one);
        et_two = view.findViewById(R.id.et_two);
        et_three = view.findViewById(R.id.et_three);
        et_four = view.findViewById(R.id.et_four);
        et_five = view.findViewById(R.id.et_five);
        et_six = view.findViewById(R.id.et_six);
        tv_resend_otp = view.findViewById(R.id.tv_resend_otp);
        tv_resend_otp_timer = view.findViewById(R.id.tv_resend_otp_timer);
        tv_resend_otp.setVisibility(View.GONE);
        mContext = view.getContext();

        otpTimer();

        Bundle bundle = getArguments();
        if (bundle!= null){
            request_id = bundle.getString("request_id");
        }

        init();

        tv_resend_otp.setOnClickListener(v -> {
            otpTimer();
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            // ...
            case SMS_CONSENT_REQUEST:
                if (resultCode == RESULT_OK) {
                    // Get SMS message content
                    String message = data.getStringExtra(SmsRetriever.EXTRA_SMS_MESSAGE);
                    // Extract one-time code from the message and complete verification
                    // `sms` contains the entire text of the SMS message, so you will need
                    // to parse the string.
                    //  String oneTimeCode = message); // define this function

                    String[] OTP= message.split("OTP",6);

                    onOTPReceived(OTP[1].trim().substring(0,6).trim());
                    // send one time code to the server
                } else {
                    // Consent canceled, handle the error ...
                }
                break;
        }
    }

    private void onOTPReceived(String message) {
        // This will match any 6 digit number in the message

        et_one.setText(message.charAt(0) + "");
        et_two.setText(message.charAt(1) + "");
        et_three.setText(message.charAt(2) + "");
        et_four.setText(message.charAt(3) + "");
        et_five.setText(message.charAt(4) + "");
        et_six.setText(message.charAt(5) + "");


    }


    private void otpTimer() {
        counter = 60;
        new CountDownTimer(60000,1000){
            @Override
            public void onTick(long millisUntilFinished) {
                tv_resend_otp.setVisibility(View.GONE);
                tv_resend_otp_timer.setVisibility(View.VISIBLE);
                tv_resend_otp_timer.setText("Resend OTP in"+" "+String.valueOf(counter)+" seconds");
                counter--;
            }

            @Override
            public void onFinish() {
                tv_resend_otp_timer.setVisibility(View.GONE);
                tv_resend_otp.setVisibility(View.VISIBLE);
            }
        }.start();

    }

    private void init() {
        et_two.setEnabled(false);
        et_three.setEnabled(false);
        et_four.setEnabled(false);
        et_five.setEnabled(false);
        et_six.setEnabled(false);
        // enter_otp_btn = view.findViewById(R.id.enter_otp_btn);
        et_one.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    et_two.setEnabled(true);
                    et_two.requestFocus();

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });


        et_two.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    et_three.setEnabled(true);
                    et_three.requestFocus();
                    verifyOtp();

                }
                if (s.toString().isEmpty()) {
                    et_one.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_three.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    et_four.setEnabled(true);
                    et_four.requestFocus();
                    verifyOtp();
                }
                if (s.toString().isEmpty()) {
                    et_two.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_four.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    et_five.setEnabled(true);
                    et_five.requestFocus();
                    verifyOtp();
                }
                if (s.toString().isEmpty()) {
                    et_three.requestFocus();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_five.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                if (s.length() == 1) {
                    et_six.setEnabled(true);
                    et_six.requestFocus();
                    verifyOtp();
                }
                if (s.toString().isEmpty()) {
                    et_four.requestFocus();
                }
            }
            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
            }
        });

        et_six.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }
            @Override
            public void afterTextChanged(Editable editable) {

                if (editable.length() ==1){
                    verifyOtp();
                }

                if (editable.toString().isEmpty()) {
                    et_five.requestFocus();
                }

            }
        });




    }

    private void verifyOtp() {

        String otp=et_one.getText().toString()+
                et_two.getText().toString()+
                et_three.getText().toString()+
                et_four.getText().toString()+
                et_five.getText().toString()+
                et_six.getText().toString();
        if(otp.length()==6){
            controller.verifyOtp(request_id,otp);
        }
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {

        if (response != null){
            Login login = (Login) response.body();
            GlobalClass.user_token = login.getData().getToken();
            Fragment fragment = new CreateMpinFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).commit();

        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }


    @Override
    public void onStop() {
        super.onStop();
        getActivity().unregisterReceiver(smsVerificationReceiver);

    }


    @Override
    public void onResume() {
        super.onResume();
        startSMSListener();
    }



    private void startSMSListener() {
        try {
            Task<Void> task = SmsRetriever.getClient(mContext).startSmsUserConsent(null);
            IntentFilter intentFilter = new IntentFilter(SmsRetriever.SMS_RETRIEVED_ACTION);
            getActivity().registerReceiver(smsVerificationReceiver,intentFilter,SmsRetriever.SEND_PERMISSION,null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static final int SMS_CONSENT_REQUEST = 2;  // Set to an unused request code
    private final BroadcastReceiver smsVerificationReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (SmsRetriever.SMS_RETRIEVED_ACTION.equals(intent.getAction())) {
                Bundle extras = intent.getExtras();
                Status smsRetrieverStatus = (Status) extras.get(SmsRetriever.EXTRA_STATUS);

                switch (smsRetrieverStatus.getStatusCode()) {
                    case CommonStatusCodes.SUCCESS:
                        // Get consent intent
                        Intent consentIntent = extras.getParcelable(SmsRetriever.EXTRA_CONSENT_INTENT);
                        try {
                            // Start activity to show consent dialog to user, activity must be started in
                            // 5 minutes, otherwise you'll receive another TIMEOUT intent
                            startActivityForResult(consentIntent, SMS_CONSENT_REQUEST);
                        } catch (ActivityNotFoundException e) {
                            e.printStackTrace();
                            // Handle the exception ...
                        }
                        break;
                    case CommonStatusCodes.TIMEOUT:
                        // Time out occurred, handle the error.
                        break;
                }
            }
        }
    };


}