package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.Services.CoorgServicesPojo;
import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragmentController {

    private ApiListner listner;

    public HomeFragmentController(ApiListner listner) {
        this.listner = listner;
    }

    public void getHomeIcons() {
        listner.onFetchProgress();
        Call<Home> call = GlobalClass.API_COORG.homeScreen();
        call.enqueue(new Callback<Home>() {
            @Override
            public void onResponse(Call<Home> call, Response<Home> response) {
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
            public void onFailure(Call<Home> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
