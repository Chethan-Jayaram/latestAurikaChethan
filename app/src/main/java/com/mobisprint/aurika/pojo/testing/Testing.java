
package com.mobisprint.aurika.pojo.testing;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Testing {

    @SerializedName("AppData")
    @Expose
    private List<AppDatum> appData = null;

    public List<AppDatum> getAppData() {
        return appData;
    }

    public void setAppData(List<AppDatum> appData) {
        this.appData = appData;
    }

}
