package com.mobisprint.aurika.coorg.controller;

import android.graphics.Bitmap;

import com.mobisprint.aurika.coorg.modle.ProfileModle;
import com.mobisprint.aurika.coorg.pojo.profile.Profile;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.helper.ProfileApiListener;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProfileController {

    private ProfileApiListener listner;

    public ProfileController(ProfileApiListener listner) {
        this.listner = listner;
    }

    public void getProfile() {
        listner.onFetchProgress();
        Call<Profile> call = GlobalClass.API_COORG.getProfile(GlobalClass.guest_id);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        listner.onFetchComplete(response,"getProfile");
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }


    public void updateProfile(ProfileModle profileModle) {
        listner.onFetchProgress();
        Call<Profile> call = GlobalClass.API_COORG.updateProfile(GlobalClass.guest_id,profileModle);
        call.enqueue(new Callback<Profile>() {
            @Override
            public void onResponse(Call<Profile> call, Response<Profile> response) {
                if (response.isSuccessful()){
                    if (response.body().getStatus()){

                        listner.onFetchComplete(response,"updateProfile");
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }else {
                    listner.onFetchError(response.message());
                }
            }

            @Override
            public void onFailure(Call<Profile> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }


    /*public void updateProfile(String toString, String toString1, String toString2, String toString3, String toString4, String toString5, String toString6, String bitMapToString) {
        listner.onFetchProgress();
        Call<Profile> call = GlobalClass.API_COORG.updateProfile();
    }*/
}
