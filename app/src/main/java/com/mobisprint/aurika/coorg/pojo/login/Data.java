package com.mobisprint.aurika.coorg.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

import java.util.List;

public class Data extends General {


    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("request_id")
    @Expose
    private String request_id;

    @SerializedName("error")
    @Expose
    private List<String> error = null;

    public List<String> getError() {
        return error;
    }

    public void setError(List<String> error) {
        this.error = error;
    }



    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRequest_id() {
        return request_id;
    }

    public void setRequest_id(String request_id) {
        this.request_id = request_id;
    }
}
