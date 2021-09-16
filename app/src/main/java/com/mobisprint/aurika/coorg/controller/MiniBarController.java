package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MiniBarController {

    private ApiListner listner;

    public MiniBarController(ApiListner listner) {
        this.listner = listner;
    }

    public void getMiniBarContent() {

        listner.onFetchProgress();
        Call<CoorgServicesPojo> call = GlobalClass.API_COORG.miniBar();
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
