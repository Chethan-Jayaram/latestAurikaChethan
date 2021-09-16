package com.mobisprint.aurika.coorg.controller.services;

import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HouseKeepingController {

    private ApiListner listner;

    public HouseKeepingController(ApiListner listner) {
        this.listner = listner;
    }

    public void getServices() {
        listner.onFetchProgress();
        Call<CoorgServicesPojo> call = GlobalClass.API_COORG.houseKeeping();
        call.enqueue(new Callback<CoorgServicesPojo>() {
            @Override
            public void onResponse(Call<CoorgServicesPojo> call, Response<CoorgServicesPojo> response) {
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
            public void onFailure(Call<CoorgServicesPojo> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
