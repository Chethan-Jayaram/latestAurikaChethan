package com.mobisprint.aurika.services;

import com.mobisprint.aurika.pojo.doorunlock.OtpAutentication;
import com.mobisprint.aurika.pojo.doorunlock.TokenAutentication;
import com.mobisprint.aurika.pojo.notification.PushNotificationResponse;
import com.mobisprint.aurika.pojo.testing.Testing;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIMethods {

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-udaipur"})
    @POST("authenticate-mobile")
    Call<TokenAutentication> userAuthentication(@Body Map map);

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-udaipur"})
    @POST("authenticate/otp/")
    Call<OtpAutentication> otpAuthentication(@Body Map map);

    @GET("/exone/AurikaAppData.json")
    Call<Testing> getData();

    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-udaipur"})
    @POST("guest-resend-otp/")
    Call<TokenAutentication> resendotp(@Body Map map);


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-udaipur"})
    @POST("mobile-key-generation/")
    Call<TokenAutentication> mobilekeyapi(@Body Map map);


    @Headers({"Content-Type:application/json", "organization-key:80b2f8f25c554f9705bb216c8128ba4f05bb0cfd", "location:aurika-udaipur"})
    @GET("guest-push-notifications/")
    Call<PushNotificationResponse> pushNotificationApi(@HeaderMap Map<String, String> token);






}
