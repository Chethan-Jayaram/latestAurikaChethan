package com.mobisprint.aurika.coorg.controller.petservices;

import com.mobisprint.aurika.coorg.pojo.petservices.PetServices;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PetFriendlyController {

    private ApiListner listner;

    public PetFriendlyController(ApiListner listner) {
        this.listner = listner;
    }


    public void getServices() {

        listner.onFetchProgress();
        Call<PetServices> call = GlobalClass.API_COORG.petFriendly();
        call.enqueue(new Callback<PetServices>() {
            @Override
            public void onResponse(Call<PetServices> call, Response<PetServices> response) {
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
            public void onFailure(Call<PetServices> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });

    }
}
