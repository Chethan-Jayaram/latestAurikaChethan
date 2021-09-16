package com.mobisprint.aurika.coorg.controller.ird;

import android.util.Log;

import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InRoomDiningMenuContoller {
    private ApiListner listner;

    public InRoomDiningMenuContoller(ApiListner listner) {
        this.listner = listner;
    }

    public void getDiningMenu(Integer category_id) {

        listner.onFetchProgress();

        Call<Dining> call = GlobalClass.API_COORG.diningMenuList(category_id);
        call.enqueue(new Callback<Dining>() {
            @Override
            public void onResponse(Call<Dining> call, Response<Dining> response) {
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
            public void onFailure(Call<Dining> call, Throwable error) {

                listner.onFetchError(error.getMessage());

            }
        });

    }
}
