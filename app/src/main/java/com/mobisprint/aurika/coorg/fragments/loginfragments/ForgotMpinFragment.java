package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.text.InputFilter;
import android.text.InputType;
import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.hbb20.CountryCodePicker;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.activity.HomeActivity;
import com.mobisprint.aurika.coorg.controller.login.ForgotMpinController;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Response;


public class ForgotMpinFragment extends Fragment implements ApiListner {

    private EditText et_email_or_phnum;
    private CheckBox checkBox;
    private CountryCodePicker countryCodePicker;
    private Button bt_send_otp;
    private ForgotMpinController forgotMpinController;
    private TextView tv_login_with_email,tv_new_user,skip;
    private Fragment fragment;
    private Boolean isEmailSelected = true,check_status;
    private String android_id;
    private Context mContext;
    private int phlength = 10;
    private int emailLength = 30;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_forgot_mpin, container, false);

        try {

            et_email_or_phnum = view.findViewById(R.id.et_email_phone);
            checkBox = view.findViewById(R.id.reset_mpin_checkbox);
            countryCodePicker = view.findViewById(R.id.country_code_picker);
            bt_send_otp = view.findViewById(R.id.btn_forgot_pin_otp);
            forgotMpinController = new ForgotMpinController(this);
            tv_login_with_email = view.findViewById(R.id.login_with_email);
            tv_new_user = view.findViewById(R.id.new_user);
            skip = view.findViewById(R.id.skip);
            mContext = getContext();
            android_id = GlobalClass.prefix + GlobalClass.android_id + GlobalClass.suffix;
            InputFilter[] FilterArray = new InputFilter[1];
            FilterArray[0] = new InputFilter.LengthFilter(phlength);
            et_email_or_phnum.setFilters(FilterArray);


            checkBox.setText(Html.fromHtml("<body>\n" +
                    "        <p" +
                    "          style=\"color:#1e0028\">  Do you agree to our  <a href=\"https://www.google.com/\" style=\"color:#1e0028\">Terms &amp; Conditions</a>\n" +
                    "         and  <a href=\"https://www.google.com/\" style=\"color:#1e0028\">Privacy Policy?</a>\n" +
                    "        </p>" +
                    "    </body>"));



            checkBox.setClickable(true);
            checkBox.setMovementMethod(LinkMovementMethod.getInstance());

            check_status = GlobalClass.sharedPreferences.getBoolean(String.valueOf(GlobalClass.Forgot_Mpin),true);

            if (GlobalClass.Forgot_Mpin){
                forgotMpinController.isEmailSelected(false);
                et_email_or_phnum.setHint("Enter Phone Number");
                et_email_or_phnum.getText().clear();
                FilterArray[0] = new InputFilter.LengthFilter(phlength);
                et_email_or_phnum.setFilters(FilterArray);
                et_email_or_phnum.setInputType(InputType.TYPE_CLASS_PHONE);
                countryCodePicker.setVisibility(View.VISIBLE);
                tv_login_with_email.setText("Login with E-mail");
                isEmailSelected= true;
            }else{
                forgotMpinController.isEmailSelected(true);
                et_email_or_phnum.setHint("Enter Email Id");
                et_email_or_phnum.getText().clear();
                FilterArray[0] = new InputFilter.LengthFilter(emailLength);
                et_email_or_phnum.setFilters(FilterArray);
                et_email_or_phnum.setInputType(InputType.TYPE_CLASS_TEXT);
                countryCodePicker.setVisibility(View.GONE);
                tv_login_with_email.setText("Login with Phone number");
                isEmailSelected = false;
            }


            tv_new_user.setOnClickListener(v -> {
                fragment = new RegistrationFragment();
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).addToBackStack(null).commit();
            });


            skip.setOnClickListener(v -> {
                Intent intent = new Intent(mContext, HomeActivity.class);
                startActivity(intent);
                getActivity().finish();
            });

            tv_login_with_email.setOnClickListener(v -> {

                if (isEmailSelected){
                    forgotMpinController.isEmailSelected(true);
                    et_email_or_phnum.setHint("Enter Email Id");
                    et_email_or_phnum.getText().clear();
                    FilterArray[0] = new InputFilter.LengthFilter(emailLength);
                    et_email_or_phnum.setFilters(FilterArray);
                    et_email_or_phnum.setInputType(InputType.TYPE_CLASS_TEXT);
                    countryCodePicker.setVisibility(View.GONE);
                    tv_login_with_email.setText("Login with Phone number");
                    isEmailSelected = false;
                    GlobalClass.Forgot_Mpin = false;
                }else{
                    forgotMpinController.isEmailSelected(false);
                    et_email_or_phnum.setHint("Enter Phone Number");
                    et_email_or_phnum.getText().clear();
                    FilterArray[0] = new InputFilter.LengthFilter(phlength);
                    et_email_or_phnum.setFilters(FilterArray);
                    et_email_or_phnum.setInputType(InputType.TYPE_CLASS_PHONE);
                    countryCodePicker.setVisibility(View.VISIBLE);
                    tv_login_with_email.setText("Login with E-mail");
                    isEmailSelected= true;
                    GlobalClass.Forgot_Mpin = true;
                }

            });


            bt_send_otp.setOnClickListener(v -> {
                    if (isEmailSelected){
                        if (et_email_or_phnum.getText().toString().trim().isEmpty()){
                            et_email_or_phnum.setError("Enter phone-number");
                        }else{
                            if (checkBox.isChecked()){
                                forgotMpinController.getOtp("+"+countryCodePicker.getSelectedCountryCode(),et_email_or_phnum.getText().toString(),android_id);
                            }else {
                                GlobalClass.ShowAlert(mContext,"Alert","Check condition checkbox");
                            }
                        }
                    }else {

                        if (et_email_or_phnum.getText().toString().trim().isEmpty()){
                            et_email_or_phnum.setError("Enter e-mail");

                        }else{
                            if (checkBox.isChecked()){
                                forgotMpinController.getOtp("",et_email_or_phnum.getText().toString(),android_id);
                            }else {
                                GlobalClass.ShowAlert(mContext,"Alert","Check condition checkbox");
                            }
                        }
                    }

            });


        }catch (Exception e){
            e.printStackTrace();
        }



        return view;
    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response!=null){
            Login login = (Login) response.body();
            Fragment fragment = new OtpFragment();
            Bundle bundle = new Bundle();
            bundle.putString("request_id",login.getData().getRequest_id());
            fragment.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).addToBackStack(null).commit();
        }

    }

    @Override
    public void onFetchError(String error) {

        GlobalClass.ShowAlert(getContext(),"Alert",error);

    }
}