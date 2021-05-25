package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.HomeActivity;
import com.mobisprint.aurika.coorg.controller.login.LoginController;
import com.mobisprint.aurika.coorg.fragments.loginfragments.ForgotMpinFragment;
import com.mobisprint.aurika.coorg.fragments.loginfragments.RegistrationFragment;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Response;


public class LoginFragment extends Fragment implements ApiListner {

    private Button btn_confirm;
    private TextView forgot_mpin, new_user, skip;
    private EditText et_one, et_two, et_three, et_four;
    private LoginController loginController;
    private String android_id;
    private Context mContext;

    private Fragment fragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        try {
            mContext = getContext();
            btn_confirm = view.findViewById(R.id.btn_confirm);
            forgot_mpin = view.findViewById(R.id.forgot_mpin);
            new_user = view.findViewById(R.id.new_user);
            skip = view.findViewById(R.id.skip);
            et_one = view.findViewById(R.id.et_1);
            et_two = view.findViewById(R.id.et_2);
            et_three = view.findViewById(R.id.et_3);
            et_four = view.findViewById(R.id.et_4);
            android_id = GlobalClass.prefix + GlobalClass.android_id + GlobalClass.suffix;


            loginController = new LoginController(this);

            init();

            forgot_mpin.setOnClickListener(v -> {
                fragment = new ForgotMpinFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).addToBackStack(null).commit();
            });


            new_user.setOnClickListener(v -> {
                fragment = new RegistrationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).addToBackStack(null).commit();
            });

            skip.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            });

            btn_confirm.setOnClickListener(v -> {

                verifyMpin();


            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;
    }

    private void verifyMpin() {

        String mpin = et_one.getText().toString() +
                et_two.getText().toString() +
                et_three.getText().toString() +
                et_four.getText().toString();
        if (mpin.length() == 4) {

            loginController.checkMpin(mpin, android_id, GlobalClass.sharedPreferences.getString("token",""));

        }

    }

    private void init() {

        et_one.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et_two.requestFocus();
                        }
                    }, 100);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        et_two.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_three.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et_one.requestFocus();
                        }
                    }
                }, 100);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        et_three.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_four.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et_two.requestFocus();
                        }
                    }
                }, 100);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et_four.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et_four.onEditorAction(EditorInfo.IME_ACTION_DONE);
                        }
                        if (s.toString().isEmpty()) {
                            et_three.requestFocus();
                        }
                    }
                }, 100);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null) {
            Login login = (Login) response.body();
            GlobalClass.user_token = login.getData().getToken();
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
            getActivity().finish();

        }

    }

    @Override
    public void onFetchError(String error) {
        GlobalClass.ShowAlert(getContext(), "Alert", error);
    }
}