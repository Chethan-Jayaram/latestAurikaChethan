package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.experiences.Experiences;
import com.mobisprint.aurika.coorg.pojo.sightseeing.SightSeeing;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExclusiveExperiencesController {
    private ApiListner listner;

    public ExclusiveExperiencesController(ApiListner listner) {
        this.listner = listner;
    }

    public void getExperiencesMenu() {
        listner.onFetchProgress();

        Call<Experiences> call = GlobalClass.API_COORG.experiencesMenu();

        call.enqueue(new Callback<Experiences>() {
            @Override
            public void onResponse(Call<Experiences> call, Response<Experiences> response) {
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
            public void onFailure(Call<Experiences> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });
    }
}
