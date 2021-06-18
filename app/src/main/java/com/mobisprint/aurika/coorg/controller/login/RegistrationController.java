package com.mobisprint.aurika.coorg.controller.login;

import com.mobisprint.aurika.coorg.pojo.login.Login;
import com.mobisprint.aurika.coorg.services.APIMethods;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.retrofit.ClientServiceGenerator;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegistrationController {

    private  ApiListner listner;

    public RegistrationController(ApiListner listner) {
        this.listner = listner;
    }

    public void pushUserDetails(String salutation, String first_name, String last_name, String email, String country_code, String phnum, String android_id) {
        listner.onFetchProgress();
        HashMap<String, String> map=new HashMap<>();
        map.put("salutation",salutation);
        map.put("first_name",first_name);
        map.put("last_name",last_name);
        map.put("email",email);
        map.put("country_code","+"+country_code);
        map.put("contact_number",phnum);
        map.put("device_id",android_id);
        Call<Login> call = GlobalClass.API_COORG.registration(map);
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
