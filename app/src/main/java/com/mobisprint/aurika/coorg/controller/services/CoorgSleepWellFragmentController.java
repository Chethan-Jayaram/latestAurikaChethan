package com.mobisprint.aurika.coorg.controller.services;

import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoorgSleepWellFragmentController {

    private ApiListner listner;

    public CoorgSleepWellFragmentController(ApiListner listner) {
        this.listner = listner;
    }

    public void getServices() {
        listner.onFetchProgress();
        Call<CoorgServicesPojo> call = GlobalClass.API_COORG.sleepWell();
        call.enqueue(new Callback<CoorgServicesPojo>() {
            @Override
            public void onResponse(Call<CoorgServicesPojo> call, Response<CoorgServicesPojo> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError(response.body().getMessage());
                    }
                }else {
                    listner.onFetchError(response.message());
                }
            }

            @Override
            public void onFailure(Call<CoorgServicesPojo> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
