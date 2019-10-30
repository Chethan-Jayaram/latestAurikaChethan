package com.mobisprint.aurika.retrofit;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

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

public class Client2ServiceGenerator {

    public static Retrofit retrofit = null;

    public static final String ROOT_URL = "https://demo.mobisprint.com/";
    public static OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

    public static Retrofit getUrlClient() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(60, TimeUnit.SECONDS)//10
                .writeTimeout(60, TimeUnit.SECONDS)//10
                .readTimeout(60, TimeUnit.SECONDS)
                .build();
        httpClient.connectTimeout(120, TimeUnit.SECONDS).writeTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS);
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        // add logging as last interceptor
        httpClient.addInterceptor(logging);  // <-- this is the important line!
        Gson gson = new GsonBuilder().create();
        //.addConverterFactory(new NullOnEmptyConverterFactory())
        retrofit = new Retrofit.Builder()
                .baseUrl(ROOT_URL)
                .addConverterFactory(new NullOnEmptyConverterFactory())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addConverterFactory(new ToStringConverterFactory())
                .client(httpClient.build())
                .build();
        return retrofit;
    }

    public static class NullOnEmptyConverterFactory extends Converter.Factory {

        @Override
        public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
            final Converter<ResponseBody, ?> delegate = retrofit.nextResponseBodyConverter(this, type, annotations);
            return new Converter<ResponseBody, Object>() {
                @Override
                public Object convert(ResponseBody body) throws IOException {
                    // Utility.Log("VMA","Response  Body "+body.contentLength());
                    if (body.contentLength() == 0) return null;
                    return delegate.convert(body);
                }
            };
        }
    }


}
