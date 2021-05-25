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
                Log.d("fetch",response.toString());
                if (response.isSuccessful()){
                    Log.d("fetch completed2",response.toString());
                    if (response.body().getStatus()){

                        Log.d("fetch completed3",response.toString());
                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError(response.body().getMessage());
                    }
                }else {
                    listner.onFetchError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Dining> call, Throwable error) {

                listner.onFetchError(error.getMessage());

            }
        });

    }
}
