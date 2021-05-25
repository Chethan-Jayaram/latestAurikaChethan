package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;

import com.hbb20.CountryCodePicker;
import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.login.RegistrationController;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Response;


public class RegistrationFragment extends Fragment implements ApiListner {

    private EditText et_firstname, et_lastname, et_email, et_ph_num;
    private Button btn_confirm_and_send_otp;
    private CheckBox reg_check_box;
    private RegistrationController registrationController;
    private CountryCodePicker countryCodePicker;
    private Spinner salutation_spinner;
    private String[] salutation = {"Mr","Mrs"};
    private String android_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registration, container, false);


        try {

            registrationController = new RegistrationController(this);
            et_firstname = view.findViewById(R.id.et_firstname);
            et_lastname = view.findViewById(R.id.et_lastname);
            et_email = view.findViewById(R.id.et_email);
            et_ph_num = view.findViewById(R.id.et_ph_num);
            reg_check_box = view.findViewById(R.id.reg_check_box);
            btn_confirm_and_send_otp = view.findViewById(R.id.btn_confirm_and_send_otp);
            salutation_spinner = view.findViewById(R.id.salutation_spinner);

            btn_confirm_and_send_otp.setEnabled(true);

            countryCodePicker = view.findViewById(R.id.country_code_picker);


            android_id = GlobalClass.prefix + GlobalClass.android_id + GlobalClass.suffix;

            ArrayAdapter arrayAdapter = new ArrayAdapter(getContext(),R.layout.spinner_list,salutation);
            arrayAdapter.setDropDownViewResource(R.layout.spinner_list);
            salutation_spinner.setAdapter(arrayAdapter);


            btn_confirm_and_send_otp.setOnClickListener(v -> {

                btn_confirm_and_send_otp.setEnabled(false);

                if (userValidation()){
                    if (reg_check_box.isChecked()) {

                        registrationController.pushUserDetails(
                                salutation_spinner.getSelectedItem().toString(),
                                et_firstname.getText().toString().trim(),
                                et_lastname.getText().toString().trim(),
                                et_email.getText().toString().trim(),
                                countryCodePicker.getSelectedCountryCode(),
                                et_ph_num.getText().toString().trim(),
                                android_id
                        );

                    }else {
                        GlobalClass.ShowAlert(getContext(),"Alert","Check condition checkbox");
                        btn_confirm_and_send_otp.setEnabled(true);
                    }

                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    private boolean userValidation() {

        if (et_firstname.getText().toString().trim().isEmpty()) {
            et_firstname.setError("Enter first name");
            btn_confirm_and_send_otp.setEnabled(true);
            return false;
        } else if (et_lastname.getText().toString().trim().isEmpty()) {
            et_lastname.setError("Enter last name");
            btn_confirm_and_send_otp.setEnabled(true);
            return false;
        } else if (et_email.getText().toString().trim().isEmpty()) {
            et_email.setError("Enter email id");
            btn_confirm_and_send_otp.setEnabled(true);
            return false;
        } else if (et_ph_num.getText().toString().trim().isEmpty()) {
            et_ph_num.setError("Enter phone number");
            btn_confirm_and_send_otp.setEnabled(true);
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString().trim()).matches()) {
            et_email.setError("Enter valid email");
            btn_confirm_and_send_otp.setEnabled(true);
            return false;
        } else {
            return true;
        }

    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null) {
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
        GlobalClass.ShowAlert(getContext(), "Alert", error);
        btn_confirm_and_send_otp.setEnabled(true);
    }
}