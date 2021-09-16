package com.mobisprint.aurika.coorg.controller.login;

import android.util.Log;


import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;


import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OtpController {

    private  ApiListner listner;

    public OtpController(ApiListner listner) {
        this.listner = listner;
    }

    public  void verifyOtp(String request_id, String otp, String android_id) {
        listner.onFetchProgress();
        HashMap<String, String> map=new HashMap<>();
        map.put("otp",otp);
        map.put("request_id",request_id);
        map.put("device_id",android_id);
        Call<Login> call = GlobalClass.API_COORG.authenticateOtp(map);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }

    public void resendOtp(String request_id) {
        listner.onFetchProgress();
        HashMap<String, String> map=new HashMap<>();
        map.put("request_id",request_id);
        Call<General> call = GlobalClass.API_COORG.resendOtp(map);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
