package com.mobisprint.aurika.coorg.controller;

import android.util.Log;

import com.mobisprint.aurika.coorg.pojo.invitationcode.InvitationCode;
import com.mobisprint.aurika.coorg.pojo.mobilekey.MobileKey;
import com.mobisprint.aurika.coorg.pojo.reservation.GuestReservation;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoorUnlockController {
    private ApiListner listner;

    public DoorUnlockController(ApiListner listner) {
        this.listner = listner;
    }

    public void getDetails(String user_token) {
        HashMap<String, String> map=new HashMap<>();
        map.put("token",user_token);
        Call<GuestReservation> call = GlobalClass.API_COORG.guest(map);
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

    public void getInvitationCode(String roomNumber) {
        Map map = new HashMap();
        map.put("room_number", roomNumber);
        map.put("token", GlobalClass.user_token);
        Call<InvitationCode> call = GlobalClass.API_COORG.getInvitaionCode(map);
        call.enqueue(new Callback<InvitationCode>() {
            @Override
            public void onResponse(Call<InvitationCode> call, Response<InvitationCode> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError(response.message());
                }
            }

            @Override
            public void onFailure(Call<InvitationCode> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }

    public void mobilekeyapi(String keytoken) {
        Map map = new HashMap();
        Log.d("token",keytoken);
        map.put("token", keytoken);
        Call<MobileKey> call = GlobalClass.API_COORG.mobilekeyapi(map);
        call.enqueue(new Callback<MobileKey>() {
            @Override
            public void onResponse(Call<MobileKey> call, Response<MobileKey> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError(response.message());
                }
            }

            @Override
            public void onFailure(Call<MobileKey> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }
}
