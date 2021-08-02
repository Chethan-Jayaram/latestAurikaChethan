package com.mobisprint.aurika.coorg.pojo.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.General;

import java.util.List;

public class Login extends General {

    @SerializedName("data")
    @Expose
    private Data data;

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

}
