package com.mobisprint.aurika.coorg.controller.services;

import android.util.Log;

import com.mobisprint.aurika.bluetooth.ClosestLockTrigger;
import com.mobisprint.aurika.coorg.fragments.services.CoorgServices;
import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AmenitiesController {

    private ApiListner listner;

    public AmenitiesController(ApiListner listner) {
        this.listner = listner;
    }

    public void getServices() {
        listner.onFetchProgress();
        Call<CoorgServicesPojo> call = GlobalClass.API_COORG.amenities();
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
