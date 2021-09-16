package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.modle.TicketModle;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WineAndDineBookTableController {

    private ApiListner listner;
    public WineAndDineBookTableController(ApiListner listner) {
        this.listner = listner;
    }

    public void generalTicket(TicketModle ticketModle) {
        listner.onFetchProgress();
        Call<General> call = GlobalClass.API_COORG.generalTickets(ticketModle);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {
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
            public void onFailure(Call<General> call, Throwable error) {

                listner.onFetchError(error.getMessage());
            }
        });
    }

}
