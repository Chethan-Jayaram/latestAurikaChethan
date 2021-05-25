package com.mobisprint.aurika.coorg.controller.spa;

import com.mobisprint.aurika.coorg.pojo.spa.Spa;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SpaMenuController {

    private ApiListner listner;

    public SpaMenuController(ApiListner listner) {
        this.listner = listner;
    }

    public void getSpaMenu() {
        listner.onFetchProgress();
        Call<Spa> call = GlobalClass.API_COORG.spaSubMenu();
        call.enqueue(new Callback<Spa>() {
            @Override
            public void onResponse(Call<Spa> call, Response<Spa> response) {
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
            public void onFailure(Call<Spa> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }
}
