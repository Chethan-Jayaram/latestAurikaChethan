package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreation;
import com.mobisprint.aurika.coorg.pojo.recreational.Recreational;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecreationalFacilitiesController {

    private ApiListner listner;

    public RecreationalFacilitiesController(ApiListner listner) {
        this.listner = listner;
    }

    public void getRecreationalFacilities() {

        listner.onFetchProgress();
        Call<Recreational> call = GlobalClass.API_COORG.recreationalFacilities();

        call.enqueue(new Callback<Recreational>() {
            @Override
            public void onResponse(Call<Recreational> call, Response<Recreational> response) {
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
            public void onFailure(Call<Recreational> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });

    }
}
