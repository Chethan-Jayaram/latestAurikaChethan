package com.mobisprint.aurika.helper;

import retrofit2.Response;

public interface ApiListner {

    void onFetchProgress();

    <ResponseType>  void onFetchComplete(Response<ResponseType> response);


    void onFetchError(String error);

}


