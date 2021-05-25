package com.mobisprint.aurika.coorg.pojo.Services;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.General;
import com.mobisprint.aurika.udaipur.pojo.GeneralPojo;

import java.util.List;

public class CoorgServicesPojo extends General {


    @SerializedName("data")
    @Expose
    private List<Data> data;

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }
}
