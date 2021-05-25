
package com.mobisprint.aurika.coorg.pojo.petservices;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.General;

public class PetServices extends General {

    @SerializedName("data")
    @Expose
    private List<K9Data> data = null;

    public List<K9Data> getData() {
        return data;
    }

    public void setData(List<K9Data> data) {
        this.data = data;
    }

}
