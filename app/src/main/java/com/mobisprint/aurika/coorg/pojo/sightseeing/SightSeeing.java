
package com.mobisprint.aurika.coorg.pojo.sightseeing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.mobisprint.aurika.coorg.pojo.General;

public class SightSeeing extends General {

    @SerializedName("data")
    @Expose
    private List<Data> data = null;



    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

}
