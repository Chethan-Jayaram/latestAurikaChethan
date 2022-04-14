package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import com.mobisprint.aurika.R;
import com.mobisprint.aurika.coorg.controller.login.CreateMpinController;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.onesignal.OneSignal;

import retrofit2.Response;


public class CreateMpinFragment extends Fragment implements ApiListner  {

    private EditText et1_one,et1_two,et1_three,et1_four,et2_one,et2_two,et2_three,et2_four;
    private CreateMpinController createMpinController;
    private Button btn_confirm_mpin;
    private String android_id;
    private Context mContext;
    private String mpin,UUID;


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_create_mpin, container, false);

        try {


              UUID = OneSignal.getDeviceState().getUserId();
            Log.d("player_id",UUID);


            et1_one = view.findViewById(R.id.et1_one);
            et1_two = view.findViewById(R.id.et1_two);
            et1_three = view.findViewById(R.id.et1_three);
            et1_four = view.findViewById(R.id.et1_four);
            et2_one = view.findViewById(R.id.et2_one);
            et2_two = view.findViewById(R.id.et2_two);
            et2_three = view.findViewById(R.id.et2_three);
            et2_four = view.findViewById(R.id.et2_four);

            mContext = getContext();
            createMpinController = new CreateMpinController(this);

            btn_confirm_mpin = view.findViewById(R.id.btn_confirm_mpin);

            android_id = GlobalClass.prefix + GlobalClass.android_id + GlobalClass.suffix;

            init();

            Bundle bundle = getArguments();

            btn_confirm_mpin.setOnClickListener(v ->{

                String mpin1 = et1_one.getText().toString()+
                        et1_two.getText().toString()+
                        et1_three.getText().toString()+
                        et1_four.getText().toString();
                String mpin2 = et2_one.getText().toString()+
                        et2_two.getText().toString()+
                        et2_three.getText().toString()+
                        et2_four.getText().toString();
                if (createMpinController.compareMpin(mpin1,mpin2)){
                    createMpinController.saveMpin(mpin1,mpin2,android_id,bundle.getString("token"),UUID);
                }else{
                    GlobalClass.ShowAlert(mContext,"Alert","Mpin mismatch");
                }
            });
            
            /*displayBiometricPrompt();*/


        }catch (Exception e){
            e.printStackTrace();
        }

        return view;
    }




    private void init() {

        et1_one.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et1_two.requestFocus();
                        }
                    }, 100);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        et1_two.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {

                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et1_three.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et1_one.requestFocus();
                        }
                    }
                }, 100);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        et1_three.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et1_four.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et1_two.requestFocus();
                        }
                    }
                }, 100);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et1_four.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (s.length() == 1) {
                            et2_one.requestFocus();
                        }
                        if (s.toString().isEmpty()) {
                            et1_three.requestFocus();
                        }
                    }
                }, 100);
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });


        et2_one.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (s.length() == 1) {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            et2_two.requestFocus();
                        }
                    }, 100);

                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });


        et2_two.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {


                new Handler().postDelayed(() -> {
                    if (s.length() == 1) {
                        et2_three.requestFocus();
                    }
                    if (s.toString().isEmpty()) {
                        et2_one.requestFocus();
                    }
                }, 100);

            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
        });
        et2_three.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(() -> {
                    if (s.length() == 1) {
                        et2_four.requestFocus();
                    }
                    if (s.toString().isEmpty()) {
                        et2_two.requestFocus();
                    }
                }, 100);


            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        et2_four.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                new Handler().postDelayed(() -> {
                    if (s.length() == 1) {
                        et2_four.onEditorAction(EditorInfo.IME_ACTION_DONE);
                    }
                    if (s.toString().isEmpty()) {
                        et2_three.requestFocus();
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
        if (response != null){

            Login login = (Login) response.body();
            mpin = et1_one.getText().toString()+
                    et1_two.getText().toString()+
                    et1_three.getText().toString()+
                    et1_four.getText().toString();

           GlobalClass.editor.clear();
            GlobalClass.editor.putString("token",login.getData().getToken());
            GlobalClass.editor.putBoolean("isMpinSetUpComplete",true);
            GlobalClass.editor.commit();
            Fragment fragment = new LoginFragment();
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container_view_tag, fragment).commit();

        }

    }

    @Override
    public void onFetchError(String error) {
        GlobalClass.ShowAlert(getContext(), "Alert", error);
    }
}