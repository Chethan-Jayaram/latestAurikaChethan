package com.mobisprint.aurika.coorg.fragments.loginfragments;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.biometrics.BiometricPrompt;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.CancellationSignal;
import android.os.Handler;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyPermanentlyInvalidatedException;
import android.security.keystore.KeyProperties;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import com.mobisprint.aurika.helper.BiometricDialogV23;
import com.mobisprint.aurika.helper.FingerprintHandler;
import com.mobisprint.aurika.helper.GlobalClass;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

import retrofit2.Response;

import static android.content.Context.FINGERPRINT_SERVICE;
import static android.content.Context.KEYGUARD_SERVICE;


public class LoginFragment extends Fragment implements ApiListner,GlobalClass.OnBiometricAuthSucess {

    private Button btn_confirm;
    private TextView forgot_mpin, new_user, skip;
    private EditText et_one, et_two, et_three, et_four;
    private LoginController loginController;
    private String android_id;
    private Context mContext;

    private Fragment fragment;

    private Cipher cipher;
    private KeyStore keyStore;
    private KeyGenerator keyGenerator;
    private static final String KEY_NAME = "RefinaryBioMetric";

    private FingerprintManager.CryptoObject cryptoObject;
    private FingerprintManager fingerprintManager;
    private KeyguardManager keyguardManager;
    private CancellationSignal cancellationSignal;
    private BiometricDialogV23 biometricDialogV23;

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

            GlobalClass.Forgot_Mpin = true;



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


    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onResume() {
        super.onResume();
       /* if (checkBiometricSupport()){
          authenticateUser(this.getView());
        }*/

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            keyguardManager = (KeyguardManager) mContext.getSystemService(KEYGUARD_SERVICE);
            fingerprintManager = (FingerprintManager) mContext.getSystemService(FINGERPRINT_SERVICE);

            if (fingerprintManager != null) {
                if (fingerprintManager.isHardwareDetected()) {
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.USE_FINGERPRINT) != PackageManager.PERMISSION_GRANTED) {

                        GlobalClass.ShowAlert(mContext, "Alert", "enable Please register at least one fingerprint in your device's Settings to use BIOMETRIC to login");
                    } else if (!fingerprintManager.hasEnrolledFingerprints()) {

                        GlobalClass.ShowAlert(mContext, "Alert", "No fingerprint configured. Please register at least one fingerprint in your device's Settings to use BIOMETRIC to login");
                        //  enableBioWithoutBiometric();
                    } else {
                        //  enableBIometric();
                       /* mBtn_biometric.setVisibility(View.VISIBLE);
                        showBio();

                        m_lyt.setAlpha(0.2f);*/
                        initiateFingerprintlistner();
                        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
                            biometricDialogV23 = new BiometricDialogV23(mContext,cancellationSignal);
                            biometricDialogV23.show();
                        }

                    }

                } else {
                    // enableMPIN();
                }
                //password protection

            } else {

                //enableMPIN();
            }


        }

    }

    @Override
    public void onFetchProgress() {

    }

    @Override
    public <ResponseType> void onFetchComplete(Response<ResponseType> response) {
        if (response != null) {
            Login login = (Login) response.body();
            GlobalClass.user_token = login.getData().getProfile().getToken();
            GlobalClass.editor.putBoolean("isMpinSetUpComplete",true);
            GlobalClass.editor.apply();
            Intent intent = new Intent(mContext, HomeActivity.class);
            startActivity(intent);
            getActivity().finish();

        }

    }

    @Override
    public void onFetchError(String error) {
        GlobalClass.ShowAlert(getContext(), "Alert", error);
    }

    private void initiateFingerprintlistner() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            try {
                generateKey();
            } catch (LoginFragment.FingerprintException e) {
                e.printStackTrace();
            }
            if (initCipher()) {
                try {
                    cryptoObject = new FingerprintManager.CryptoObject(cipher);
                    FingerprintHandler helper = new FingerprintHandler(mContext,this );
                    helper.startAuth(fingerprintManager, cryptoObject);
                    cancellationSignal = new CancellationSignal();
                    fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, helper, null);
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void generateKey() throws LoginFragment.FingerprintException {
        try {

            keyStore = KeyStore.getInstance("AndroidKeyStore");


            keyGenerator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");

            keyStore.load(null);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                keyGenerator.init(new
                        KeyGenParameterSpec.Builder(KEY_NAME,
                        KeyProperties.PURPOSE_ENCRYPT |
                                KeyProperties.PURPOSE_DECRYPT)
                        .setBlockModes(KeyProperties.BLOCK_MODE_CBC)
                        .setUserAuthenticationRequired(true)
                        .setEncryptionPaddings(
                                KeyProperties.ENCRYPTION_PADDING_PKCS7)
                        .build());
            }

            keyGenerator.generateKey();

        } catch (KeyStoreException
                | NoSuchAlgorithmException
                | NoSuchProviderException
                | InvalidAlgorithmParameterException
                | CertificateException
                | IOException exc) {
            exc.printStackTrace();
            throw new FingerprintException(exc);
        }


    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public boolean initCipher() {
        try {
            cipher = Cipher.getInstance(
                    KeyProperties.KEY_ALGORITHM_AES + "/"
                            + KeyProperties.BLOCK_MODE_CBC + "/"
                            + KeyProperties.ENCRYPTION_PADDING_PKCS7);
        } catch (NoSuchAlgorithmException |
                NoSuchPaddingException e) {
            throw new RuntimeException("Failed to get Cipher", e);
        }

        try {
            keyStore.load(null);
            SecretKey key = (SecretKey) keyStore.getKey(KEY_NAME,
                    null);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            return true;
        } catch (KeyPermanentlyInvalidatedException e) {
            return false;
        } catch (KeyStoreException | CertificateException
                | UnrecoverableKeyException | IOException
                | NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to init Cipher", e);
        }
    }

    @Override
    public void onSucessfullBiometricAuth() {
        stopFingerAuth();
        if (biometricDialogV23.isShowing()){
            biometricDialogV23.dismiss();
        }



        loginController.checkMpin("", android_id, GlobalClass.sharedPreferences.getString("token",""));


        /*GlobalClass.editor.putBoolean("isMpinSetUpComplete",true);
        GlobalClass.editor.apply();
        Intent intent = new Intent(mContext, HomeActivity.class);
        startActivity(intent);
        getActivity().finish();*/
    }

    private class FingerprintException extends Exception {

        public FingerprintException(Exception e) {
            super(e);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        try{
            stopFingerAuth();

           /* if(mBioAlertDialog!=null && mBioAlertDialog.isShowing()){
                mBioAlertDialog.dismiss();
            }*/

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void stopFingerAuth(){
        if(cancellationSignal != null && !cancellationSignal.isCanceled()){
            cancellationSignal.cancel();
        }
    }

}