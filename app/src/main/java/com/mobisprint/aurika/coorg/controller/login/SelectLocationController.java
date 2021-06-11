package com.mobisprint.aurika.coorg.controller.login;

import com.mobisprint.aurika.coorg.pojo.location.SelectLocation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SelectLocationController {


    private ApiListner listner;

    public SelectLocationController(ApiListner listner) {
        this.listner = listner;
    }

    public void getLocations() {
        listner.onFetchProgress();
        Call<SelectLocation> call = GlobalClass.API_COORG.getLocation();
        call.enqueue(new Callback<SelectLocation>() {
            @Override
            public void onResponse(Call<SelectLocation> call, Response<SelectLocation> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                        listner.onFetchComplete(response);
                }else {
                    listner.onFetchError(response.body().getMessage());
                }
            }
            @Override
            public void onFailure(Call<SelectLocation> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }

}
