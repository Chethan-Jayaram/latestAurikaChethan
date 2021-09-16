package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.dining.Dining;
import com.mobisprint.aurika.coorg.pojo.guestbooking.GuestBooking;
import com.mobisprint.aurika.coorg.pojo.guestfoilos.GuestFoilos;
import com.mobisprint.aurika.coorg.pojo.payment.GenerateOrderId;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyStayController {

    private ApiListner listner;

    public MyStayController(ApiListner listner) {
        this.listner = listner;
    }

    public void getGuestBooking(String user_token) {
        listner.onFetchProgress();
        HashMap map=new HashMap<>();
        map.put("token",user_token);
        Call<GuestBooking> call = GlobalClass.API_COORG.guestBooking(map);
        call.enqueue(new Callback<GuestBooking>() {
            @Override
            public void onResponse(Call<GuestBooking> call, Response<GuestBooking> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<GuestBooking> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }

    public void getGuestFoilos(String bookingNumber) {
        Call<GuestFoilos> call = GlobalClass.API_COORG.getGuestFoilos(bookingNumber);
        call.enqueue(new Callback<GuestFoilos>() {
            @Override
            public void onResponse(Call<GuestFoilos> call, Response<GuestFoilos> response) {
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
            public void onFailure(Call<GuestFoilos> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
