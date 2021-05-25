package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.login.OtpController;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Response;


public class OtpFragment extends Fragment implements ApiListner {

 private EditText et_one,et_two,et_three,et_four,et_five,et_six;
 private OtpController controller;
 private String request_id;

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

        Bundle bundle = getArguments();
        if (bundle!= null){
            request_id = bundle.getString("request_id");
        }

        init();

        return view;
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
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).addToBackStack(null).commit();

        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}