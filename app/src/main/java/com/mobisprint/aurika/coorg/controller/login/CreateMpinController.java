package com.mobisprint.aurika.coorg.controller.login;

import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateMpinController {

    private ApiListner listner;

    public CreateMpinController(ApiListner listner) {
        this.listner = listner;
    }

    public boolean compareMpin(String mpin1, String mpin2) {

        if (mpin1.equalsIgnoreCase(mpin2)){
            return true;
        }else
        return false;
    }

    public void saveMpin(String mpin1, String mpin2, String android_id, String user_token) {
        listner.onFetchProgress();
        HashMap<String, String> map=new HashMap<>();
        map.put("mpin",mpin1);
        map.put("confirm_mpin",mpin2);
        map.put("device_id",android_id);
        map.put("token",user_token);
        Call<Login> call = GlobalClass.API_COORG.createMpin(map);
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
