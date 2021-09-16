package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.navigation.Navigation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeActivityController {

    private ApiListner listner;

    public HomeActivityController(ApiListner listner) {
        this.listner = listner;
    }

    public void getNavigationMenu() {
        listner.onFetchProgress();
        Call<Navigation> call = GlobalClass.API_COORG.navigationBar();
        call.enqueue(new Callback<Navigation>() {
            @Override
            public void onResponse(Call<Navigation> call, Response<Navigation> response) {
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
            public void onFailure(Call<Navigation> call, Throwable error) {

                listner.onFetchError(error.getMessage());

            }
        });
    }
}
