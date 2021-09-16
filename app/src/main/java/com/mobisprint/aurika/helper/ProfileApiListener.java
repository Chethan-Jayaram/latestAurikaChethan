package com.mobisprint.aurika.helper;

import retrofit2.Response;

public interface ProfileApiListener {

    void onFetchProgress();

    <ResponseType>  void onFetchComplete(Response<ResponseType> response,String responseType);


    void onFetchError(String error);
}
