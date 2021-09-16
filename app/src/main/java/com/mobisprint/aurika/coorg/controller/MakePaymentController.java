package com.mobisprint.aurika.coorg.controller;

import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.coorg.pojo.payment.GenerateOrderId;
import com.mobisprint.aurika.coorg.pojo.ticketing.Ticket;
import com.mobisprint.aurika.helper.ApiListner;
import com.mobisprint.aurika.helper.GlobalClass;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static org.bouncycastle.crypto.tls.MACAlgorithm.hmac_sha256;

public class MakePaymentController {

    private ApiListner listner;
    public MakePaymentController(ApiListner listner) {
        this.listner = listner;
    }

    public void generateOrderId(String amount) {
        HashMap map=new HashMap<>();
        map.put("token",GlobalClass.user_token);
        map.put("order_currency","INR");
        map.put("order_amount",Double.parseDouble(amount)*100);
        Call<GenerateOrderId> call = GlobalClass.API_COORG.generateID(map);
        call.enqueue(new Callback<GenerateOrderId>() {
            @Override
            public void onResponse(Call<GenerateOrderId> call, Response<GenerateOrderId> response) {
                if (response.isSuccessful() && response.body().getStatus()){
                    listner.onFetchComplete(response);
                }else {
                    listner.onFetchError("Something went wrong, please try again later");
                }
            }

            @Override
            public void onFailure(Call<GenerateOrderId> call, Throwable error) {
            listner.onFetchError(error.getMessage());
            }
        });
    }

    public void paymentSuccess(String signature, String orderId, String paymentId, String order_reciept, String folio_id, String amount) {
        HashMap map=new HashMap<>();

        map.put("token",GlobalClass.user_token);
        map.put("razorpay_payment_id",paymentId);
        map.put("razorpay_order_id",orderId);
        map.put("razorpay_signature",signature);
        map.put("folioID",folio_id);
        map.put("order_receipt",order_reciept);
        map.put("order_amount",amount);
        Call<General> call = GlobalClass.API_COORG.verifySignature(map);
        call.enqueue(new Callback<General>() {
            @Override
            public void onResponse(Call<General> call, Response<General> response) {

                try{
                    if (response.isSuccessful() && response.body().getStatus()){
                        listner.onFetchComplete(response);
                    }else {
                        listner.onFetchError("Something went wrong, please try again later");
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }

            @Override
            public void onFailure(Call<General> call, Throwable error) {
                listner.onFetchError(error.getMessage());
            }
        });
    }
}
