package com.mobisprint.aurika.coorg.controller;

import android.util.Log;

import com.mobisprint.aurika.coorg.modle.DiningModle;
import com.mobisprint.aurika.coorg.modle.LaundryModle;
import com.mobisprint.aurika.coorg.modle.PetServicesModle;
import com.mobisprint.aurika.coorg.modle.ServiceModle;
import com.mobisprint.aurika.coorg.modle.SleepWellModle;
import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BottomDailogController {
    private ApiListner listner;
    public BottomDailogController(ApiListner listner) {
        this.listner = listner;
    }


    public void ticketingCreation(ServiceModle serviceModle) {
        Call<General> call = GlobalClass.API_COORG.ticketing(serviceModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }


    public void ticketingCreation(SleepWellModle sleepWellModle) {
        Call<General> call = GlobalClass.API_COORG.sleepwellTicketing(sleepWellModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }

    public void ticketingCreation(LaundryModle laundryModle) {
        Call<General> call = GlobalClass.API_COORG.laundryTicketing(laundryModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }


    public void k9TicketCreation(PetServicesModle petServicesModle) {
        Call<General> call = GlobalClass.API_COORG.k9Ticketing(petServicesModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }


    public void diningTicketCreation(DiningModle diningModle) {
        Call<General> call = GlobalClass.API_COORG.diningTicketing(diningModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());

            }
        });
    }

    public void wineDineTicket(TicketModle ticketModle) {
        Call<General> call = GlobalClass.API_COORG.wineDineTicket(ticketModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {

                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });
    }


    public void generalTicket(TicketModle ticketModle) {
        Call<General> call = GlobalClass.API_COORG.generalTickets(ticketModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {

                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });
    }


}
