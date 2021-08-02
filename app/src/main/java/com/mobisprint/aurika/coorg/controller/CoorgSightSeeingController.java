package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.navigation.Navigation;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CoorgSightSeeingController {

    private ApiListner listner;

    public CoorgSightSeeingController(ApiListner listner) {
        this.listner = listner;
    }
    public void getSightSeeing() {

        listner.onFetchProgress();

        Call<SightSeeing> call = GlobalClass.API_COORG.sightSeeing();
        call.enqueue(new Callback<SightSeeing>() {
            @Override
            public void onResponse(Call<SightSeeing> call, Response<SightSeeing> response) {
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
            public void onFailure(Call<SightSeeing> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });

    }
}
