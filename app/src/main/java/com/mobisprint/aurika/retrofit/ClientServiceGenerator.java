package com.mobisprint.aurika.retrofit;

import android.widget.Switch;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mobisprint.aurika.helper.GlobalClass;
import com.mobisprint.aurika.notification.UnlockNotification;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ClientServiceGenerator {


    // private static final String ROOT_URL = "https://dev.mobisprint.com/api/v1/";
    private static final String ROOT_URL_UDAIPUR_DOOR_UNLOCK = "https://exoneapi.aurikahotels.com/api/v1/";

    private static final String ROOT_URL_UDAIPUR_APP_CONTENT = "https://demo.mobisprint.com/";


    private static final String ROOT_URL_COORG = "https://dev.mobisprint.com:8006/api/v1/";


    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient httpClient = new OkHttpClient.Builder()
            .addInterceptor(logging).connectTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS).build();
    ;


    private static Retrofit UDAIPUR_UNLOCK_BUILDER =
            new Retrofit.Builder()
                    .baseUrl(ROOT_URL_UDAIPUR_DOOR_UNLOCK)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new ToStringConverterFactory())
                    .client(httpClient).build();

    private static Retrofit UDAIPUR_APP_CONTENT_BUILDER =
            new Retrofit.Builder()
                    .baseUrl(ROOT_URL_UDAIPUR_APP_CONTENT)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new ToStringConverterFactory())
                    .client(httpClient).build();


    private static Retrofit COORG_BUILDER =
            new Retrofit.Builder()
                    .baseUrl(ROOT_URL_COORG)
                    .addConverterFactory(new NullOnEmptyConverterFactory())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addConverterFactory(new ToStringConverterFactory())
                    .client(httpClient).build();


    //   private static Retrofit retrofit = builder.build();
    public static Retrofit getUrlClient(String location) {



        switch (location) {
            case GlobalClass.UDAIPUR_DOOR_UNLOCK:
                return UDAIPUR_UNLOCK_BUILDER;
            case GlobalClass.UDAIPUR:
                return UDAIPUR_APP_CONTENT_BUILDER;
            case GlobalClass.COORG:
                return COORG_BUILDER;
            default:
               return UDAIPUR_APP_CONTENT_BUILDER;
        }

    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return (Converter<ResponseBody, Object>) body -> {
                // Utility.Log("VMA","Response  Body "+body.contentLength());
                if (body.contentLength() == 0) return null;
                return delegate.convert(body);
            };
        }
    }
}
