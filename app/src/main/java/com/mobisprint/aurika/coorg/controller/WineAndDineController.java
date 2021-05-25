package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.recreational.Recreational;
import com.mobisprint.aurika.coorg.pojo.winedine.WineAndDine;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WineAndDineController {

    private ApiListner listner;

    public WineAndDineController(ApiListner listner) {
        this.listner = listner;
    }

    public void getWineAndDineMenu() {
        listner.onFetchProgress();
        Call<WineAndDine> call = GlobalClass.API_COORG.wineAndDineMenu();
        call.enqueue(new Callback<WineAndDine>() {
            @Override
            public void onResponse(Call<WineAndDine> call, Response<WineAndDine> response) {
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
            public void onFailure(Call<WineAndDine> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });

    }
}
