package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.home.Home;
import com.mobisprint.aurika.coorg.pojo.reservation.GuestReservation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuestReservationController {
    private ApiListner listner;

    public GuestReservationController(ApiListner listner) {
        this.listner = listner;
    }

    public void checkReservation(String user_token) {
        HashMap<String, String> map=new HashMap<>();
        map.put("token",user_token);
        Call<GuestReservation> call = GlobalClass.API_COORG.reservation(map);
        call.enqueue(new Callback<GuestReservation>() {
            @Override
            public void onResponse(Call<GuestReservation> call, Response<GuestReservation> response) {
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
            public void onFailure(Call<GuestReservation> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
