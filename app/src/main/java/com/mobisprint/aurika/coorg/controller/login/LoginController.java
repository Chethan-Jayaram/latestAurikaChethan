package com.mobisprint.aurika.coorg.controller.login;

import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginController {

    private ApiListner listner;

    public LoginController(ApiListner listner) {
        this.listner = listner;
    }

    public void checkMpin(String mpin, String android_id, String user_token) {
        listner.onFetchProgress();

        HashMap<String, String> map=new HashMap<>();
        map.put("mpin",mpin);
        map.put("device_id",android_id);
        map.put("token",user_token);
        Call<Login> call = GlobalClass.API_COORG.loginWithMpin(map);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError(response.body().getMessage());
                }
            }

            @Override
            public void onFailure(Call<Login> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });


    }
}
