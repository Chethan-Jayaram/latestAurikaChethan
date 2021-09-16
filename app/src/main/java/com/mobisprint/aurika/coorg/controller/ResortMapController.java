package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.resortmap.ResortMap;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ResortMapController {

    private ApiListner listner;

    public ResortMapController(ApiListner listner) {
        this.listner = listner;
    }

    public void getData() {
        listner.onFetchProgress();

        Call<ResortMap> call = GlobalClass.API_COORG.resortMap();
        call.enqueue(new Callback<ResortMap>() {
            @Override
            public void onResponse(Call<ResortMap> call, Response<ResortMap> response) {
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
            public void onFailure(Call<ResortMap> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });
    }
}
