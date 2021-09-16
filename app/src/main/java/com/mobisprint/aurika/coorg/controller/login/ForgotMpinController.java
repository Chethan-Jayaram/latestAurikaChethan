package com.mobisprint.aurika.coorg.controller.login;

import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotMpinController {
    private Boolean isEmailSelected = false;

    private ApiListner listner;

    public ForgotMpinController(ApiListner listner) {
        this.listner = listner;
    }


    public void isEmailSelected(Boolean check) {
       /* if (isEmailSelected){
            isEmailSelected = false;
        }else {
            isEmailSelected = true;
        }*/
        isEmailSelected = check;
    }

    public void getOtp(String country_code, String ph_num, String android_id) {
        listner.onFetchProgress();
        HashMap<String, String> map=new HashMap<>();
        map.put("country_code",country_code);

        if (isEmailSelected){
            map.put("email",ph_num);
        }else {
            map.put("mobile",ph_num);
        }
        map.put("device_id",android_id);
        Call<Login> call = GlobalClass.API_COORG.forgotMpin(map);
        call.enqueue(new Callback<Login>() {
            @Override
            public void onResponse(Call<Login> call, Response<Login> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){
                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError(response.body().getMessage());
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

}
